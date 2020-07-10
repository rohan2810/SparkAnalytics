import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.datastax.bdp.graph.spark.graphframe.DseGraphFrameBuilder;
import com.typesafe.config.ConfigFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class UpdateJob {
    private GraphTraversalSource g;
    private DseGraphFrame gf;
    private SparkSession sparkSession;


    @BeforeTest
    public void initialize() {
        Cluster cluster = Cluster.build().addContactPoint("localhost").port(8182).create();
        g = AnonymousTraversalSource.traversal().withRemote(DriverRemoteConnection.using(cluster, "iap2.g"));
        SparkConf conf = new SparkConf();
        ConfigFactory.load().entrySet().forEach(stringConfigValueEntry -> {
            conf.set(stringConfigValueEntry.getKey(), stringConfigValueEntry.getValue().unwrapped().toString());
        });
        sparkSession = SparkSession.builder().config(conf).master("local[1]").appName("test").getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        gf = DseGraphFrameBuilder.dseGraph("iap2", sparkSession);
    }

    @Test
    public void testUpdate() {
        IAPEntityDataLookup k = new IAPEntityDataLookup(g, sparkSession);
        Dataset<Row> publishedEntity = k.getActiveEntities("tenant1", "ad", "inetorgperson", null);

        Dataset<Row> probableEntity = sparkSession.read()
                .format("jdbc")
                .option("url", "jdbc:postgresql://localhost:5432/iagdb")
                .option("dbtable", "iap_probable_entity")
                .option("user", "iaguser")
                .option("password", "password")
                .load();

        Dataset<Row> merged = publishedEntity.join(probableEntity,
                (publishedEntity.col("entityGlobalId").
                        eqNullSafe
                                (probableEntity.col("gid"))
                )).select(publishedEntity.col("tenantId"), publishedEntity.col("appId"), publishedEntity.col("nativeType"), publishedEntity.col("entityGlobalId"), probableEntity.col("changelog"), publishedEntity.col("data")).where(probableEntity.col("changelog").isNotNull());

        Dataset<Row> mapPartition = MapPartition.petitioner(merged);

        MapPartition.updateGraph(sparkSession, mapPartition);


    }
}



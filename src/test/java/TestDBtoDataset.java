import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.io.Reader;

//import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class TestDBtoDataset {
    private GraphTraversalSource g;
    private SparkSession sparkSession;
    private ScriptEngine engine;

    @BeforeTest
    public void initializeConnection() {
        SparkConf conf = new SparkConf().setAppName("Spark MultipleContest Test").setMaster("local[2]");
        this.sparkSession = SparkSession.builder().config(conf).getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
//        Graph graph = TinkerGraph.open();
//        this.g = graph.traversal();
        ScriptEngineManager factory = new ScriptEngineManager();
        this.engine = factory.getEngineByName("groovy");
        this.engine.put("g", g);
    }

    @Test
    public void basicTest() throws Exception {
        Reader reader = new FileReader("src/test/resources/data/onlyEntity.groovy");
        engine.eval(reader);
        IAPEntityDataLookup k = new IAPEntityDataLookup(g, sparkSession);
        Dataset<Row> publishedEntity = k.getActiveEntities("tenant1", "ad", "inetorgperson", null);

        Dataset<Row> probableEntity = sparkSession.read()
                .format("jdbc")
                .option("url", "jdbc:postgresql://localhost:5432/iagdb")
                .option("dbtable", "iap_probable_entity")
                .option("user", "iaguser")
                .option("password", "password")
                .load();


//        probableEntity.write().json("src/test/resources/data/dbJson");


        Dataset<Row> merged = publishedEntity.join(probableEntity,
                (publishedEntity.col("entityGlobalId").
                        eqNullSafe
                                (probableEntity.col("gid"))
                )).select(publishedEntity.col("tenantId"), publishedEntity.col("appId"), publishedEntity.col("nativeType"), publishedEntity.col("entityGlobalId"), probableEntity.col("changelog"), publishedEntity.col("data")).where(probableEntity.col("changelog").isNotNull());

        Dataset<Row> mapPartition = MapPartition.petitioner(merged);


        mapPartition.show();

//        DseGraphFrame gf = DseGraphFrameBuilder.dseGraph("iap", sparkSession);
//        MapPartition mapPartition1 = new MapPartition(gf, mapPartition);
//        mapPartition1.updateGraph();

    }

}

import com.datastax.dse.driver.api.core.graph.DseGraph;
import com.datastax.oss.driver.api.core.CqlSession;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntityMapper {
    @Test
    public void test() {
        Logger logger = LoggerFactory.getLogger(TestEntityMapper.class);
        logger.info("hello there");

        CqlSession session = CqlSession.builder().build();
        GraphTraversalSource g = AnonymousTraversalSource.traversal().withRemote(DseGraph.remoteConnectionBuilder(session).build());
        SparkConf conf;
        conf = new SparkConf();
        conf.setAppName("Spark MultipleContest Test");
        conf.setMaster("local");
        SparkSession sparkSession = SparkSession.builder().config(conf).getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        IAPEntityDataLookup k = new IAPEntityDataLookup(g, sparkSession);
        List<?> mapList = k.getData("tenant1", "ad", "inetorgperson");
        Map<String, Object> map = (Map<String, Object>) mapList.get(0);
        EntityMapper em = new EntityMapper();
        Dataset<Row> ds = k.getActiveEntities("tenant1", "ad", "inetorgperson", null);
        ds.show();
//        StructType f = EntityMapper.schema;
//        System.out.println(Arrays.toString(f.fields()));
        try {
            g.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        sparkSession.close();


    }

    @Test
    public void basicFlow() {
        Map graphResult = new HashMap<String, Object>();

    }
}

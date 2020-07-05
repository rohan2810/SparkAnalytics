import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.io.Reader;

public class TestEntityMapper {
    private final GraphTraversalSource g;
    private final SparkSession sparkSession;
    private final ScriptEngine engine;


    public TestEntityMapper() {
        Graph graph = TinkerGraph.open();
        this.g = graph.traversal();
        SparkConf conf = new SparkConf().setAppName("Spark MultipleContest Test").setMaster("local[2]");
        this.sparkSession = SparkSession.builder().config(conf).getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        ScriptEngineManager factory = new ScriptEngineManager();
        this.engine = factory.getEngineByName("groovy");
        this.engine.put("g", g);


    }


    @Test
    public void testCompleteFlow() throws Exception {
        Logger logger = LoggerFactory.getLogger(TestEntityMapper.class);
        Reader reader = new FileReader("src/main/resources/data/completeGraph.groovy");
        g.V().drop();
        engine.eval(reader);
        IAPEntityDataLookup k = new IAPEntityDataLookup(g, sparkSession);
        Dataset<Row> ds = k.getActiveEntities("tenant1", "ad", "inetorgperson", null);
        ds.show();
        try {
            g.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sparkSession.close();


    }


}

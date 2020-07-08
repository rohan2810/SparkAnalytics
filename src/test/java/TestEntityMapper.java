import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
//import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.io.Reader;

public class TestEntityMapper {
    private GraphTraversalSource g;
    private SparkSession sparkSession;
    private ScriptEngine engine;

    @BeforeSuite
    public void sparkInitialize() {
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
    public void testCompleteFlow() throws Exception {
        Reader reader = new FileReader("src/test/resources/data/completeGraph.groovy");
        engine.eval(reader);
        IAPEntityDataLookup k = new IAPEntityDataLookup(g, sparkSession);
        Dataset<Row> ds = k.getActiveEntities("tenant1", "ad", "inetorgperson", null);
        ds.show();
        g.V().drop().tryNext();
    }

    @Test
    public void noEntityFlow() throws Exception {
        Reader reader = new FileReader("src/test/resources/data/completeGraph.groovy");
        engine.eval(reader);
        IAPEntityDataLookup k = new IAPEntityDataLookup(g, sparkSession);
        Dataset<Row> ds = k.getActiveEntities("tenant1", "ad", "inetorgperson1", null);
        ds.show();
        g.V().drop().tryNext();

    }


    @AfterSuite
    public void closeResources() {
        try {
            g.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sparkSession.close();
    }

}

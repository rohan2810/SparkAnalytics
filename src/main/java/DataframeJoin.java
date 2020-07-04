import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.elementMap;

public class DataframeJoin {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf();
        conf.setAppName("Spark MultipleContest Test");
        conf.setMaster("local");

        SparkSession sparkSession = SparkSession.builder().config(conf).getOrCreate();
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());


        Cluster cluster = Cluster.build().addContactPoint("localhost").port(8182).create();
        GraphTraversalSource g = traversal().withRemote(DriverRemoteConnection.using(cluster, "withComment.g"));
        List<Map<String, Object>> list = g.V().hasLabel("Ask").has("status", "closed").as("a").inE("For").as("b").outV().hasLabel("Contribution").as("c")
                .select("a").inE("For").as("d").outV().hasLabel("Authorization").as("e")
                .select("a", "e").by(elementMap()).toList();

        System.out.println(list.size());
//        JavaRDD<Map<String, Object>> javaRDD = javaSparkContext.parallelize(list);
//        JavaRDD<Row> rddCommentsRow = javaRDD.map(mapper());
//        JavaRDD<Row> rddCommentsRow1 = javaRDD.map(mapper1());
//        JavaRDD<Row> rddCommentsRow2 = javaRDD.map(mapper2());
//        JavaRDD<Row> rddComments3 = rddCommentsRow.union(rddCommentsRow1);
//        Dataset<Row> vertex = sparkSession.createDataFrame(rddComments3, schema()).distinct();
//        Dataset<Row> edge = sparkSession.createDataFrame(rddCommentsRow2, schema1()).distinct();
////
//        GraphFrame gf = GraphFrame.apply(vertex, edge);
////        gf.vertices().show();
////        gf.edges().show();
//////        gf.filterEdges("`~label` = 'For'").edges().show();
//        gf.find("(c)-[e]->(a)").filter("e.label = 'For'").filter("c.label = 'Contribution'").
//                filter("a.label = 'Ask'").show();

    }

    private static Function<Map<String, Object>, Row> mapper() {
        return new Function<Map<String, Object>, Row>() {
            @Override
            public Row call(Map<String, Object> v1) {
                LinkedHashMap<String, Object> a = (LinkedHashMap<String, Object>) v1.get("a");
                String id = (String) a.get(a.keySet().toArray()[0]);
                String label = (String) a.get(a.keySet().toArray()[1]);
                String tenantId = (String) a.get("tenantId");
                String context = (String) a.get("context");


                return RowFactory.create(id, label, tenantId, context);
            }
        };
    }

    private static Function<Map<String, Object>, Row> mapper1() {
        return new Function<Map<String, Object>, Row>() {
            @Override
            public Row call(Map<String, Object> v1) {
                LinkedHashMap<String, Object> c = (LinkedHashMap<String, Object>) v1.get("c");
                String id = (String) c.get(c.keySet().toArray()[0]);
                String label = (String) c.get(c.keySet().toArray()[1]);
                String tenantId = (String) c.get("tenantId");
                String gid = (String) c.get("gid");


                return RowFactory.create(id, label, tenantId, gid);
            }
        };
    }


    private static Function<Map<String, Object>, Row> mapper2() {
        return new Function<Map<String, Object>, Row>() {
            @Override
            public Row call(Map<String, Object> v1) {
                LinkedHashMap<String, Object> contributionHashMap = (LinkedHashMap<String, Object>) v1.get("b");
                LinkedHashMap<String, Object> in = (LinkedHashMap<String, Object>) contributionHashMap.get(contributionHashMap.keySet().toArray()[2]);
                LinkedHashMap<String, Object> out = (LinkedHashMap<String, Object>) contributionHashMap.get(contributionHashMap.keySet().toArray()[3]);
                String edge_in = (String) in.get(in.keySet().toArray()[0]);
                String edge_out = (String) out.get(out.keySet().toArray()[0]);
                String label = (String) contributionHashMap.get(contributionHashMap.keySet().toArray()[1]);

                return RowFactory.create(edge_out, edge_in, label);
            }
        };
    }

    private static StructType schema() {

        StructField[] structFields = new StructField[]{
                new StructField("id", DataTypes.StringType, true, Metadata.empty()),
                new StructField("label", DataTypes.StringType, true, Metadata.empty()),
                new StructField("tenantId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("context", DataTypes.StringType, true, Metadata.empty()),
        };
        return new StructType(structFields);
    }

    private static StructType schema2() {

        StructField[] structFields = new StructField[]{
                new StructField("id", DataTypes.StringType, true, Metadata.empty()),
                new StructField("label", DataTypes.StringType, true, Metadata.empty()),
                new StructField("tenantId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("gid", DataTypes.StringType, true, Metadata.empty()),
        };
        return new StructType(structFields);
    }

    private static StructType schema1() {

        StructField[] structFields = new StructField[]{
                new StructField("src", DataTypes.StringType, true, Metadata.empty()),
                new StructField("dst", DataTypes.StringType, true, Metadata.empty()),
                new StructField("label", DataTypes.StringType, true, Metadata.empty()),
        };
        return new StructType(structFields);
    }

}
/*
testing
                for (Map<String, Object> m:list){
            LinkedHashMap<String, Object> a = (LinkedHashMap<String, Object>) m.get("c");
                    System.out.println(a);
//            Map.Entry<String, Object> entry = a.entrySet().stream().findFirst().get();
//           c =  a.entrySet().stream().findFirst().get();
//            System.out.println(entry.getValue());
//            System.out.println(a.get("id"));
//            System.out.println(a.get("gid"));
//            System.out.println(a.containsKey("tenantId"));
//            System.out.println(a);
        }

 */
/*
OLD
//        Dataset<Row> df2 = df.join(df1,
//                (
//                        (df.col("col1").eqNullSafe(df1.col("col5"))
//                                .and(
//                                        (df.col("col4").eqNullSafe(df1.col("col8")))
//                                ).or(
//                                        df1.col("col6").eqNullSafe(lit("test")))
//                                )
//                        ),"inner");
//        Dataset<Row> df2 = df.join(df1, ((df.col("col1").eqNullSafe(lit("test"))
//                .and(
//                        (df.col("col4").eqNullSafe(df1.col("col8")))
//                )).or(df.col("col2").eqNullSafe(df1.col("col6")))));
//        df2.show();
 */
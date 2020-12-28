package DatabaseConnection;

import com.typesafe.config.ConfigFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.jdbc.JdbcDialect;
import org.apache.spark.sql.jdbc.JdbcDialects;
import org.apache.spark.sql.types.DataTypes;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class JdbcConnection {
    private final String url = "jdbc:postgresql://localhost:5432/testdb";
    private final String user = "rohansurana";
    private final String password = "";
    SparkSession sparkSession;
    Dataset<Row> jdbcDF;

    @BeforeTest
    public void init() {
        SparkConf conf = new SparkConf();
        ConfigFactory.load().entrySet().forEach(stringConfigValueEntry -> conf.set(stringConfigValueEntry.getKey(), stringConfigValueEntry.getValue().unwrapped().toString()));
        sparkSession = SparkSession.builder().config(conf).master("local[1]").appName("test").getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
    }

    @Test
    public void readValues() {
        jdbcDF = sparkSession.read()
                .format("jdbc")
                .option("url", "jdbc:postgresql://localhost:5432/testdb")
                .option("dbtable", "iap_probable_entity")
                .option("user", "rohansurana")
                .option("password", "")
                .load();

        jdbcDF.printSchema();
        jdbcDF.show();

        System.out.println(jdbcDF.select("alerts").schema());
        System.out.println(jdbcDF.select("alerts").schema());
    }

//    @Test
//    public void writeValuesWithSchema() {
//        List<Row> entities = new ArrayList<>();
//        entities.add(RowFactory.create("1", "p1", "dp1", "open", "status", true, "10234-34923", "344523", "jdoe", "description", "nativeStatus", "signature", "jdoe", "action", "data", "{\"test\":\"value\"}",
//                "{\"test\":\"value\"}", "relationships", "chnageLog", Timestamp.from(Instant.now())));
//        entities.add(RowFactory.create("2", "p2", "dp2", "closed", "status", false, "45rwefe-34923", "edf4545", "kdoe", "description", "nativeStatus", "signature", "kdoe", "action", "data", "{\"test\":\"value\"}",
//                "{\"test\":\"value\"}", "relationships", "chnageLog", Timestamp.from(Instant.now())));
//
//
//        Dataset<Row> df = sparkSession.createDataFrame(entities, this.schema());
//
////        df.show();
//        df.write()
//                .format("jdbc")
//                .option("url", "jdbc:postgresql://localhost:5432/testdb")
//                .option("dbtable", "user_info2")
//                .option("user", "rohansurana")
//                .option("password", "")
////                .mode("ovesrwrite")
//                .save();

//    }

    @Test
    public void insertSQLstatementJSONB() throws SQLException {

//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(url, user, password);
//            System.out.println("Connected to the PostgreSQL server successfully.");
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        assert conn != null;
//        PreparedStatement st = conn.prepareStatement("INSERT INTO testing (ID, NAME , VALUE) VALUES (?, ?, ? ::jsonb)");
//        st.setInt(1, 4);
//        st.setString(2, "title");
//        st.setString(3, "{\"name\":\"James\"}");
//        st.executeUpdate();
//        st.close();


        JdbcDialect f = JdbcDialects.get(url);
        System.out.println(f.getJDBCType(DataTypes.StringType));

    }

}

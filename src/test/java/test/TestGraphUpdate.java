package test;

import com.typesafe.config.ConfigFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import update.impl.DseUpdate;

import java.util.ArrayList;
import java.util.List;

public class TestGraphUpdate {
    private SparkSession sparkSession;
    private DseUpdate dseUpdate;

    @BeforeTest
    public void initialize() {
        SparkConf conf = new SparkConf();
        ConfigFactory.load().entrySet().forEach(stringConfigValueEntry -> {
            conf.set(stringConfigValueEntry.getKey(), stringConfigValueEntry.getValue().unwrapped().toString());
        });
        sparkSession = SparkSession.builder().config(conf).master("local[1]").appName("test").getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        dseUpdate = new DseUpdate(sparkSession, "iap2");

    }

    @Test
    public void testUpdateVertex() {

        List<Row> entities = new ArrayList<>();
        entities.add(RowFactory.create("tenant1", "UnixPlatformPermissionSet", "Platform Permission Set", "81177aa6-97d2-11ea-bb37-0242ac130002", "kdoe"));
        entities.add(RowFactory.create("tenant1", "UnixPlatformPermissionSet", "Platform Permission Set", "81177aa6-97d2-11ea-bb37-0242ac130001", "kdoe1"));

        StructType schema = new StructType(new StructField[]{
                new StructField("tenantId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("appId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("nativeType", DataTypes.StringType, true, Metadata.empty()),
                new StructField("entityGlobalId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("entityKey", DataTypes.StringType, true, Metadata.empty()),

        });


        Dataset<Row> df = sparkSession.createDataFrame(entities, schema);
        dseUpdate.updateVertices("Entity", df);

        System.out.println("vertex update done");
    }

    @Test
    public void testUpdateEdge() {

        List<Row> person = new ArrayList<>();
        person.add(RowFactory.create("tenant1", "jdoe", "rohan", "tenant1", "UnixPlatformPermissionSet", "Platform Permission Set", "jdoe1", "testEntry2"));
        person.add(RowFactory.create("tenant1", "jdoe1", "rohan", "tenant1", "UnixPlatformPermissionSet", "Platform Permission Set", "jdoe", "testEntry2"));


        StructType schema = new StructType(new StructField[]{
                new StructField("Person_tenantId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Person_userId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("displayName", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Account_tenantId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Account_appId", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Account_nativeType", DataTypes.StringType, true, Metadata.empty()),
                new StructField("Account_entityKey", DataTypes.StringType, true, Metadata.empty()),
                new StructField("type", DataTypes.StringType, true, Metadata.empty()),
        }
        );

        Dataset<Row> df = sparkSession.createDataFrame(person, schema);
        dseUpdate.updateEdges("Account", "With_Actor", "Person", df);
        System.out.println("edge update done");


    }

    @AfterTest
    public void closeConnections() {
        sparkSession.close();
    }

}

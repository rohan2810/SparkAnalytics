package FPTree_Spark.mllib

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.ml.fpm.FPGrowth
import org.apache.spark.mllib.fpm.FPTree
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.testng.annotations.Test

class AppAccountsFP {
  val spark: SparkSession = SparkSession
    .builder
    .appName(s"${this.getClass.getSimpleName}")
    .master("local[2]")
    .getOrCreate()
  val sparkContext: SparkContext = spark.sparkContext

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)


  @Test
  def BasicFlow(): Unit = {
    val file = "/Users/rohansurana/Desktop/AppAccounts.csv"
    val data = sparkContext.textFile("/Users/rohansurana/Desktop/AppAccounts.csv")
    val k = new FPTree[Int]

    def user_entitlements_path = file

    spark.sparkContext.setLogLevel("ERROR")
    get_outliers(spark, user_entitlements_path)

  }

  def get_outliers(spark: SparkSession, user_entitlements_path: String): Unit = {

    //this block retrieves and cleans data
    val data = spark.read.format("csv").option("header", "true").load(user_entitlements_path)
    val newData = data.groupBy("UID").agg(collect_set("ENTITLEMENT").as("ENT")).filter(size(col("ENT")) > 0)

    newData.show(5)
    get_frequent_items("d", newData)

    //    val sparse_dataset = data.groupBy("UserId").agg(collect_set("EntitlementId").as("EntitlementId"), first("JobCode").as("JobCode"), first("ManagerId").as("ManagerId"))
    //    val dataset = sparse_dataset.filter(size(col("EntitlementId")) > 0)
    //    // end of block
    //
    //
    //    // obtain the frequent items datasets for each attribute
    //    val frequent_items_by_manager: DataFrame = get_frequent_items("ManagerId", newData)
    //    val frequent_items_by_jobcode: DataFrame = get_frequent_items("JobCode", newData)
    //    //
    //    val frequent_items = frequent_items_by_manager.unionByName(frequent_items_by_jobcode).groupBy("items").agg(sum(col("weighed_freq")) / 3)
    //
    //    println("Frequent items suggested:")
    //    frequent_items.show(false)
  }


  //function to find frequent dataset by each attribute

  def get_frequent_items(attribute: String, dataset: DataFrame): Unit = {

    //    dataset.repartition(col(attribute))
    //        val unique_attributes = dataset.select(col(attribute)).dropDuplicates().rdd.map(r => r(0)).collect()
    //        val frequent_sets_by_attribute = mutable.ArrayBuffer[DataFrame]()
    //   frequent_sets_by_attribute
    //        for (unique_attribute <- unique_attributes) {
    //
    //          val peers_dataset = dataset.filter(col(attribute) === unique_attribute)
    //          peers_dataset
    //          val peers_count = peers_dataset.select("UserId").count()
    //          val fpgrowth = new FPGrowth().setItemsCol("EntitlementId").setMinSupport(0.7)
    //          val model = fpgrowth.fit(peers_dataset)
    //
    //          val weight: Int = {
    //            if (attribute.equals("ManagerId")) 2
    //            else 1
    //          }
    //
    //      val frequent_sets_by_unique_attribute = model.freqItemsets.withColumn("frequency", bround(col("freq") / peers_count * 100)).withColumn("weighed_freq", bround(col("freq") / peers_count * weight * 100)).withColumn("items", sort_array(col("items")))
    //      println("For peers by :" + attribute + " " + unique_attribute)
    //      frequent_sets_by_unique_attribute.show(false)
    //
    //      //join all scores into one df
    //      if (frequent_sets_by_attribute.isEmpty)
    //        frequent_sets_by_attribute.insert(0, frequent_sets_by_unique_attribute)
    //      else
    //        frequent_sets_by_attribute.insert(0, frequent_sets_by_attribute(0).unionByName(frequent_sets_by_unique_attribute))
    //    }
    //    println("For attribute: ", attribute)
    //    frequent_sets_by_attribute(0).groupBy("items").agg(max("weighed_freq").as("weighed_freq")).show(false)
    //
    //    frequent_sets_by_attribute(0).groupBy("items").agg(max("weighed_freq").as("weighed_freq"))
    val unique_attributes = dataset.dropDuplicates().rdd.map(r => r(0)).collect()
    for (_ <- unique_attributes) {
      val fpgrowth = new FPGrowth().setItemsCol("ENT").setMinSupport(0.7)
      val model = fpgrowth.fit(dataset)
      model.freqItemsets.show()
    }
    //for (unique)
    //    val fpgrowth = new FPGrowth().setItemsCol("ENT").setMinSupport(0.5).setMinConfidence(0.6)
    //    //    val encoder = org.apache.spark.sql.Encoders.product[String]
    //    val dataset1 = dataset.filter(col("UID").isNotNull)
    //    val l = fpgrowth.fit(dataset1)
    //    l.freqItemsets.show()
    //    l.associationRules.show()
    //    l.transform(dataset1).show()


  }

}

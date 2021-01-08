package FPTree_Spark.mllib

import org.apache.spark.ml.fpm.FPGrowth
import org.apache.spark.sql.functions.{col, collect_set, size}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object role_miner_closed {
  def main(args: Array[String]): Unit = {
    val start = System.currentTimeMillis()
    val spark: SparkSession = {
      SparkSession
        .builder
        .appName(s"${this.getClass.getSimpleName}")
        .master("local[2]")
        .getOrCreate()
    }

    def user_entitlements_path = "/Users/rohansurana/Desktop/AppAccounts.csv"

    spark.sparkContext.setLogLevel("ERROR")
    get_outliers(spark, user_entitlements_path)
    val end = System.currentTimeMillis()
    print("Time taken in millis: " + (end - start))
  }

  def get_outliers(spark: SparkSession, user_entitlements_path: String): Unit = {

    val user_entitlements = spark.read.format("csv").option("header", "true").load(user_entitlements_path)
    val sparse_dataset = user_entitlements.groupBy("UserId").agg(collect_set("EntitlementId").as("ENT")).filter(size(col("ENT")) > 0)
    get_frequent_items(sparse_dataset)
  }

  def get_frequent_items(dataset: DataFrame): Unit = {
    //  println(s"dataset: ${dataset.select(col("ENT")).show(false)}")
    val fpgrowth = new FPGrowth().setItemsCol("ENT").setMinSupport(0.1).setMinConfidence(0.2)
    val model = fpgrowth.fit(dataset)
    val fq = model.freqItemsets
    fq.show(false)
    val count = fq.select(col("freq")).dropDuplicates().collectAsList()

    val freMap = Map.empty[Long, List[Dataset[Row]]]
    for (i <- 0 until count.size() - 1) {
      val temp = List(fq.select(col("items")).where(fq("freq") === count.get(i).getLong(0)))
      println(s"$i $temp")
      freMap + (count.get(i).getLong(0) -> temp)
    }
    val freMap1 = Map.empty[Long, List[Dataset[Row]]]
    for (i <- 0 until count.size() - 1) {
      val temp = List(fq.select(col("items")).where(fq("freq") <= count.get(i).getLong(0)))
      println(s"$i $temp")
      freMap1 + (count.get(i).getLong(0) -> temp)
    }

  }
}
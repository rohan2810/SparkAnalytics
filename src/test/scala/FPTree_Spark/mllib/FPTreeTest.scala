package FPTree_Spark.mllib

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.fpm.FPGrowth
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.{Encoder, SparkSession}
import org.testng.annotations.Test


class FPTreeTest {
  val spark: SparkSession = SparkSession
    .builder
    .appName(s"${this.getClass.getSimpleName}")
    .master("local[2]")
    .getOrCreate()

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  @Test
  def BasicTest(): Unit = {

    val dataset = spark.createDataset(Seq(
      "toothpaste beer fruits",
      "toothpaste beer banana fruits",
      "toothpaste beer banana")
    )(ExpressionEncoder(): Encoder[String]).map(t => t.split(" "))(ExpressionEncoder(): Encoder[Array[String]]).toDF("items")
    dataset.show()

    val fpgrowth = new FPGrowth().setItemsCol("items").setMinSupport(0.5).setMinConfidence(0.6)
    val model = fpgrowth.fit(dataset)

    // Display frequent itemsets.
    model.freqItemsets.show()

    // Display generated association rules.
    model.associationRules.show()

    // transform examines the input items against all the association rules and summarize the
    // consequents as prediction
    model.transform(dataset).show()
  }

}

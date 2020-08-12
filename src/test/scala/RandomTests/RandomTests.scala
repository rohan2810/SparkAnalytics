package RandomTests

import java.util
import java.util.stream.Collectors

import com.fasterxml.jackson.databind.node.{ArrayNode, ObjectNode}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import groovy.transform.TailRecursive
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Encoders, Row, SparkSession}
import org.testng.annotations.Test


class RandomTests {
  val spark = SparkSession.builder()
    .appName("TaskSpec")
    .master("local[2]")
    .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    .getOrCreate()
  val objectMapper = new ObjectMapper()

  @Test def RowToJson(): Unit = {


    /*

    METHOD  : works for nested
        val ds1 = spark.createDataFrame(rows, schema).toJSON.collectAsList()
    println(ds1.get(0))
    {"first_name":"rohan","last_name":"surana","data":[{"level":"level","group":"group1"}]}
     */

    /*
    METHOD  : {"first_name":"rohan","last_name":"surana","data":"WrappedArray([level,group1])"}   ==> with nested data
        val k = ds.first().getValuesMap[AnyRef](ds.first().schema.fieldNames).asJava
    val json = new JSONObject(k).toString

     */

    //    val data = StructType(
    //      StructField("level", DataTypes.StringType, nullable = true) ::
    //        StructField("group", DataTypes.StringType, nullable = true) ::
    //        Nil
    //    )
    //    val schema: StructType = StructType(
    //      StructField("first_name", DataTypes.StringType, nullable = true) ::
    //        StructField("last_name", DataTypes.StringType, nullable = true) ::
    //        StructField("data", DataTypes.createArrayType(data)) ::
    //        Nil)
    //    val dataArr = new util.ArrayList[Row]()
    //    dataArr.add(RowFactory.create("level", "group1"))
    //
    //    val row = RowFactory.create("rohan", "surana", dataArr.toArray())
    //    val rows = new util.ArrayList[Row]
    //    rows.add(row)
    //    val ds = spark.createDataFrame(rows, schema)


    // for struct testing
    //    val structureData = Seq(
    //      Row(Row("James ", "", "Smith"), "36636", "M", 3100),
    //      Row(Row("Michael ", "Rose", ""), "40288", "M", 4300),
    //      Row(Row("Robert ", "", "Williams"), "42114", "M", 1400),
    //      Row(Row("Maria ", "Anne", "Jones"), "39192", "F", 5500),
    //      Row(Row("Jen", "Mary", "Brown"), "", "F", -1)
    //    )
    //
    //    val structureSchema = new StructType()
    //      .add("name", new StructType()
    //        .add("firstname", StringType)
    //        .add("middlename", StringType)
    //        .add("lastname", StringType))
    //      .add("id", StringType)
    //      .add("gender", StringType)
    //      .add("salary", IntegerType)
    //
    //    val df2 = spark.createDataFrame(
    //      spark.sparkContext.parallelize(structureData), structureSchema)
    //
    //    val row = Row(Row("James ", "", "Smith"), "36636", "M", 4545)


    // for array string testing
    //    val arrayStructureData = Seq(
    //      Row("James,,Smith", List("Java", "Scala", "C++"), List("Spark", "Java"), "OH", "CA"),
    //      Row("Michael,Rose,", List("Spark", "Java", "C++"), List("Spark", "Java"), "NY", "NJ"),
    //      Row("Robert,,Williams", List("CSharp", "VB"), List("Spark", "Python"), "UT", "NV")
    //    )
    //
    //    val arrayStructureSchema = new StructType()
    //      .add("name", StringType)
    //      .add("languagesAtSchool", ArrayType(StringType))
    //      .add("languagesAtWork", ArrayType(StringType))
    //      .add("currentState", StringType)
    //      .add("previousState", StringType)
    //
    //    val df = spark.createDataFrame(
    //      spark.sparkContext.parallelize(arrayStructureData), arrayStructureSchema)
    //
    //    val row = Row("James,,Smith", List("Java", "Scala", "C++"), List("Spark", "Java"), "OH", "CA")

    // for array of struct
    //
    //    val arrayStructData = Seq(
    //      Row("James", List(Row("Java", "XX", 120), Row("Scala", "XA", 300))),
    //      Row("Michael", List(Row("Java", "XY", 200), Row("Scala", "XB", 500))),
    //      Row("Robert", List(Row("Java", "XZ", 400), Row("Scala", "XC", 250))),
    //      Row("Washington", null)
    //    )
    //
    //    val arrayStructSchema = new StructType().add("name", StringType)
    //      .add("booksIntersted", ArrayType(new StructType()
    //        .add("name", StringType)
    //        .add("author", StringType)
    //        .add("pages", LongType)))
    //
    //    val df = spark.createDataFrame(spark.sparkContext
    //      .parallelize(arrayStructData), arrayStructSchema)
    //
    //
    //    val row = Row("James", List(Row("Java", "XX", 120), Row("Scala", "XA", 300)))

    //    complete flow

    val arrayStructData = Seq(
      Row("James", 12, 45453453452L, true, java.lang.Short.MAX_VALUE, Row("James ", 14, "Smith"), List(Row("Java", "XX", 120), Row("Scala", "XA", 300)), List("Java", "Scala", "C++"), List("Spark", "Java")),
      Row("Jdoe", 34, 354534534L, false, java.lang.Short.MIN_VALUE, Row("James ", 14, "Smith"), List(Row("Java", "XX", 120), Row("Scala", "XA", 300)), List("Java", "Scala", "C++"), List("Spark", "Java")),
      Row("Andrew", 23, 34545353L, true, java.lang.Short.MAX_VALUE, Row("James ", 14, "Smith"), List(Row("Java", "XX", 120), Row("Scala", "XA", 300)), List("Java", "Scala", "C++"), List("Spark", "Java")),
      Row("XLEE", null, 543535234234L, null, null, null, null, null, null)
    )

    val arrayStructSchema = new StructType()
      .add("name", StringType)
      .add("age", IntegerType)
      .add("longVal", LongType)
      .add("bool", BooleanType)
      .add("short", ShortType)
      .add("details", new StructType()
        .add("firstname", StringType)
        .add("age", IntegerType)
        .add("lastname", StringType))
      .add("Knows", ArrayType(new StructType()
        .add("Language", StringType)
        .add("why", StringType)
        .add("since", IntegerType)))
      .add("LanguageAtWork", ArrayType(StringType))
      .add("MoreLanguages", ArrayType(StringType))


    val df = spark.createDataFrame(spark.sparkContext
      .parallelize(arrayStructData), arrayStructSchema)

    df.show()

    val row = Row("XLEE", null, 543535234234L, null, null, null, null, null, null)
    val mapped = jsonMapper(row, df.schema)
    println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapped))

  }

  private def jsonMapper(row: Row, schema: StructType): JsonNode = {

    val node: ObjectNode = objectMapper.createObjectNode()
    schema.fields.zipWithIndex
      .foreach(
        fieldWithIndex => {
          fieldWithIndex._1.dataType match {
            case x: StructType =>
              val structValue = row.getStruct(fieldWithIndex._2)
              if (structValue != null) {
                node.set(fieldWithIndex._1.name, jsonMapper(structValue, x))
              }
            case x: ArrayType =>
              val listOfValues = row.getList[AnyRef](fieldWithIndex._2)
              if (listOfValues != null && listOfValues.size() > 0) {
                x.elementType match {
                  case elementStructType: StructType =>
                    val value = listOfValues.asInstanceOf[java.util.List[Row]].stream().map[ObjectNode](
                      new java.util.function.Function[Row, ObjectNode]() {
                        override def apply(t: Row): ObjectNode = jsonMapper(t, elementStructType).asInstanceOf[ObjectNode]
                      }
                    ).collect(Collectors.toList[JsonNode])
                    val arrNode: ArrayNode = objectMapper.valueToTree(value)
                    node.withArray(fieldWithIndex._1.name).addAll(arrNode)
                  case _ =>
                    val arrNode: ArrayNode = objectMapper.valueToTree(listOfValues)
                    node.withArray(fieldWithIndex._1.name).addAll(arrNode)
                }
              }
            case _ =>
              val value = row.getAs[AnyRef](fieldWithIndex._2)
              if (value != null) {
                value match {
                  case integer: java.lang.Integer =>
                    node.put(fieldWithIndex._1.name, integer)
                  case str: java.lang.String =>
                    node.put(fieldWithIndex._1.name, str)
                  case boolean: java.lang.Boolean =>
                    node.put(fieldWithIndex._1.name, boolean)
                  case long: java.lang.Long =>
                    node.put(fieldWithIndex._1.name, long)
                  case short: java.lang.Short =>
                    node.put(fieldWithIndex._1.name, short)

                }
              }
          }
        }
      )
    node
  }

  @Test
  def recursion(): Unit = {
    org.testng.Assert.assertEquals(120, facto(5))

  }

  @TailRecursive
  private def facto(n: Int): Int = {
    if (n == 1) 1
    else n * facto(n - 1)
  }

  @Test
  def jsonToDf(): Unit = {
    import spark.implicits._
    val jsonStr = """{"name":"James","age":12,"longVal":45453453452,"bool":true,"short":32767,"details":{"firstname":"James ","age":14,"lastname":"Smith"},"Knows":[{"Language":"Java","why":"XX","since":120},{"Language":"Scala","why":"XA","since":300}],"LanguageAtWork":["Java","Scala","C++"],"MoreLanguages":["Spark","Java"]}"""
    val jsonStr1 = """{"name":"James","age":12,"longVal":45453453452,"bool":true,"short":32767,"details":{"firstname":"James ","age":14,"lastname":"Smith"},"Knows":[{"Language":"Java","why":"XX","since":120},{"Language":"Scala","why":"XA","since":300}],"LanguageAtWork":["Java","Scala","C++"],"MoreLanguages":["Spark","Java"]}"""
    val df = spark.read.json(Seq(jsonStr, jsonStr1).toDS())
    df.show()
    val ds = df.map(
      x => (EntityMeta.empty, x)
    )(Encoders.tuple(Encoders.product[EntityMeta], RowEncoder(df.schema)))
    ds.show()


  }

  @Test
  def datasetFromRowFromScalaObjects(): Unit = {

    val list = new util.ArrayList[AnyRef]()
    list.add("adsd")
    list.add("sdf")
    list.add("dfdf")

    import scala.collection.JavaConverters._
    val l = new java.util.ArrayList[java.lang.String]
    l.add("dfdf")
    l.add("dsdsd")
    val s = l.asScala
    println(s.mkString(","))
  }


  @Test
  def jsonFromScalaMap(): Unit = {
    val map = Map("Rohan" -> Seq(("ROhan","rojs","rihsd")))
    val seq = Seq("ROhan","ejife","rfuef")
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)
    val jsonNode: String = objectMapper.writeValueAsString(seq)
    println(jsonNode.getClass)

  }
}

/*
//    val arrayStructData = Seq((entityMeta, Row("James", 12, 45453453452L, true, java.lang.Short.MAX_VALUE, Row("James ", 14, "Smith"), List(Row("Java", "XX", 120), Row("Scala", "XA", 300)), List("Java", "Scala", "C++"), List("Spark", "Java"))),
//      (entityMeta, Row("Jdoe", 34, 354534534L, false, java.lang.Short.MIN_VALUE, Row("James ", 14, "Smith"), List(Row("Java", "XX", 120), Row("Scala", "XA", 300)), List("Java", "Scala", "C++"), List("Spark", "Java"))),
//      (entityMeta, Row("Andrew", 23, 34545353L, true, java.lang.Short.MAX_VALUE, Row("James ", 14, "Smith"), List(Row("Java", "XX", 120), Row("Scala", "XA", 300)), List("Java", "Scala", "C++"), List("Spark", "Java"))),
//      (entityMeta, Row("XLEE", null, 543535234234L, null, null, null, null, null, null)))
//
//
//    val arrayStructSchema = new StructType()
//      .add("name", StringType)
//      .add("age", IntegerType)
//      .add("longVal", LongType)
//      .add("bool", BooleanType)
//      .add("short", ShortType)
//      .add("details", new StructType()
//        .add("firstname", StringType)
//        .add("age", IntegerType)
//        .add("lastname", StringType))
//      .add("Knows", ArrayType(new StructType()
//        .add("Language", StringType)
//        .add("why", StringType)
//        .add("since", IntegerType)))
//      .add("LanguageAtWork", ArrayType(StringType))
//      .add("MoreLanguages", ArrayType(StringType))


//    val ds: Dataset[(EntityMeta, Row)] = spark.createDataset(arrayStructData)(Encoders.tuple(Encoders.product[EntityMeta], RowEncoder(arrayStructSchema)))

 */
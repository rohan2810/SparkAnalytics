package PersistProbableEntites

import java.sql.{Connection, PreparedStatement}
import java.util.stream.Collectors

import com.fasterxml.jackson.databind.node.{ArrayNode, ObjectNode}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{ArrayType, StructType}

/**
 * util class for persist probable entities
 * includes :
 * SavePartition method
 * private getAsJson method 
 * getJsonObject method
 */
class PersistProbableEntitiesUtil {
  val objectMapper = new ObjectMapper()
  objectMapper.registerModule(DefaultScalaModule)

  def getJsonObject(row: Row, schema: StructType): Array[Byte] = {
    val node: JsonNode = getAsJson(row, schema)
    val json: Array[Byte] = node.toString.getBytes
    //      objectMapper.writerWithDefaultPrettyPrinter.writeValueAsString(node)
    json
  }

  def savePartition(connectionFactory: () => Connection, iterator: Iterator[Row], insertStmt: String, datasetSchema: StructType): Unit = {
    val conn = connectionFactory.apply()
    assert(conn != null)
    val st: PreparedStatement = conn.prepareStatement(insertStmt)

    try {
      while (iterator.hasNext) {
        val row: Row = iterator.next()
        for (i <- 0 until row.size) {
          datasetSchema.fieldNames(i) match {
            case "DATA" =>
              if (row.isNullAt(i))
                st.setNull(i + 1, java.sql.Types.OTHER)
              else
                st.setBytes(i + 1, row.getAs[Array[Byte]](i))
            case "DISCOVERED" =>
              if (row.isNullAt(i))
                st.setNull(i + 1, java.sql.Types.BOOLEAN)
              else
                st.setBoolean(i + 1, row.getBoolean(i))
            case "CREATE_TS" =>
              if (row.isNullAt(i))
                throw new Exception("CREATE_TS is a not null field")
              else
                st.setTimestamp(i + 1, row.getTimestamp(i))
            case "EVENTS" =>
              if (row.isNullAt(i))
                st.setNull(i + 1, java.sql.Types.OTHER)
              else {
                val eventString = objectMapper.writeValueAsString(row.getSeq(i))
                st.setString(i + 1, eventString)
              }
            case "RELATIONSHIPS" =>
              if (row.isNullAt(i))
                st.setNull(i + 1, java.sql.Types.OTHER)
              else {
                val eventString = objectMapper.writeValueAsString(row.getMap(i))
                st.setString(i + 1, eventString)
              }
            case _ =>
              if (row.isNullAt(i))
                st.setNull(i + 1, java.sql.Types.VARCHAR)
              else
                st.setString(i + 1, row.getString(i))
          }
        }
        st.execute()
      }
    }
    catch {
      case e: Exception =>
        throw new Exception(e)

    }
    finally {
      st.close()
      conn.close()
    }

  }

  private def getAsJson(row: Row, schema: StructType): JsonNode = {

    val node: ObjectNode = objectMapper.createObjectNode()
    schema.fields.zipWithIndex
      .foreach(
        fieldWithIndex => {
          fieldWithIndex._1.dataType match {
            case x: StructType =>
              val structValue = row.getStruct(fieldWithIndex._2)
              if (structValue != null) {
                node.set(fieldWithIndex._1.name, getAsJson(structValue, x))
              }
            case x: ArrayType =>
              val listOfValues = row.getList[AnyRef](fieldWithIndex._2)
              if (listOfValues != null && listOfValues.size() > 0) {
                x.elementType match {
                  case elementStructType: StructType =>
                    val value = listOfValues.asInstanceOf[java.util.List[Row]].stream().map[ObjectNode](
                      new java.util.function.Function[Row, ObjectNode]() {
                        override def apply(t: Row): ObjectNode = getAsJson(t, elementStructType).asInstanceOf[ObjectNode]
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

}

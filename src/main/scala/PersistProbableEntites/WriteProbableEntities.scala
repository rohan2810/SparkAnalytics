package PersistProbableEntites

import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{Dataset, Row}

trait WriteProbableEntities {
  def write(tenantId: String, parameters: Map[String, Object], dataset: Dataset[Row])

  val schema: StructType = StructType(
    StructField("ID", DataTypes.StringType, nullable = true) ::
      StructField("PROCESS_ID", DataTypes.StringType, nullable = true) ::
      StructField("DATASET_PROCESS_ID", DataTypes.StringType, nullable = true) ::
      StructField("ENTITY_STATUS", DataTypes.StringType, nullable = true) ::
      StructField("ANALYSIS_STATUS", DataTypes.StringType, nullable = true) ::
      StructField("DISCOVERED", DataTypes.BooleanType, nullable = true) ::
      StructField("GID", DataTypes.StringType, nullable = true) ::
      StructField("NATIVE_ID", DataTypes.StringType, nullable = true) ::
      StructField("DISPLAY_NAME", DataTypes.StringType, nullable = true) ::
      StructField("DESCRIPTION", DataTypes.StringType, nullable = true) ::
      StructField("NATIVE_STATUS", DataTypes.StringType, nullable = true) ::
      StructField("DATA_SIGNATURE", DataTypes.StringType, nullable = true) ::
      StructField("ENTITY_KEY", DataTypes.StringType, nullable = true) ::
      StructField("ENTITY_ACTION", DataTypes.StringType, nullable = true) ::
      StructField("DATA", DataTypes.BinaryType, nullable = true) ::
      StructField("ALERTS", DataTypes.StringType, nullable = true) ::
      StructField("EVENTS", DataTypes.StringType, nullable = true) ::
      StructField("RELATIONSHIPS", DataTypes.StringType, nullable = true) ::
      StructField("CHANGELOG", DataTypes.StringType, nullable = true) ::
      StructField("CREATE_TS", DataTypes.TimestampType, nullable = true) ::
      Nil)
}

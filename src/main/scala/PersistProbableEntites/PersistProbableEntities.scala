//package PersistProbableEntites
//
//import java.sql.{Connection, DriverManager, SQLException}
//
//import org.apache.spark.TaskContext
//import org.apache.spark.sql.catalyst.encoders.RowEncoder
//import org.apache.spark.sql.types.StructType
//import org.apache.spark.sql.{Dataset, Row, RowFactory}
//
//class PersistProbableEntities extends ActionTask[EntityDatabag, EntityDatabag] {
//
//  val writeEntities: WriteProbableEntities = (tenantId: String, parameters: Map[String, Object], dataset: Dataset[Row]) => {
//    val insertStmt: String = "INSERT INTO iap_probable_entity (ID,PROCESS_ID,DATASET_PROCESS_ID,ENTITY_STATUS," +
//      "ANALYSIS_STATUS,DISCOVERED,GID,NATIVE_ID,DISPLAY_NAME,DESCRIPTION,NATIVE_STATUS,DATA_SIGNATURE," +
//      "ENTITY_KEY,ENTITY_ACTION,DATA,ALERTS,EVENTS,RELATIONSHIPS,CHANGELOG, CREATE_TS) " +
//      " VALUES (?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?,?, ?::jsonb,?::jsonb, ?, ?,?)"
//    val datasetSchema = dataset.schema
//    val connectionfactory: () => Connection = () => {
//      val url = "jdbc:postgresql://localhost:5432/testdb"
//      val user = "rohansurana"
//      val password = "passw0rd"
//
//      var conn: Connection = null
//      try {
//        conn = DriverManager.getConnection(url, user, password)
//        conn
//      } catch {
//        case e: SQLException =>
//          throw e.getNextException
//
//      }
//    }
//    dataset.rdd.foreachPartition(
//      iterator => new PersistProbableEntitiesUtil().savePartition(connectionfactory, iterator, insertStmt, datasetSchema)
//    )
//  }
//
//  /**
//   * Store entities present in the 'datasets' into IAP_PROBABLE_ENTITY table using JDBC Datasource
//   *
//   * @param context
//   * @param databags   includes input databags
//   * @param aggregates includes aggregated JobMessageDataset beyond regular ApplicationDatasetInfo aggregates
//   * @return
//   */
//  override def performAction(context: TaskContext, databags: Seq[EntityDatabag], aggregates: Seq[EntityDatabag], recordOutcomeJournal: RecordOutcomeJournal): ActionTaskResult = {
//    databags.foreach(
//      databag => {
//        val fields = writeEntities.schema
//        val fieldsLength = fields.length
//        val values = new Array[AnyRef](fieldsLength)
//        val dsToStore = databag.dataset.data.map(
//          x => {
//            val rowSchema: StructType = x._2.schema
//            for (i <- 0 until fieldsLength) {
//              fields(i).name match {
//                case "ID" => values(i) = "id"
//                case "PROCESS_ID" => values(i) = "processId" //x._2 //check
//                case "DATASET_PROCESS_ID" => values(i) = "datasetId" //x._1 //check
//                case "ENTITY_STATUS" => values(i) = x._1.entityStatus
//                case "ANALYSIS_STATUS" => values(i) = x._1.analysisStatus
//                case "DISCOVERED" => x._1.discovered // check
//                case "GID" => values(i) = x._1.globalId
//                case "NATIVE_ID" => values(i) = x._1.nativeId
//                case "DISPLAY_NAME" => values(i) = x._1.displayName
//                case "DESCRIPTION" => values(i) = x._1.description
//                case "NATIVE_STATUS" => values(i) = x._1.nativeStatus
//                case "DATA_SIGNATURE" => values(i) = x._1.signature
//                case "ENTITY_KEY" => values(i) = x._1.entityKey
//                case "ENTITY_ACTION" => values(i) = x._1.action
//                case "DATA" => values(i) = new PersistProbableEntitiesUtil().getJsonObject(x._2, rowSchema)
//                case "ALERTS" => values(i) = """{"a": 1, "b": 2, "c": 3}""" // from recorder
//                case "EVENTS" => values(i) = Seq("test", "values") //"""{"a": 1, "b": 2, "c": 3}"""
//                case "RELATIONSHIPS" => values(i) = Map("test" -> "value") //x._1.relationships
//                case "CHANGELOG" => values(i) = x._1.changeLog
//                case "CREATE_TS" => values(i) = x._1.stateAsOnTime //check
//              }
//            }
//            RowFactory.create(values(0), values(1), values(2), values(3), values(4), values(5), values(6), values(7),
//              values(8), values(9), values(10), values(11), values(12), values(13), values(14), values(15), values(16), values(17), values(18), values(19))
//          }
//        )(RowEncoder.apply(fields))
//        writeEntities.write(context.analysisRequest.tenantOrgId, Map.empty, dsToStore)
//      }
//    )
//    ActionTaskResult(TaskCompletedSuccessfully, Seq.empty)
//
//  }
//
//  override def identifier(): String = "Persist Probable Entities"
//}

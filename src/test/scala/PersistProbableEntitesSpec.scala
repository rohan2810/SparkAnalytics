//package com.confluxsys.iap.analysis.stagedata.task
//
//import java.time.Instant
//
//import PersistProbableEntites.PersistProbableEntities
//import com.confluxsys.iap.analysis.common.model.Facts
//import com.confluxsys.iap.analysis.common.stagedataprocess.{EntityMeta, EntityMetaBuilder}
//import com.confluxsys.iap.analysis.databag.Databag
//import com.confluxsys.iap.analysis.job.Session
//import org.apache.spark.sql.Encoders
//import org.apache.spark.sql.catalyst.encoders.RowEncoder
//import org.testng.annotations.Test
//
//class PersistProbableEntitesSpec extends TaskSpecBase {
//  lazy val task: PersistProbableEntities = new PersistProbableEntities
//
//  override def requisiteDatabag: Option[String => Databag] = ???
//
//
//  @Test
//  def basicFlow(): Unit = {
//
//    import spark.implicits._
//    val jsonStr = """{"name":"JDOE","age":34,"longVal":44354554,"bool":true,"short":32767,"details":{"firstname":"James ","age":14,"lastname":"Smith"},"Knows":[{"Language":"Java","why":"XX","since":120},{"Language":"Scala","why":"XA","since":300}],"LanguageAtWork":["Java","Scala","C++"],"MoreLanguages":["Spark","Java"]}"""
//    val jsonStr1 = """{"name":"XLEE","age":34,"longVal":44354554,"bool":true,"short":32767,"details":{"firstname":"James ","age":14,"lastname":"Smith"},"Knows":[{"Language":"Java","why":"XX","since":120},{"Language":"Scala","why":"XA","since":300}],"LanguageAtWork":["Java","Scala","C++"],"MoreLanguages":["Spark","Java"]}"""
//    val jsonStr2 = """{"name":"Andrew","age":null,"longVal":null,"bool":null,"short":null,"details":null,"Knows":null,"LanguageAtWork":null,"MoreLanguages":null}"""
//    val jsonStr3 = """{"name":"James","age":34,"longVal":44354554,"bool":true,"short":32767,"details":{"firstname":"James ","age":14,"lastname":"Smith"},"Knows":[{"Language":"Java","why":"XX","since":120},{"Language":"Scala","why":"XA","since":300}],"LanguageAtWork":["Java","Scala","C++"],"MoreLanguages":["Spark","Java"]}"""
//
//    val entMeta: EntityMetaBuilder = EntityMeta.builder(EntityMeta.empty)
//    entMeta.entityKey = "jdoe2"
//    entMeta.changeLog = "changeLog"
//    entMeta.stateAsOnTime = java.sql.Timestamp.from(Instant.now())
//    entMeta.nativeId = "nativeId"
//    entMeta.displayName = "displayName"
//    entMeta.signature = "signature"
//    entMeta.discovered = true
//    entMeta.entityStatus = "entityStatus"
//    entMeta.nativeType = "nativetype"
//    entMeta.description = "description"
//    entMeta.nativeStatus = "nativeStatus"
//    entMeta.globalId = "globalId"
//    entMeta.sourceDataLocator = "dataLocator"
//    entMeta.instanceId = "instanceId"
//    entMeta.analysisStatus = "analysisStatus"
//    entMeta.action = "action"
//    val entityMeta: EntityMeta = entMeta.build()
//
//    val df = spark.read.json(Seq(jsonStr, jsonStr1, jsonStr2, jsonStr3).toDS())
//    val ds = df.map(
//      x => (entityMeta, x)
//    )(Encoders.tuple(Encoders.product[EntityMeta], RowEncoder(df.schema)))
//
//    task.performAction(buildContext(Facts.empty, Session.empty), createEntityDatabags(ds), null, null)
//  }
//}

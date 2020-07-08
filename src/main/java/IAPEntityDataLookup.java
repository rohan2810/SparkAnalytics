import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import java.util.List;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

public class IAPEntityDataLookup {

    private final GraphTraversalSource g;
    private final SparkSession sparkSession;


    public IAPEntityDataLookup(GraphTraversalSource g, SparkSession sparkSession) {
        this.g = g;
        this.sparkSession = sparkSession;
    }


    /**
     * Lookup Active Entities from IAP
     *
     * @param tenantId
     * @param appId
     * @param nativeType
     * @param entityKeys
     * @return a dataframe with following key columns:
     * All properties of the Entity {data, status, metaType, nativeStatus, nativeAsOnTime, nativeModifiedOnTime, nativeId, name, entityKey, entityGlobalId} ==> all vertex
     * $relationships(Array of Struct): Related entities (not actors). {type, data, valuePath, valueSignature, category, [relatedEntityKey, relatedEntityGlobalId] ==> rom vertex} else from edge
     * $alerts (Array Of Struct) {level, raisedTime, gid, alertType, summary, taskId, valuePath, valueSignature, messages, acqRequired, acknowledged}  ==> alert vertex
     * $actors (Array of Struct) {type, data, valuePath, valueSignature, userId}  ==> entitlement,account to person(With_actor)
     */
    public Dataset<Row> getActiveEntities(String tenantId, String appId, String nativeType, String[] entityKeys) {

        List<Map<String, Object>> list = getData(tenantId, appId, nativeType);
        JavaRDD<Map<String, Object>> javaRDD = (new JavaSparkContext(sparkSession.sparkContext())).parallelize(list);
        EntityMapper mapper = new EntityMapper();
        JavaRDD<Row> mappedList = javaRDD.map(mapper);

        return sparkSession.createDataFrame(mappedList, EntityMapper.schema);


    }

    public List<Map<String, Object>> getData(String tenantId, String appId, String nativeType) {
        return g.V().has("Entity", "appId", appId).has("nativeType", nativeType).has("tenantId", tenantId).as("entity").project("Entity", "$alerts", "$relationships").

                by(valueMap()).

                by(select("entity").out("With_Alert").hasLabel("Alert").as("Alert").
                        select("Alert").
                        by(valueMap("level", "raisedTime", "gid", "alertType", "summary", "taskId", "valuePath", "valueSignature", "messages", "acqRequired", "acknowledged")).fold()).


                by(select("entity").in("Is").as("Bridge").project("Actor", "RelatedEntity").
                        by(outE("With_Actor").as("With_Actor").inV().hasLabel("Person").as("Person").select("With_Actor", "Person").
                                by(valueMap("type", "data", "valuePath", "valueSignature").by(fold())).
                                by(valueMap("userId"))).
                        by(outE("Is_Related_To").as("Is_Related_To").inV().as("RelatedEntity").project("Is_Related_To", "$RelatedEntity").
                                by(select("Is_Related_To").valueMap("type", "data", "valuePath", "valueSignature", "category").by(fold())).
                                by(select("RelatedEntity").valueMap("entityKey", "entityGlobalId"))).fold())
                .toList();

    }


}

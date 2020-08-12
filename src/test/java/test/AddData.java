package test;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import java.time.Instant;
import java.util.*;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class AddData {

    private final static Cluster cluster = Cluster.build().addContactPoint("localhost").port(8182).create();

    public static void main(String[] args) {
        GraphTraversalSource g = traversal().withRemote(DriverRemoteConnection.using(cluster, "iap2.g"));
        generate(g);
    }

    public static void generate(GraphTraversalSource g) {

        List<String> message = new ArrayList<>();
        message.add("high");
        message.add("this a more verbose message");

        List<List<String>> alertMessages = new ArrayList<>();
        alertMessages.add(message);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");

        g.addV("Account").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe1").property("nativeId", "jdoe1").
                property("nativeType", "inetorgperson").property("metaType", "metaType").property("category", "Account").
                property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe1").as("account1").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe1").property("nativeId", "jdoe1").
                property("nativeType", "inetorgperson").property("metaType", "metaType").
                property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe").
                property("data", "{\"Role Name\":\"IAP Dev Role\", \"Description\":\"This role gives access to iap git branches\"}").as("accEntity1").
                addV("Account").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe2").property("nativeId", "jdoe2").
                property("nativeType", "inetorgperson").property("metaType", "metaType").property("category", "Account").
                property("createTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe2").as("account2").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe2").property("nativeId", "jdoe2").
                property("nativeType", "inetorgperson").property("metaType", "metaType").
                property("createTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe2").
                property("data", "{\"Role Name\":\"IAP Dev Role\", \"Description\":\"This role gives access to iap git branches\"}").as("accEntity2").
                addV("Account").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe3").property("nativeId", "jdoe3").
                property("nativeType", "inetorgperson").property("metaType", "metaType").property("category", "Account").
                property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130003")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe3").as("account3").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe3").property("nativeId", "jdoe3").
                property("nativeType", "inetorgperson").property("metaType", "metaType").
                property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130003")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe3").
                property("data", "{\"Role Name\":\"IAP Dev Role\", \"Description\":\"This role gives access to iap git branches\"}").as("accEntity3").
                addV("Account").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe4").property("nativeId", "jdoe4").
                property("nativeType", "inetorgperson").property("metaType", "metaType").property("category", "Account").
                property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130004")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe4").as("account4").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe4").property("nativeId", "jdoe4").
                property("nativeType", "inetorgperson").property("metaType", "metaType").
                property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130004")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe4").
                property("data", "{\"Role Name\":\"IAP Dev Role\", \"Description\":\"This role gives access to iap git branches\"}").as("accEntity4").
                addV("Account").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe5").property("nativeId", "jdoe5").
                property("nativeType", "inetorgperson").property("metaType", "metaType").property("category", "Account").
                property("createTime", Instant.parse("2020-05-26T12:55:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:55:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:55:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:55:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe5").as("account5").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe5").property("nativeId", "jdoe5").
                property("nativeType", "inetorgperson").property("metaType", "metaType").
                property("createTime", Instant.parse("2020-05-26T12:55:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:55:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:55:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:55:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe5").
                property("data", "{\"Role Name\":\"IAP Dev Role\", \"Description\":\"This role gives access to iap git branches\"}").as("accEntity5").
                addV("Account").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe6").property("nativeId", "jdoe6").
                property("nativeType", "inetorgperson").property("metaType", "metaType").property("category", "Account").
                property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130006")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe6").as("account6").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe6").property("nativeId", "jdoe6").
                property("nativeType", "inetorgperson").property("metaType", "metaType").
                property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130006")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe6").
                property("data", "{\"Role Name\":\"IAP Dev Role\", \"Description\":\"This role gives access to iap git branches\"}").as("accEntity6").
                addV("Account").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe7").property("nativeId", "jdoe7").
                property("nativeType", "inetorgperson").property("metaType", "metaType").property("category", "Account").
                property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130007")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe7").as("account7").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "jdoe7").property("nativeId", "jdoe7").
                property("nativeType", "inetorgperson").property("metaType", "metaType").
                property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130007")).property("description", "desc").
                property("status", "Active").property("nativeStatus", "Active").property("name", "John Doe7").
                property("data", "{\"Role Name\":\"IAP Dev Role\", \"Description\":\"This role gives access to iap git branches\"}").as("accEntity7").
                addV("Entitlement").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "groupOfUniqueNames").property("metaType", "metaType").
                property("nativeId", "Group1").property("entityKey", "GROUP1").property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("updateTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).
                property("status", "Active").property("nativeStatus", "Active").property("name", "Group 1").property("displayName", "Group 1").
                property("description", "desc").as("entitlement1").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "GROUP1").property("nativeId", "Group1").property("nativeType", "groupOfUniqueNames").
                property("metaType", "metaType").property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).property("status", "Active").property("nativeStatus", "Active").
                property("name", "Group 1").property("data", "{\"Name\":\"Group1\", \"Description\":\"desc\"}").as("entEntity1").
                addV("Entitlement").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "groupOfUniqueNames").property("metaType", "metaType").
                property("nativeId", "Group2").property("entityKey", "GROUP2").property("createTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("updateTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).
                property("status", "Active").property("nativeStatus", "Active").property("name", "Group 2").property("displayName", "Group 2").
                property("description", "desc").as("entitlement2").
                addV("Entity").property("tenantId", "tenant1").property("appId", "ad").property("entityKey", "GROUP2").property("nativeId", "Group2").property("nativeType", "groupOfUniqueNames").
                property("metaType", "metaType").property("createTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("updateTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("nativeAsOnTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("nativeModifiedOnTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).property("status", "Active").property("nativeStatus", "Active").
                property("name", "Group 2").property("data", "{\"Name\":\"Group2\", \"Description\":\"desc\"}").as("entEntity2").
                addV("Alert").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("level", 1).
                property("gid", "03177aa6-97d2-11ea-bb37-0242ac130001").property("updateTime", Instant.parse("2020-05-26T12:34:56.00Z")).property("raisedTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).property("summary", "summarized message").property("taskId", 34).
                property("taskName", "taskName").property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("alertType", "Schemaviolation").
                property("acknowledged", true).property("ackRequired", true).property("acknowledgedByUserid", "b1").
                property("messages", alertMessages).property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).
                as("alert1").
                addV("Alert").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("level", 2).
                property("gid", "03177aa6-97d2-11ea-bb37-0242ac130002").property("updateTime", Instant.parse("2020-05-26T12:36:56.00Z")).property("raisedTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).property("summary", "summarized message").property("taskId", 35).
                property("taskName", "taskName").property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("alertType", "Schemaviolation").
                property("acknowledged", true).property("ackRequired", true).property("acknowledgedByUserid", "b1").
                property("messages", alertMessages).property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).
                as("alert2").
                addV("Alert").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("level", 2).
                property("gid", "03177aa6-97d2-11ea-bb37-0242ac130003").property("updateTime", Instant.parse("2020-05-26T12:40:56.00Z")).property("raisedTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130003")).property("summary", "summarized message").property("taskId", 36).
                property("taskName", "taskName").property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("alertType", "Schemaviolation").
                property("acknowledged", true).property("ackRequired", true).property("acknowledgedByUserid", "b1").
                property("messages", alertMessages).property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).
                as("alert3").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130001").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).
                property("attributes", attributes).property("createTimeBucket", "202004").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event1").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130002").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event2").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130003").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130003")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event3").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130004").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130004")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event4").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:55:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130005").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130005")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event5").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:34:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130006").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130006")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event6").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130007").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130007")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event7").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:36:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130003")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130008").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130008")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event8").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130004")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130009").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130009")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event9").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130006")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130010").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130010")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event10").
                addV("Event").property("tenantId", "tenant1").property("appId", "ad").property("nativeType", "inetorgperson").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("entityGlobalId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130007")).
                property("gid", "04177aa6-97d2-11ea-bb37-0242ac130011").property("eventCategory", "StateChange").property("type", "ModifyEntity").
                property("processId", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130011")).
                property("attributes", attributes).property("createTimeBucket", "202005").
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").as("event11").
                addV("User").property("tenantId", "tenant1").property("entityKey", "JDOE1").property("nativeId", "PBULE").property("appId", "ad").as("person1").
                addV("User").property("tenantId", "tenant1").property("entityKey", "JDOE2").property("nativeId", "PBULE").property("appId", "ad").as("person2").
                addV("User").property("tenantId", "tenant1").property("entityKey", "JDOE3").property("nativeId", "PBULE").property("appId", "ad").as("person3").
                addV("User").property("tenantId", "tenant1").property("entityKey", "JDOE4").property("nativeId", "PBULE").property("appId", "ad").as("person4").
                addV("User").property("tenantId", "tenant1").property("entityKey", "JDOE5").property("nativeId", "PBULE").property("appId", "ad").as("person5").
                addE("Is_Related_To").from("account1").to("person1").property("type", "Owner").property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("data", "{\"Name\":\"name\", \"Description\":\"desc\"}").
                addE("Is_Related_To").from("account2").to("person2").property("type", "Owner").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("data", "{\"Name\":\"name\", \"Description\":\"desc\"}").
                addE("Is_Related_To").from("account3").to("person3").property("type", "Owner").property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("data", "{\"Name\":\"name\", \"Description\":\"desc\"}").
                addE("Is_Related_To").from("account4").to("person4").property("type", "Owner").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("data", "{\"Name\":\"name\", \"Description\":\"desc\"}").
                addE("Is_Related_To").from("account5").to("person5").property("type", "Owner").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("data", "{\"Name\":\"name\", \"Description\":\"desc\"}").
                addE("Is").from("account1").to("accEntity1").
                addE("Is").from("account2").to("accEntity2").
                addE("Is").from("account3").to("accEntity3").
                addE("Is").from("account4").to("accEntity4").
                addE("Is").from("account5").to("accEntity5").
                addE("Is").from("account6").to("accEntity6").
                addE("Is").from("account7").to("accEntity7").
                addE("Is").from("entitlement1").to("entEntity1").
                addE("Is").from("entitlement2").to("entEntity2").
                addE("Is_Related_To").from("account5").to("entitlement1").property("type", "Groups").property("createTime", Instant.parse("2020-05-26T12:50:56.00Z")).
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("data", "{\"Name\":\"Group1\", \"Description\":\"desc\"}").
                property("category", "category").
                addE("Is_Related_To").from("account5").to("entitlement2").property("type", "Groups").property("createTime", Instant.parse("2020-05-26T12:40:56.00Z")).
                property("valuePath", "valuePath").property("valueSignature", "valueSignature").property("data", "{\"Name\":\"Group2\", \"Description\":\"desc\"}").
                property("category", "category").
                addE("With_Event").from("accEntity1").to("event4").
                addE("With_Event").from("accEntity1").to("event5").
                addE("With_Event").from("accEntity2").to("event6").
                addE("With_Event").from("accEntity2").to("event7").
                addE("With_Event").from("accEntity3").to("event8").
                addE("With_Event").from("accEntity4").to("event9").
                addE("With_Event").from("accEntity5").to("event1").
                addE("With_Event").from("accEntity5").to("event2").
                addE("With_Event").from("accEntity5").to("event3").
                addE("With_Event").from("accEntity6").to("event10").
                addE("With_Event").from("accEntity7").to("event11").
                addE("With_Alert").from("accEntity5").to("alert1").
                addE("With_Alert").from("accEntity5").to("alert2").
                addE("With_Alert").from("accEntity5").to("alert3").
                iterate();
    }


}

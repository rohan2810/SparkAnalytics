package data

import java.time.Instant

g.addV("Alert").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("ackRequired", true).
        property("level", Byte.valueOf("1")).
        property("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130001")).
        property("updateTime", Instant.now()).
        property("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("summary", "summarized message").
        property("taskId", Short.valueOf("34")).property("alertType", "Schemaviolation").next()

g.addV("Alert").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("ackRequired", true).
        property("level", Byte.valueOf("2")).
        property("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130002")).
        property("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("updateTime", Instant.now()).
        property("summary", "summarized message").
        property("taskId", Short.valueOf("34")).
        property("alertType", "Schemaviolation").next()

g.addV("Alert").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("ackRequired", true).
        property("level", Byte.valueOf("1")).
        property("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130003")).
        property("updateTime", Instant.now()).
        property("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("summary", "summarized message").
        property("taskId", Short.valueOf("34")).
        property("alertType", "Schemaviolation").next()

g.addV("Alert").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("ackRequired", true).
        property("level", Byte.valueOf("2")).
        property("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130004")).
        property("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("updateTime", Instant.now()).
        property("summary", "summarized message").
        property("taskId", Short.valueOf("34")).
        property("alertType", "Schemaviolation").next()

// Entity

g.addV("Entity").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130001")).
        property("status", "Active").
        property("name", "John Doe").
        property("status", "open").
        property("metaType", "type1").next()

g.addV("Entity").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130002")).
        property("status", "Active").
        property("name", "John Doe").
        property("status", "cloase").
        property("metaType", "type2").next()


//Events


g.addV("Event").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).
        property("eventCategory", "StageChange").
        property("type", "ModifyEntity").
        property("processId", "04177aa6-97d2-11ea-bb37-0242ac130002").
        property("attributes", new HashMap<>().put("changeLog", "new value")).next()

//entity --> event

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").
        has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130001")).as("entity").
        V().hasLabel("Event").has("appId", "ad").has("nativeType", "inetorgperson").has("tenantId", "tenant1").has("eventCategory", "StageChange").has("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).has("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130001")).as("event").
        addE("With_Event").from("entity").to("event").next()


g.addV("Event").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).
        property("eventCategory", "StageChange").
        property("type", "ModifyEntity").
        property("processId", "04177aa6-97d2-11ea-bb37-0242ac130002").
        property("attributes", new HashMap<>().put("changeLog", "new value")).next()

//entity --> event

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").
        has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130001")).as("entity").
        V().hasLabel("Event").has("appId", "ad").has("nativeType", "inetorgperson").has("tenantId", "tenant1").has("eventCategory", "StageChange").has("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        has("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130002")).as("event").
        addE("With_Event").from("entity").to("event").next()
//
//
g.addV("Event").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130003")).
        property("eventCategory", "StageChange").
        property("type", "ModifyEntity").
        property("processId", "04177aa6-97d2-11ea-bb37-0242ac130002").
        property("attributes", new HashMap<>().put("changeLog", "new value")).next()

//entity --> event

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").
        has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130002")).as("entity").
        V().hasLabel("Event").has("appId", "ad").has("nativeType", "inetorgperson").has("tenantId", "tenant1").has("eventCategory", "StageChange").
        has("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).has("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130003")).as("event").
        addE("With_Event").from("entity").to("event").next()


g.addV("Event").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "inetorgperson").
        property("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).
        property("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130004")).
        property("eventCategory", "StageChange").
        property("type", "ModifyEntity").
        property("processId", "04177aa6-97d2-11ea-bb37-0242ac130002").
        property("attributes", new HashMap<>().put("changeLog", "new value")).next()


//entity --> event

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").
        has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130002")).as("entity").
        V().hasLabel("Event").has("appId", "ad").has("nativeType", "inetorgperson").has("tenantId", "tenant1").has("eventCategory", "StageChange").
        has("createTime", Instant.parse("2014-01-01T00:00:00.00Z")).has("gid", UUID.fromString("04177aa6-97d2-11ea-bb37-0242ac130004")).as("event").
        addE("With_Event").from("entity").to("event").next()


//person

//
g.addV("Person").
        property("tenantId", "tenant1").
        property("userId", "jdoe").
        property("status", "Active").
        property("userType", "Employee").
        property("displayName", "john doe").next()

g.addV("Person").
        property("tenantId", "tenant1").
        property("userId", "jdoe1").
        property("status", "Active").
        property("userType", "Employee").
        property("displayName", "john doe1").next()


//account
g.addV("Account").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("category", "Account").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130002")).
        property("status", "Active").
        property("name", "John Doe").next()
g.addV("Account").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe1").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("category", "Account").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130003")).
        property("status", "Active").
        property("name", "John Doe").next()


//account --> entity

g.V().hasLabel("Account").has("appId", "ad").has("nativeType", "inetorgperson").has("entityKey", "jdoe").has("tenantId", "tenant1").as("account").
        V().has("Entity", "appId", "ad").has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130001")).has("tenantId", "tenant1").as("entity").
        addE("Is").from("account").to("entity").next()

g.V().hasLabel("Account").has("appId", "ad").has("nativeType", "inetorgperson").has("entityKey", "jdoe1").has("tenantId", "tenant1").as("account").
        V().has("Entity", "appId", "ad").has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130002")).has("tenantId", "tenant1").as("entity").
        addE("Is").from("account").to("entity").next()


//account --> person

g.V().hasLabel("Account").
        has("nativeType", "inetorgperson").has("appId", "ad").has("tenantId", "tenant1").has("entityKey", "jdoe").as("a").
        addV("Person").property("userId", "jdoe").property("tenantId", "tenant1").as("p").
        addE("With_Actor").from("a").to("p").property("type", "owner").next()


g.V().hasLabel("Account").
        has("nativeType", "inetorgperson").has("appId", "ad").has("tenantId", "tenant1").has("entityKey", "jdoe1").as("a").
        addV("Person").property("userId", "jdoe1").property("tenantId", "tenant1").as("p").
        addE("With_Actor").from("a").to("p").property("type", "owner").next()

//
////entitlement
//
g.addV("Entitlement").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "groupOfUniqueNames").
        property("entityKey", "GROUP1").
        property("nativeId", "Group1").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("21174aa6-17d2-41ea-bb37-0242ac130001")).
        property("status", "Active").
        property("name", "Group 1").
        property("displayName", "Group 1").
        property("description", "desc").next()

g.addV("Entitlement").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("nativeType", "groupOfUniqueNames").
        property("entityKey", "GROUP2").
        property("nativeId", "Group2").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("21174aa6-17d2-41ea-bb37-0242ac130001")).
        property("status", "Active").
        property("name", "Group 1").
        property("displayName", "Group 1").
        property("description", "desc").next()


// account to entitlement

g.V().has("Entitlement", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "groupOfUniqueNames").has("entityKey", "GROUP1").as("entitlement").
        V().has("Account", "appId", "ad").has("nativeType", "inetorgperson").has("tenantId", "tenant1").has("entityKey", "jdoe").as("account").
        addE("Is_Related_To").from("account").to("entitlement").property("type", "Groups").next()


g.V().has("Entitlement", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "groupOfUniqueNames").has("entityKey", "GROUP2").as("entitlement").
        V().has("Account", "appId", "ad").has("nativeType", "inetorgperson").has("tenantId", "tenant1").has("entityKey", "jdoe1").as("account").
        addE("Is_Related_To").from("account").to("entitlement").property("type", "Groups").next()


// entity --> alert

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130001")).as("e").V().has("Alert", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("level", Byte.valueOf("1")).has("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).has("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130001")).as("a").addE("With_Alert").from("e").to("a").next()

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130001")).as("e").V().has("Alert", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("level", Byte.valueOf("2")).has("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).has("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130002")).as("a").addE("With_Alert").from("e").to("a").next()

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130002")).as("e").V().has("Alert", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("level", Byte.valueOf("1")).has("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).has("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130003")).as("a").addE("With_Alert").from("e").to("a").next()

g.V().has("Entity", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("entityGlobalId", UUID.fromString("81177aa6-97d2-11ea-bb37-0242ac130002")).as("e").V().has("Alert", "appId", "ad").has("tenantId", "tenant1").has("nativeType", "inetorgperson").has("level", Byte.valueOf("2")).has("raisedTime", Instant.parse("2014-01-01T00:00:00.00Z")).has("gid", UUID.fromString("03177aa8-97d2-11ea-bb37-0242ac130004")).as("a").addE("With_Alert").from("e").to("a").next()

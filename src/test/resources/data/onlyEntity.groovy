package data

import java.time.Instant

g.addV("Entity").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("be00fcf7-5ef5-416b-ae41-8f1a0d049c28")).
        property("status", "Active").
        property("name", "John Doe").
        property("status", "open").
        property("data","{\"name\":\"jdoe\",\"role\":\"role1\",\"age\":34}").
        property("metaType", "type1").next()

g.addV("Entity").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("72718101-d307-4202-8bb1-871ee3e1660b")).
        property("status", "Active").
        property("name", "John Doe").
        property("status", "cloase").
        property("data","{\"name\":\"jdoe2\",\"role\":\"role2\",\"age\":45}").
        property("metaType", "type2").next()

g.addV("Entity").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("c8eaa769-368b-4983-a7a1-bfd673f574a4")).
        property("status", "Active").
        property("name", "John Doe").
        property("status", "open").
        property("data","{\"name\":\"jdoe3\",\"role\":\"role3\",\"age\":15}").
        property("metaType", "type1").next()

g.addV("Entity").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("c67e2bd5-9f96-4802-bc41-abf10f68ef36")).
        property("status", "Active").
        property("name", "John Doe").
        property("status", "cloase").
        property("data","{\"name\":\"jdoe4\",\"role\":\"role4\",\"age\":7841}").
        property("metaType", "type2").next()

g.addV("Entity").
        property("tenantId", "tenant1").
        property("appId", "ad").
        property("entityKey", "jdoe").
        property("nativeId", "jdoe").
        property("nativeType", "inetorgperson").
        property("updateTime", Instant.now()).
        property("entityGlobalId", UUID.fromString("957f9775-df1b-4b8d-9d1c-83adf2b3cb59")).
        property("status", "Active").
        property("name", "John Doe").
        property("status", "cloase").
        property("data","{\"name\":\"jdoe5\",\"role\":\"role5\",\"age\":78}").
        property("metaType", "type2").next()

/*





schema.vertexLabel('Entity').
        ifNotExists().
        property('status', Ascii).
        property('metaType', Ascii).
        property('nativeStatus', Ascii).
        property('updateTime', Timestamp).
        property('nativeAsOnTime', Timestamp).
        property('nativeModifiedOnTime', Timestamp).
        property('createTime', Timestamp).
        property('nativeId', Text).
        property('data', Text).
        property('name', Text).
        create()
 */
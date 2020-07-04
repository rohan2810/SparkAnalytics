import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class EntityMapper implements Function<Map<String, Object>, Row> {

    public static final StructType schema = new StructType(new StructField[]{
            new StructField("data", DataTypes.StringType, true, Metadata.empty()),
            new StructField("status", DataTypes.StringType, true, Metadata.empty()),
            new StructField("metaType", DataTypes.StringType, true, Metadata.empty()),
            new StructField("nativeStatus", DataTypes.StringType, true, Metadata.empty()),
            new StructField("nativeAsOnTime", DataTypes.TimestampType, true, Metadata.empty()),
            new StructField("nativeModifiedOnTime", DataTypes.TimestampType, true, Metadata.empty()),
            new StructField("nativeId", DataTypes.StringType, true, Metadata.empty()),
            new StructField("name", DataTypes.StringType, true, Metadata.empty()),
            new StructField("$relationships", DataTypes.createArrayType(DataTypes.createStructType(new StructField[]{
                    new StructField("type", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("data", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("valuePath", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("valueSignature", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("category", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("relatedEntityKey", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("relatedEntityGlobalId", DataTypes.StringType, true, Metadata.empty()),
            })), true, Metadata.empty()),
            new StructField("$actors", DataTypes.createArrayType(DataTypes.createStructType(new StructField[]{
                    new StructField("type", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("data", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("valuePath", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("valueSignature", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("userId", DataTypes.StringType, true, Metadata.empty()),
            })), true, Metadata.empty()),
            new StructField("$alerts", DataTypes.createArrayType(DataTypes.createStructType(new StructField[]{
                    new StructField("level", DataTypes.ByteType, true, Metadata.empty()),
                    new StructField("raisedTime", DataTypes.TimestampType, true, Metadata.empty()),
                    new StructField("gid", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("alertType", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("summary", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("taskId", DataTypes.ShortType, true, Metadata.empty()),
                    new StructField("valuePath", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("valueSignature", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("messages", DataTypes.createArrayType(DataTypes.createStructType(new StructField[]{
                            new StructField("Severity", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("Message", DataTypes.StringType, true, Metadata.empty())
                    }), true), true, Metadata.empty()),
                    new StructField("acqRequired", DataTypes.BooleanType, true, Metadata.empty()),
                    new StructField("acknowledged", DataTypes.BooleanType, true, Metadata.empty()),

            })), true, Metadata.empty()),

    });

    private final BiFunction<Map<String, Object>, StructType, Row> relationshipMapper = new BiFunction<Map<String, Object>, StructType, Row>() {
        @Override
        public Row apply(Map<String, Object> data, StructType schema) {
            Object[] fields = new Object[schema.length()];
            StructField[] fieldSchema = schema.fields();
            Object value;
            Map<String, Object> relatedEntity = (Map<String, Object>) data.get("RelatedEntity");
            Map<String, Object> vertex = (Map<String, Object>) relatedEntity.get("$RelatedEntity");
            Map<String, Object> edge = (Map<String, Object>) relatedEntity.get("Is_Related_To");
            for (int i = 0; i < fieldSchema.length; i++) {
                if (fieldSchema[i].name().equals("relatedEntityKey")) {
                    value = unwrap(vertex.get("entityKey"));
                } else if (fieldSchema[i].name().equals("relatedEntityGlobalId")) {
                    value = unwrap(vertex.get("entityGlobalId"));
                } else {
                    value = unwrap(edge.get(fieldSchema[i].name()));
                }
                fields[i] = value;
            }
            return RowFactory.create(fields);
        }
    };
    private final BiFunction<Map<String, Object>, StructType, Row> alertMapper = new BiFunction<Map<String, Object>, StructType, Row>() {
        @Override
        public Row apply(Map<String, Object> data, StructType schema) {
            Object[] fields = new Object[schema.length()];
            StructField[] fieldSchema = schema.fields();
            Map<String, Object> alert = (Map<String, Object>) data.get("Alert");
            Object value = null;
            for (int i = 0; i < fieldSchema.length; i++) {
                if (fieldSchema[i].name().equals("messages")) {
                    if (alert.get(fieldSchema[i].name()) != null) {
                        value = alert.get(fieldSchema[i].name());
                        if (value instanceof List) {
                            Object[] fieldValue = ((List<List<List<String>>>) value).get(0).stream().map(
                                    listOfTup -> RowFactory.create(listOfTup.toArray())
                            ).toArray();
                            fields[i] = fieldValue;
                        } else
                            throw new IllegalArgumentException("Expected a list of object, got: " + value + value.getClass().getName());
                    } else {
                        fields[i] = null;
                    }
                } else {
                    value = unwrap(alert.get(fieldSchema[i].name()));
                    fields[i] = value;
                }
            }
            return RowFactory.create(fields);
        }
    };
    private final BiFunction<Map<String, Object>, StructType, Row> actorMapper = new BiFunction<Map<String, Object>, StructType, Row>() {
        @Override
        public Row apply(Map<String, Object> data, StructType schema) {

            Object[] fields = new Object[schema.size()];
            StructField[] fieldSchema = schema.fields();
            Object value;
            Map<String, Object> actor = (Map<String, Object>) data.get("Actor");
            Map<String, Object> edge = (Map<String, Object>) actor.get("With_Actor");
            Map<String, Object> vertex = (Map<String, Object>) data.get("Person");
            for (int i = 0; i < fieldSchema.length; i++) {
                value = unwrap(edge.get(fieldSchema[i].name()));
                fields[i] = value;
            }
            return RowFactory.create(fields);
        }

    };

    public Row call(Map<String, Object> data) {
        StructField[] fieldSchemaArr = schema.fields();
        Object[] rowValues = new Object[fieldSchemaArr.length];
        Map<String, Object> entity = (Map) data.get("Entity");

        for (int iFieldSchema = 0; iFieldSchema < fieldSchemaArr.length; iFieldSchema++) {
            StructField fieldSchema = fieldSchemaArr[iFieldSchema];
            switch (fieldSchema.name()) {
                case "$relationships":
                    ArrayType relationshipSchema = (ArrayType) fieldSchema.dataType();
                    Object[] rowValue = ((List<Map<String, Object>>) data.get(fieldSchema.name())).stream().map(
                            aRawValue -> {
                                return relationshipMapper.apply(aRawValue, (StructType) relationshipSchema.elementType());
                            }
                    ).toArray();
                    rowValues[iFieldSchema] = rowValue;
                    break;
                case "$actors":
                    ArrayType actorSchema = (ArrayType) fieldSchema.dataType();
                    Object[] rowValue1 = ((List<Map<String, Object>>) data.get("$relationships")).stream().map(
                            aRawValue -> {
                                return actorMapper.apply(aRawValue, (StructType) actorSchema.elementType());
                            }
                    ).toArray();
                    rowValues[iFieldSchema] = rowValue1;
                    break;
                case "$alerts":
                    ArrayType alertSchema = (ArrayType) fieldSchema.dataType();
                    Object[] rowValue2 = ((List<Map<String, Object>>) data.get(fieldSchema.name())).stream().map(
                            aRawValue -> {
                                return alertMapper.apply(aRawValue, (StructType) alertSchema.elementType());
                            }
                    ).toArray();
                    rowValues[iFieldSchema] = rowValue2;
                    break;

                default:
                    // This is entity attribute
                    Object rawValue = entity.get(fieldSchema.name());
                    rowValues[iFieldSchema] = unwrap(rawValue);
                    break;
            }

        }
        return RowFactory.create(rowValues);
    }
//
//    public Row callX(Map<String, Object> data) {
//
//        StructField[] fieldSchema = schema.fields();
//        List<Object> fields = new ArrayList<>(fieldSchema.length);
//        for (int i = 0; i < data.size(); i++) {
//            Object rawValue;
//            Object fieldValue;
//            Map<String, Object> entity = (Map<String, Object>) data.get("Entity");
//            for (int j = 0; j < entity.size() - 1; j++) {
//                rawValue = entity.get(fieldSchema[i].name());
//                fieldValue = unwrap(rawValue);
//                fields.add(i++, fieldValue);
//            }
//            rawValue = data.get(fieldSchema[i].name());
//            if (rawValue != null) {
//                switch (fieldSchema[i].name()) {
//                    case "$relationships":
//                        ArrayType relationshipSchema = (ArrayType) fieldSchema[i].dataType();
//                        fieldValue = ((List<Map<String, Object>>) rawValue).stream().map(
//                                aRawValue -> {
//                                    return relationshipMapper.apply(aRawValue, (StructType) relationshipSchema.elementType());
//                                }
//                        ).collect(Collectors.toList());
//                        fields.add(i, fieldValue);
//                    case "$actors":
//                        ArrayType actorSchema = (ArrayType) fieldSchema[i].dataType();
//                        rawValue = data.get(fieldSchema[i].name());
//                        fieldValue = ((List<Map<String, Object>>) rawValue).stream().map(
//                                aRawValue -> {
//                                    return actorMapper.apply(aRawValue, (StructType) actorSchema.elementType());
//                                }
//                        ).collect(Collectors.toList());
//                        fields.add(i, fieldValue);
//                    case "$alerts":
//                        ArrayType alertSchema = (ArrayType) fieldSchema[i + 2].dataType();
//                        rawValue = data.get(fieldSchema[i + 2].name());
//                        fieldValue = ((List<Map<String, Object>>) rawValue).stream().map(
//                                aRawValue -> {
//                                    return alertMapper.apply(aRawValue, (StructType) alertSchema.elementType());
//                                }
//                        ).collect(Collectors.toList());
//                        fields.add(i, fieldValue);
//                    default:
//                        fieldValue = unwrap(rawValue);
//                        break;
//                }
//            } else {
//                fields.add(i, null);
//            }
//        }
//        return RowFactory.create(fields);
//    }


    private Object unwrap(Object obj) {
        Object value;
        if (obj == null) {
            value = null;
        } else {
            List<?> val = (List<?>) obj;
            if (val.get(0) instanceof UUID) {
                value = val.get(0).toString();
            } else if (val.get(0) instanceof Instant) {
                value = Timestamp.from((Instant) val.get(0));
            } else {
                value = val.get(0);
            }
        }
        return value;
    }

}

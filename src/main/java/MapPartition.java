import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.fge.jsonpatch.JsonPatch.fromJson;

public class MapPartition {

    public static Dataset<Row> petitioner(Dataset<Row> merged) {
        StructType schema = merged.schema();
        Dataset<Row> ds = merged.mapPartitions(new MapPartitionsFunction<Row, Row>() {

                                                   final ObjectMapper mapper = new ObjectMapper();

                                                   @Override
                                                   public Iterator<Row> call(Iterator<Row> input) throws Exception {
                                                       JsonNode changes;
                                                       List<Row> resultantRows = new ArrayList<>();
                                                       if (input.hasNext()) {
                                                           Row currentRow = input.next();
                                                           Object[] values = new Object[currentRow.size()];
                                                           String data = currentRow.getAs("data");
                                                           String changeLog = currentRow.getAs("changelog");
                                                           JsonNode dataNode = mapper.readTree(data);
                                                           JsonNode changeNode = mapper.readTree(changeLog);
                                                           JsonPatch patch = fromJson(changeNode);
                                                           changes = patch.apply(dataNode);

                                                           StructField[] fields = schema.fields();
                                                           for (int i = 0; i < fields.length; i++) {
                                                               if (fields[i].name().equals("data")) {
                                                                   values[i] = (changes.toString());
                                                               } else
                                                                   values[i] = (currentRow.get(i));
                                                           }
                                                           resultantRows.add(RowFactory.create(values));
                                                       }
                                                       return resultantRows.iterator();
                                                   }
                                               }, RowEncoder.apply(schema)
        );
        return ds;
    }

    public static void updateGraph(DseGraphFrame gf, Dataset<Row> mapPartition) {
        gf.updateVertices("Entity", mapPartition);
        System.out.println("update done");
    }
}

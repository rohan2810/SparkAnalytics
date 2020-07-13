package update.impl;

import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.datastax.bdp.graph.spark.graphframe.DseGraphFrameBuilder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import update.inter.GraphUpdate;


public class DseUpdate implements GraphUpdate {
    private DseGraphFrame gf;

    public DseUpdate(SparkSession sparkSession, String graphName) {
        this.gf = DseGraphFrameBuilder.dseGraph(graphName, sparkSession);
    }

    @Override
    public void updateVertices(String vertexLabel, Dataset<Row> vertices) {
        gf.updateVertices(vertexLabel, vertices);
    }

    @Override
    public void updateEdges(String inVertexLabel, String edgeLabel, String outVertexLabel, Dataset<Row> edges) {
        gf.updateEdges(inVertexLabel, edgeLabel, outVertexLabel, edges);
    }

    @Override
    public void deleteVertices(String vertexName) {
        gf.deleteVertices(vertexName);
    }

    @Override
    public void deleteEdges(Dataset<Row> edges) {
        gf.deleteEdges(edges);
    }

    @Override
    public void deleteEdges(Dataset<Row> edges, Boolean cache) {
        gf.deleteEdges(edges, cache);
    }

    @Override
    public void cleanUp() {
        gf.cleanUp();
    }
}

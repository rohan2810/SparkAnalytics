package update.inter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface GraphUpdate {


    /**
     * Update Graph Vertices - Create or Modify
     *
     * @param vertices    the dataframe must have the primary key's and other column name must match with the graph Vertex's property name
     * @param vertexLabel Vertex label
     */
    public void updateVertices(String vertexLabel, Dataset<Row> vertices);


    /**
     * Update Graph Edges - Create or Modify
     *
     * @param inVertexLabel  Source Vertex label
     * @param edgeLabel      edge label(relationship)
     * @param outVertexLabel Destination Vertex label
     * @param edges          the dataframe must have the primary keys of both the src vertex and the dst vertex, other column must match with the Graph Edge's property name
     */
    public void updateEdges(String inVertexLabel, String edgeLabel, String outVertexLabel, Dataset<Row> edges);


    /**
     * Delete Graph Vertices
     *
     * @param vertexName Label of the vertex to be deleted
     */
    public void deleteVertices(String vertexName);


    /**
     * Delete Graph Edges
     *
     * @param edges the dataframe must have the primary keys of both the src vertex and the dst vertex, other column must match with the Graph Edge's property name
     */
    public void deleteEdges(Dataset<Row> edges);


    /**
     * Delete Graph Edges
     *
     * @param edges the dataframe must have the primary keys of both the src vertex and the dst vertex, other column must match with the Graph Edge's property name
     * @param cache cache
     */
    public void deleteEdges(Dataset<Row> edges, Boolean cache);

    /**
     * Remove any invalid edge entries from the database backend.
     */
    public void cleanUp();

}

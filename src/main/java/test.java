import com.datastax.dse.driver.api.core.graph.DseGraph;
import com.datastax.dse.driver.api.core.graph.GraphResultSet;
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement;
import com.datastax.oss.driver.api.core.CqlSession;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

public class test {
    public static void main(String[] args) {
        CqlSession session = CqlSession.builder().build();
        GraphTraversalSource g = AnonymousTraversalSource.traversal().withRemote(DseGraph.remoteConnectionBuilder(session).build());
//        System.out.println(g.V().hasLabel("Contribution").valueMap().toList());
        String str = "g.V().hasLabel('Contribution').outE('SubmittedBy').subgraph('sg').cap('sg').next().traversal()";
        String str1 = "g.V().hasLabel('Contribution')";
        ScriptGraphStatement statement = ScriptGraphStatement.builder(str).build();
        GraphResultSet result = session.execute(statement);
//        for (GraphNode node : result){
//            System.out.println(node);
//        }

//        List< Object> objects= g.V().hasLabel("Contribution").outE("SubmittedBy").subgraph("sg").cap("sg");
//        System.out.println(objects.size());
//        System.out.println(g.V().hasLabel("Contribution").valueMap().toList());
//        System.out.println(sg.V().valueMap().toList());

//
//
//        GraphTraversal sg =  g.V().hasLabel("Contribution").has("gid", "c9").outE("SubmittedBy").subgraph("sg").inV().hasLabel("Person").outE("AssessedBy").subgraph("sg").cap("sg");
//
//        System.out.println(sg.V().count().next());
//        System.out.println(g.V().count().next());
//        List<Vertex> sg = sg.V().toList();
//        List<Vertex> d1 = g.V().toList();
//        System.out.println(sg.toString());
//        System.out.println();
//        System.out.println(d1.toString());
    }
}
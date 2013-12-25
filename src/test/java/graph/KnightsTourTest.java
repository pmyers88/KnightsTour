package graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.junit.Assert;

public class KnightsTourTest extends TestCase {
    
    private static final int MAX_MOVES = 25;
    
    @SuppressWarnings("serial")
    Map<Integer, List<Integer>> PHONE_PAD_GRAPH = Collections.unmodifiableMap(new HashMap<Integer, List<Integer>>(){{
        // from key 0, the night can move to key 4 and key 6, etc
        put(0, Arrays.asList(4,6));
        put(1, Arrays.asList(6,8));
        put(2, Arrays.asList(7,9));
        put(3, Arrays.asList(4,8));
        put(4, Arrays.asList(0,3,9));
        put(5, null);
        put(6, Arrays.asList(0,1,7));
        put(7, Arrays.asList(2,6));
        put(8, Arrays.asList(1,3));
        put(9, Arrays.asList(2,4));
    }});

    public void testKnightsTour() {
        // build the graph
        List<Vertex> graph = new ArrayList<Vertex>();
        for(int i = 0; i < 10; i++) {
            graph.add(new Vertex(i));
        }
        for(Vertex v: graph) {
            List<Integer> connections = PHONE_PAD_GRAPH.get(v.getValue());
            if(connections != null) {
                for(Integer index: connections) {
                    Vertex vertex = graph.get(index);
                    v.addConnection(vertex);
                }
            }
        }
        
        // set the graph data structure
        Graph g = new Graph(graph);
        
        // for max moves 0, answer should be 0
        Assert.assertEquals(0, g.findNumPaths(0, 0));
        
        // for max moves = 1, answer should be == number of connections
        for(Entry<Integer, List<Integer>> e : PHONE_PAD_GRAPH.entrySet()) {
            if(e.getValue() != null) {
                Assert.assertEquals(e.getValue().size(), g.findNumPaths(e.getKey(), 1));
            }
        }

        // calculated that the answer is 10 for 3 moves starting at 1
        Assert.assertEquals(10, g.findNumPaths(1, 3));
        
        // compared fast method to slow method
        long start = System.currentTimeMillis();
        long answerFast = g.findNumPaths(1, MAX_MOVES);
        long endFast = System.currentTimeMillis();
        long answerSlow = g.findNumPathsSlow(1, MAX_MOVES);
        long endSlow = System.currentTimeMillis();
        Assert.assertEquals(answerFast, answerSlow);
        
        System.out.println("Fast find paths took " + (endFast - start) + " milliseconds.");
        System.out.println("Slow find paths took " + (endSlow - endFast) + " milliseconds.");
        System.out.println("Fast answer: " + answerFast + " paths found.");
        System.out.println("Slow answer: " + answerSlow + " paths found.");
    }
}

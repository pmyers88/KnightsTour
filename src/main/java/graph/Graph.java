package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    
    private List<Vertex> vertices = new ArrayList<Vertex>();
    
    // cache the number of paths for each numMoves/vertex value pair
    // e.g. if the number of paths has been found for start key 1/
    // maximum of 5 moves, save that answer and reuse it
    private Map<Pair<Integer>, Long> movesValueCache;
    
    public Graph() {
        movesValueCache = new HashMap<Pair<Integer>, Long>();
    }
    
    public Graph(List<Vertex> vertices) {
        this.vertices.addAll(vertices);
        movesValueCache = new HashMap<Pair<Integer>, Long>();
    }
    
    public void addConnection(Vertex vertex) {
        vertices.add(vertex);
        // need to clear the cache if the graph has changed
        if(!movesValueCache.isEmpty()) {
            movesValueCache.clear();
        }
    }
    
    public void removeConnection(Vertex vertex) {
        vertices.remove(vertex);
        if(!movesValueCache.isEmpty()) {
            movesValueCache.clear();
        }
    }
    
    public long findNumPaths(int startingPoint, int maxMoves) {
        if(maxMoves == 0) {
            return 0;
        }
        Vertex start = vertices.get(startingPoint);
        return findNumPaths(start, maxMoves);
    }
    
    private long findNumPaths(Vertex start, int numMoves) {
        Pair<Integer> key = new Pair<Integer>(numMoves, start.getValue());
        Long answer = movesValueCache.get(key);
        // we've already found the answer for this vertex/maxMoves, so return it
        if(answer != null) {
            return answer;
        }
        if(numMoves == 0) {
            return 1;
        } 
        long sum = 0;
        // decrement the number of moves remaining
        numMoves--;
        for(Vertex v: start.getConnections()) {
            // recursively find the number of paths for all the connections of v
            sum += findNumPaths(v, numMoves);
        }
        // cache the answer for this input
        movesValueCache.put(key, sum);
        return sum;
    }
    
    // non-cached version...much slower
    public long findNumPathsSlow(int startingPoint, int maxLength) {
        if(maxLength == 0) {
            return 0;
        }
        Vertex start = vertices.get(startingPoint);
        return findNumPathsSlow(start, maxLength);
    }
    
    private long findNumPathsSlow(Vertex start, int level) {
        if(level == 0) {
            return 1;
        }
        long sum = 0;
        level--;
        for(Vertex v: start.getConnections()) {
            sum += findNumPathsSlow(v, level);
        }
        return sum;
    }
    
    private final class Pair<T> {
        private final T level;
        private final T value;
        
        private int hash;
        
        public Pair(T level, T value) {
            if(level == null || value == null) {
                throw new IllegalArgumentException("Entries cannot be null");
            }
            this.level = level;
            this.value = value;
        }
        
        @Override
        public boolean equals(Object o) {
            if(o == this) {
                return true;
            }
            if(!(o instanceof Pair)) {
                return false;
            }
            // raw types because cannot be sure of the parameterized type of o
            @SuppressWarnings("rawtypes")
            Pair p = (Pair) o;
            return p.level.equals(level) && p.value.equals(value);
        }
        
        @Override
        public int hashCode() {
            int h = hash;
            if(hash == 0) {
                h = 31;
                h += 17 * level.hashCode();
                h += 17 * value.hashCode();
                hash = h;
            }
            return h;
        }
    }

}

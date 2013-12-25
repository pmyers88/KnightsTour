package graph;

import java.util.LinkedList;
import java.util.List;


public class Vertex {
	
	private final Integer value;
	private List<Vertex> connections;
	
	public Vertex(Integer value, List<Vertex> connections) {
		this.value = value;
		this.connections = connections;
	}
	
	public Vertex(Integer value) {
		this.value = value;
		this.connections = new LinkedList<Vertex>();
	}
	
	public void addConnection(Vertex vertex) {
		connections.add(vertex);
	}
	
	public void removeConnection(Vertex vertex) {
		connections.remove(vertex);
	}
	
	public Integer getValue() {
		return value;
	}
	
	public List<Vertex> getConnections() {
		return connections;
	}
 
}

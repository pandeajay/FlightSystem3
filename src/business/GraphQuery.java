package business;

import graphs.Graph;

import java.util.List;

public interface GraphQuery {
	public void initialize(Graph graph) throws Exception;
	public double findShortestPathWeight(String from, String to );				
	public List<?> findShortestPathVertices(String from, String to );

}

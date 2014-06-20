package business;

import graphs.Graph;

import java.util.List;

public interface GraphQuery {
	public void initialize(Graph graph) throws Exception;
	public double getShortestPathWeight(String from, String to );				
	public List<?> getShortestPathVetices(String from, String to );

}

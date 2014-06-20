package business.impl;

import graphs.Graph;

import java.util.List;

import business.GraphQuery;

public class GraphQueryImpl implements GraphQuery {
	private Graph graph = null;
	
	@Override
	public void initialize(Graph graph) throws Exception {
		if(this.graph != null){
			throw new Exception("GraphQueryImpl is already initialized");
		}
		this.graph = graph;		
	}	

	@Override
	public double getShortestPathWeight(String from, String to) {
		return this.graph.getShortestPathWeight(from, to);
	}

	@Override
	public List<?> getShortestPathVetices(String from, String to) {
		return this.graph.getShortestPathVetices(from, to);
	}



	
}

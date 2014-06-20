package business.impl;

import graphs.Graph;

import java.util.List;
import java.util.Map;

import utilities.Utils;
import business.Edge;
import business.GraphBuilder;
import business.Node;

public class GraphBuilderImpl implements GraphBuilder {
	
	private Graph graph = null;

	public boolean initialize(Graph userGraph) throws Exception{
		if(this.graph != null){			
			throw new Exception ("Graph already initialized");
		}
		this.graph  = userGraph;
		return true;		
	}

	@Override
	public void buildGraph() {
		try{
			Map<String, String> userInputMap = Utils.getDataNodesFile();		
			String nodesDataPath = userInputMap.get("DataFile");
			List<Node> newNodesList = Utils.getAllNodesFromJson(nodesDataPath);				
			graph.addNodes(newNodesList);
			graph.addEdges(newNodesList);
		}catch(Exception ex){
			
		}		
	}

	@Override
	public void addNode(Node node) {
		try{
			graph.addNode(node);
			
		}catch(Exception ex){
			
		}	
		
	}



	@Override
	public void createEdgesForNode(Node node) {
		try{
			graph.addEdge(node);
			
		}catch(Exception ex){
			
		}
		
	}

	@Override
	public void deleteNode(String nodeId) {
		try{
			graph.deleteNode(nodeId);
			
		}catch(Exception ex){
			
		}		
	}

	@Override
	public void close() {
		this.graph.close();
		this.graph = null;
		
	}
}

package business.impl;

import graphs.Graph;
import graphs.impl.Jgraph;
import graphs.impl.NeoGraph;
import graphs.impl.NeoGraphRest;

import java.util.List;
import java.util.Map;

import org.jgraph.JGraph;

import utilities.Logger;
import utilities.Utils;
import business.Edge;
import business.GraphBuilder;
import business.Node;

public class GraphBuilderImpl implements GraphBuilder {
	
	private Graph graph = null;
	Logger logger = new Logger();
	logger.init();

	public boolean initialize() throws Exception{
		
		if(this.graph != null){			
			throw new Exception ("Graph already initialized");
		}
		
		String graphStyle = Utils.getDataNodesFile().get("Graph-Style");
		if(graphStyle.equals("NeoGraph")){
			this.graph  = new NeoGraph();
		}else if(graphStyle.equals("NeoGraphREST")){
			this.graph  = new NeoGraphRest();
		}else if(graphStyle.equals("JGraph")){
			this.graph  = new Jgraph();
		}
		
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

	@Override
	public Graph getGraph() {
		return this.graph;
	}
}

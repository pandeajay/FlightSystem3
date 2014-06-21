package business.impl;

import graphs.Graph;
import graphs.impl.Jgraph;
import graphs.impl.NeoGraph;
import graphs.impl.NeoGraphRest;

import java.util.List;
import java.util.Map;

import log.impl.LogImpl;

import org.jgraph.JGraph;

import utilities.Logger;
import utilities.Utils;
import business.Edge;
import business.GraphBuilder;
import business.Node;

public class GraphBuilderImpl implements GraphBuilder {
	
	private Graph graph = null;	
	private Logger logger = Logger.getLogger(new LogImpl());

	public boolean initialize() throws Exception{			
		logger.fine("Intializing GraphBuilderImpl ...");		
		
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
		
		logger.fine("Intialized GraphBuilderImpl.");		
		return true;		
	}

	@Override
	public void buildGraph() {
		try{
			logger.fine("Buidling Graph...");	
			Map<String, String> userInputMap = Utils.getDataNodesFile();		
			String nodesDataPath = userInputMap.get("DataFile");
			List<Node> newNodesList = Utils.getAllNodesFromJson(nodesDataPath);				
			graph.addNodes(newNodesList);
			graph.addEdges(newNodesList);
		}catch(Exception ex){
			logger.warning("Error in buidling graph. Error : " + ex);	
		}		
	}

	@Override
	public void addNode(Node node) {
		try{
			logger.fine("Adding node " + node.getNodeId() + "...");	
			graph.addNode(node);
			logger.fine("Added a node " + node.getNodeId());				
		}catch(Exception ex){
			logger.warning("Error in buidling graph. Error : " + ex);				
		}			
	}



	@Override
	public void createEdgesForNode(Node node) {
		try{
			logger.fine("Creating edges for a node " + node.getNodeId() + " ...");	
			graph.addEdge(node);
			logger.fine("Created edges for a node " + node.getNodeId() );	
			
		}catch(Exception ex){
			logger.warning("Error in creating edges for a node"+  node.getNodeId() + " . Error : " + ex);				
		}
		
	}

	@Override
	public void deleteNode(String nodeId) {
		try{
			logger.fine("Deleting a node "+ nodeId +" ...");	
			graph.deleteNode(nodeId);
			logger.fine("Deleted a node "+ nodeId);
			
		}catch(Exception ex){
			logger.warning("Error in deleting node " +nodeId + ". Error : " + ex);				
		}		
	
	}

	@Override
	public void close() {
		try{
			logger.fine("Closing graph ...  " );	
			this.graph.close();
			this.graph = null;
			logger.fine("Closed graph . " );
		}catch(Exception ex){
			logger.warning("Error in closing graph. Error : " + ex);			
		}
		
	}

	@Override
	public Graph getGraph() {
		return this.graph;
	}
}

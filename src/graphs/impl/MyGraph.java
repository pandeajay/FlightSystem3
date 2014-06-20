package graphs.impl;


import graphs.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import business.Edge;
import business.impl.EdgeImpl;
import business.Node;


public class MyGraph implements Graph{	
	Map<String, List<String> > nodesAndConnections = new HashMap<String, List<String>>();
	Map<String, Edge> edges = new HashMap<String, Edge>();	
	Map<String, Node> nodes = new HashMap<String, Node>();
	Map<String, String> graph = new HashMap<String, String>();
	AtomicInteger edgeIdentity = new AtomicInteger();
	String nodeSeparator = "-";
	String pathSeparator = "::";
	Map<String, String> weightPath = new HashMap<String, String>();
	String minWeightStr = "minWeight=";
	String minPathsStr = ";paths=";
	
	public MyGraph() {
		super();
		edgeIdentity.set(0);
	}
	


	/**
	 * Gets specified node
	 * @param nodeName
	 * @return
	 */
	Node getNode(String nodeName) {
		return this.nodes.get(nodeName);
	}
	
	List<String> getIdsFromNode (String nodeId){
		List<String> listFromNode = new ArrayList<String>();
		Node node = getNode(nodeId);
		Iterator<Entry<String, String>>  it = node.getEdges().entrySet().iterator();
		while(it.hasNext()){
			String str = it.next().getKey();
			listFromNode.add(str);
		}
				
		return listFromNode;		
	}
	
	
	public List<String> getAllPaths(String startNodeId){
		List<String> tempList = new ArrayList<String>();
		Node startNode = getNode(startNodeId);
		if (startNode.getEdges().entrySet().size() == 0){
			return tempList;
		}
		Iterator<Entry<String, String>> it = startNode.getEdges().entrySet().iterator();
		while(it.hasNext()){			
			tempList.add(it.next().getKey());			
		}
		Iterator<String> it2 = tempList.iterator();
		while(it2.hasNext()){
			tempList.addAll(getAllPaths(it2.next()));
		}		
		return tempList;
		
		
	}
	
	public Vector<String> findPath (String from){
		Vector<String> paths = new Vector<String>();		
		try{			
			//try building possibilities from the existing nodes
			Node startNode = getNode(from);			
			Iterator<Entry<String, String>> it = startNode.getEdges().entrySet().iterator();
			
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				paths.add(from + nodeSeparator + entry.getKey() );
			}		
			
			int depth = 0;
			for(int i = 0 ; i < paths.size()  ; i++ ) {
				String str = paths.get(i);
				String[] path  = str.split(nodeSeparator);
				String lastNodeStr = path[path.length - 1];
				Node lastNode = getNode(lastNodeStr);
				Iterator<Entry<String, String>> it2 = lastNode.getEdges().entrySet().iterator();
				
				while(it2.hasNext()){
					depth++;
					if(depth > 100){
						break;
					}					
					Entry<String, String> entry = it2.next();
					paths.add(str + nodeSeparator + entry.getKey() );
				}					
			}		
		
		}catch(Exception e){
			System.out.println("findShortestPath exception " + e);
		}
		return paths;
		
	}

	/**
	 * Creates a node from passed JSon node
	 */
	@Override
	public long addNode(Node node) {
		try {
			List<String> toList = new ArrayList<String>();
			Iterator<Entry<String, String>> it = node.getEdges().entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				toList.add(entry.getKey());
			}
			if(this.nodesAndConnections.containsKey(node.getNodeId())){
				List<String> list = this.nodesAndConnections.get(node.getNodeId());
				list.addAll(toList);
				
			}else{
				this.nodesAndConnections.put(node.getNodeId(),  toList);
			}
			nodes.put(node.getNodeId(), node);			
		} catch (Exception e) {
			System.out.println("Exception while createNode in the Graph. Reason ::" + e);
		}
		return 1;
		
	}


	/**
	 * For a from and to pair, returns weight of the shortest path between from and to
	 */

	@Override
	public double getShortestPathWeight(String from, String to) {
		
		//don't recalculate. Have this intelligence build in the system
		if(weightPath.containsKey(from + nodeSeparator + to	)){
			String str = weightPath.get(from + nodeSeparator + to	);
			String[] strs = str.split(";");
			double weight = 0.0;
			for(int i = 0 ; i < strs.length ; i++){
				String temp = strs[i];
				if(temp.contains(minWeightStr)){
					weight = Double.parseDouble(temp.substring(minWeightStr.length()));
					break;
				}			
			}			
			return weight;
		}
		
		List<Object> paths = getPathVertices(from, to);
		Map<String, String> tempWeightPath = new HashMap<String, String>();
		
		int depth = 0 ; 
		Iterator<Object> it = paths.iterator();
		while(it.hasNext() && depth < 100){
			depth++;
			double weight = 0.0;
			String path = (String) it.next();
			String[] pathNodes = path.split(nodeSeparator);
			for(int i = 0 ; i <= pathNodes.length - 2; i++){
				Node prevNode = getNode(pathNodes[i]);
				Node nextNode = getNode(pathNodes[i+1]);
				weight = weight +  Double.parseDouble(prevNode.getEdges().get(nextNode.getNodeId()));				
			}
			tempWeightPath.put(path, ""+weight);
		}
		
		Iterator<Entry<String, String>> it2 = tempWeightPath.entrySet().iterator();
		String minPath = "";
		double minWeight = 0.0; 
		while(it2.hasNext()){
			Entry<String, String> entry = it2.next();
			if(minPath.length() == 0){
				minPath = entry.getKey();
				minWeight = Double.parseDouble(entry.getValue());
			}else{
				if(minWeight > Double.parseDouble(entry.getValue())){
					minPath = entry.getKey();
					minWeight = Double.parseDouble(entry.getValue());					
				}				
			}
		}	
		weightPath.put(from + nodeSeparator + to, minWeightStr + minWeight + minPathsStr + minPath);		
		return minWeight;
	}


	/**
	 * Gets path vertices
	 * @param from
	 * @param to
	 * @return
	 */
	
	public List<Object> getPathVertices(String from, String to) {
		List<Object> list = new ArrayList<Object>();
		Vector<String> paths = findPath(from);
		Iterator<String> it = paths.iterator();
		int depth = 0;
		while(it.hasNext() && depth < 100){
			depth++;
			String path = it.next();
			if(path.contains(nodeSeparator + to )){
				list.add(path);		
			}
		}		
		return list;		
	}
	
	/**
	 * For a from and to pair returns shortest path vertices between from and to
	 */
	@Override
	public List<String> getShortestPathVetices(String from, String to) {
		getShortestPathWeight(from, to);		
		List<String> list = new ArrayList<String>();	
		String paths = weightPath.get(from + nodeSeparator + to);
		paths = paths.substring(  paths.indexOf(";")+ minPathsStr.length());
		String[] path = paths.split(nodeSeparator);
		for(String temp : path){
			list.add(temp);
		}

		return list;		
	}

	@Override
	public void close() {
		nodes.clear();
		nodesAndConnections.clear();
		edges.clear();
		
	}

	/**
	 * Creates nodes from JSon nodes
	 */
	@Override
	public void addNodes(List<Node> nodes) {
		for(Node node : nodes){
			addNode(node);
		}		
	}

	/**
	 * Creates edges from nodes
	 */
	@Override
	public void addEdges(List<Node> nodes) {
		for(Node node : nodes){
			addEdge(node);
		}
		
	}

	/**
	 * Creates edge from node
	 */
	@Override
	public long  addEdge(Node node) {		
		try{
			Iterator<Entry<String, String>> it = node.getEdges().entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				int id	= edgeIdentity.incrementAndGet();
				Edge edgeInfo = new EdgeImpl(""+id, node.getNodeId(), entry.getKey(), entry.getValue());
				edges.put(""+id, edgeInfo);
			}			
		}catch(Exception e){
			System.out.println("Exception while createEdge in the Graph. Reason ::" 	+ e);
			return 0;
		}
		
		return edgeIdentity.longValue();
		
	}

	/**
	 * Deletes a specified node
	 */
	@Override
	public long deleteNode(String nodeId) {
		try {
			this.nodes.remove(nodeId);
			this.nodesAndConnections.remove(nodeId);				
		} catch (Exception e) {
			System.out.println("Exception while deleteing in the Graph. Reason ::" 	+ e);
			return 0;
		}
		return 1;
	}

	/**
	 * Deletes specified nodes
	 */
	@Override
	public void deleteNodes(List<Node> nodes) {
		for(Node node : nodes){
			deleteNode(node.getNodeId());
		}	
	}

	/**
	 * Gets all edges in graph
	 */
	@Override
	public List<Object> getAllEdges() {
		List<Object> edgeList = new ArrayList<Object>();
		Iterator<Entry<String, Edge>> it = edges.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Edge> entry = it.next();
			edgeList.add(entry.getValue());
		}
		return null;
	}

	/**
	 * Deletes all nodes from Graph
	 */
	@Override
	public int deleteAllNodes() {
		nodesAndConnections.clear();
		nodes.clear();
		return 1;		
	}

	/**
	 * Deletes all edges from a Graph
	 */
	@Override
	public int deleteAllEdges() {
		edges.clear();
		return 1;
	}
}

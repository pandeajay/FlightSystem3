import graphs.Graph
;
import graphs.impl.MyGraph;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import business.Node;
import utilities.Utils;


public class MainMyGraph {

	public static void main(String[] args) {
		new MainMyGraph().start();

	}

	private void start() {
		
		Map<String, String> userInputMap = Utils.getDataNodesFile();		
		String nodesDataPath = userInputMap.get("DataFile");
		List<Node> newNodesList = Utils.getAllNodesFromJson(nodesDataPath);	
		
		Graph graph = new MyGraph();
		
		graph.addNodes(newNodesList);
		graph.addEdges(newNodesList);
		
		List<String> queryList = Utils.getFromAndToList(userInputMap);
		for(String str : queryList){
			String[] strs = str.split("-");
			if(strs.length == 2){
				
				double weight = graph.getShortestPathWeight(strs[0], strs[1]);				
				List<?> edgeList = graph.getShortestPathVetices(strs[0], strs[1]);				
				Iterator<?> it = edgeList.iterator();				
				String shortestPath = "";
				while(it.hasNext()){
					Object edge = it.next();
					shortestPath += edge.toString();					
				}
				
				System.out.println("For fromtoPair = " + strs[0]+"--" + strs[1] 
						+"     shortestPath = " + (shortestPath.length() == 0 ? "Path does not exist"  : shortestPath) 
						+ " and its weight = " +weight + (weight > 999 ? "   practically impossible" : ""));
			}
		}		
	}

}

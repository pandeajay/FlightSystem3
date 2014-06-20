import graphs.Graph
;
import graphs.impl.NeoGraph;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import business.impl.GraphBuilderImpl;
import business.impl.GraphQueryImpl;
import utilities.Utils;


public class MainNeoGraph {

	String nodeSeparator = "-";
	public static void main(String[] args) {
		new MainNeoGraph().start();

	}

	private void start() {
		try {
			GraphBuilderImpl graphBuilder = new GraphBuilderImpl();					
			graphBuilder.initialize();
			graphBuilder.buildGraph();
			
			GraphQueryImpl query = new GraphQueryImpl();
			query.initialize(graphBuilder.getGraph());
			
			Map<String, String> userInputMap = Utils.getDataNodesFile();			
			List<String> queryList = Utils.getFromAndToList(userInputMap);
			
			
			
			for(String str : queryList){
				String[] strs = str.split("-");
				if(strs.length == 2){
					
					double weight = query.getShortestPathWeight(strs[0], strs[1]);				
					List<?> edgeList = query.getShortestPathVetices(strs[0], strs[1]);				
					Iterator<?> it = edgeList.iterator();				
					String shortestPath = "";
					while(it.hasNext()){
						Object edge = it.next();
						shortestPath += edge.toString() + nodeSeparator;					
					}
					if(shortestPath != null && shortestPath.length() > 0  ){
						shortestPath = shortestPath.substring(0,shortestPath.length() - 1);
					}
					
					System.out.println("For fromtoPair = " + strs[0]+"--" + strs[1] +
							"     shortestPath = " + (shortestPath.length() == 0 ? "Path does not exist"  : shortestPath)+ 
							" and its weight = " +weight+ (weight > 999 ? "   practically impossible" : "") );
				}
			}				
			
			graphBuilder.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	

}

/**
 * 
*/
package tests;

import static org.junit.Assert.*;
import graphs.Graph;
import graphs.impl.Jgraph;
import graphs.impl.MyGraph;
import graphs.impl.NeoGraph;
import graphs.impl.NeoGraphRest;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import business.Node;
import business.impl.NodeImpl;
import utilities.Utils;


public class GraphTests {

	static Graph graph = null;
	static String pathForInputFile;
	static List<Node> newNodesList;
	String nodesDataPath;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass for test");
		graph = new NeoGraph();
		//graph = new NeoGraphRest();
		//graph = new Jgraph();
		//graph = new MyGraph();
		String flightSystemsHome  = System.getenv("FlightSystems_Home");
		pathForInputFile = flightSystemsHome + "\\FlightSystem\\user-files\\test.json";
		newNodesList = Utils.getAllNodesFromJson(pathForInputFile);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		graph.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		createAllNodes();
		//createAllEdges();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// deleteAllEdges();
		deleteAllNodes();		
	}
	
	@Test
	 public void testNodesFromJson() {
		assertEquals(newNodesList.size(),4);
	} 
	
	@Test
	 public void testEachNodesFromJson() {
		for(Node node : newNodesList){
			assertNotNull(node.getNodeId());
			assertNotEquals(node.getNodeId().length(),0);
		}	
	} 

	@Test
	 public void testGraphNotNull() {
		assertNotNull(graph);
		assertNotNull(pathForInputFile);
	} 

	@Test
	public void testCreateNode() {
		deleteAllNodes();
		assertTrue(graph.addNode(newNodesList.get(0)) > 0);
		
	}

	public int createAllNodes() {
		for (Node node : newNodesList) {
			assertTrue(graph.addNode(node) > 0);
		}
		return 1;
	}

	public int deleteAllNodes() {
		return graph.deleteAllNodes();
	}

	@Test
	public void testCreateEdge() {		
		assertTrue(graph.addEdge(newNodesList.get(1)) > 0);		
	} 

	@Test
	public void testDeleteNode() {		
		assertTrue(graph.deleteNode(newNodesList.get(1).getNodeId()) > 0);		
	}
	
	@Test
	public void testCreateAllNodes() {
		deleteAllNodes();
		assertEquals(1,createAllNodes());
	} 
	
	@Test
	public void testDeleteAllNodes() {
		assertEquals(1,deleteAllNodes());
	}
	
	public void createAllEdges(){
		graph.addEdges(newNodesList);
	}
	
	public int deleteAllEdges(){
		return graph.deleteAllEdges();
	}
	
	@Test
	public void testShortestPath() {
		String from = "1";
		String to = "4";
		double weight = graph.getShortestPathWeight(from, to);	
		List<?> edgeList = graph.getShortestPathVetices(from, to);				
		Iterator<?> it = edgeList.iterator();				
		String shortestPath = "";
		
		while(it.hasNext()){
			Object edge = it.next();
			shortestPath += edge.toString() + "-";					
		}
		
		if(shortestPath != null && shortestPath.length() > 0  ){
			shortestPath = shortestPath.substring(0,shortestPath.length() - 1);
		}			
	
		
		String result = "For fromtoPair = " + from +"--" + to +
				"     shortestPath = " + (shortestPath.length() == 0 ? "Path does not exist"  : shortestPath)+ 
				" and its weight = " +weight + (weight > 999 ? "   practically impossible" : "");
		
		assertEquals(result,"For fromtoPair = 1--4     shortestPath = 1-3-4 and its weight = 4.0");
	}
	
	@Test
	public void testShortestPathWeight() {
		String from = "1";
		String to = "4";
		double weight = graph.getShortestPathWeight(from, to);
		assertEquals(""+weight, "4.0");
	}
	
	@Test
	public void testShortestPathVertices() {
		String from = "1";
		String to = "4";
		List<?> vertices = graph.getShortestPathVetices(from, to);	
		assertEquals(vertices.size(), 3);
		assertTrue(vertices.contains("1"));
		assertTrue(vertices.contains("3"));
		assertTrue(vertices.contains("4"));
	}
	
	@Test
	public void testDeleteAllEdges() {
		assertEquals(1, deleteAllEdges());		
	}
}

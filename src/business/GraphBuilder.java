/**
 * 
 */
package business;

import graphs.Graph;

/**
 * @author apande
 *
 */
public interface GraphBuilder {
	boolean initialize() throws Exception ;
	void buildGraph();
	void addNode(Node node );	
	void createEdgesForNode(Node node );
	void deleteNode(String nodeId );
	Graph getGraph();
	void close();
}

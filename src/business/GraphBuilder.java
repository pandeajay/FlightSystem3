/**
 * 
 */
package business;

import java.util.List;

import graphs.Graph;

/**
 * @author apande
 *
 */
public interface GraphBuilder {
	public boolean initialize(Graph userGraph) throws Exception ;
	public void buildGraph();
	public void addNode(Node node );	
	public void createEdgesForNode(Node node );
	public void deleteNode(String nodeId );
	public void close();
}

package business;

import java.util.List;

public interface Edge {	
	public String getId();
	public String getStartVetex(String edgeId);
	public String getEndVetex(String edgeId);
	public String getWeight(String edgeId);

}

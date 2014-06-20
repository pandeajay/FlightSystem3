package business.impl;

import business.Edge;

public class EdgeImpl implements Edge{
	
	private String id;
	private String from;
	private String to;
	private String weight;
	
	public EdgeImpl(String edgeId, String from, String to, String weight) {
		super();
		this.id = edgeId;
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	@Override
	public String getStartVetex(String edgeId) {
		return this.from;
	}

	@Override
	public String getEndVetex(String edgeId) {
		return this.to;
	}

	@Override
	public String getWeight(String edgeId) {
		return this.weight;
	}

	@Override
	public String getId() {
		return this.id;
	}

}

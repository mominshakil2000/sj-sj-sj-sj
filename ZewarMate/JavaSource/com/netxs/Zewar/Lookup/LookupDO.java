package com.netxs.Zewar.Lookup;

public class LookupDO {
	private int id;

	private String label;

	public LookupDO() {
	}

	public LookupDO(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
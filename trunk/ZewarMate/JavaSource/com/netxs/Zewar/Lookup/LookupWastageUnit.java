package com.netxs.Zewar.Lookup;

import java.util.*;

import com.netxs.Zewar.Lookup.LookupDO;

public class LookupWastageUnit {
	private static ArrayList list = new ArrayList();

	static {
		list.add(new LookupDO(1, "Percentage"));
		list.add(new LookupDO(2, "Raati"));
	}

	public static Collection getList() {
		return list;
	}
}
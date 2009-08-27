package com.netxs.Zewar.Lookup;

import java.util.*;

import com.netxs.Zewar.Lookup.LookupDO;

public class LookupItemValueCalculateBy {
	private static ArrayList list = new ArrayList();

	static {
		list.add(new LookupDO(1, "Quantity"));
		list.add(new LookupDO(2, "Weight"));
	}

	public static Collection getList() {
		return list;
	}
}
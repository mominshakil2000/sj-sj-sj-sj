package com.netxs.Zewar.Lookup;

import java.util.*;

import com.netxs.Zewar.Lookup.LookupDO;

public class LookupItemStockType {
	private static ArrayList list = new ArrayList();

	static {
		list.add(new LookupDO(1, "Our Stock"));
		list.add(new LookupDO(2, "Customer"));
	}

	public static Collection getList() {
		return list;
	}
}
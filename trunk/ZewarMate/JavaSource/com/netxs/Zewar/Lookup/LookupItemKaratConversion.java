package com.netxs.Zewar.Lookup;

import java.util.*;

import com.netxs.Zewar.Lookup.LookupDO;

public class LookupItemKaratConversion {
	private static ArrayList list = new ArrayList();

	static {
		list.add(new LookupDO(1, "Increase"));
		list.add(new LookupDO(2, "Decrease"));
	}

	public static Collection getList() {
		return list;
	}
}
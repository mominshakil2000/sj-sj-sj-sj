package com.netxs.Zewar.Lookup;

import java.util.*;

import com.netxs.Zewar.Lookup.LookupDO;

public class LookupWeightUnit {
	private static ArrayList list = new ArrayList();

	static {
		list.add(new LookupDO(1, "Grams"));
		list.add(new LookupDO(2, "Carats"));
	}

	public static Collection getList() {
		return list;
	}
}
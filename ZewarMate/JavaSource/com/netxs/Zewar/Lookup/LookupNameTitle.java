package com.netxs.Zewar.Lookup;

import java.util.*;

import com.netxs.Zewar.Lookup.LookupDO;

public class LookupNameTitle {
	private static ArrayList list = new ArrayList();

	static {
		list.add(new LookupDO(0, "Mr."));
		list.add(new LookupDO(0, "Mrs."));
		list.add(new LookupDO(0, "Miss."));
	}

	public static Collection getList() {
		return list;
	}
}
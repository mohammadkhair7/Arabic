package net.oujda_nlp_team.util;

import java.util.Iterator;
import java.util.Map.Entry;

import net.oujda_nlp_team.config.Database;

public class SortOutput {

	private static final SortOutput instance = new SortOutput();
	private java.util.Map<String, Integer> mapFrequency = new java.util.HashMap<>();
	
	public static SortOutput getInstance() {
		return instance;
	}
	private SortOutput() {
		chargeFrequency();
	}
	public void chargeFrequency() {
		String fileLemma = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("DATA.Derived.Patterns.Lemmas.Voweled.Canonic.Frequency");		
		this.mapFrequency = IOFile.getInstance().deserializeMap(fileLemma);
	}

	public static java.util.Map<String, Integer> descendingOrder(java.util.Map<String, Integer> map) {

		java.util.List<java.util.Map.Entry<String, Integer>> entryList = new java.util.ArrayList<>(map.entrySet());

		java.util.Collections.sort(entryList, new java.util.Comparator<java.util.Map.Entry<String, Integer>>() {
			@Override
			public int compare(java.util.Map.Entry<String, Integer> entry1,
					java.util.Map.Entry<String, Integer> entry2) {
				// Compare values in descending order
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});

		java.util.Map<String, Integer> sortedMap = new java.util.LinkedHashMap<>();

		for (java.util.Map.Entry<String, Integer> entry : entryList) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public java.util.Map<String, Integer> getMapFrequency() {
		return mapFrequency;
	}

	public java.util.List<String> descendingOrderList(java.util.List<String> list) {
		java.util.Map<String, Integer> map = new java.util.HashMap<>();
		java.util.List<String> listSorted = new java.util.ArrayList<>();
		java.util.Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String token = (String) it.next();
			map.put(token, !this.mapFrequency.containsKey(token) ? 1 : this.mapFrequency.get(token));
		}
//		System.out.println(map.toString());
		Iterator<Entry<String, Integer>> it2 = descendingOrder(map).entrySet().iterator();
		while (it2.hasNext()) {
			java.util.Map.Entry<String, Integer> entry = it2.next();
			String key = entry.getKey();
			listSorted.add(key);
		}
		return listSorted;
	}

}

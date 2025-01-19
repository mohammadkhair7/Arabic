package net.oujda_nlp_team.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.SortOutput;
import net.oujda_nlp_team.util.Transliteration;

public class ResultList {
	private List<Result> allResult = new ArrayList<>();

	public void add(Result res) {
		this.allResult.add(res);
	}

	public void addAll(List<Result> res) {
		this.allResult.addAll(res);
	}

	private boolean isEmpty() {
		return this.allResult.isEmpty();
	}

	public boolean isAnalyzed() {
		return !isEmpty();
	}

	public int size() {
		return this.allResult.size();
	}

	public List<Result> getAllResults() {
		return this.allResult;
	}

	public void print(List<String> result) {
		Iterator<String> it_result = result.iterator();
		while (it_result.hasNext()) {
			String st_result = it_result.next();
			String bw = Transliteration.getInstance().getArabicToBuckWalter(st_result);
			System.out.println(st_result + "\t" + bw);
		}
	}

	public String listToString(List<String> result) {
		String res = "";
		Iterator<String> it_result = result.iterator();
		while (it_result.hasNext()) {
			String st_result = it_result.next();
			res = res + st_result + " ";
		}
		return res.trim();
	}

	public List<String> getAllRoots() {
		List<String> _res = new ArrayList<>();
		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				String st_result = ((Result) it_result.next()).getRoot();
				if (!lSet.contains(st_result)) {
					lSet.add(st_result);
					_res.add(st_result);
				}
			}
		}
		return _res;
	}

	public List<String> getAllLemmas() {
		List<String> _res = new ArrayList<>();
		List<String> _resSort = new ArrayList<>();

		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				String st_result = ((Result) it_result.next()).getLemma();
				if (!lSet.contains(st_result)) {
					lSet.add(st_result);
					_res.add(st_result);
				}
			}
		}
		/*-------------------samir---------------------*/	 
//		_resSort =  SortOutput.getInstance().descendingOrderList(_res);
		/*---------------------------------------------*/
//		return _resSort;
		return _res;
	}
	/*-------------------samir---------------------*/	
	public List<String> getAllLemmasByFrequency() {
		List<String> _res = new ArrayList<>();
		List<String> _resSort = new ArrayList<>();

		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				String st_result = ((Result) it_result.next()).getLemma();
				if (!lSet.contains(st_result)) {
					lSet.add(st_result);
					_res.add(st_result);
				}
			}
		}
		/*-------------------samir---------------------*/	 
		return  SortOutput.getInstance().descendingOrderList(_res);

		/*---------------------------------------------*/
	}
	

	public List<String> getAllDiac() {
		List<String> _res = new ArrayList<>();
		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				String st_result = ((Result) it_result.next()).getVoweledWord();
				if (!lSet.contains(st_result)) {
					lSet.add(st_result);
					_res.add(st_result);
				}
			}
		}
		return _res;
	}

	public Set<String> getAllDiacMajrour() {
		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				Result re = it_result.next();
				String st_result = re.getVoweledWord();
				String st_Mood = re.getCaseOrMood();
				if (!lSet.contains(st_result) && st_Mood.equals("مجرور"))
					lSet.add(st_result);
			}
		}
		return lSet;
	}

	public List<String> getAllStems() {
		List<String> _res = new ArrayList<>();
		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				String st_result = ((Result) it_result.next()).getStem();
				if (!lSet.contains(st_result)) {
					lSet.add(st_result);
					_res.add(st_result);
				}
			}
		}
		return _res;
	}

	public List<String> getAllVoweledWord() {
		List<String> _res = new ArrayList<>();
		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				String st_result = ((Result) it_result.next()).getVoweledWord();
				st_result = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(st_result);
				if (!lSet.contains(st_result)) {
					lSet.add(st_result);
					_res.add(st_result);
				}
			}
		}
		return _res;
	}

	public String getAllVoweledWordString() {
		String str = "";
		Set<String> lSet = new HashSet<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				String st_result = ((Result) it_result.next()).getVoweledWord();
				st_result = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(st_result);
				if (!lSet.contains(st_result)) {
					lSet.add(st_result);
					str = str + "<a class='dropdown-item' style='font-size: 1.5em;'>" + st_result + "</a>";
				}
			}
		}
		return str;
	}

	public Map<String, Set<String>> getAllLemmaStems() {
		Map<String, Set<String>> map = new HashMap<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				Result res = it_result.next();
				String lemma = res.getLemma();
				String stem = res.getStem();
				if (map.containsKey(lemma)) {
					((Set<String>) map.get(lemma)).add(stem);
					continue;
				}
				Set<String> lStem = new HashSet<>();
				lStem.add(stem);
				map.put(lemma, lStem);
			}
		}
		return map;
	}

	public Map<String, Set<String>> getAllLemmaRoots() {
		Map<String, Set<String>> map = new HashMap<>();
		if (!this.allResult.isEmpty()) {
			Iterator<Result> it_result = this.allResult.iterator();
			while (it_result.hasNext()) {
				Result res = it_result.next();
				String lemma = res.getLemma();
				String root = res.getRoot();
				if (map.containsKey(lemma)) {
					((Set<String>) map.get(lemma)).add(root);
					continue;
				}
				Set<String> lRoot = new HashSet<>();
				lRoot.add(root);
				map.put(lemma, lRoot);
			}
		}
		return map;
	}

	public void sort() {
		Map<String, Result> iMap = new HashMap<>();
		Iterator<Result> it = this.allResult.iterator();
		while (it.hasNext()) {
			Result rs = it.next();
			if (!iMap.containsKey(rs.toString()))
				iMap.put(rs.toString(), rs);
		}
		this.allResult = new ArrayList<>();
		Iterator<String> itt = iMap.keySet().iterator();
		for (; itt.hasNext(); add(iMap.get(itt.next())))
			;
		Collections.sort(this.allResult);
	}

	public String toString() {
		return this.allResult.toString();
	}

	public void printResult() {
		Iterator<Result> it = this.allResult.iterator();
		while (it.hasNext()) {
			Result rs = it.next();
			String word = rs.getVoweledWord();
			String root = rs.getRoot();
			String lemma = rs.getLemma();
			String stem = rs.getStem();
			String pLemma = rs.getPatternLemma();
			String pStem = rs.getPatternStem();
			String mood = rs.getCaseOrMood();
			String pos = rs.getPartOfSpeech();
			String enc = rs.getEnclitic();
			String pro = rs.getProclitic();
			String p = "";
			if (root.length() > 0) {
				p = "" + root.charAt(0);
				for (int i = 1; i < root.length(); i++)
					p = p + "." + root.charAt(i);
			}
			String res = word + "\t[" + p + "] " + mood + "\n";
			res = res + "الجذع: " + stem + "/" + pStem + "/\tالفرع: " + lemma + "/" + pLemma + "/\n";
			res = res + pos + "\n";
			res = res + pro + "\t" + enc;
			System.out.println(res);
			System.out.println("------------------------");
		}
	}

	public void printAllStems() {
		Iterator<String> it = getAllStems().iterator();
		while (it.hasNext()) {
			String word = it.next();
			String bwWord = Transliteration.getInstance().getArabicToBuckWalter(word);
			System.out.println(word + "\t/" + bwWord + "/");
		}
	}

	public boolean containsType(String type) {
		Iterator<Result> it = this.allResult.iterator();
		while (it.hasNext()) {
			Result rs = it.next();
			String word = rs.getVoweledWord();
			String root = rs.getRoot();
			String lemma = rs.getLemma();
			String stem = rs.getStem();
			String pLemma = rs.getPatternLemma();
			String pStem = rs.getPatternStem();
			String mood = rs.getCaseOrMood();
			String pos = rs.getPartOfSpeech();
			String enc = rs.getEnclitic();
			String pro = rs.getProclitic();
			if (pos.startsWith("اسم|" + type))
				return true;
		}
		return false;
	}

	public List<String> getAllResultInLine() {
		List<String> result = new ArrayList<>();
		Iterator<Result> it = this.allResult.iterator();
		while (it.hasNext()) {
			Result rs = it.next();
			String word = rs.getVoweledWord();
			String root = rs.getRoot();
			String lemma = rs.getLemma();
			String stem = rs.getStem();
			String pLemma = rs.getPatternLemma();
			String pStem = rs.getPatternStem();
			String mood = rs.getCaseOrMood();
			String pos = rs.getPartOfSpeech();
			String enc = rs.getEnclitic();
			String pro = rs.getProclitic();
			String priority = rs.getPriority();
			result.add(priority + rs);
		}
		Collections.sort(result, Collections.reverseOrder());
		return result;
	}

	public String getAllLemmasString() {
		List<String> list = getAllLemmas();
		StringBuilder str = new StringBuilder();
		Iterator<String> it = list.iterator();
		for (; it.hasNext(); str.append(it.next()).append(':'))
			;
		return str.toString().trim();
	}

	public String getAllLemmasString(char diliminateur) {
		List<String> list = getAllLemmas();
		StringBuilder str = new StringBuilder();
		Iterator<String> it = list.iterator();
		for (; it.hasNext(); str.append(it.next()).append(diliminateur))
			;
		return str.toString().trim();
	}

	public String getAllRootString() {
		List<String> list = getAllRoots();
		StringBuilder str = new StringBuilder();
		Iterator<String> it = list.iterator();
		for (; it.hasNext(); str.append(it.next()).append(':'))
			;
		return str.toString().trim();
	}

	public String getAllStemString() {
		List<String> list = getAllStems();
		StringBuilder str = new StringBuilder();
		Iterator<String> it = list.iterator();
		for (; it.hasNext(); str.append(it.next()).append(':'));
		return str.toString().trim();

	}
}

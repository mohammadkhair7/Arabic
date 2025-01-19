package net.oujda_nlp_team.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oujda_nlp_team.entity.Formulas;
import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Root;
import net.oujda_nlp_team.entity.Unvoweled;
import net.oujda_nlp_team.entity.VEntity;
import net.oujda_nlp_team.entity.Voweled;

public abstract class DerivedFactory {
	protected List iCaseOrMood;

	protected List partOfSpeech;

	protected Map<Integer, List<Formulas>> vector;

	protected Map<Character, List<Root>> trilateralRootMap;

	protected Map<Character, List<Root>> quadriliteralRootMap;

	protected Map<String, Integer> trilateralRootList;

	protected Map<String, Integer> quadriliteralRootList;

	protected Map unvoweledPatternStem;

	protected Map voweledDiacPatternLemmaMap;

	protected Map voweledCanonicPatternLemmaMap;

	protected Map voweledDiacPatternStemMap;

	protected Map voweledCanonicPatternStemMap;

	public boolean isEmptyCaseOrMood() {
		return this.iCaseOrMood.isEmpty();
	}

	public VEntity getCaseOrMood(int index) {
		return !this.iCaseOrMood.isEmpty() ? (VEntity) this.iCaseOrMood.get(index - 1) : null;
	}

	public boolean isEmptyPartOfSpeech() {
		return this.partOfSpeech.isEmpty();
	}

	public PartOfSpeech getPartOfSpeech(int index) {
		return (PartOfSpeech) this.partOfSpeech.get(index - 1);
	}

	public boolean isEmptyVector() {
		return this.vector.isEmpty();
	}

	public boolean containsValVector(int len) {
		return (this.vector != null) ? this.vector.containsKey(Integer.valueOf(len)) : false;
	}

	public List<Formulas> getListVectors(int len) {
		return this.vector.get(Integer.valueOf(len));
	}

	public boolean isEmptyTrilateralRootMap() {
		return this.trilateralRootMap.isEmpty();
	}

	public boolean containsTrilateralCharRoot(char rootC1) {
		return (this.trilateralRootMap != null) ? this.trilateralRootMap.containsKey(Character.valueOf(rootC1)) : false;
	}

	public List<Root> getTrilateralRootMap(char rootC1) {
		return this.trilateralRootMap.get(Character.valueOf(rootC1));
	}

	public Root getTrilateralRoot(String root) {
		return getTrilateralRootMap(root.charAt(0)).get(getTrilateralRootList(root));
	}

	public boolean isEmptyQuadriliteralRootMap() {
		return this.quadriliteralRootMap.isEmpty();
	}

	public boolean containsQuadriliteralCharRoot(char rootC1) {
		return (this.quadriliteralRootMap != null) ? this.quadriliteralRootMap.containsKey(Character.valueOf(rootC1))
				: false;
	}

	public List<Root> getQuadriliteralRootMap(char rootC1) {
		return this.quadriliteralRootMap.get(Character.valueOf(rootC1));
	}

	public Root getQuadriliteralRoot(String root) {
		return getQuadriliteralRootMap(root.charAt(0)).get(getQuadriliteralRootList(root));
	}

	public boolean isEmptyTrilateralRootList() {
		return this.trilateralRootList.isEmpty();
	}

	public boolean containsTrilateralRoot(String root) {
		return (this.trilateralRootList != null) ? this.trilateralRootList.containsKey(root) : false;
	}

	public int getTrilateralRootList(String root) {
		return ((Integer) this.trilateralRootList.get(root)).intValue();
	}

	public boolean isEmptyQuadriliteralRootList() {
		return this.quadriliteralRootList.isEmpty();
	}

	public boolean containsQuadriliteralRoot(String root) {
		return (this.quadriliteralRootList != null) ? this.quadriliteralRootList.containsKey(root) : false;
	}

	public int getQuadriliteralRootList(String root) {
		return ((Integer) this.quadriliteralRootList.get(root)).intValue();
	}

	public boolean containsIdUnvoweled(int len) {
		return (this.unvoweledPatternStem != null) ? this.unvoweledPatternStem.containsKey(Integer.valueOf(len))
				: false;
	}

	public List getUnvoweledList(int len) {
		return (List) this.unvoweledPatternStem.get(Integer.valueOf(len));
	}

	public Unvoweled getUnvoweledForm(int len, int index) {
		return (Unvoweled) getUnvoweledList(len).get(index - 1);
	}

	public boolean containsIdVoweledDiacPatternLemma(int len) {
		return (this.voweledDiacPatternLemmaMap != null)
				? this.voweledDiacPatternLemmaMap.containsKey(Integer.valueOf(len))
				: false;
	}

	public List getVoweledDiacPatternLemmaList(int len) {
		return (List) this.voweledDiacPatternLemmaMap.get(Integer.valueOf(len));
	}

	public Voweled getVoweledDiacPatternLemma(int len, int index) {
		return (Voweled) getVoweledDiacPatternLemmaList(len).get(index - 1);
	}

	public boolean containsIdVoweledCanonicPatternLemma(int len) {
		return (this.voweledCanonicPatternLemmaMap != null)
				? this.voweledCanonicPatternLemmaMap.containsKey(Integer.valueOf(len))
				: false;
	}

	public List getVoweledCanonicPatternLemmaList(int len) {
		return (List) this.voweledCanonicPatternLemmaMap.get(Integer.valueOf(len));
	}

	public Voweled getVoweledCanonicPatternLemma(int len, int index) {
		return (Voweled) getVoweledCanonicPatternLemmaList(len).get(index - 1);
	}

	public boolean containsIdVoweledDiacPatternStem(int len) {
		return (this.voweledDiacPatternStemMap != null)
				? this.voweledDiacPatternStemMap.containsKey(Integer.valueOf(len))
				: false;
	}

	public List getVoweledDiacPatternStemList(int len) {
		return (List) this.voweledDiacPatternStemMap.get(Integer.valueOf(len));
	}

	public Voweled getVoweledDiacPatternStem(int len, int index) {
		return (Voweled) getVoweledDiacPatternStemList(len).get(index - 1);
	}

	public boolean containsIdVoweledCanonicPatternStem(int len) {
		return (this.voweledCanonicPatternStemMap != null)
				? this.voweledCanonicPatternStemMap.containsKey(Integer.valueOf(len))
				: false;
	}

	public List getVoweledCanonicPatternStemList(int len) {
		return (List) this.voweledCanonicPatternStemMap.get(Integer.valueOf(len));
	}

	public Voweled getVoweledCanonicPatternStem(int len, int index) {
		return (Voweled) getVoweledCanonicPatternStemList(len).get(index - 1);
	}

	public List getInfoResult(String idRoot, String idPatternStem, int len) {
		List<Formulas> result = new ArrayList();
		Set<String> listPatternStem = new HashSet<>();
		listPatternStem.addAll(Arrays.asList(idPatternStem.split(" ")));
		for (String idR : idRoot.split(" ")) {
			int idForm = Integer.parseInt(idR);
			Formulas ids = getListVectors(len).get(idForm - 1);
			if (listPatternStem.contains(ids.getIdDiacPatternStem()))
				result.add(ids);
		}

		return result;
	}

	public String getLenTrilateralRoot(String root, int len) {
		switch (len) {
		case 1:
			return getTrilateralRoot(root).getLen1();
		case 2:
			return getTrilateralRoot(root).getLen2();
		case 3:
			return getTrilateralRoot(root).getLen3();
		case 4:
			return getTrilateralRoot(root).getLen4();
		case 5:
			return getTrilateralRoot(root).getLen5();
		case 6:
			return getTrilateralRoot(root).getLen6();
		case 7:
			return getTrilateralRoot(root).getLen7();
		case 8:
			return getTrilateralRoot(root).getLen8();
		case 9:
			return getTrilateralRoot(root).getLen9();
		case 10:
			return getTrilateralRoot(root).getLen10();
		case 11:
			return getTrilateralRoot(root).getLen11();
		case 12:
			return getTrilateralRoot(root).getLen12();
		}
		return "";
	}

	public String getLenQuadriliteralRoot(String root, int len) {
		switch (len) {
		case 1:
			return getQuadriliteralRoot(root).getLen1();
		case 2:
			return getQuadriliteralRoot(root).getLen2();
		case 3:
			return getQuadriliteralRoot(root).getLen3();
		case 4:
			return getQuadriliteralRoot(root).getLen4();
		case 5:
			return getQuadriliteralRoot(root).getLen5();
		case 6:
			return getQuadriliteralRoot(root).getLen6();
		case 7:
			return getQuadriliteralRoot(root).getLen7();
		case 8:
			return getQuadriliteralRoot(root).getLen8();
		case 9:
			return getQuadriliteralRoot(root).getLen9();
		case 10:
			return getQuadriliteralRoot(root).getLen10();
		case 11:
			return getQuadriliteralRoot(root).getLen11();
		case 12:
			return getQuadriliteralRoot(root).getLen12();
		}
		return "";
	}

	public void clear() {
		this.iCaseOrMood.clear();
		this.vector.clear();
		this.partOfSpeech.clear();
		this.quadriliteralRootMap.clear();
		this.trilateralRootMap.clear();
		this.quadriliteralRootList.clear();
		this.trilateralRootList.clear();
		this.unvoweledPatternStem.clear();
		this.voweledDiacPatternLemmaMap.clear();
		this.voweledCanonicPatternLemmaMap.clear();
		this.voweledDiacPatternStemMap.clear();
		this.voweledCanonicPatternStemMap.clear();
	}
}

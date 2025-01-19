package net.oujda_nlp_team.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Settings;

public final class Result implements Comparable<Result>, Serializable {
	private static final long serialVersionUID = 124211L;

	private String voweledWord;

	private String stem;

	private String partofspeech;

	private String diacPatternStem;

	private String patternStem;

	private String lemma;

	private String patternLemma;

	private String root;

	private String caseormood;

	private String proclitic;

	private String enclitic;

	private String procliticNoDec;

	private String encliticNoDec;

	private String priority;

	public void setVoweledWord(String _voweledWord) {
		this.voweledWord = Settings.choiceVoweledword ? _voweledWord : "-";
	}

	public void setStem(String _stem) {
		this.stem = Settings.choiceStem ? _stem : "-";
	}

	public void setPartOfSpeech(String _partofspeech) {
		this.partofspeech = Settings.choicePartOfSpeech ? _partofspeech : "-";
	}

	public void setDiacPatternStem(String _diacPatternStem) {
		this.diacPatternStem = Settings.choiceDiacPattern ? _diacPatternStem : "-";
	}

	public void setPatternStem(String _patternStem) {
		this.patternStem = Settings.choicePatternStem ? _patternStem : "-";
	}

	public void setLemma(String _lemma) {
		this.lemma = Settings.choiceLemma ? _lemma : "-";
	}

	public void setPatternLemma(String _patternLemma) {
		this.patternLemma = Settings.choicePatternLemma ? _patternLemma : "-";
	}

	public void setRoot(String _root) {
		this.root = Settings.choiceRoot ? _root : "-";
	}

	public void setCaseOrMood(String _caseOrMood) {
		this.caseormood = Settings.choiceCaseOrMood ? _caseOrMood : "-";
	}

	public void setProclitic(String _proclitic) {
		this.proclitic = Settings.prefchoice ? _proclitic : "-";
	}

	public void setEnclitic(String _enclitic) {
		this.enclitic = Settings.suffixchoice ? _enclitic : "-";
	}

	public void setProcliticNoDec(String _procliticNoDec) {
		this.procliticNoDec = Settings.prefchoice ? _procliticNoDec : "-";
	}

	public void setEncliticNoDec(String _encliticNoDec) {
		this.encliticNoDec = Settings.suffixchoice ? _encliticNoDec : "-";
	}

	public void setPriority(String _priority) {
		this.priority = _priority;
	}

	public String getVoweledWord() {
		return this.voweledWord;
	}

	public String getStem() {
		return this.stem;
	}

	public String getPartOfSpeech() {
		return this.partofspeech;
	}

	public String getDiacPatternStem() {
		return this.diacPatternStem;
	}

	public String getPatternStem() {
		return this.patternStem;
	}

	public String getLemma() {
		return this.lemma;
	}

	public String getPatternLemma() {
		return this.patternLemma;
	}

	public String getRoot() {
		return this.root;
	}

	public String getCaseOrMood() {
		return this.caseormood;
	}

	public String getProclitic() {
		return this.proclitic;
	}

	public String getEnclitic() {
		return this.enclitic;
	}

	public String getProcliticNoDec() {
		return this.procliticNoDec;
	}

	public String getEncliticNoDec() {
		return this.encliticNoDec;
	}

	public String getPriority() {
		return this.priority;
	}

	public Result() {
	}

	public Result(List<String> result, String priority) {
		Iterator<String> ires = result.iterator();
		if (Settings.choiceVoweledword)
			this.voweledWord = ires.next();
		if (Settings.prefchoice)
			this.proclitic = ires.next();
		if (Settings.choiceStem)
			this.stem = ires.next();
		if (Settings.choicePartOfSpeech)
			this.partofspeech = ires.next();
		if (Settings.choiceDiacPattern)
			this.diacPatternStem = ires.next();
		if (Settings.choicePatternStem)
			this.patternStem = ires.next();
		if (Settings.choiceLemma)
			this.lemma = ires.next();
		if (Settings.choicePatternLemma)
			this.patternLemma = ires.next();
		if (Settings.choiceRoot)
			this.root = ires.next();
		if (Settings.choiceCaseOrMood)
			this.caseormood = ires.next();
		if (Settings.suffixchoice)
			this.enclitic = ires.next();
		this.priority = priority;
	}

	public Result(String _voweledWord, String _proclitic, String _stem, String _partofspeech, String wordDiacPattern,
			String _patternStem, String _lemma, String _patternLemma, String _root, String _cas, String _enclitic) {
		setVoweledWord(_voweledWord);
		setStem(_stem);
		setLemma(_lemma);
		setRoot(_root);
		setPatternStem(_patternStem);
		setPatternLemma(_patternLemma);
		setPartOfSpeech(_partofspeech);
		setCaseOrMood(_cas);
		setProclitic(_proclitic);
		setEnclitic(_enclitic);
	}

	public Result(String _voweledWord, String _proclitic, String _stem, String _partofspeech, String _diacPatternStem,
			String _patternStem, String _lemma, String _patternLemma, String _root, String _caseormood,
			String _enclitic, String _priority) {
		setVoweledWord(_voweledWord);
		setProclitic(_proclitic);
		setEnclitic(_enclitic);
		_diacPatternStem = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(_diacPatternStem);
		if (!_root.equals("#"))
			_stem = ArabicStringUtil.getInstance().getWordFromRootAndPattern(_root, _diacPatternStem);
		setStem(_stem);
		setDiacPatternStem(_diacPatternStem);
		setLemma(_lemma);
		setRoot(_root);
		setPatternStem(_patternStem);
		setPatternLemma(_patternLemma);
		setPartOfSpeech(_partofspeech);
		setCaseOrMood(_caseormood);
		setPriority(_priority);
	}

	public Result(String _voweledWord, String _proclitic, String _procliticNoDec, String _stem, String _partofspeech,
			String _diacPatternStem, String _patternStem, String _lemma, String _patternLemma, String _root,
			String _caseormood, String _enclitic, String _encliticNoDec, String _priority) {
		setVoweledWord(_voweledWord);
		setProclitic(_proclitic);
		setProcliticNoDec(_procliticNoDec);
		setEnclitic(_enclitic);
		setEncliticNoDec(_encliticNoDec);
		_diacPatternStem = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(_diacPatternStem);
		if (!_root.equals("#") && !_root.equals("-") && !_diacPatternStem.equals("-"))
			_stem = ArabicStringUtil.getInstance().getWordFromRootAndPattern(_root, _diacPatternStem);
		setStem(_stem);
		setDiacPatternStem(_diacPatternStem);
		setLemma(_lemma);
		setRoot(_root);
		setPatternStem(_patternStem);
		setPatternLemma(_patternLemma);
		setPartOfSpeech(_partofspeech);
		setCaseOrMood(_caseormood);
		setPriority(_priority);
	}

	public String toString() {
		return (Settings.choiceVoweledword ? ("\t" + this.voweledWord) : "")
				+ (Settings.prefchoice ? ("\t" + this.proclitic) : "") + (Settings.choiceStem ? ("\t" + this.stem) : "")
				+ (Settings.choicePartOfSpeech ? ("\t" + this.partofspeech) : "")
				+ (Settings.choicePatternStem ? ("\t" + this.patternStem) : "")
				+ (Settings.choiceLemma ? ("\t" + this.lemma) : "")
				+ (Settings.choicePatternLemma ? ("\t" + this.patternLemma) : "")
				+ (Settings.choiceRoot ? ("\t" + this.root) : "")
				+ (Settings.choiceCaseOrMood ? ("\t" + this.caseormood) : "")
				+ (Settings.suffixchoice ? ("\t" + this.enclitic) : "");
	}

	public String print() {
		return toString() + "|" + this.priority + "|";
	}

	public boolean equals(Object obj) {
		return (obj.hashCode() == hashCode());
	}

	public int hashCode() {
		int hash = this.voweledWord.hashCode();
		hash += this.stem.hashCode();
		hash += this.lemma.hashCode();
		hash += this.root.hashCode();
		hash += this.patternLemma.hashCode();
		hash += this.patternStem.hashCode();
		hash += this.proclitic.hashCode();
		hash += this.enclitic.hashCode();
		hash += this.partofspeech.hashCode();
		hash += this.caseormood.hashCode();
		return hash;
	}

	public int compareTo(Result o) {
		return o.getPriority().compareTo(getPriority());
	}
}

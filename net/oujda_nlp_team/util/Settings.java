package net.oujda_nlp_team.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public final class Settings {
	public static boolean choiceDataBase = false;

	public static boolean choiceVoweledword = true;

	public static boolean prefchoice = true;

	public static boolean choiceStem = true;

	public static boolean choicePartOfSpeech = true;

	public static boolean choicePatternStem = true;

	public static boolean choiceDiacPattern = true;

	public static boolean choiceLemma = true;

	public static boolean choicePatternLemma = true;

	public static boolean choiceRoot = true;

	public static boolean choiceCaseOrMood = true;

	public static boolean suffixchoice = true;

	public static boolean Tchoice = true;

	public static boolean isText = true;

	private static int ncoloumns;

	public void upDateSettings() {
		String fileIn = "/AlKhalil2/db/settings";
		File settings = new File(fileIn);
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(settings));
			out.write("dbchoice=" + (choiceDataBase ? "true" : "false") + "\n");
			out.write("vowchoice=" + (choiceVoweledword ? "true" : "false") + "\n");
			out.write("prefchoice=" + (prefchoice ? "true" : "false") + "\n");
			out.write("stemchoice=" + (choiceStem ? "true" : "false") + "\n");
			out.write("typechoice=" + (choicePartOfSpeech ? "true" : "false") + "\n");
			out.write("diacpatternchoice=" + (choiceDiacPattern ? "true" : "false") + "\n");
			out.write("canpatternchoice=" + (choicePatternStem ? "true" : "false") + "\n");
			out.write("lemmechoice=" + (choiceLemma ? "true" : "false") + "\n");
			out.write("lemmepatternchoice=" + (choicePatternLemma ? "true" : "false") + "\n");
			out.write("rootchoice=" + (choiceRoot ? "true" : "false") + "\n");
			out.write("poschoice=" + (choiceCaseOrMood ? "true" : "false") + "\n");
			out.write("suffixchoice=" + (suffixchoice ? "true" : "false") + "\n");
			out.close();
		} catch (IOException ex) {
			System.out.println("Erreur : " + ex);
		}
	}

	public void readSettings() throws IOException {
		String fileIn = "/AlKhalil2/db/settings";
		InputStream settings = IOFile.getInstance().openFileXml(fileIn);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(settings));
			choiceDataBase = in.readLine().endsWith("true");
			choiceVoweledword = in.readLine().endsWith("true");
			prefchoice = in.readLine().endsWith("true");
			choiceStem = in.readLine().endsWith("true");
			choicePartOfSpeech = in.readLine().endsWith("true");
			choiceDiacPattern = in.readLine().endsWith("true");
			choicePatternStem = in.readLine().endsWith("true");
			choiceLemma = in.readLine().endsWith("true");
			choicePatternLemma = in.readLine().endsWith("true");
			choiceRoot = in.readLine().endsWith("true");
			choiceCaseOrMood = in.readLine().endsWith("true");
			suffixchoice = in.readLine().endsWith("true");
			in.close();
		} catch (IOException ex) {
			System.out.println("Erreur : " + ex);
		}
	}

	public static void changeSettings(boolean _vowchoice, boolean _prefchoice, boolean _stemchoice, boolean _typechoice,
			boolean _canpatternchoice, boolean _diacpatternchoice, boolean _lemmechoice, boolean _lemmepatternchoice,
			boolean _rootchoice, boolean _poschoice, boolean _suffixchoice) {
		choiceVoweledword = _vowchoice;
		prefchoice = _prefchoice;
		choiceStem = _stemchoice;
		choicePartOfSpeech = _typechoice;
		choicePatternStem = _canpatternchoice;
		choiceDiacPattern = _diacpatternchoice;
		choiceLemma = _lemmechoice;
		choicePatternLemma = _lemmepatternchoice;
		choiceRoot = _rootchoice;
		choiceCaseOrMood = _poschoice;
		suffixchoice = _suffixchoice;
	}

	public static void changeSettings(boolean _dbchoice, boolean _vowchoice, boolean _prefchoice, boolean _stemchoice,
			boolean _typechoice, boolean _canpatternchoice, boolean _diacpatternchoice, boolean _lemmechoice,
			boolean _lemmepatternchoice, boolean _rootchoice, boolean _poschoice, boolean _suffixchoice) {
		choiceDataBase = _dbchoice;
		choiceVoweledword = _vowchoice;
		prefchoice = _prefchoice;
		choiceStem = _stemchoice;
		choicePartOfSpeech = _typechoice;
		choicePatternStem = _canpatternchoice;
		choiceDiacPattern = _diacpatternchoice;
		choiceLemma = _lemmechoice;
		choicePatternLemma = _lemmepatternchoice;
		choiceRoot = _rootchoice;
		choiceCaseOrMood = _poschoice;
		suffixchoice = _suffixchoice;
	}

	public static int getNumberOfColumn() {
		ncoloumns = 0;
		ncoloumns += choiceVoweledword ? 1 : 0;
		ncoloumns += prefchoice ? 1 : 0;
		ncoloumns += choiceStem ? 1 : 0;
		ncoloumns += choicePartOfSpeech ? 1 : 0;
		ncoloumns += choicePatternStem ? 1 : 0;
		ncoloumns += choiceLemma ? 1 : 0;
		ncoloumns += choicePatternLemma ? 1 : 0;
		ncoloumns += choiceRoot ? 1 : 0;
		ncoloumns += choiceCaseOrMood ? 1 : 0;
		ncoloumns += suffixchoice ? 1 : 0;
		return ncoloumns;
	}
}

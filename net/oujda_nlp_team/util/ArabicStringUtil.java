package net.oujda_nlp_team.util;

import java.util.Iterator;
import java.util.List;

import net.oujda_nlp_team.entity.Segment;

public class ArabicStringUtil {
	private static final ArabicStringUtil instance = new ArabicStringUtil();

	public static ArabicStringUtil getInstance() {
		return instance;
	}

	public String getIsHamza(char car) {
		switch (car) {
		case 'أ':
		case 'ؤ':
		case 'إ':
		case 'ئ':
			return "ء";
		}
		return "" + car;
	}

	public String replaceAllHamza(String _word) {
		return ArabicString.ALL_HAMZA.matcher(_word).replaceAll("");
	}

	public String removeAllDiacriticsOfWord(String _word) {
		try {
			return ArabicString.ALL_DIACRITICS.matcher(_word).replaceAll("");
		} catch (NullPointerException e) {
			System.out.println(_word);
			return _word;
		}
	}

	public String removeLastDiacriticsOfWord(String _word) {
		return ArabicString.DIACRITICS_EXCEPT_SHADDA.matcher(_word).replaceAll("");
	}

	public static String getListToString(List<String> list) {
		String str = "";
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String st = it.next();
			str = str + st + '\t';
		}
		return str.replaceAll("\t$", "");
	}

	public String[] Vowelize(Segment segment, String pattern) {
		String res, resultat[] = { "", "" };
		String proClass = segment.getProclitic().getClasse();
		String encClass = segment.getEnclitic().getClasse();
		String stem = segment.getStem();
		if (encClass.equals("V4")) {
			String diacritics = "[ًٌٍَُِْ]$";
			pattern = pattern.replaceAll(diacritics, "ُ");
		}
		String result = getDiactirizationStem(pattern, stem);
		result = result.charAt(0)
				+ ((Validator.getInstance().isDefinit(proClass) && Validator.getInstance().isSolar(stem.charAt(0)))
						? "ّ"
						: "")
				+ result.substring(1);
		String soukoun = (Validator.getInstance().isDefinit(proClass)
				&& !Validator.getInstance().isSolar(stem.charAt(0))) ? "ْ" : "";
		if (!segment.getEnclitic().getVoweledform().equals(""))
			result = result.replace('ة', 'ت');
		if ((result.endsWith("ُوا") || result.endsWith("وْا")) && !segment.getEnclitic().getUnvoweledform().equals(""))
			result = result.substring(0, result.length() - 1);
		if (segment.getEnclitic().getUnvoweledform().equals("ه"))
			if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
				segment.getEnclitic().setVoweledform("هِ");
			} else {
				segment.getEnclitic().setVoweledform("هُ");
			}
		if (segment.getEnclitic().getUnvoweledform().equals("هما"))
			if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
				segment.getEnclitic().setVoweledform("هِمَا");
			} else {
				segment.getEnclitic().setVoweledform("هُمَا");
			}
		if (segment.getEnclitic().getUnvoweledform().equals("هم"))
			if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
				segment.getEnclitic().setVoweledform("هِمْ");
			} else {
				segment.getEnclitic().setVoweledform("هُمْ");
			}
		if (segment.getEnclitic().getUnvoweledform().equals("هن"))
			if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
				segment.getEnclitic().setVoweledform("هِنَّ");
			} else {
				segment.getEnclitic().setVoweledform("هُنَّ");
			}
		if ((result.charAt(result.length() - 1) == 'ُ' || result.charAt(result.length() - 1) == 'َ')
				&& segment.getEnclitic().getUnvoweledform().equals("ي"))
			result = result.substring(0, result.length() - 1) + 'ِ';
		String encVoweled = segment.getEnclitic().getVoweledform();
		if (encVoweled.length() > 0) {
			result = result.replaceAll("ى", "ا");
			res = ((!result.endsWith("يْ") || !segment.getEnclitic().getClasse().equals("N1")) && (result.endsWith("ْ")
					|| encVoweled.length() == 1 || !Validator.getInstance().isDiacritic(encVoweled.charAt(1))))
							? addDiacBeforeString(encVoweled, result)
							: (result + encVoweled);
		} else {
			res = result;
		}
		if (!res.equals(""))
			res = CreatHamza.correctHamza(res);
		resultat[0] = segment.getProclitic().getVoweledform() + soukoun
				+ ((segment.getProclitic().getUnvoweledform().equals("أ") && res.length() > 3 && res.charAt(0) == 'ا'
						&& res.charAt(2) == 'ْ') ? res

								.substring(1) : res);
		if (resultat[0].endsWith("يْي") && segment.getEnclitic().getClasse().equals("N1"))
			resultat[0] = resultat[0].replaceAll("يْي$", "يَّ");
		resultat[1] = result;
		return resultat;
	}
	public String getWordFromRootAndPattern(String root, String diacLemma) {
		String str = "";
		boolean passe = false;
		boolean passe2 = false;
		for (int i = 0; i < diacLemma.length(); i++) {
			if (diacLemma.charAt(i) == 'ف') {
				str = str + root.charAt(0);
			} else if (diacLemma.charAt(i) == 'ع') {
				str = str + root.charAt(1);
			} else if (diacLemma.charAt(i) == 'ل') {
				if (!passe) {
					str = str + root.charAt(2);
				} else if (!passe2) {
					str = str + root.charAt(3);
					if (root.length() > 4)
						passe2 = true;
				} else {
					str = str + root.charAt(4);
				}
				if (root.length() != 3)
					passe = true;
			} else {
				str = str + diacLemma.charAt(i);
			}
		}
		
		return CreatHamza.correctHamza(str);
	}

	public static int Max(int a, int b) {
		return (a > b) ? a : b;
	}

	public static int Min(int a, int b) {
		return (a < b) ? a : b;
	}

	public String getPatternCompile() {
		String str = "";
		new ArabicString();
		str = str + "[ٱءآأإؤئابةتثجحخدذرزسشصضطظعغفقكلمنهويى][ٱءآأإؤئابةتثجحخدذرزسشصضطظعغفقكلمنهويى" + "ًٌٍَُِّْ" + "]*";
		String chainePattern = "((" + str + ")";
		chainePattern = chainePattern + "|(\\.)";
		chainePattern = chainePattern + "|(:)";
		chainePattern = chainePattern + "|(؟)";
		chainePattern = chainePattern + "|(؛)";
		chainePattern = chainePattern + "|(!)";
		chainePattern = chainePattern + "|(\n)";
		chainePattern = chainePattern + ")";
		return chainePattern;
	}

	private String getDiactirizationStem(String pattern, String stem) {
		String result = "";
		int j = 0;
		for (int i = 0; i < pattern.length(); i++)
			result = result
					+ (Validator.getInstance().isDiacritic(pattern.charAt(i)) ? pattern.charAt(i) : stem.charAt(j++));
		return result;
	}

	private String addDiacBeforeString(String encVoweled, String result) {
		switch (encVoweled.charAt(0)) {
		case 'و':
			return result.substring(0, result.length() - 1) + 'ُ' + encVoweled;
		case 'ي':
			return result.substring(0, result.length() - 1) + 'ِ' + encVoweled;
		case 'ا':
			return result.substring(0, result.length() - 1) + 'َ' + encVoweled;
		}
		return result + encVoweled;
	}

	public String correctErreur(String normalizedWord) {
		normalizedWord = (normalizedWord.length() >= 3 && normalizedWord.charAt(1) == 'ّ')
				? (normalizedWord.charAt(0) + normalizedWord.substring(2))
				: normalizedWord;
		normalizedWord = normalizedWord.replaceAll("ٱ", "ا");
		normalizedWord = normalizedWord.replaceAll("اُ", "ا");
		normalizedWord = normalizedWord.replaceAll("اَّ", "َّا");
		normalizedWord = normalizedWord.replaceAll("ىً$", "ًى");
		normalizedWord = normalizedWord.replaceAll("اً$", "ًا");
		normalizedWord = normalizedWord.replaceAll("اَ", "ا");
		normalizedWord = normalizedWord.replaceAll("اِ", "ا");
		normalizedWord = normalizedWord.replaceAll("ِا", "ا");
		normalizedWord = normalizedWord.replaceAll("ِّ", "ِّ");
		normalizedWord = normalizedWord.replaceAll("َّ", "َّ");
		normalizedWord = normalizedWord.replaceAll("ُّ", "ُّ");
		normalizedWord = normalizedWord.replaceAll("ٌّ", "ٌّ");
		normalizedWord = normalizedWord.replaceAll("ًّ", "ًّ");
		normalizedWord = normalizedWord.replaceAll("ٍّ", "ٍّ");
		normalizedWord = normalizedWord.replaceAll("آَ", "آ");
		return normalizedWord;
	}

	public String getNormalizeHamza(String word) {
		String in = "[ؤأإئ]";
		String out = "ء";
		return word.replaceAll(in, out);
	}
	/*============================================================================*/
	/**
	 * Samir BELAYACHI
	 *
	/*============================================================================*/
	
	public String getTypeHamzaFromWord(String normalizedWord) {
		
		
		if(wordContainHamza(normalizedWord)) {
			
			char[] chars = normalizedWord.toCharArray();
			for (char c : chars) {
				switch (c) {
				case 'أ' : return 'أ'+""; 
				case 'ؤ' : return 'ؤ'+""; 
				case 'إ' : return 'إ'+""; 
				case 'ئ' : return 'ئ'+""; 
				default :  break;
				}
			}
		}
		return "";
		
		
	}
	/*============================================================================*/
	public boolean wordContainHamza(String normalizedWord) {
		 
		return ArabicString.ALL_HAMZA.matcher(normalizedWord).find();
		
	}
	/*============================================================================*/
}

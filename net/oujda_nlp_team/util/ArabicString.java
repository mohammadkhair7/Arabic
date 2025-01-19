package net.oujda_nlp_team.util;

import java.util.regex.Pattern;

public class ArabicString {
	public static final String allArabicCharacter = "ٱءآأإؤئابةتثجحخدذرزسشصضطظعغفقكلمنهويى";

	public static final String allDiacritics = "ًٌٍَُِّْ";

	public static final String allDiacriticsExceptSHADDA = "ًٌٍَُِْ";

	public static final Pattern DIACRITICS_EXCEPT_SHADDA = Pattern.compile("[ًٌٍَُِْ]$");

	public static final Pattern ALL_DIACRITICS = Pattern.compile("[ًٌٍَُِّْ]");

	public static final Pattern ALL_HAMZA = Pattern.compile("[أإؤئ]");
}


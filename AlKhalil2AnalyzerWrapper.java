<<<<<<< HEAD
package net.oujda_nlp_team;

import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.entity.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import net.oujda_nlp_team.util.ArabicStringUtil;

public class AlKhalil2AnalyzerWrapper {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No input text provided");
            return;
        }
        AlKhalil2AnalyzerWrapper analyzer = new AlKhalil2AnalyzerWrapper();
        String result = analyzer.processText(args[0]);
        System.out.println(result);
    }

    public String processText(String inputText) {
        JSONArray resultsArray = new JSONArray();

        try {
            // System.out.println("Debug: Processing word: " + inputText);

            String[] words = inputText.split("\\s+");
            for (String word : words) {
                // System.out.println("Processing word: " + word);
                try {
                    ResultList rsl = AlKhalil2Analyzer.getInstance().processToken(word);
                    if (rsl.getAllResults().isEmpty()) {
                        System.out.println("No results for word: " + word);
                        String wordWithoutTashkeel = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
                        System.out.println("Reprocessing word without Tashkeel: " + wordWithoutTashkeel);
                        rsl = AlKhalil2Analyzer.getInstance().processToken(wordWithoutTashkeel);
                        if (rsl.getAllResults().isEmpty()) {
                            System.out.println("No results for word without Tashkeel: " + wordWithoutTashkeel);
                            resultsArray.put(createDefaultEntry(word));
                        } else {
                            for (Result rs : rsl.getAllResults()) {
                                JSONObject wordResult = new JSONObject();
                                wordResult.put("Word", word);
                                wordResult.put("Lemma", rs.getLemma());
                                wordResult.put("Part Of Speech", rs.getPartOfSpeech());
                                wordResult.put("Pattern Stem", rs.getPatternStem());
                                wordResult.put("Root", rs.getRoot());
                                resultsArray.put(wordResult);
                                // System.out.println("Word: " + word);
                            }
                        }
                    } else {
                        for (Result rs : rsl.getAllResults()) {
                            JSONObject wordResult = new JSONObject();
                            wordResult.put("Word", word);
                            wordResult.put("Lemma", rs.getLemma());
                            wordResult.put("Part Of Speech", rs.getPartOfSpeech());
                            wordResult.put("Pattern Stem", rs.getPatternStem());
                            wordResult.put("Root", rs.getRoot());
                            resultsArray.put(wordResult);
                            // System.out.println("Word: " + word);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing word: " + word + " - " + e.getMessage());
                    resultsArray.put(createDefaultEntry(word));
                }
            }

            // System.out.println("Debug: Final JSON output: " + resultsArray.toString());
            return resultsArray.toString();
        } catch (Exception e) {
            System.out.println("Debug: Exception occurred: " + e.getMessage());
            return "[]";  // Return empty JSON array as string in case of error
        }
    }

    private JSONObject createDefaultEntry(String word) {
        JSONObject defaultEntry = new JSONObject();
        defaultEntry.put("Word", word);
        defaultEntry.put("Lemma", "-");
        defaultEntry.put("Part Of Speech", "-");
        defaultEntry.put("Pattern Stem", "-");
        defaultEntry.put("Root", "-");
        return defaultEntry;
    }
=======
package net.oujda_nlp_team;

import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.entity.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import net.oujda_nlp_team.util.ArabicStringUtil;

public class AlKhalil2AnalyzerWrapper {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No input text provided");
            return;
        }
        AlKhalil2AnalyzerWrapper analyzer = new AlKhalil2AnalyzerWrapper();
        String result = analyzer.processText(args[0]);
        System.out.println(result);
    }

    public String processText(String inputText) {
        JSONArray resultsArray = new JSONArray();

        try {
            // System.out.println("Debug: Processing word: " + inputText);

            String[] words = inputText.split("\\s+");
            for (String word : words) {
                // System.out.println("Processing word: " + word);
                try {
                    ResultList rsl = AlKhalil2Analyzer.getInstance().processToken(word);
                    if (rsl.getAllResults().isEmpty()) {
                        System.out.println("No results for word: " + word);
                        String wordWithoutTashkeel = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
                        System.out.println("Reprocessing word without Tashkeel: " + wordWithoutTashkeel);
                        rsl = AlKhalil2Analyzer.getInstance().processToken(wordWithoutTashkeel);
                        if (rsl.getAllResults().isEmpty()) {
                            System.out.println("No results for word without Tashkeel: " + wordWithoutTashkeel);
                            resultsArray.put(createDefaultEntry(word));
                        } else {
                            for (Result rs : rsl.getAllResults()) {
                                JSONObject wordResult = new JSONObject();
                                wordResult.put("Word", word);
                                wordResult.put("Lemma", rs.getLemma());
                                wordResult.put("Part Of Speech", rs.getPartOfSpeech());
                                wordResult.put("Pattern Stem", rs.getPatternStem());
                                wordResult.put("Root", rs.getRoot());
                                resultsArray.put(wordResult);
                                // System.out.println("Word: " + word);
                            }
                        }
                    } else {
                        for (Result rs : rsl.getAllResults()) {
                            JSONObject wordResult = new JSONObject();
                            wordResult.put("Word", word);
                            wordResult.put("Lemma", rs.getLemma());
                            wordResult.put("Part Of Speech", rs.getPartOfSpeech());
                            wordResult.put("Pattern Stem", rs.getPatternStem());
                            wordResult.put("Root", rs.getRoot());
                            resultsArray.put(wordResult);
                            // System.out.println("Word: " + word);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing word: " + word + " - " + e.getMessage());
                    resultsArray.put(createDefaultEntry(word));
                }
            }

            // System.out.println("Debug: Final JSON output: " + resultsArray.toString());
            return resultsArray.toString();
        } catch (Exception e) {
            System.out.println("Debug: Exception occurred: " + e.getMessage());
            return "[]";  // Return empty JSON array as string in case of error
        }
    }

    private JSONObject createDefaultEntry(String word) {
        JSONObject defaultEntry = new JSONObject();
        defaultEntry.put("Word", word);
        defaultEntry.put("Lemma", "-");
        defaultEntry.put("Part Of Speech", "-");
        defaultEntry.put("Pattern Stem", "-");
        defaultEntry.put("Root", "-");
        return defaultEntry;
    }
>>>>>>> 11acb38 (Add untracked files)
}  
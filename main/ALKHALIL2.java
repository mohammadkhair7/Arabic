package net.oujda_nlp_team.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.oujda_nlp_team.AlKhalil2Analyzer;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.util.BuildResults;
import net.oujda_nlp_team.util.Tokenization;

public class ALKHALIL2 {

    public static StringBuilder getAnalyzedTextToJsonXmlHTML(String TextAnalyzed, String formatText) {

        Map<Object, Object> allResults = new HashMap<>();
        StringBuilder res = null;
        // System.out.println("Start analysis: ");
        Tokenization.getInstance().setTokenizationString(TextAnalyzed);
        Iterator<String> it_tokens = Tokenization.getInstance().getTokens().iterator();
        while (it_tokens.hasNext()) {
            String token = it_tokens.next();
            List<Result> result = AlKhalil2Analyzer.getInstance().analyzerToken(token);
            allResults.put(token, result);
        }
        if (!formatText.equals("") && formatText.endsWith("json")) //IOFile.getInstance().writeListToFile("analysis_results.json", "UTF8", (new BuildResults()).getResultsMorphologyJson(allResults)); 
        {
            res = (new BuildResults()).getResultsMorphologyJsonToString(allResults);
        }
        if (!formatText.equals("") && formatText.endsWith("xml")) {
            //IOFile.getInstance().writeListToFile("analysis_results.xml", "UTF8", (new BuildResults()).getResultsMorphologyXML(allResults)); 
            res = (new BuildResults()).getResultsMorphologyXMLToString(allResults);
        }
        if (!formatText.equals("") && formatText.endsWith("html")) {
            //IOFile.getInstance().writeListToFile("analysis_results.xml", "UTF8", (new BuildResults()).getResultsMorphologyXML(allResults)); 
            res = (new BuildResults()).getResultsMorphologyHTML(allResults);
        }

        return res;

    }
}

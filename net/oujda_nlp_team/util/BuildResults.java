  package net.oujda_nlp_team.util;
  
  import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.oujda_nlp_team.AlKhalil2Analyzer;
import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.Result;
  
  public class BuildResults {
    public List<String> getResultsIndexationXML() {
      List<String> list = new ArrayList<>();
      list.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
      list.add("<Indexation>");
      Iterator<Integer> itp = Indexation.getiWordsPos().keySet().iterator();
      while (itp.hasNext()) {
        int stp = ((Integer)itp.next()).intValue();
        int size = (((String)Indexation.getiWordsPos().get(Integer.valueOf(stp))).split(" ")).length;
        int p = 1;
        list.add("\t<words id=\"" + stp + "\" val=\"" + (String)Indexation.getiIDWords().get(Integer.valueOf(stp)) + "\" nbPos=\"" + size + "\" >");
        Iterator<String> itStrs = Arrays.<String>asList(((String)Indexation.getiWordsPos().get(Integer.valueOf(stp))).split(" ")).iterator();
        while (itStrs.hasNext()) {
          String stStrs = itStrs.next();
          String[] stl = stStrs.split(";");
          if (stl.length == 2) {
            list.add("\t\t<pos idpos=\"" + p + "\" prev=\"" + stl[0] + "\" next=\"" + stl[1] + "\" />");
            p++;
          } 
        } 
        list.add("\t</words>");
      } 
      list.add("</Indexation>");
      return list;
    }
    
    public List<String> getResultsIndexationHTML() {
      List<String> list = new ArrayList<>();
      String cellBeg = "<td align=\"right\"><font style=\"font-size:12px\">";
      String cellEnd = "</font></td>";
      list.add("<html><head><style>table{border-collapse:collapse;}table,th,td{border:1px solid black;font-size: 12px;}p{font-size: 12px;}</style></head><body lang=\"ar-SA\" dir=\"ltr\"><center><font size=5> <b>الفَهرَسَة <br> Indexing</b></font></center><br>");
      list.add("<p align=\"right\">عدد الكلمات :<font color=red>" + Normalization.nbTotalWord + "</font></p><p align=\"right\">عدد الكلمات بدون تكرار :<font color=red>" + Indexation.getiWordsPos().size() + "</font></p>");
      list.add("<DIV ALIGN=RIGHT>");
      list.add("<TABLE DIR=\"RTL\" BORDER=1 style=\"width:100%\" >");
      list.add("<tr valign=top>" + cellBeg + "<B>السياقات</B>" + cellEnd + cellBeg + "<B> مواقع الكلمات </B>" + cellEnd + cellBeg + "<B>التكرار</B>" + cellEnd + cellBeg + "<B>الكلمة</B>" + cellEnd + "</tr>");
      Iterator<Integer> itp = Indexation.getiWordsPos().keySet().iterator();
      while (itp.hasNext()) {
        int stp = ((Integer)itp.next()).intValue();
        list.add("<tr valign=top> ");
        String[] strs = ((String)Indexation.getiWordsPos().get(Integer.valueOf(stp))).split(" ");
        int p = 1;
        if (strs.length > 0) {
          String[] stl = strs[0].split(";");
          if (stl.length == 2) {
            StringBuilder res = new StringBuilder();
            res.append("<td align=\"right\" style=\"width:50%\"><font style=\"font-size:12px\">");
            if (!"0".equals(stl[0]))
              res.append(Indexation.getLeftContext(Integer.parseInt(stl[0].split("\\.")[0]), Integer.parseInt(stl[0].split("\\.")[1]), 5)); 
            res.append("<font color=red>").append(Indexation.getiIDWords().get(Integer.valueOf(stp))).append("</font>");
            if (!"0".equals(stl[1]))
              res.append(Indexation.getRightContext(Integer.parseInt(stl[1].split("\\.")[0]), Integer.parseInt(stl[1].split("\\.")[1]), 5)); 
            res.append(cellEnd);
            list.add(res.toString());
          } 
          list.add("<td align=\"right\" style=\"width:15%\"><font style=\"font-size:12px\">" + p + cellEnd);
          p++;
        } 
        list.add("<td align=\"right\" rowspan=\"" + strs.length + "\" style=\"width:15%\"><font style=\"font-size:12px\">" + strs.length + cellEnd);
        list.add("<td align=\"right\" rowspan=\"" + strs.length + "\" style=\"width:20%\"><font style=\"font-size:12px\">" + (String)Indexation.getiIDWords().get(Integer.valueOf(stp)) + cellEnd);
        list.add("</tr>");
        for (int i = 1; i < strs.length; i++) {
          list.add("<tr valign='top'>");
          String[] stl = strs[i].split(";");
          if (stl.length == 2) {
            StringBuilder res = new StringBuilder();
            res.append("<td align=\"right\" style=\"width:50%\"><font style=\"font-size:12px\">");
            if (!"0".equals(stl[0]))
              res.append(Indexation.getLeftContext(Integer.parseInt(stl[0].split("\\.")[0]), Integer.parseInt(stl[0].split("\\.")[1]), 5)); 
            res.append("<font color=red>").append(Indexation.getiIDWords().get(Integer.valueOf(stp))).append("</font>");
            if (!"0".equals(stl[1]))
              res.append(Indexation.getRightContext(Integer.parseInt(stl[1].split("\\.")[0]), Integer.parseInt(stl[1].split("\\.")[1]), 5)); 
            res.append(cellEnd);
            list.add(res.toString());
          } 
          list.add("<td align=\"right\" style=\"width:15%\"><font style=\"font-size:12px\">" + p + cellEnd);
          list.add("</tr>");
          p++;
        } 
      } 
      list.add("</table></div></body></html>");
      return list;
    }
    
    public List<String> getResultsIndexationCSV() {
      List<String> list = new ArrayList<>();
      list.add("الكلمات;التكرار;موقع الكلمات;السياق\n");
      Iterator<Integer> itp = Indexation.getiWordsPos().keySet().iterator();
      while (itp.hasNext()) {
        int stp = ((Integer)itp.next()).intValue();
        String[] strs = ((String)Indexation.getiWordsPos().get(Integer.valueOf(stp))).split(" ");
        int p = 1;
        for (String str : strs) {
          StringBuilder res = new StringBuilder();
          res.append(Indexation.getiIDWords().get(Integer.valueOf(stp))).append(";");
          res.append(strs.length).append(";");
          String[] stl = str.split(";");
          if (stl.length == 2) {
            res.append(p).append(";");
            p++;
            if (!"0".equals(stl[0]))
              res.append(Indexation.getLeftContext(Integer.parseInt(stl[0].split("\\.")[0]), Integer.parseInt(stl[0].split("\\.")[1]), 5)); 
            res.append(' ').append(Indexation.getiIDWords().get(Integer.valueOf(stp))).append(' ');
            if (!"0".equals(stl[1]))
              res.append(Indexation.getRightContext(Integer.parseInt(stl[1].split("\\.")[0]), Integer.parseInt(stl[1].split("\\.")[1]), 5)); 
          } 
          list.add(res.toString());
        } 
      } 
      return list;
    }
    
    public static void writeResultsIndexationToXml(String nameFile, String nameCharset) {
      IOFile.getInstance().writeListToFile(nameFile, nameCharset, (new BuildResults()).getResultsIndexationXML());
    }
    
    public static void writeResultsIndexationToHtml(String nameFile, String nameCharset) {
      IOFile.getInstance().writeListToFile(nameFile, nameCharset, (new BuildResults()).getResultsIndexationHTML());
    }
    
    public static String getResultsIndexationToHtml() {
      return (new Collections()).getListToString((new BuildResults()).getResultsIndexationHTML());
    }
    
    public static void writeResultsIndexationToCsv(String nameFile, String nameCharset) {
      IOFile.getInstance().writeListToFile(nameFile, nameCharset, (new BuildResults()).getResultsIndexationCSV());
    }
    
    public List<String> getResultsMorphologyXML() {
      List<String> list = new ArrayList<>();
      list.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
      list.add("<analysis>");
      Iterator<String> it = (AlKhalil2Analyzer.getInstance()).allResults.keySet().iterator();
      while (it.hasNext()) {
        String normalized = it.next();
        StringBuilder str = new StringBuilder();
        List<Result> res = (List)(AlKhalil2Analyzer.getInstance()).allResults.get(normalized);
        str.append('\t').append("<result word='").append(normalized).append("' nbresult='").append(res.size()).append("' nbOccu='").append(Tokenization.TokensRepeat.get(normalized)).append("'>").append('\n');
        if (res.isEmpty()) {
          str.append('\t').append('\t').append("لا توجد نتائج").append('\n');
        } else {
          Iterator<Result> itt = res.iterator();
          while (itt.hasNext()) {
            Result r = itt.next();
            str.append('\t').append('\t').append("<morph_feature_set ").append(getResultsMorphologyATT(r)).append("/>").append('\n');
          } 
        } 
        str.append('\t').append("</result>");
        list.add(str.toString());
      } 
      list.add("</analysis>");
      return list;
    }
     public StringBuilder getResultsMorphologyXMLToString(Map allResults) {
      
        StringBuilder str = new StringBuilder();
      str.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      str.append("<analysis>\n");
      Iterator<String> it = allResults.keySet().iterator();

      while (it.hasNext()) {
        String normalized = it.next();
        List<Result> res = (List)allResults.get(normalized);
        str.append('\t').append("<result word='").append(normalized).append("' nbresult='").append(res.size()).append("' nbOccu='").append(Tokenization.TokensRepeat.get(normalized)).append("'>").append('\n');
        if (res.isEmpty()) {
          str.append('\t').append('\t').append("لا توجد نتائج").append('\n');
        } else {
          Iterator<Result> itt = res.iterator();
          while (itt.hasNext()) {
            Result r = itt.next();
            str.append('\t').append('\t').append("<morph_feature_set ").append(getResultsMorphologyATT(r)).append("/>").append('\n');
          } 
        } 
        str.append('\t').append("</result>\n");
      } 
      str.append("</analysis>");
      return str;
    }
    
     public List<String> getResultsMorphologyJson(Map allResults) {
    List<String> list = new ArrayList<>();
    list.add("{");
    list.add("\t\"analysis\": {");
    list.add("\t\t\"result\": [");
    Iterator<String> it = allResults.keySet().iterator();
    while (it.hasNext()) {
      list.add("\t\t\t{");
      String normalized = it.next();
      StringBuilder str = new StringBuilder();
      List<Result> res = (List)allResults.get(normalized);
      str.append("\t\t\t\t").append("\"word\": \"").append(normalized).append("\",\n");
       str.append("\t\t\t\t").append("\" nbresult\": \"").append(res.size()).append("\",\n");
       str.append("\t\t\t\t").append("\" nbOccu=\": \"").append(Tokenization.TokensRepeat.get(normalized)).append("\",\n");
      if (res.isEmpty()) {
          str.append("\t\t\t\t").append("\"morph_feature_set\": [\"لا توجد نتائج\"]\n");
          str.append("\t\t\t\t").append("\n");

      } else {
        Iterator<Result> itt = res.iterator();
        str.append("\t\t\t\t").append("\"morph_feature_set\": [\n");

        while (itt.hasNext()) {
          Result r = itt.next();
          str.append("\t\t\t\t\t").append("{\n\t\t\t\t\t\t");
          str.append(getResultsMorphologyATTJson(r));
          if (!itt.hasNext()) 
            str.append("\t\t\t\t\t}\n");
          else
            str.append("\t\t\t\t\t},\n");
        }
        str.append("\t\t\t\t").append("]\n");
        
      } 
      if (!it.hasNext()) 
        str.append("\t\t\t").append("}\n");
      else
        str.append("\t\t\t").append("},\n");
      list.add(str.toString());
    }
    list.add("\t\t]");
    list.add("\t}");
    list.add("}");
    
    return list;
  }
     
      public StringBuilder getResultsMorphologyJsonToString(Map allResults) {
        StringBuilder str = new StringBuilder();

        str.append("{\n");
        str.append("\t\"analysis\": {\n");
        str.append("\t\t\"result\": [\n");
        Iterator<String> it = allResults.keySet().iterator();
        while (it.hasNext()) {
          str.append("\t\t\t{\n");
          String normalized = it.next();
          List<Result> res = (List)allResults.get(normalized);
          str.append("\t\t\t\t").append("\"word\": \"").append(normalized).append("\",\n");
           str.append("\t\t\t\t").append("\" nbresult\": \"").append(res.size()).append("\",\n");
           str.append("\t\t\t\t").append("\" nbOccu=\": \"").append(Tokenization.TokensRepeat.get(normalized)).append("\",\n");
          if (res.isEmpty()) {
              str.append("\t\t\t\t").append("\"morph_feature_set\": [\"لا توجد نتائج\"]\n");
              str.append("\t\t\t\t").append("\n");

          } else {
            Iterator<Result> itt = res.iterator();
            str.append("\t\t\t\t").append("\"morph_feature_set\": [\n");

            while (itt.hasNext()) {
              Result r = itt.next();
              str.append("\t\t\t\t\t").append("{\n\t\t\t\t\t\t");
              str.append(getResultsMorphologyATTJson(r));
              if (!itt.hasNext()) 
                str.append("\t\t\t\t\t}\n");
              else
                str.append("\t\t\t\t\t},\n");
            }
            str.append("\t\t\t\t").append("]\n");

          } 
          if (!it.hasNext()) 
            str.append("\t\t\t").append("}\n");
          else
            str.append("\t\t\t").append("},\n");
        }
        str.append("\t\t]\n");
        str.append("\t}\n");
        str.append("}\n");

        return str;
  }
    public StringBuilder getResultsMorphologyHTML(Map allResults) {
      StringBuilder str = new StringBuilder();
      int cmp = (new Processing()).getNbrHeaderResult();
      str.append("<table BORDER=1 dir=\"rtl\">");
      str.append("<tr>").append("<th style=\"text-align: left;\">").append("الكلمة").append("</th>").append(getResultsMorphologyTH()).append("</tr>");
      Iterator<String> it = allResults.keySet().iterator();

      while (it.hasNext()) {
        String words = it.next();
        List<Result> result = (List)allResults.get(words);
        if (result.isEmpty()) {
          str.append("<tr>").append("<td dir=\"rtl\">").append(words).append("</td>").append("<td colspan=").append(cmp).append(">لا توجد نتائج لتحليل هذه الكلمة</td>").append("</tr>");
          continue;
        } 
        int size = result.size();
        str.append("<tr>").append("<td rowspan=").append(size).append(" >").append(words).append("</td>");
        Iterator<Result> itRes = result.iterator();
        if (itRes.hasNext()) {
          Result r = itRes.next();
          str.append(getResultsMorphologyTD(r));
        } 
        str.append("</tr>");
        while (itRes.hasNext()) {
          Result r = itRes.next();
          str.append("<tr>").append(getResultsMorphologyTD(r)).append("</tr>");
        } 
      } 
      return str;
    }
    public List<String> getResultsMorphologyXML(Map allResults) {
      List<String> list = new ArrayList<>();
      list.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
      list.add("<analysis>");
      Iterator<String> it = allResults.keySet().iterator();
      while (it.hasNext()) {
        String normalized = it.next();
        StringBuilder str = new StringBuilder();
        List<Result> res = (List)allResults.get(normalized);
        str.append('\t').append("<result word='").append(normalized).append("' nbresult='").append(res.size()).append("' nbOccu='").append(Tokenization.TokensRepeat.get(normalized)).append("'>").append('\n');
        if (res.isEmpty()) {
          str.append('\t').append('\t').append("لا توجد نتائج").append('\n');
        } else {
          Iterator<Result> itt = res.iterator();
          while (itt.hasNext()) {
            Result r = itt.next();
            str.append('\t').append('\t').append("<morph_feature_set ").append(getResultsMorphologyATT(r)).append("/>").append('\n');
          } 
        } 
        str.append('\t').append("</result>");
        list.add(str.toString());
      } 
      list.add("</analysis>");
      return list;
    }
    
    public List<String> getResultsMorphologyRAW(Map allResults) {
      List<String> list = new ArrayList<>();
      Iterator<String> it = allResults.keySet().iterator();
      while (it.hasNext()) {
        String normalized = it.next();
        List<Result> res = (List)allResults.get(normalized);
        String BW = Transliteration.getInstance().getArabicToBuckWalter(normalized);
        list.add("Processing token:\t" + normalized);
        list.add("Transliteration:\t" + BW);
        list.add("Number of solutions:\t" + res.size());
        list.add("Frequency Count:\t" + Tokenization.TokensRepeat.get(normalized));
        if (res.isEmpty()) {
          list.add("\tToken not analyzed !!!");
        } else {
          Iterator<Result> itt = res.iterator();
          int i = 1;
          while (itt.hasNext()) {
            list.add("\tSOLUTION: #" + i);
            Result r = itt.next();
            list.add(getResultsMorphologyATT_RAW(r));
            i++;
          } 
        } 
        list.add("--------------------");
      } 
      return list;
    }
    
    public List<String> getResultsMorphologyHTML() {
      List<String> list = new ArrayList<>();
      StringBuilder str = new StringBuilder();
      int cmp = (new Processing()).getNbrHeaderResult();
      list.add("<table float=\"center\" WIDTH=90% BORDER=1 dir=\"rtl\">");
      str.append("<tr>").append("<th>").append("الكلمة").append("</th>").append(getResultsMorphologyTH()).append("</tr>");
      list.add(str.toString());
      Iterator<String> it = (AlKhalil2Analyzer.getInstance()).allResults.keySet().iterator();
      while (it.hasNext()) {
        String words = it.next();
        List<Result> result = (List)(AlKhalil2Analyzer.getInstance()).allResults.get(words);
        if (result.isEmpty()) {
          str = new StringBuilder();
          str.append("<tr>").append("<td>").append(words).append("</td>").append("<td colspan=").append(cmp).append(">لا توجد نتائج لتحليل هذه الكلمة</td>").append("</tr>");
          list.add(str.toString());
          continue;
        } 
        str = new StringBuilder();
        int size = result.size();
        str.append("<tr>").append("<td rowspan=").append(size).append(" >").append(words).append("</td>");
        Iterator<Result> itRes = result.iterator();
        if (itRes.hasNext()) {
          Result r = itRes.next();
          str.append(getResultsMorphologyTD(r));
        } 
        str.append("</tr>");
        list.add(str.toString());
        while (itRes.hasNext()) {
          str = new StringBuilder();
          Result r = itRes.next();
          str.append("<tr>").append(getResultsMorphologyTD(r)).append("</tr>");
          list.add(str.toString());
        } 
      } 
      list.add("</table>");
      return list;
    }
    
    public List<String> getResultsMorphologyCSV() {
      List<String> list = new ArrayList<>();
      Iterator<String> it = (AlKhalil2Analyzer.getInstance()).allResults.keySet().iterator();
      while (it.hasNext()) {
        String normalizedWord = it.next();
        List<Result> res = (List)(AlKhalil2Analyzer.getInstance()).allResults.get(normalizedWord);
        StringBuilder str = new StringBuilder();
        if (res.isEmpty()) {
          str.append(normalizedWord).append(";");
          str.append("لا توجد نتائج لتحليل هذه الكلمة;;;;;;;;#;#");
          list.add(str.toString());
          continue;
        } 
        Iterator<Result> itt = res.iterator();
        while (itt.hasNext()) {
          Result r = itt.next();
          str.append(getResultsMorphologyROW(r)).append('\n');
          list.add(str.toString());
        } 
      } 
      return list;
    }
    
    public static void writeResultMorphologyToXml(String nameFile, String nameCharset) {
      IOFile.getInstance().writeListToFile(nameFile, nameCharset, (new BuildResults()).getResultsMorphologyXML());
    }
    
    public static void writeResultMorphologyToHtml(String nameFile, String nameCharset) {
      IOFile.getInstance().writeListToFile(nameFile, nameCharset, (new BuildResults()).getResultsMorphologyHTML());
    }
    
    public static void writeResultMorphologyToCsv(String nameFile, String nameCharset) {
      IOFile.getInstance().writeListToFile(nameFile, nameCharset, (new BuildResults()).getResultsMorphologyCSV());
    }
    
    private StringBuilder getResultsMorphologyTD(Result r) {
      StringBuilder str = new StringBuilder();
      str.append(Settings.choiceVoweledword ? ("<td>" + r.getVoweledWord() + "</td>") : "");
      str.append(Settings.prefchoice ? ("<td>" + r.getProclitic() + "</td>") : "");
      str.append(Settings.choiceStem ? ("<td>" + r.getStem() + "</td>") : "");
      str.append(Settings.choicePartOfSpeech ? ("<td>" + r.getPartOfSpeech() + "</td>") : "");
      str.append(Settings.choicePatternStem ? ("<td>" + r.getPatternStem() + "</td>") : "");
      str.append(Settings.choiceLemma ? ("<td>" + r.getLemma() + "</td>") : "");
      str.append(Settings.choicePatternLemma ? ("<td>" + r.getPatternLemma() + "</td>") : "");
      str.append(Settings.choiceRoot ? ("<td>" + r.getRoot() + "</td>") : "");
      str.append(Settings.choiceCaseOrMood ? ("<td>" + r.getCaseOrMood() + "</td>") : "");
      str.append(Settings.suffixchoice ? ("<td>" + r.getEnclitic() + "</td>") : "");
      return str;
    }
    
    private StringBuilder getResultsMorphologyTH() {
      StringBuilder str = new StringBuilder();
      str.append(Settings.choiceVoweledword ? "<th>الكلمة مشكولة</th>" : "");
      str.append(Settings.prefchoice ? "<th>السابق</th>" : "");
      str.append(Settings.choiceStem ? "<th>الجذع</th>" : "");
      str.append(Settings.choicePartOfSpeech ? "<th>نوع الكلمة</th>" : "");
      str.append(Settings.choicePatternStem ? "<th>وزن الكلمة</th>" : "");
      str.append(Settings.choiceLemma ? "<th>الفرع</th>" : "");
      str.append(Settings.choicePatternLemma ? "<th>وزن الفرع</th>" : "");
      str.append(Settings.choiceRoot ? "<th>الجذر</th>" : "");
      str.append(Settings.choiceCaseOrMood ? "<th>الحالة الإعرابية</th>" : "");
      str.append(Settings.suffixchoice ? "<th>اللاحق</th>" : "");
      return str;
    }
    
    private StringBuilder getResultsMorphologyROW(Result r) {
      StringBuilder str = new StringBuilder();
      str.append(Settings.choiceVoweledword ? r.getVoweledWord() : "");
      str.append(Settings.prefchoice ? r.getProclitic() : "");
      str.append(Settings.choiceStem ? r.getStem() : "");
      str.append(Settings.choicePartOfSpeech ? r.getPartOfSpeech() : "");
      str.append(Settings.choicePatternStem ? r.getPatternStem() : "");
      str.append(Settings.choiceLemma ? r.getLemma() : "");
      str.append(Settings.choicePatternLemma ? r.getPatternLemma() : "");
      str.append(Settings.choiceRoot ? r.getRoot() : "");
      str.append(Settings.choiceCaseOrMood ? r.getCaseOrMood() : "");
      str.append(Settings.suffixchoice ? r.getEnclitic() : "");
      return str;
    }
    
    private StringBuilder getResultsMorphologyATTJson(Result r) {
    StringBuilder str = new StringBuilder();
    str.append(Settings.choiceVoweledword ? ("\"diac\": \"" + r.getVoweledWord() + "\",\n ") : "");
    str.append(Settings.choiceStem ? ("\t\t\t\t\t\t\"stem\": \"" + r.getVoweledWord() +  "\",\n ") : "");
    str.append(Settings.choiceLemma ? ("\t\t\t\t\t\t\"lemma\": \"" + r.getLemma() +  "\",\n ") : "");
    str.append(Settings.choiceRoot ? ("\t\t\t\t\t\t\"root\": \"" + r.getRoot() +  "\",\n ") : "");
    str.append(Settings.choicePatternLemma ? ("\t\t\t\t\t\t\"pat_lemma\": \"" + r.getPatternLemma() +  "\",\n ") : "");
    str.append(Settings.choicePatternStem ? ("\t\t\t\t\t\t\"pat_stem\": \"" + r.getPatternStem() +  "\",\n ") : "");
    str.append(Settings.choicePartOfSpeech ? ("\t\t\t\t\t\t\"pos\": \"" + r.getPartOfSpeech() +  "\",\n ") : "");
    str.append(Settings.choiceCaseOrMood ? ("\t\t\t\t\t\t\"cas=\": \"" + r.getCaseOrMood() +  "\",\n ") : "");
    str.append(Settings.prefchoice ? ("\t\t\t\t\t\t\"proc\": \"" + r.getProclitic() +  "\",\n ") : "");
    str.append(Settings.suffixchoice ? ("\t\t\t\t\t\t\"enc\": \"" + r.getEnclitic() +  "\"\n ") : "");
    return str;
  }
    
    private StringBuilder getResultsMorphologyATT(Result r) {
      StringBuilder str = new StringBuilder();
      str.append(Settings.choiceVoweledword ? ("diac=\"" + r.getVoweledWord() + "\" ") : "");
      str.append(Settings.choiceStem ? ("stem=\"" + r.getVoweledWord() + "\" ") : "");
      str.append(Settings.choiceLemma ? ("lemma=\"" + r.getLemma() + "\" ") : "");
      str.append(Settings.choiceRoot ? ("root=\"" + r.getRoot() + "\" ") : "");
      str.append(Settings.choicePatternLemma ? ("pat_lemma=\"" + r.getPatternLemma() + "\" ") : "");
      str.append(Settings.choicePatternStem ? ("pat_stem=\"" + r.getPatternStem() + "\" ") : "");
      str.append(Settings.choicePartOfSpeech ? ("pos=\"" + r.getPartOfSpeech() + "\" ") : "");
      str.append(Settings.choiceCaseOrMood ? ("cas=\"" + r.getCaseOrMood() + "\" ") : "");
      str.append(Settings.prefchoice ? ("proc=\"" + r.getProclitic() + "\" ") : "");
      str.append(Settings.suffixchoice ? ("enc=\"" + r.getEnclitic() + "\" ") : "");
      return str;
    }
    
    private String getResultsMorphologyATT_RAW(Result r) {
      StringBuilder str = new StringBuilder();
      if (Database.getInstance().getResources().getProperty("Data.Output.Word").equals("true")) {
        String BW = Transliteration.getInstance().getArabicToBuckWalter(r.getVoweledWord());
        str.append("\t\tVocalized as:\t").append(r.getVoweledWord()).append("\t").append(BW).append("\n");
      } 
      if (Database.getInstance().getResources().getProperty("Data.Output.Stem").equals("true")) {
        String BW = Transliteration.getInstance().getArabicToBuckWalter(r.getStem());
        str.append("\t\tStem:\t").append(r.getStem()).append("\t").append(BW).append("\n");
      } 
      if (Database.getInstance().getResources().getProperty("Data.Output.Lemma").equals("true")) {
        String BW = Transliteration.getInstance().getArabicToBuckWalter(r.getLemma());
        str.append("\t\tLemma:\t").append(r.getLemma()).append("\t").append(BW).append("\n");
      } 
      if (Database.getInstance().getResources().getProperty("Data.Output.Root").equals("true")) {
        String BW = Transliteration.getInstance().getArabicToBuckWalter(r.getRoot());
        str.append("\t\tRoot:\t").append(r.getRoot()).append("\t").append(BW).append("\n");
      } 
      if (Database.getInstance().getResources().getProperty("Data.Output.Pattern.Stem").equals("true")) {
        String BW = Transliteration.getInstance().getArabicToBuckWalter(r.getPatternStem());
        str.append("\t\tPattern of stem:\t").append(r.getPatternStem()).append("\n");
      } 
      if (Database.getInstance().getResources().getProperty("Data.Output.Pattern.Lemma").equals("true")) {
        String BW = Transliteration.getInstance().getArabicToBuckWalter(r.getPatternLemma());
        str.append("\t\tPattern of Lemma:\t").append(r.getPatternLemma()).append("\n");
      } 
      if (Database.getInstance().getResources().getProperty("Data.Output.PartOfSpeech").equals("true"))
        str.append("\t\tPart of speech:\t").append(r.getPartOfSpeech()).append("\n"); 
      if (Database.getInstance().getResources().getProperty("Data.Output.CaseOrMood").equals("true"))
        str.append("\t\tcase or mood:\t").append(r.getCaseOrMood()).append("\n"); 
      if (Database.getInstance().getResources().getProperty("Data.Output.Proclitics").equals("true"))
        str.append("\t\tProclitics:\t").append(r.getProclitic()).append("\n"); 
      if (Database.getInstance().getResources().getProperty("Data.Output.Enclitics").equals("true"))
        str.append("\t\tEnclitics:\t").append(r.getEnclitic()).append("\n"); 
      return str.toString().replaceAll("\n$", "");
    }
  }


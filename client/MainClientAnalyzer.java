   package net.oujda_nlp_team.client;
   
   import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.oujda_nlp_team.util.IOFile;
   
   public class MainClientAnalyzer {
     public static void main1(String[] args) throws IOException {
       AlKhalil2ClientAnalyzer _analyzer = new AlKhalil2ClientAnalyzer("127.0.0.1", 19901);
       String path = "M:\\PHD\\CORPORA\\SHAMILA\\ALL\\trainingCorpusesWdwUniform90\\trainingCorpusesWdwUniform90\\";
       String pathRoot = "M:\\PHD\\CORPORA\\SHAMILA\\trainingCorpusesWdwUniform90\\root\\";
       String pathLemma = "M:\\PHD\\CORPORA\\SHAMILA\\trainingCorpusesWdwUniform90\\lemma\\";
       String pathStem = "M:\\PHD\\CORPORA\\SHAMILA\\trainingCorpusesWdwUniform90\\stem\\";
       String encoding = "UTF-8";
       int nbfile = 0;
       File file = new File(path);
       for (String pathFile : file.list()) {
         System.out.println(++nbfile);
         List<String> resRoot = new ArrayList<>();
         List<String> resLemma = new ArrayList<>();
         List<String> resStem = new ArrayList<>();
         List<String> list = IOFile.getInstance().readFileToList(path + pathFile, "Cp1256");
         Iterator<String> it = list.iterator();
         while (it.hasNext()) {
           String st = it.next();
           if (st.startsWith("I") || st.startsWith("F")) {
             resRoot.add(st.substring(0, 1));
             resLemma.add(st.substring(0, 1));
             resStem.add(st.substring(0, 1));
             continue;
           } 
           String[] stp = st.split("\t");
           if (stp.length >= 2) {
             resRoot.add(stp[1] + "\t" + _analyzer.getAllRoots(stp[1]));
             resLemma.add(stp[1] + "\t" + _analyzer.getAllLemmas(stp[1]));
             resStem.add(stp[1] + "\t" + _analyzer.getAllStems(stp[1]));
           } 
         } 
         IOFile.getInstance().writeListToFile(pathRoot + pathFile, encoding, resRoot);
         IOFile.getInstance().writeListToFile(pathLemma + pathFile, encoding, resLemma);
         IOFile.getInstance().writeListToFile(pathStem + pathFile, encoding, resStem);
       } 
     }
     
     public static void main(String[] args) throws IOException {
       AlKhalil2ClientAnalyzer _analyzer = new AlKhalil2ClientAnalyzer("127.0.0.1", 19901);
       String word = "المعلم";
       System.out.println(_analyzer.getAllLemmas(word));
     }
     
     public static void main11(String[] args) throws IOException {
       AlKhalil2ClientAnalyzer _analyzer = new AlKhalil2ClientAnalyzer("127.0.0.1", 19901);
       String path = "M:\\PHD\\CORPORA\\SHAMILA\\ALL\\trainingCorpusesWdwUniform90\\trainingCorpusesWdwUniform90\\";
       String pathS = "M:\\PHD\\CORPORA\\SHAMILA\\ALL\\trainingCorpusesWdwUniform90\\fileSortie.txt";
       String pathRoot = "M:\\PHD\\CORPORA\\SHAMILA\\ALL\\trainingCorpusesWdwUniform90\\rootSortie.txt";
       String pathLemma = "M:\\PHD\\CORPORA\\SHAMILA\\ALL\\trainingCorpusesWdwUniform90\\lemmaSortie.txt";
       String pathStem = "M:\\PHD\\CORPORA\\SHAMILA\\ALL\\trainingCorpusesWdwUniform90\\stemSortie.txt";
       String encoding = "UTF-8";
       int nbline = 0;
       try {
         List<String> res = new ArrayList<>();
         OutputStreamWriter outRoot = new OutputStreamWriter(new FileOutputStream(new File(pathRoot)), encoding);
         OutputStreamWriter outLemma = new OutputStreamWriter(new FileOutputStream(new File(pathLemma)), encoding);
         OutputStreamWriter outStem = new OutputStreamWriter(new FileOutputStream(new File(pathStem)), encoding);
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathS)), encoding));
         String line;
         while ((line = in.readLine()) != null) {
           if (++nbline % 50000 == 0) {
             List<String> list = _analyzer.listAnalyzer(res);
             Iterator<String> iterator = list.iterator();
             while (iterator.hasNext()) {
               String word = iterator.next();
               outStem.write(word + "\n");
               word = iterator.hasNext() ? iterator.next() : "";
               outLemma.write(word + "\n");
               word = iterator.hasNext() ? iterator.next() : "";
               outRoot.write(word + "\n");
             } 
             System.out.println(nbline);
             res = new ArrayList<>();
           } 
           res.add(line);
         } 
         List<String> resss = _analyzer.listAnalyzer(res);
         Iterator<String> it = resss.iterator();
         while (it.hasNext()) {
           String word = it.next();
           outStem.write(word + "\n");
           word = it.hasNext() ? it.next() : "";
           outLemma.write(word + "\n");
           word = it.hasNext() ? it.next() : "";
           outRoot.write(word + "\n");
         } 
         System.out.println(nbline);
         in.close();
         outRoot.close();
         outLemma.close();
         outStem.close();
       } catch (IOException ex) {
         System.out.println("Erreur : " + ex);
       } 
     }
   }

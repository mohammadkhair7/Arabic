   package net.oujda_nlp_team.main;
   
   import java.util.ArrayList;
import java.util.List;

import net.oujda_nlp_team.ADATAnalyzer;
   
   public class ADATMain {
     public static void main(String[] args) {
       List<String> argsList = new ArrayList<>();
       List<Option1> optsList = new ArrayList<>();
       List<String> doubleOptsList = new ArrayList<>();
       String rawinput = "";
       String rawinputencoding = "utf-8";
       String rawoutput = "";
       String rawoutputencoding = "utf-8";
       String choice = "lemma";
       for (String arg : args) {
         if (arg.startsWith("--rawinput="))
           rawinput = arg.substring(11); 
         if (arg.startsWith("-i="))
           rawinput = arg.substring(3); 
         if (arg.startsWith("--rawinputencoding="))
           rawinputencoding = arg.substring(19); 
         if (arg.startsWith("-ie="))
           rawinputencoding = arg.substring(4); 
         if (arg.startsWith("--rawoutput="))
           rawoutput = arg.substring(12); 
         if (arg.startsWith("-o="))
           rawoutput = arg.substring(3); 
         if (arg.startsWith("--rawoutputencoding="))
           rawoutputencoding = arg.substring(20); 
         if (arg.startsWith("-oe="))
           rawoutputencoding = arg.substring(4); 
         if (arg.startsWith("--choice="))
           choice = arg.substring(9); 
         if (arg.startsWith("-c="))
           choice = arg.substring(3); 
       } 
       if (!rawinput.equals("") && !rawoutput.equals("")) {
         System.out.print("Start analysis ....");
         if (choice.equals("lemma")) {
           ADATAnalyzer.getInstance().processLemmatization(rawinput, rawinputencoding, rawoutput, rawoutputencoding);
         } else if (choice.equals("stem")) {
           ADATAnalyzer.getInstance().processLightStemmer(rawinput, rawinputencoding, rawoutput, rawoutputencoding);
         } else if (choice.equals("root")) {
           ADATAnalyzer.getInstance().processHeavyStemmer(rawinput, rawinputencoding, rawoutput, rawoutputencoding);
         } 
         System.out.println(".... end.");
       } else {
         if (rawinput.equals(""))
           System.out.println("Input File Error ...!!"); 
         if (rawoutput.equals(""))
           System.out.println("Output File Error ...!!"); 
       } 
     }
   }


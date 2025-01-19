   package net.oujda_nlp_team.server;
   
   import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.oujda_nlp_team.AlKhalil2Analyzer;
import net.oujda_nlp_team.entity.ResultList;
   
   public class AlKhalil2ServerAnalyzer {
     private static int port = 19901;
     
     private ExecutorService pool;
     
     public static Set<String> notAnaTokens;
     
     public static void main(String[] args) throws Exception {
       ExecutorService pool = Executors.newFixedThreadPool(10000);
       Thread client = new Thread(new Serveur(19901, pool));
       client.start();
       System.out.println("AlKhalil2 server is running.");
     }
     
     static class Server1 extends Thread {
       private Socket socket;
       
       public Server1(Socket socket) {
         this.socket = socket;
       }
       
       public void run() {
         try {
           BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
           PrintWriter out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"), true);
           while (true) {
             String input = in.readLine();
             if (input == null || input.equals("."))
               break; 
             if (input.equals("ROOT")) {
               out.println("PASS");
               out.flush();
               input = in.readLine();
               ResultList result = AlKhalil2Analyzer.getInstance().processToken(input);
               out.println(listToString(result.getAllRoots()));
               out.flush();
             } else if (input.equals("STEM")) {
               out.println("PASS");
               out.flush();
               input = in.readLine();
               ResultList result = AlKhalil2Analyzer.getInstance().processToken(input);
               out.println(listToString(result.getAllStems()));
               out.flush();
             } else if (input.equals("LEMMA")) {
               do {
                 out.println("PASS");
                 out.flush();
                 input = in.readLine();
                 ResultList result = AlKhalil2Analyzer.getInstance().processToken(input);
                 out.println(input + "\t" + listToString(result.getAllStems()));
                 out.println(input + "\t" + listToString(result.getAllLemmas()));
                 out.println(input + "\t" + listToString(result.getAllRoots()));
                 out.flush();
               } while (input != null);
             } 
             out.close();
           } 
         } catch (IOException iOException) {
           try {
             this.socket.close();
           } catch (IOException iOException1) {}
         } finally {
           try {
             this.socket.close();
           } catch (IOException iOException) {}
         } 
       }
       
       private String listToString(List<String> _res) {
         String res = "";
         Iterator<String> it = _res.iterator();
         while (it.hasNext())
           res = res + (String)it.next() + " "; 
         return res.equals("") ? "+" : res.trim();
       }
     }
   }

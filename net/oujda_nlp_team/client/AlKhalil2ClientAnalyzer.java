   package net.oujda_nlp_team.client;
   
   import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
   
   public class AlKhalil2ClientAnalyzer {
     private BufferedReader in;
     
     private PrintWriter out;
     
     private Socket socket;
     
     private String adr;
     
     private int port;
     
     private String encoding;
     
     public AlKhalil2ClientAnalyzer(String _adr, int _port) {
       this.adr = _adr;
       this.port = _port;
       this.encoding = "utf-8";
     }
     
     public List<String> listAnalyzer(List<String> _res) {
       List<String> _ress = new ArrayList<>();
       try {
         this.socket = new Socket(this.adr, this.port);
         this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), this.encoding));
         this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), this.encoding), true);
         this.out.println("LEMMA");
         this.out.flush();
         Iterator<String> it = _res.iterator();
         while (it.hasNext()) {
           String word = it.next();
           if (this.in.readLine().equals("PASS")) {
             this.out.println(word);
             this.out.flush();
             _ress.add(this.in.readLine());
             _ress.add(this.in.readLine());
             _ress.add(this.in.readLine());
           } 
         } 
       } catch (IOException ex) {
         System.out.println("Erreur : " + ex);
       } 
       return _ress;
     }
     
     public String getAllLemmas(String word) {
       try {
         this.socket = new Socket(this.adr, this.port);
         this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), this.encoding));
         this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), this.encoding), true);
         this.out.println("LEMMA");
         this.out.flush();
         String readLine = this.in.readLine();
         String st = "";
         if (readLine.equals("PASS")) {
           this.out.println(word);
           this.out.flush();
           st = this.in.readLine();
         } 
         this.in.close();
         this.out.close();
         this.socket.close();
         return st;
       } catch (IOException ex) {
         System.out.println("Erreur : " + ex);
         return "";
       } 
     }
     
     public String getAllStems(String word) {
       try {
         this.socket = new Socket(this.adr, this.port);
         this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), this.encoding));
         this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), this.encoding), true);
         this.out.println("STEM");
         this.out.flush();
         String readLine = this.in.readLine();
         String st = "";
         if (readLine.equals("PASS")) {
           this.out.println(word);
           this.out.flush();
           st = this.in.readLine();
         } 
         this.in.close();
         this.out.close();
         this.socket.close();
         return st;
       } catch (IOException ex) {
         System.out.println("Erreur : " + ex);
         return "";
       } 
     }
     
     public String getAllRoots(String word) {
       try {
         this.socket = new Socket(this.adr, this.port);
         this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), this.encoding));
         this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), this.encoding), true);
         this.out.println("ROOT");
         this.out.flush();
         String readLine = this.in.readLine();
         String st = "";
         if (readLine.equals("PASS")) {
           this.out.println(word);
           this.out.flush();
           st = this.in.readLine();
         } 
         this.in.close();
         this.out.close();
         this.socket.close();
         return st;
       } catch (IOException ex) {
         System.out.println("Erreur : " + ex);
         return "";
       } 
     }
   }


   package net.oujda_nlp_team.server;
   
   import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
   
   class Serveur implements Runnable {
     private int port;
     
     private ServerSocket serveur;
     
     private ExecutorService pool;
     
     public Serveur(int port, ExecutorService pool) {
       this.port = port;
       try {
         this.serveur = new ServerSocket(port);
         this.pool = pool;
       } catch (IOException e) {
         e.printStackTrace();
       } 
     }
     
     public void run() {
       try {
         while (true)
           this.pool.execute(new AlKhalil2ServerAnalyzer.Server1(this.serveur.accept())); 
       } catch (IOException e) {
         e.printStackTrace();
         return;
       } 
     }
   }

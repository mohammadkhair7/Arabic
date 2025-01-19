   package net.oujda_nlp_team.impl;
   
   import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.factory.CliticFactory;
import net.oujda_nlp_team.interfaces.IClitic;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.xml.XMLClitic;
   
   public class ProcliticImpl extends CliticFactory implements IClitic {
     private static final IClitic instance = new ProcliticImpl();
     
     public static IClitic getInstance() {
       return instance;
     }
     
     private ProcliticImpl() {
       this.clitic = new HashMap<>();
       this.xml = new XMLClitic();
       addClitics();
     }
     
     public void addXMLClitics() {
       if (this.clitic.isEmpty()) {
         String data = Database.getInstance().getResources().getProperty("XML.Proclitics");
         this.clitic = this.xml.parserXml(IOFile.getInstance().openFileXml(data), "proclitic");
       } 
     }
     
     public void addClitics() {
       if (this.clitic.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Proclitics");
         this.clitic = IOFile.getInstance().deserializeMap(data);
       } 
     }
     
     public List getListsClitics(String token) {
       List listClitics = new LinkedList();
       int size = token.length();
       int ip = 0;
       int Max_Proclitic = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Proclitics.Max"));
       while (ip < size && ip <= Max_Proclitic) {
         listClitics.addAll(possibleClitics(token.substring(0, ip)));
         ip++;
       } 
       return listClitics;
     }
   }


  package net.oujda_nlp_team.impl;
  
  import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.factory.CliticFactory;
import net.oujda_nlp_team.interfaces.IClitic;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.xml.XMLClitic;
  
  public class EncliticImpl extends CliticFactory implements IClitic {
    private static final IClitic instance = new EncliticImpl();
    
    public static IClitic getInstance() {
      return instance;
    }
    
    private EncliticImpl() {
      this.clitic = new HashMap<>();
      this.xml = new XMLClitic();
      addClitics();
    }
    
    public void addXMLClitics() {
      if (this.clitic.isEmpty()) {
        String data = Database.getInstance().getResources().getProperty("XML.Enclitics");
        this.clitic = this.xml.parserXml(IOFile.getInstance().openFileXml(data), "enclitic");
      } 
    }
    
    public void addClitics() {
      if (this.clitic.isEmpty()) {
        String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Enclitics");
        this.clitic = IOFile.getInstance().deserializeMap(data);
      } 
    }
    
    public List getListsClitics(String token) {
      List listClitics = new LinkedList();
      int size = token.length();
      int ip = 0;
      int Max_Enclitic = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Enclitics.Max"));
      while (ip < size && ip <= Max_Enclitic) {
        listClitics.addAll(possibleClitics(token.substring(size - ip, size)));
        ip++;
      } 
      return listClitics;
    }
  }

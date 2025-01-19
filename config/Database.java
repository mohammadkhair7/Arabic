   package net.oujda_nlp_team.config;
   
   import java.util.Properties;

import net.oujda_nlp_team.util.IOFile;
   
   public class Database {
     private static final Database instance = new Database();
     
     private final Properties resources;
     
     private final String path;
     
     public static Database getInstance() {
       return instance;
     }
     
     public Database() {
       String filename = "/net/oujda_nlp_team/config/AlKhalil2.properties";
       this.resources = IOFile.getInstance().loadProperties(IOFile.getInstance().openFileXml(filename));
       this.path = this.resources.getProperty("ALKHALIL2.database.path");
     }
     
     public Database(String filename) {
       this.resources = IOFile.getInstance().loadProperties(IOFile.getInstance().openFileXml(filename));
       this.path = this.resources.getProperty("ALKHALIL2.database.path");
     }
     
     public Properties getResources() {
       return this.resources;
     }
     
     public String getPath() {
       return this.path;
     }
   }


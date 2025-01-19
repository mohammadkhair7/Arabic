   package net.oujda_nlp_team.impl;
   
   import java.util.ArrayList;
import java.util.HashMap;

import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.Toolwords;
import net.oujda_nlp_team.factory.UnderivedFactory;
import net.oujda_nlp_team.interfaces.IUnderived;
import net.oujda_nlp_team.util.IOFile;
   
   public class ToolwordsUnderivedImpl extends UnderivedFactory implements IUnderived {
     private static final ToolwordsUnderivedImpl instance = new ToolwordsUnderivedImpl();
     
     public static ToolwordsUnderivedImpl getInstance() {
       return instance;
     }
     
     private ToolwordsUnderivedImpl() {
       this.partOfspeech = new ArrayList();
       this.unvoweledForm = new HashMap<>();
       this.allVoweled = new ArrayList();
       addToolwordsUnderivedImpl();
     }
     
     private void addToolwordsUnderivedImpl() {
       addPartOfSpeech();
       addUnvoweledForm();
       addVoweledForm();
     }
     
     public void addPartOfSpeech() {
       if (isEmptyPartOfSpeech()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Toolwords.PartOfSpeech");
         this.partOfspeech = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addUnvoweledForm() {
       if (isEmptyUnvoweled()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Toolwords.Unvoweled");
         this.unvoweledForm = IOFile.getInstance().deserializeMap(data);
       } 
     }
     
     public void addVoweledForm() {
       if (isEmptyVoweled()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Toolwords.Voweled");
         this.allVoweled = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public Toolwords getVoweledFormValue(int idVoweled) {
       return (Toolwords) this.allVoweled.get(idVoweled - 1);
     }
     
     public int size() {
       return this.allVoweled.size();
     }
     
     public void addCaseOrMood() {}
   }


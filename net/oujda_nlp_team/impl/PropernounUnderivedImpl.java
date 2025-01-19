   package net.oujda_nlp_team.impl;
   
   import java.util.ArrayList;
import java.util.HashMap;

import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.Voweled;
import net.oujda_nlp_team.factory.UnderivedFactory;
import net.oujda_nlp_team.interfaces.IUnderived;
import net.oujda_nlp_team.util.IOFile;
   
   public class PropernounUnderivedImpl extends UnderivedFactory implements IUnderived {
     private static final PropernounUnderivedImpl instance = new PropernounUnderivedImpl();
     
     public static PropernounUnderivedImpl getInstance() {
       return instance;
     }
     
     private PropernounUnderivedImpl() {
       this.iCaseOrMood = new ArrayList();
       this.partOfspeech = new ArrayList();
       this.unvoweledForm = new HashMap<>();
       this.allVoweled = new ArrayList();
       addCaseOrMood();
       addPartOfSpeech();
       addUnvoweledForm();
       addVoweledForm();
     }
     
     public void addCaseOrMood() {
       if (isEmptyCaseOrMood()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.CaseOrMood");
         this.iCaseOrMood = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addPartOfSpeech() {
       if (isEmptyPartOfSpeech()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.PartOfSpeech");
         this.partOfspeech = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addUnvoweledForm() {
       if (isEmptyUnvoweled()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.Unvoweled");
         this.unvoweledForm = IOFile.getInstance().deserializeMap(data);
       } 
     }
     
     public void addVoweledForm() {
       if (isEmptyVoweled()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.Voweled");
         this.allVoweled = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public Voweled getVoweledFormValue(int idVoweled) {
       return (Voweled) this.allVoweled.get(idVoweled - 1);
     }
     
     public int size() {
       return this.allVoweled.size();
     }
   }


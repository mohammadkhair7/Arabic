package net.oujda_nlp_team.interfaces;

import java.util.Set;

public interface IUnderived {
  Object getPartOfSpeech(int paramInt);
  
  String getUnvoweledFormValue(String paramString);
  
  Object getVoweledFormValue(int paramInt);
  
  Set getUnvoweledForm();
  
  void addCaseOrMood();
  
  void addPartOfSpeech();
  
  void addUnvoweledForm();
  
  void addVoweledForm();
  
  boolean containsVal(String paramString);
  
  int size();
  
  void clear();
}


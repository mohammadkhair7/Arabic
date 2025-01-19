package net.oujda_nlp_team.interfaces;

import java.util.List;

public interface IClitic {
  void addClitics();
  
  void addXMLClitics();
  
  List getListsClitics(String paramString);
  
  void clear();
}


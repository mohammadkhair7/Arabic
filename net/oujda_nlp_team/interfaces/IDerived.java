package net.oujda_nlp_team.interfaces;

import java.util.List;

import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Root;
import net.oujda_nlp_team.entity.Unvoweled;
import net.oujda_nlp_team.entity.Voweled;

public interface IDerived {
  void addCaseOrMood();
  
  void addPartOfSpeech();
  
  void addAllVectorList();
  
  boolean containsValVector(int paramInt);
  
  List getListVectors(int paramInt);
  
  List getInfoResult(String paramString1, String paramString2, int paramInt);
  
  PartOfSpeech getPartOfSpeech(int paramInt);
  
  boolean isEmptyQuadriliteralRootMap();
  
  boolean isEmptyQuadriliteralRootList();
  
  boolean isEmptyTrilateralRootList();
  
  boolean isEmptyTrilateralRootMap();
  
  void addAllQuadriliteralRootMap();
  
  void addAllTrilateralRootMap();
  
  void addQuadriliteralRootList();
  
  void addTrilateralRootList();
  
  boolean containsQuadriliteralRoot(String paramString);
  
  boolean containsTrilateralRoot(String paramString);
  
  boolean containsQuadriliteralCharRoot(char paramChar);
  
  boolean containsTrilateralCharRoot(char paramChar);
  
  int getQuadriliteralRootList(String paramString);
  
  int getTrilateralRootList(String paramString);
  
  List<Root> getQuadriliteralRootMap(char paramChar);
  
  List<Root> getTrilateralRootMap(char paramChar);
  
  Root getQuadriliteralRoot(String paramString);
  
  Root getTrilateralRoot(String paramString);
  
  String getLenTrilateralRoot(String paramString, int paramInt);
  
  String getLenQuadriliteralRoot(String paramString, int paramInt);
  
  void addAllUnvoweledPatternMap();
  
  boolean containsIdUnvoweled(int paramInt);
  
  List getUnvoweledList(int paramInt);
  
  Unvoweled getUnvoweledForm(int paramInt1, int paramInt2);
  
  void addAllVoweledDiacPatternLemmaMap();
  
  void addAllVoweledCanonicPatternLemmaMap();
  
  void addAllVoweledDiacPatternStemMap();
  
  void addAllVoweledCanonicPatternStemMap();
  
  boolean containsIdVoweledDiacPatternLemma(int paramInt);
  
  boolean containsIdVoweledCanonicPatternLemma(int paramInt);
  
  boolean containsIdVoweledDiacPatternStem(int paramInt);
  
  boolean containsIdVoweledCanonicPatternStem(int paramInt);
  
  List getVoweledDiacPatternLemmaList(int paramInt);
  
  List getVoweledCanonicPatternLemmaList(int paramInt);
  
  List getVoweledDiacPatternStemList(int paramInt);
  
  List getVoweledCanonicPatternStemList(int paramInt);
  
  Voweled getVoweledDiacPatternLemma(int paramInt1, int paramInt2);
  
  Voweled getVoweledCanonicPatternLemma(int paramInt1, int paramInt2);
  
  Voweled getVoweledDiacPatternStem(int paramInt1, int paramInt2);
  
  Voweled getVoweledCanonicPatternStem(int paramInt1, int paramInt2);
  
  void clear();
}


package net.oujda_nlp_team.interfaces;

import java.util.List;

import net.oujda_nlp_team.entity.Segment;

public interface IDerivedAnalyzer {
  List analyzedSegment(String paramString, Segment paramSegment);
  
  List getPossiblesSolutions(String paramString, Segment paramSegment);
  
  void clear();
}

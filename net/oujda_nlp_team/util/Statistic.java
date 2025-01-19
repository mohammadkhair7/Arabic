   package net.oujda_nlp_team.util;
   
   public class Statistic {
     private int nbWords;
     
     private int nbTotalWords;
     
     private int nbNoAnalyzedWord;
     
     private int nbAnalyzedWord;
     
     private long times;
     
     public void setNbWord(int nbWord) {
       this.nbWords = nbWord;
     }
     
     public void setNbTotalWords(int nbTotalWords) {
       this.nbTotalWords = nbTotalWords;
     }
     
     public void setNbAnalyzedWord(int nbAnalyzedWord) {
       this.nbAnalyzedWord = nbAnalyzedWord;
     }
     
     public void setNbNoAnalyzedWord(int nbNoAnalyzedWord) {
       this.nbNoAnalyzedWord = nbNoAnalyzedWord;
     }
     
     public void setTimes(long times) {
       this.times = times;
     }
     
     public int getNbWord() {
       return this.nbWords;
     }
     
     public int getNbTotalWords() {
       return this.nbTotalWords;
     }
     
     public int getNbAnalyzedWord() {
       return this.nbAnalyzedWord;
     }
     
     public int getNbAnalyzedWordPourCent() {
       return (this.nbWords == 0) ? 0 : (this.nbAnalyzedWord * 100 / this.nbTotalWords);
     }
     
     public int getNbNoAnalyzedWord() {
       return this.nbNoAnalyzedWord;
     }
     
     public int getNbNoAnalyzedWordPourCent() {
       return (this.nbWords == 0) ? 0 : (this.nbNoAnalyzedWord * 100 / this.nbTotalWords);
     }
     
     public int getWordsOfSec() {
       return (int)((this.times == 0L) ? 0L : ((this.nbWords * 1000) / this.times));
     }
   }


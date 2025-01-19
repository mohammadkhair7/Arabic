/*    */ package net.oujda_nlp_team.util.xml;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import net.oujda_nlp_team.entity.Formulas;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
/*    */ 
/*    */ public class XMLFormulas extends DefaultHandler {
/*    */   private List iFormulasXml;
/*    */   
/*    */   public synchronized List parserXml(InputStream path) {
/* 34 */     XMLFormulas parse = new XMLFormulas();
/* 35 */     parse.iFormulasXml = new ArrayList();
/*    */     try {
/* 37 */       SAXParserFactory spfac = SAXParserFactory.newInstance();
/* 38 */       SAXParser sp = spfac.newSAXParser();
/* 39 */       sp.parse(path, parse);
/* 41 */     } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
/* 42 */       System.out.println(" : " + ex);
/*    */     } 
/* 44 */     return parse.iFormulasXml;
/*    */   }
/*    */   
/*    */   public synchronized List parserXml(String path) {
/* 48 */     XMLFormulas parse = new XMLFormulas();
/* 49 */     parse.iFormulasXml = new ArrayList();
/*    */     try {
/* 51 */       SAXParserFactory spfac = SAXParserFactory.newInstance();
/* 52 */       SAXParser sp = spfac.newSAXParser();
/* 53 */       sp.parse(path, parse);
/* 55 */     } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
/* 56 */       System.out.println(" : " + ex);
/*    */     } 
/* 58 */     return parse.iFormulasXml;
/*    */   }
/*    */   
/*    */   public void characters(char[] buffer, int start, int length) {}
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 67 */     if (qName.equalsIgnoreCase("formula")) {
/* 68 */       Formulas f = new Formulas();
/* 69 */       f.setIdPartOfSpeech(attributes.getValue("idPOS"));
/* 70 */       f.setIdRoot(attributes.getValue("idR"));
/* 71 */       f.setIdCanonicPatternStem(attributes.getValue("idCPS"));
/* 72 */       f.setIdDiacPatternStem(attributes.getValue("idDPS"));
/* 73 */       f.setIdCanonicPatternLemma(attributes.getValue("idCPL"));
/* 74 */       f.setIdDiacPatternLemma(attributes.getValue("idDPL"));
/* 75 */       f.setIdCaseOrMood(attributes.getValue("idCoM"));
/* 76 */       this.iFormulasXml.add(f);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void endElement(String uri, String localName, String qName) {}
/*    */ }


/* Location:              /home/samir/eclipse-workspace/Belayachi-20200130/Belayachi-20200130/ADAT-Analyzer-20180101.v2.jar!/net/oujda_nlp_team/util/xml/XMLFormulas.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.oujda_nlp_team.util.xml;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import net.oujda_nlp_team.entity.Exceptional;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
/*    */ 
/*    */ public class XMLExceptional extends DefaultHandler {
/*    */   private Map iExceptionalXml;
/*    */   
/*    */   public Map parserXml(InputStream path) {
/* 34 */     XMLExceptional parse = new XMLExceptional();
/* 35 */     parse.iExceptionalXml = new HashMap<>();
/*    */     try {
/* 37 */       SAXParserFactory spfac = SAXParserFactory.newInstance();
/* 38 */       SAXParser sp = spfac.newSAXParser();
/* 39 */       sp.parse(path, parse);
/* 41 */     } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
/* 42 */       System.out.println(" : " + ex);
/*    */     } 
/* 44 */     return parse.iExceptionalXml;
/*    */   }
/*    */   
/*    */   public Map parserXml(String path) {
/* 48 */     XMLExceptional parse = new XMLExceptional();
/* 49 */     parse.iExceptionalXml = new HashMap<>();
/*    */     try {
/* 51 */       SAXParserFactory spfac = SAXParserFactory.newInstance();
/* 52 */       SAXParser sp = spfac.newSAXParser();
/* 53 */       sp.parse(path, parse);
/* 55 */     } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
/* 56 */       System.out.println(" : " + ex);
/*    */     } 
/* 58 */     return parse.iExceptionalXml;
/*    */   }
/*    */   
/*    */   public void characters(char[] buffer, int start, int length) {}
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 67 */     if (qName.equalsIgnoreCase("exceptionalword")) {
/* 68 */       String un = attributes.getValue("unvoweledform");
/* 69 */       Exceptional e = new Exceptional();
/* 70 */       e.setUnvoweledform(un);
/* 71 */       e.setVoweledform(attributes.getValue("voweledform"));
/* 72 */       e.setProclitic(attributes.getValue("prefix"));
/* 73 */       e.setStem(attributes.getValue("stem"));
/* 74 */       e.setPartOfSpeech(attributes.getValue("type"));
/* 75 */       e.setEnclitic(attributes.getValue("suffix"));
/* 76 */       this.iExceptionalXml.put(un, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void endElement(String uri, String localName, String qName) {}
/*    */ }


/* Location:              /home/samir/eclipse-workspace/Belayachi-20200130/Belayachi-20200130/ADAT-Analyzer-20180101.v2.jar!/net/oujda_nlp_team/util/xml/XMLExceptional.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
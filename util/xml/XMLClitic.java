/*    */ package net.oujda_nlp_team.util.xml;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import net.oujda_nlp_team.entity.Clitic;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
/*    */ 
/*    */ public class XMLClitic extends DefaultHandler {
/*    */   private Map<String, List> lClitics;
/*    */   
/*    */   private String _qName;
/*    */   
/*    */   public Map<String, List> parserXml(InputStream path, String qName) {
/* 37 */     XMLClitic parse = new XMLClitic();
/* 38 */     parse.lClitics = new HashMap<>();
/* 39 */     parse._qName = qName;
/*    */     try {
/* 42 */       SAXParserFactory spfac = SAXParserFactory.newInstance();
/* 44 */       SAXParser sp = spfac.newSAXParser();
/* 45 */       sp.parse(path, parse);
/* 47 */     } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
/* 48 */       System.out.println(" : " + ex);
/*    */     } 
/* 50 */     return parse.lClitics;
/*    */   }
/*    */   
/*    */   public Map<String, List> parserXml(String path, String qName) {
/* 55 */     XMLClitic parse = new XMLClitic();
/* 56 */     parse.lClitics = new HashMap<>();
/* 57 */     parse._qName = qName;
/*    */     try {
/* 60 */       SAXParserFactory spfac = SAXParserFactory.newInstance();
/* 62 */       SAXParser sp = spfac.newSAXParser();
/* 63 */       sp.parse(path, parse);
/* 65 */     } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
/* 66 */       System.out.println(" : " + ex);
/*    */     } 
/* 68 */     return parse.lClitics;
/*    */   }
/*    */   
/*    */   public void characters(char[] buffer, int start, int length) {}
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 79 */     if (qName.equalsIgnoreCase(this._qName)) {
/* 81 */       Clitic affix = new Clitic();
/* 82 */       String unvoweled = attributes.getValue("unvoweledform");
/* 83 */       affix.setUnvoweledform(unvoweled);
/* 84 */       affix.setVoweledform(attributes.getValue("voweledform"));
/* 85 */       affix.setDesc(attributes.getValue("desc"));
/* 86 */       affix.setClasse(attributes.getValue("classe"));
/* 88 */       if (this.lClitics.containsKey(unvoweled)) {
/* 89 */         ((List<Clitic>)this.lClitics.get(unvoweled)).add(affix);
/*    */       } else {
/* 92 */         List<Clitic> list = new ArrayList();
/* 93 */         list.add(affix);
/* 94 */         this.lClitics.put(unvoweled, list);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void endElement(String uri, String localName, String qName) {}
/*    */ }


/* Location:              /home/samir/eclipse-workspace/Belayachi-20200130/Belayachi-20200130/ADAT-Analyzer-20180101.v2.jar!/net/oujda_nlp_team/util/xml/XMLClitic.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
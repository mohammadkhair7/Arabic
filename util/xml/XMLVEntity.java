   package net.oujda_nlp_team.util.xml;
   
   import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import net.oujda_nlp_team.entity.VEntity;
   
   public class XMLVEntity extends DefaultHandler {
     private List iVEntity;
     
     public synchronized List parserXml(InputStream path) {
       XMLVEntity parse = new XMLVEntity();
       parse.iVEntity = new LinkedList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iVEntity;
     }
     
     public List parserXml(String path) {
       XMLVEntity parse = new XMLVEntity();
       parse.iVEntity = new LinkedList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iVEntity;
     }
     
     public void characters(char[] buffer, int start, int length) {}
     
     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if (qName.equalsIgnoreCase("casemood") || qName.equalsIgnoreCase("POSDesc")) {
         VEntity cm = new VEntity();
         cm.setId(attributes.getValue("id"));
         cm.setValAR(attributes.getValue("valAR"));
         cm.setValEN(attributes.getValue("valEN"));
         this.iVEntity.add(cm);
       } 
     }
     
     public void endElement(String uri, String localName, String qName) {}
   }

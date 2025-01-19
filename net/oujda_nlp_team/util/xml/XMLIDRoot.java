   package net.oujda_nlp_team.util.xml;
   
   import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
   
   public class XMLIDRoot extends DefaultHandler {
     private Map<String, Integer> idRootXml;
     
     public synchronized Map parserXml(InputStream path) {
       XMLIDRoot parse = new XMLIDRoot();
       parse.idRootXml = new HashMap<>();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.idRootXml;
     }
     
     public synchronized Map parserXml(String path) {
       XMLIDRoot parse = new XMLIDRoot();
       parse.idRootXml = new HashMap<>();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.idRootXml;
     }
     
     public void characters(char[] buffer, int start, int length) {}
     
     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if (qName.equalsIgnoreCase("root")) {
         int id = Integer.parseInt(attributes.getValue("id"));
         String val = attributes.getValue("val");
         this.idRootXml.put(val, Integer.valueOf(id));
       } 
     }
     
     public void endElement(String uri, String localName, String qName) {}
   }

   package net.oujda_nlp_team.util.xml;
   
   import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import net.oujda_nlp_team.entity.Unvoweled;
   
   public class XMLUnvoweled extends DefaultHandler {
     private List iUnvoweledXml;
     
     private Map iUnvoweledUnderived;
     
     public List parserDerivedXml(InputStream path) {
       XMLUnvoweled parse = new XMLUnvoweled();
       parse.iUnvoweledXml = new ArrayList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iUnvoweledXml;
     }
     
     public List parserDerivedXml(String path) {
       XMLUnvoweled parse = new XMLUnvoweled();
       parse.iUnvoweledXml = new ArrayList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iUnvoweledXml;
     }
     
     public Map parserNonDerivedXml(InputStream path) {
       XMLUnvoweled parse = new XMLUnvoweled();
       parse.iUnvoweledUnderived = new HashMap<>();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iUnvoweledUnderived;
     }
     
     public Map parserNonDerivedXml(String path) {
       XMLUnvoweled parse = new XMLUnvoweled();
       parse.iUnvoweledUnderived = new HashMap<>();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iUnvoweledUnderived;
     }
     
     public void characters(char[] buffer, int start, int length) {}
     
     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if (qName.equalsIgnoreCase("pattern")) {
         Unvoweled unvoweledForm = new Unvoweled();
         unvoweledForm.setVal(attributes.getValue("val"));
         unvoweledForm.setRules(attributes.getValue("rules"));
         unvoweledForm.setIds(attributes.getValue("ids"));
         this.iUnvoweledXml.add(unvoweledForm);
       } 
       if (qName.equalsIgnoreCase("propernoun")) {
         String unvoweledform = attributes.getValue("unvoweledform");
         String ids = attributes.getValue("ids");
         this.iUnvoweledUnderived.put(unvoweledform, ids);
       } 
       if (qName.equalsIgnoreCase("toolword")) {
         String unvoweledform = attributes.getValue("unvoweledform");
         String ids = attributes.getValue("ids");
         this.iUnvoweledUnderived.put(unvoweledform, ids);
       } 
     }
     
     public void endElement(String uri, String localName, String qName) {}
   }

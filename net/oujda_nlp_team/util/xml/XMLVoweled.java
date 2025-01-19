   package net.oujda_nlp_team.util.xml;
   
   import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import net.oujda_nlp_team.entity.Toolwords;
import net.oujda_nlp_team.entity.Voweled;
   
   public class XMLVoweled extends DefaultHandler {
     private List iVoweled;
     
     public List parserXml(InputStream path) {
       XMLVoweled parse = new XMLVoweled();
       parse.iVoweled = new ArrayList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iVoweled;
     }
     
     public List parserXml(String path) {
       XMLVoweled parse = new XMLVoweled();
       parse.iVoweled = new ArrayList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iVoweled;
     }
     
     public void characters(char[] buffer, int start, int length) {}
     
     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if (qName.equalsIgnoreCase("propernoun")) {
         Voweled voweledForm = new Voweled();
         voweledForm.setId(attributes.getValue("id"));
         voweledForm.setVoweledform(attributes.getValue("vowform"));
         voweledForm.setLemma(attributes.getValue("lemma"));
         voweledForm.setRoot(attributes.getValue("root"));
         voweledForm.setPartOfSpeech(attributes.getValue("pos"));
         voweledForm.setCase(attributes.getValue("case"));
         this.iVoweled.add(voweledForm);
       } else if (qName.equalsIgnoreCase("toolword")) {
         Toolwords voweledForm = new Toolwords();
         voweledForm.setId(attributes.getValue("id"));
         voweledForm.setVoweledform(attributes.getValue("voweledform"));
         voweledForm.setPartOfSpeech(attributes.getValue("type"));
         voweledForm.setClassProclitic(attributes.getValue("prefclass"));
         voweledForm.setClassEnclitic(attributes.getValue("suffclass"));
         voweledForm.setPriority(attributes.getValue("priority"));
         voweledForm.setRoot(attributes.getValue("root"));
         voweledForm.setLemma(attributes.getValue("lemma"));
         this.iVoweled.add(voweledForm);
       } else if (qName.equalsIgnoreCase("pattern")) {
         Voweled voweledForm = new Voweled();
         voweledForm.setId(attributes.getValue("id"));
         voweledForm.setVal(attributes.getValue("val"));
         voweledForm.setFreq(attributes.getValue("ferq"));
         this.iVoweled.add(voweledForm);
       } 
     }
     
     public void endElement(String uri, String localName, String qName) {}
   }

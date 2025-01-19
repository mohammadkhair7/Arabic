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

import net.oujda_nlp_team.entity.PartOfSpeech;
   
   public class XMLPartOfSpeech extends DefaultHandler {
     private List iPartOfSpeechXml;
     
     private String isVal;
     
     public XMLPartOfSpeech() {}
     
     public XMLPartOfSpeech(String _isVal) {
       this.isVal = _isVal;
     }
     
     public synchronized List parserXml(InputStream path, String _isVal) {
       XMLPartOfSpeech parse = new XMLPartOfSpeech(this.isVal);
       parse.iPartOfSpeechXml = new ArrayList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iPartOfSpeechXml;
     }
     
     public List parserXml(String path, String _isVal) {
       XMLPartOfSpeech parse = new XMLPartOfSpeech(_isVal);
       parse.iPartOfSpeechXml = new ArrayList();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|java.io.IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iPartOfSpeechXml;
     }
     
     public void characters(char[] buffer, int start, int length) {}
     
     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if (qName.equalsIgnoreCase("POS"))
         if (this.isVal.equals("Noun")) {
           PartOfSpeech pos = new PartOfSpeech();
           pos.setId(attributes.getValue("id"));
           pos.setMain(attributes.getValue("main"));
           pos.setType(attributes.getValue("type"));
           pos.setGender(attributes.getValue("gender"));
           pos.setNumber(attributes.getValue("number"));
           pos.setNbroot(attributes.getValue("nbroot"));
           pos.setDefinit(attributes.getValue("definit"));
           pos.setFreq(attributes.getValue("freq"));
           this.iPartOfSpeechXml.add(pos);
         } else if (this.isVal.equals("Verb")) {
           PartOfSpeech pos = new PartOfSpeech();
           pos.setId(attributes.getValue("id"));
           pos.setMain(attributes.getValue("main"));
           pos.setType(attributes.getValue("type"));
           pos.setPerson(attributes.getValue("person"));
           pos.setPerson2(attributes.getValue("person2"));
           pos.setVoice(attributes.getValue("voice"));
           pos.setAugmented(attributes.getValue("augmented"));
           pos.setEmphasized(attributes.getValue("emphasized"));
           pos.setTransitivity(attributes.getValue("transitivity"));
           pos.setNbroot(attributes.getValue("nbroot"));
           pos.setFreq(attributes.getValue("freq"));
           this.iPartOfSpeechXml.add(pos);
         } else if (this.isVal.equals("Propernoun")) {
           PartOfSpeech pos = new PartOfSpeech();
           pos.setId(attributes.getValue("id"));
           pos.setMain(attributes.getValue("main"));
           pos.setType(attributes.getValue("type"));
           pos.setGender(attributes.getValue("gender"));
           pos.setNumber(attributes.getValue("number"));
           pos.setDefinit(attributes.getValue("definit"));
           pos.setFreq(attributes.getValue("freq"));
           this.iPartOfSpeechXml.add(pos);
         }  
     }
     
     public void endElement(String uri, String localName, String qName) {}
   }

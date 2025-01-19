   package net.oujda_nlp_team.util.xml;
   
   import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import net.oujda_nlp_team.entity.Root;
   
   public class XMLRoot extends DefaultHandler {
     private List<Root> iRootsXml;
     
     public synchronized List parserXml(InputStream path) {
       XMLRoot parse = new XMLRoot();
       parse.iRootsXml = new ArrayList<>();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException ex) {
         System.out.println(" : " + ex);
       } catch (SAXException ex) {
         System.out.println(" : " + ex);
       } catch (IOException ex) {
         System.out.println(" : " + ex);
       } 
       return parse.iRootsXml;
     }
     
     public List parserXml(String path) {
       XMLRoot parse = new XMLRoot();
       parse.iRootsXml = new ArrayList<>();
       try {
         SAXParserFactory spfac = SAXParserFactory.newInstance();
         SAXParser sp = spfac.newSAXParser();
         sp.parse(path, parse);
       } catch (ParserConfigurationException|SAXException|IOException ex) {
         System.out.println(" : " + path);
       } 
       return parse.iRootsXml;
     }
     
     public void characters(char[] buffer, int start, int length) {}
     
     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if (qName.equalsIgnoreCase("root")) {
         Root root = new Root();
         root.setVal(attributes.getValue("val"));
         root.setLen1(attributes.getValue("L1"));
         root.setLen2(attributes.getValue("L2"));
         root.setLen3(attributes.getValue("L3"));
         root.setLen4(attributes.getValue("L4"));
         root.setLen5(attributes.getValue("L5"));
         root.setLen6(attributes.getValue("L6"));
         root.setLen7(attributes.getValue("L7"));
         root.setLen8(attributes.getValue("L8"));
         root.setLen9(attributes.getValue("L9"));
         root.setLen10(attributes.getValue("L10"));
         root.setLen11(attributes.getValue("L11"));
         root.setLen12(attributes.getValue("L12"));
         root.setFreq(attributes.getValue("freq"));
         this.iRootsXml.add(root);
       } 
     }
     
     public void endElement(String uri, String localName, String qName) {}
   }

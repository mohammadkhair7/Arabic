package net.oujda_nlp_team.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;

public class IOFile {
  private static final IOFile instance = new IOFile();
  
  public static IOFile getInstance() {
    return instance;
  }
  
  public InputStream openStream(String nameFile) {
    try {
      return getClass().getResource(nameFile).openStream();
    } catch (IOException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
      return null;
    } 
  }
  
  public InputStream openFileXml(String nameFile) {
    try {
      return getClass().getResource(nameFile).openStream();
    } catch (IOException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
      return null;
    } 
  }
  
  public void openFileChooser(JTextPane inputText, String nameCharset, int seuil) {
    JFileChooser jfc = new JFileChooser();
    int resultat = jfc.showOpenDialog(null);
    if (resultat == 0) {
      readFile(jfc.getSelectedFile(), nameCharset, inputText, seuil);
    } else {
      System.out.println("Error : Fichier n'existe pas");
    } 
  }
  
  public String getArabicURL(String str, Map<String, String> iMap) {
    Iterator<String> it = iMap.keySet().iterator();
    while (it.hasNext()) {
      String key = it.next();
      if (str.endsWith(key))
        return str.substring(0, str.length() - 10) + (String)iMap.get(key); 
    } 
    return str;
  }
  
  public void serializeMap(String BinDirMap, Map input) {
    try {
      FileOutputStream fos = new FileOutputStream(BinDirMap);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(input);
      oos.close();
      fos.close();
    } catch (IOException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
  }
  
  public void serializeList(String BinDirMap, List input) {
    try {
      FileOutputStream fos = new FileOutputStream(BinDirMap);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(input);
      oos.close();
      fos.close();
    } catch (IOException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
  }
  
  public void serializeObject(String BinDirMap, Object input) {
    try {
      FileOutputStream fos = new FileOutputStream(BinDirMap);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(input);
      oos.close();
      fos.close();
    } catch (IOException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
  }
  
  public Map deserializeMap(String BinDirMap) {
    try {
      ObjectInputStream ois = new ObjectInputStream(openFileXml(BinDirMap));
      Map map = (Map)ois.readObject();
      ois.close();
      return map;
    } catch (IOException|ClassNotFoundException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
      return new HashMap<>();
    } 
  }
  
  public List deserializeList(String BinDirMap) {
    try {
      ObjectInputStream ois = new ObjectInputStream(openFileXml(BinDirMap));
      List map = (List)ois.readObject();
      ois.close();
      return map;
    } catch (IOException|ClassNotFoundException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
      return new ArrayList();
    } 
  }
  
  public Object deserializeObject(String BinDirMap) {
    try {
      ObjectInputStream ois = new ObjectInputStream(resolveName1(BinDirMap));
      Object map = ois.readObject();
      ois.close();
      return map;
    } catch (IOException|ClassNotFoundException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
      return new HashMap<>();
    } 
  }
  
  private InputStream resolveName(String name) {
    if (name == null)
      return null; 
    if (!name.startsWith("/")) {
      String baseName = getClass().getName();
      int index = baseName.lastIndexOf('.');
      if (index != -1)
        name = baseName.substring(0, index).replace('.', '/') + "/" + name; 
    } else {
      name = name.substring(1);
    } 
    return getClass().getClassLoader().getResourceAsStream(name);
  }
  
  private InputStream resolveName1(String name) {
    try {
      return new FileInputStream(name);
    } catch (IOException ex) {
      Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, (String)null, ex);
      return null;
    } 
  }
  
  public Set readFileToSet(String nameFileIn, String nameCharset) {
    Set lset = new HashSet();
    List list = readFileToList(nameFileIn, nameCharset);
    lset.addAll(list);
    return lset;
  }
  
  public List readFileToList(String nameFileIn, String nameCharset) {
    List<String> list = new ArrayList();
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(nameFileIn)), nameCharset));
      String line;
      while ((line = in.readLine()) != null)
        list.add(line); 
      in.close();
    } catch (IOException ex) {
      System.out.println("Erreur : " + ex);
    } 
    return list;
  }
  
  public List readFileToList(InputStream nameFileIn, String nameCharset) {
    List<String> list = new ArrayList();
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(nameFileIn, nameCharset));
      String line;
      while ((line = in.readLine()) != null)
        list.add(line); 
      in.close();
    } catch (IOException ex) {
      System.out.println("Erreur : " + ex);
    } 
    return list;
  }
  
  public List readFileStreamToList(String nameFileIn, String nameCharset) {
    List<String> list = new ArrayList();
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(openStream(nameFileIn), nameCharset));
      String line;
      while ((line = in.readLine()) != null)
        list.add(line); 
      in.close();
    } catch (IOException ex) {
      System.out.println("Erreur : " + ex);
    } 
    return list;
  }
  
  public List<String> readSubFileToList(String nameFileIn, String nameCharset, int start) {
    List<String> iResult = new ArrayList<>();
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(nameFileIn)), nameCharset));
      int i = 0;
      String line;
      while ((line = in.readLine()) != null) {
        if (++i >= start)
          iResult.add(line); 
      } 
      in.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erreur ...." + e);
    } catch (IOException e) {
      System.out.println("Erreur ...." + e);
    } 
    return iResult;
  }
  
  public void readFile(File fichier, String nameCharset, JTextPane inputText, int seuil) {
    StringBuilder buf = new StringBuilder();
    try {
      inputText.setText("");
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), nameCharset));
      int i = 0;
      String ligne;
      while (i < seuil && (ligne = in.readLine()) != null) {
        buf.append(ligne).append('\n');
        i++;
      } 
      inputText.setText(buf.toString());
    } catch (IOException e) {
      System.out.println("Error : " + e);
    } 
  }
  
  public List<String> readFileToList(String namePathIN, String charsetCode, int start, int fin) {
    List<String> iResult = new ArrayList<>();
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(namePathIN)), charsetCode));
      int i = 0;
      String line;
      while ((line = in.readLine()) != null && i < fin) {
        if (i >= start)
          iResult.add(line); 
        i++;
      } 
      in.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erreur ...." + e);
    } catch (IOException e) {
      System.out.println("Erreur ...." + e);
    } 
    return iResult;
  }
  
  public Properties loadProperties(InputStream filename) {
    try {
      Properties properties = new Properties();
      properties.load(filename);
      filename.close();
      return properties;
    } catch (IOException ex) {
      System.out.println("Erreur : " + ex);
      return null;
    } 
  }
  
  public void writeSetToFile(String nameFile, String nameCharset, Set l) {
    List list = new LinkedList();
    list.addAll(l);
    writeListToFile(nameFile, nameCharset, list);
  }
  
  public void writeListToFile(String nameFile, String nameCharset, Set l) {
    List<Comparable> list = new LinkedList();
    list.addAll(l);
    Collections.sort(list);
    writeListToFile(nameFile, nameCharset, (Set) list);
  }
  
  public void writeListToFile(String nameFile, String nameCharset, List<String> l) {
    try {
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(nameFile)), nameCharset);
      Iterator<String> it = l.iterator();
      while (it.hasNext()) {
        String normalizedWord = it.next();
        out.write(normalizedWord + "\n");
      } 
      out.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erreur ...." + e);
    } catch (IOException e) {
      System.out.println("Erreur ...." + e);
    } 
  }
  
  public void writeListToFile(String nameFile, String nameCharset, List<String> l, boolean ecraser) {
    try {
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(nameFile), ecraser), nameCharset);
      Iterator<String> it = l.iterator();
      while (it.hasNext()) {
        String normalizedWord = it.next();
        out.write(normalizedWord + "\n");
      } 
      out.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erreur ...." + e);
    } catch (IOException e) {
      System.out.println("Erreur ...." + e);
    } 
  }
  
  public void writeMapToFile(String nameFile, String nameCharset, Map<String, Integer> map, boolean ecraser) {
    try {
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(nameFile), ecraser), nameCharset);
      for (Map.Entry<String, Integer> entry : map.entrySet())
        out.write((String)entry.getKey() + "\t" + entry.getValue() + "\n"); 
      out.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erreur ...." + e);
    } catch (IOException e) {
      System.out.println("Erreur ...." + e);
    } 
  }
  
  public void writeStringToFile(String nameFile, String nameCharset, String text, boolean ecraser) {
    try {
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(nameFile), ecraser), nameCharset);
      out.write(text + "\n");
      out.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erreur ...." + e);
    } catch (IOException e) {
      System.out.println("Erreur ...." + e);
    } 
  }
}

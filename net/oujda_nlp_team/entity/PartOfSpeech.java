   package net.oujda_nlp_team.entity;
   
   import java.io.Serializable;
   
   public class PartOfSpeech implements Serializable {
     private static final long serialVersionUID = 1240L;
     
     private String id;
     
     private String main;
     
     private String type;
     
     private String gender;
     
     private String number;
     
     private String definit;
     
     private String nbroot;
     
     private String person;
     
     private String person2;
     
     private String voice;
     
     private String emphasized;
     
     private String transitivity;
     
     private String augmented;
     
     private String freq;
     
     public void setId(String _id) {
       this.id = _id;
     }
     
     public void setMain(String _main) {
       this.main = _main;
     }
     
     public void setType(String _type) {
       this.type = _type;
     }
     
     public void setGender(String _gender) {
       this.gender = _gender;
     }
     
     public void setNumber(String _number) {
       this.number = _number;
     }
     
     public void setDefinit(String _definit) {
       this.definit = _definit;
     }
     
     public void setNbroot(String _nbroot) {
       this.nbroot = _nbroot;
     }
     
     public void setPerson(String _person) {
       this.person = _person;
     }
     
     public void setPerson2(String _person2) {
       this.person2 = _person2;
     }
     
     public void setVoice(String _voice) {
       this.voice = _voice;
     }
     
     public void setEmphasized(String _emphasized) {
       this.emphasized = _emphasized;
     }
     
     public void setTransitivity(String _transitivity) {
       this.transitivity = _transitivity;
     }
     
     public void setAugmented(String _augmented) {
       this.augmented = _augmented;
     }
     
     public void setFreq(String _freq) {
       this.freq = _freq;
     }
     
     public String getId() {
       return this.id;
     }
     
     public String getMain() {
       return this.main;
     }
     
     public String getType() {
       return this.type;
     }
     
     public String getGender() {
       return this.gender;
     }
     
     public String getNumber() {
       return this.number;
     }
     
     public String getDefinit() {
       return this.definit;
     }
     
     public String getNbroot() {
       return this.nbroot;
     }
     
     public String getPerson() {
       return this.person;
     }
     
     public String getPerson2() {
       return this.person2;
     }
     
     public String getEmphasized() {
       return this.emphasized;
     }
     
     public String getTransitivity() {
       return this.transitivity;
     }
     
     public String getAugmented() {
       return this.augmented;
     }
     
     public String getVoice() {
       return this.voice;
     }
     
     public String getFreq() {
       return this.freq;
     }

	@Override
	public String toString() {
		return "PartOfSpeech [id=" + id + ", main=" + main + ", type=" + type + ", gender=" + gender + ", number="
				+ number + ", definit=" + definit + ", nbroot=" + nbroot + ", person=" + person + ", person2=" + person2
				+ ", voice=" + voice + ", emphasized=" + emphasized + ", transitivity=" + transitivity + ", augmented="
				+ augmented + ", freq=" + freq + "]";
	}
     
   }



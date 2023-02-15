package auftrag.entities.persons;

import xmlEntities.caseData.participantData.Address;

public class Person {
    private String anrede;
    private String vName;
    private String nName;
    private Address address;
    private String tel;
    private String mail;

    public Person(String anrede, String vName, String nName, Address address, String tel, String mail) {
        this.anrede = anrede;
        this.vName = vName;
        this.nName = nName;
        this.address = address;
        this.tel = tel;
        this.mail = mail;
    }

    public Person (){

    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

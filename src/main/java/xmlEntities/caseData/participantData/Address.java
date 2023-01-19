package xmlEntities.caseData.participantData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Address {

    private String street;
    private String number;
    private String postalcode;
    private String city;
    private Country country;
    private List<Contact> contacts;

    public Address(){

    }

    public Address(String street, String number, String postalcode, String city, Country country, List<Contact> contacts) {
        this.street = street;
        this.number = number;
        this.postalcode = postalcode;
        this.city = city;
        this.country = country;
        this.contacts = contacts;
    }
    @XmlElement
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    @XmlElement
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    @XmlElement
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
    @XmlElement
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @XmlElement
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    @XmlElement
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}

package xmlEntities.caseData.participantData;

import constants.ContactType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
    private ContactType name;
    private String value;

    public Contact() {

    }

    public Contact(ContactType name1, String value) {
        this.name = name1;
        this.value = value;
    }
    @XmlElement
    public ContactType getName() {
        return name;
    }

    public void setName(ContactType name) {
        this.name = name;
    }
    @XmlElement
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

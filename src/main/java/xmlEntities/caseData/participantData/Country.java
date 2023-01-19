package xmlEntities.caseData.participantData;

import constants.CountryCode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Country {
    private CountryCode code;

    public Country(){

    }

    public Country(CountryCode code) {
        this.code = code;
    }
    @XmlElement
    public CountryCode getCode() {
        return code;
    }

    public void setCode(CountryCode code) {
        this.code = code;
    }
}

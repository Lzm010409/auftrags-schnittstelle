package auftrag.entities.persons;

import constants.PersonType;
import xmlEntities.caseData.participantData.Address;

public class Versicherung extends Person{

   private PersonType personType = PersonType.VERSICHERUNG;


    public Versicherung(String anrede, String vName, String nName, Address address, String tel, String mail) {
        super(anrede, vName, nName, address, tel, mail);
    }

    public Versicherung() {
    }
}

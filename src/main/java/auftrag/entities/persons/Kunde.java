package auftrag.entities.persons;

import constants.PersonType;
import xmlEntities.caseData.participantData.Address;

public class Kunde extends Person{

   private PersonType personType = PersonType.KUNDE;


    public Kunde(String anrede, String vName, String nName, Address address, String tel, String mail) {
        super(anrede, vName, nName, address, tel, mail);
    }

    public Kunde() {
    }
}

package auftrag.entities.persons;

import constants.PersonType;
import xmlEntities.caseData.participantData.Address;

public class Rechtsanwalt extends Person{

   private PersonType personType = PersonType.RECHTSANWALT;


    public Rechtsanwalt(String anrede, String vName, String nName, Address address, String tel, String mail) {
        super(anrede, vName, nName, address, tel, mail);
    }

    public Rechtsanwalt() {
    }
}

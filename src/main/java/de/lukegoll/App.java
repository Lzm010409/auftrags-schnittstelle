package de.lukegoll;

import constants.ContactType;
import constants.DocumentType;
import textextractor.AdminDataExtractor;
import textextractor.ParticipantsDataExtractor;
import textextractor.VehicleDataExtractor;
import xmlEntities.Case;
import xmlEntities.ClaimnetDistribution;
import xmlEntities.Document;
import xmlEntities.caseData.*;
import xmlEntities.caseData.participantData.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String filename="/Users/lukegollenstede/Desktop/dynarex_order_assignment/TestDateien/test-3.pdf";

        JAXBContext contextObj = null;
        try {
            contextObj = JAXBContext.newInstance(Document.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Document document = new Document();
            Case c1 = new Case();

           Vehicle vehicle = new VehicleDataExtractor().extractText(new File(filename));

            Admin_Data admin_data = new AdminDataExtractor().extractText(new File(filename));
/*
* Offensichtlich funktioniert die dateTime Annotation in der Umwandlung zu XMl nicht richtig. Fix folgt später*/





            Participants participants = new Participants();
            participants.setParticipant(new ParticipantsDataExtractor().extractText(new File(filename)));
            ClaimnetInfo claimnetInfo = new ClaimnetInfo();
            claimnetInfo.setComment("TESTEDFG");
            claimnetInfo.setOrder_type("Gutachten");

            ClaimnetDistribution claimnetDistribution = new ClaimnetDistribution();
            claimnetDistribution.setReceiver_id("DYC-ABCDEFG-123-4567-hijkl-edfd4e5188a7");
            claimnetDistribution.setExternal_id("0123/456TG");
            claimnetDistribution.setSender_id("LukeGollenstede");

            c1.setAdmin_data(admin_data);
            c1.setParticipants(participants);
            c1.setVehicle(vehicle);
            c1.setClaimnetInfo(claimnetInfo);


            document.setFall(c1);
            document.setClaimnetDistribution(claimnetDistribution);

            marshallerObj.marshal(document, new FileOutputStream("document.xml"));



        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

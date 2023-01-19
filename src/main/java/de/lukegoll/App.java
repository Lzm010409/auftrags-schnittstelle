package de.lukegoll;

import constants.ContactType;
import constants.DocumentType;
import xmlEntities.Case;
import xmlEntities.ClaimnetDistribution;
import xmlEntities.Document;
import xmlEntities.caseData.*;
import xmlEntities.caseData.participantData.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

        JAXBContext contextObj = null;
        try {
            contextObj = JAXBContext.newInstance(Document.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Document document = new Document();
            Case c1 = new Case();

            Vehicle vehicle = new Vehicle();
            vehicle.setManufaturer("VW");
            vehicle.setModel("Golf");
            vehicle.setPlate_number("KR-G2001");

            Admin_Data admin_data = new Admin_Data();
            admin_data.setCase_comment("TEST");

            List<Participant> participantList= new ArrayList<Participant>();
            Participant p1 = new Participant();
            p1.setName1("Luke Gollenstede");
            p1.setGender("Male");
            Address address = new Address();
            address.setStreet("Achterstraße");
            address.setCity("28359");
            address.setCity("Bremen");
            address.setNumber("15");

            Contact contact = new Contact(ContactType.MOBILE,"+4915751405748");
            List<Contact>contacts = new ArrayList<Contact>();
            contacts.add(contact);
            Contacts contacts1 = new Contacts();
            contacts1.setContact(contacts);
            address.setContacts(contacts1);
            p1.setAddress(address);

            participantList.add(p1);

            Attachment attachment = new Attachment();
            attachment.setBase64("TEHFDHGGJHGHJGKJFFH;HK");
            attachment.setFilename("TESTFILE");
            attachment.setFile_extension(".pdf");
            attachment.setDocument_type(DocumentType.MAIN_DOCUMENT);

            Attachment attachment1 = new Attachment();
            attachment1.setBase64("TEHFDHGGJHGHJGKJFFH;HK");
            attachment1.setFilename("TESTFILE");
            attachment1.setFile_extension(".pdf");
            attachment1.setDocument_type(DocumentType.MAIN_DOCUMENT);

            List<Attachment> attachments = new ArrayList<>();
            attachments.add(attachment1);
            attachments.add(attachment);

            Attachments attachments1 = new Attachments();
            attachments1.setAttachment(attachments);
            Participants participants = new Participants();
            participants.setParticipant(participantList);
            ClaimnetInfo claimnetInfo = new ClaimnetInfo();
            claimnetInfo.setComment("TESTEDFG");
            claimnetInfo.setOrder_type("Gutachten");

            ClaimnetDistribution claimnetDistribution = new ClaimnetDistribution();
            claimnetDistribution.setReceiver_id("DYC-ABCDEFG-123-4567-hijkl-edfd4e5188a7");
            claimnetDistribution.setExternal_id("0123/456TG");
            claimnetDistribution.setSender_id("LukeGollenstede");

            c1.setAdmin_data(admin_data);
            c1.setAttachments(attachments1);
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
        }


    }
}

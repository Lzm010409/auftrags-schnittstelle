package de.lukegoll;

import textextractor.PersonDataExtractor;
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




/*
* Offensichtlich funktioniert die dateTime Annotation in der Umwandlung zu XMl nicht richtig. Fix folgt später*/









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

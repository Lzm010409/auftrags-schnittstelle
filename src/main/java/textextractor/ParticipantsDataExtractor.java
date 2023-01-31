package textextractor;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredTextEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import constants.ContactType;
import constants.CountryCode;
import constants.ParticipantType;
import org.jboss.logging.Logger;
import textextractor.coordinates.Coordinates;
import xmlEntities.caseData.Admin_Data;
import xmlEntities.caseData.participantData.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ParticipantsDataExtractor implements TextExtractor {
    private final Logger logger = Logger.getLogger(ParticipantsDataExtractor.class);
    private Participant testLawyer = new Participant();


    @Override
    public List<Participant> extractText(File file) throws IOException {
        intializeLTestLawyer();
        try {
            Rectangle rect;
            TextRegionEventFilter regionFilter;
            ITextExtractionStrategy strategy;
            String str;
            StringBuilder builder = new StringBuilder();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
            List<Participant> participantList = new ArrayList<>();

            rect = new Rectangle(Coordinates.AS.getX(), Coordinates.AS.getY(), Coordinates.AS.getWidth(), Coordinates.AS.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            participantList.add(buildCustomer(str));

            if (participantList.size() == 1) {
                addGender(participantList.get(0), file);
                addContacts(participantList.get(0), file);
            }

            participantList.add(testLawyer);


            for (int i = 0; i < participantList.size(); i++) {
                logger.log(Logger.Level.INFO, String.format("Auslesen der ParticipantData erfolgreich! Folgende Dinge wurden erfasst: Gender: %s, Name: " +
                                "%s, Adresse: %s %s, %s %s", participantList.get(i).getGender(), participantList.get(i).getName1(),
                        participantList.get(i).getAddress().getStreet(), participantList.get(i).getAddress().getNumber(),
                        participantList.get(i).getAddress().getPostalcode(), participantList.get(i).getAddress().getCity()));
            }


            return participantList;
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            return null;
        }
    }

    private void addGender(Participant participant, File file) throws IOException {
        Rectangle rect;
        TextRegionEventFilter regionFilter;
        ITextExtractionStrategy strategy;
        String str;
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
        rect = new Rectangle(Coordinates.GENDER.getX(), Coordinates.GENDER.getY(), Coordinates.GENDER.getWidth(), Coordinates.GENDER.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        participant.setGender(str);
    }

    private Participant buildCustomer(String str) {
        List<String> list = new LinkedList<>(Arrays.asList(str.split("\n")));
        Participant participant = new Participant();
        Address address = new Address();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("AS:")) {
                //list.remove(i);
                continue;
            }
            if (isPlz(list.get(i))) {
                String[] arr = list.get(i).split(" ");
                address.setPostalcode(arr[0]);
                address.setCity(arr[1]);
                //list.remove(i);
                continue;
            }
            if (isAdress(list.get(i))) {
                String[] arr = list.get(i).split(" ");
                address.setStreet(arr[0]);
                address.setNumber(arr[1]);
                address.setCountry(new Country(CountryCode.DE));
                //list.remove(i);
                continue;
            } else {
                participant.setName1(list.get(i));
            }


        }
        String type = ParticipantType.CL.toString() + "," + ParticipantType.CU.toString() + "," + ParticipantType.VO.toString();
        participant.setType(type);
        participant.setAddress(address);


        return participant;

    }

    private boolean isAdress(String s) {
        boolean isAddress = false;
        List<String> tempList = new LinkedList<>(Arrays.asList(s.split(" ")));
        int digitCounter = 0;
        int letterCounter = 0;
        for (int i = 0; i < tempList.size(); i++) {
            char[] chars = tempList.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (Character.isDigit(chars[j])) {
                    digitCounter += 1;
                } else {
                    letterCounter += 1;
                }
            }
            if (digitCounter < 5 && letterCounter < 2) {
                isAddress = true;
            }
            digitCounter=0;
            letterCounter=0;
        }
        return isAddress;
    }

    private void intializeLTestLawyer() {
        Participant participant = new Participant();
        Address address = new Address();
        Contacts contacts = new Contacts();
        Contact tel = new Contact();
        Contact mail = new Contact();

        participant.setName1("Test Rechtsanwalt");
        address.setStreet("Musteralle 5");
        address.setCity("Teststadt");
        address.setPostalcode("12345");
        address.setNumber("2");

        tel.setValue("1234567657");
        tel.setName(ContactType.PHONE);
        mail.setValue("test@test.de");
        mail.setName(ContactType.MAIL);

        List<Contact> list = new ArrayList<>();
        list.add(tel);
        list.add(mail);

        contacts.setContact(list);

        address.setContacts(contacts);
        participant.setType("LA");
        participant.setAddress(address);

        testLawyer = participant;

    }

    private void addContacts(Participant participant, File file) throws IOException {
        Rectangle rect;
        TextRegionEventFilter regionFilter;
        ITextExtractionStrategy strategy;
        String str;
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
        Contacts contacts = new Contacts();
        Contact tel = new Contact();
        Contact mail = new Contact();
        rect = new Rectangle(Coordinates.TEL.getX(), Coordinates.TEL.getY(), Coordinates.TEL.getWidth(), Coordinates.TEL.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        tel.setName(ContactType.PHONE);
        tel.setValue(str);

        rect = new Rectangle(Coordinates.MAIL.getX(), Coordinates.MAIL.getY(), Coordinates.MAIL.getWidth(), Coordinates.MAIL.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        mail.setName(ContactType.MAIL);
        mail.setValue(str);

        List<Contact> list = new LinkedList<>();
        list.add(tel);
        list.add(mail);

        contacts.setContact(list);

        participant.getAddress().setContacts(contacts);
    }

    private boolean isPlz(String string) {
        boolean isPlz = false;
        List<String> tempList = new LinkedList<>(Arrays.asList(string.split(" ")));
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).length() == 5) {
                isPlz = true;
            }
        }
        return isPlz;
    }

    public Participant getTestLawyer() {
        return testLawyer;
    }

    public void setTestLawyer(Participant testLawyer) {
        this.testLawyer = testLawyer;
    }
}

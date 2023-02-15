package textextractor;

import auftrag.entities.persons.Kunde;
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
import xmlEntities.caseData.participantData.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PersonDataExtractor implements TextExtractor {
    private final Logger logger = Logger.getLogger(PersonDataExtractor.class);
    private Participant testLawyer = new Participant();


    @Override
    public Kunde extractText(File file) throws IOException {
        try {
            Rectangle rect;
            TextRegionEventFilter regionFilter;
            ITextExtractionStrategy strategy;
            String str;
            StringBuilder builder = new StringBuilder();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));

            rect = new Rectangle(Coordinates.AS.getX(), Coordinates.AS.getY(), Coordinates.AS.getWidth(), Coordinates.AS.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            Kunde kunde = buildCustomer(str);

            if (kunde != null) {
                addGender(kunde, file);
                addContacts(kunde, file);
            }


            return kunde;
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            return null;
        }
    }

    private void addGender(Kunde kunde, File file) throws IOException {
        Rectangle rect;
        TextRegionEventFilter regionFilter;
        ITextExtractionStrategy strategy;
        String str;
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
        rect = new Rectangle(Coordinates.GENDER.getX(), Coordinates.GENDER.getY(), Coordinates.GENDER.getWidth(), Coordinates.GENDER.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        kunde.setAnrede(str);
    }

    private Kunde buildCustomer(String str) {
        List<String> list = new LinkedList<>(Arrays.asList(str.split("\n")));
        Kunde participant = new Kunde();
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
                participant.setvName(list.get(i));
            }


        }
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
            digitCounter = 0;
            letterCounter = 0;
        }
        return isAddress;
    }


    private void addContacts(Kunde kunde, File file) throws IOException {
        Rectangle rect;
        TextRegionEventFilter regionFilter;
        ITextExtractionStrategy strategy;
        String str;
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
        Contacts contacts = new Contacts();
        String tel = "";
        String mail = "";
        rect = new Rectangle(Coordinates.TEL.getX(), Coordinates.TEL.getY(), Coordinates.TEL.getWidth(), Coordinates.TEL.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        tel = str;

        rect = new Rectangle(Coordinates.MAIL.getX(), Coordinates.MAIL.getY(), Coordinates.MAIL.getWidth(), Coordinates.MAIL.getHeight());
        regionFilter = new TextRegionEventFilter(rect);
        strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
        str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
        mail = str;


        kunde.setTel(tel);
        kunde.setMail(mail);
    }

    private boolean isPlz(String string) {
        boolean isPlz = false;
        int counter = 0;
        List<String> tempList = new LinkedList<>(Arrays.asList(string.split(" ")));
        for (int i = 0; i < tempList.size(); i++) {
            char[] chars = tempList.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (Character.isDigit(chars[i])) {
                    counter += 1;
                }
                if (Character.isLetter(chars[i])) {
                    break;
                }
            }
            if (counter == 5) {
                isPlz = true;
                break;
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

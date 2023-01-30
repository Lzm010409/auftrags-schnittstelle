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


    @Override
    public List<Participant> extractText(File file) throws IOException {
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

        if(participantList.size()==1){
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
            participantList.get(0).getAddress().setContacts(contacts);
        }

        return participantList;
    }

    private Participant buildCustomer(String str) {
        List<String> list = new LinkedList<>(Arrays.asList(str.split("\n")));
        Participant participant = new Participant();
        Address address = new Address();

        for (int i = 0; i < list.size(); i++) {
            List<String> tempList = new LinkedList<>(Arrays.asList(list.get(i).split(" ")));
            for (int j = 0; j < tempList.size(); j++) {
                if (tempList.get(j).contains("AS:")) {
                    //list.remove(i);
                    break;
                }
                if (tempList.get(j).contains("Hr.") || tempList.get(j).contains("Fr.")) {
                    String[] arr = list.get(i).split("\\.");
                    participant.setName1(arr[1]);
                    if (arr[0].equalsIgnoreCase("hr")) {
                        participant.setGender("Herr");
                    } else {
                        participant.setGender("Frau");
                    }
                    //list.remove(i);
                    break;
                }
                if (isPlz(tempList.get(j))) {
                    String[] arr = list.get(i).split(" ");
                    address.setPostalcode(arr[0]);
                    address.setCity(arr[1]);
                    //list.remove(i);
                    break;
                } else {
                    String[] arr = list.get(i).split(" ");
                    address.setStreet(arr[0]);
                    address.setNumber(arr[1]);
                    address.setCountry(new Country(CountryCode.DE));
                    //list.remove(i);
                    break;
                }

            }
        }
        String type= ParticipantType.CL.toString()+","+ParticipantType.CU.toString()+","+ParticipantType.VO.toString();
        participant.setType(type);
        participant.setAddress(address);

        return participant;
    }

    private boolean isPlz(String string) {
        if (string.length() == 5) {
            return true;
        } else {
            return false;
        }
    }


}

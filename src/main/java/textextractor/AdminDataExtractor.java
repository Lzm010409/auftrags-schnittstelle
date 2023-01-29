package textextractor;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredTextEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import constants.ClaimType;
import org.jboss.logging.Logger;
import textextractor.coordinates.Coordinates;
import xmlEntities.caseData.Admin_Data;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class AdminDataExtractor implements TextExtractor {

    private final Logger logger = Logger.getLogger(AdminDataExtractor.class);

    /**
     *
     * @param file
     * @return Admin_Data Objekt
     * @throws IOException
     *
     * Lies den Aufnahmebogen an bestimmten Stellen aus und generiert daraus ein Admin_Data Objekt
     */
    @Override
    public Admin_Data extractText(File file) throws IOException {
        try {
            Rectangle rect;
            TextRegionEventFilter regionFilter;
            ITextExtractionStrategy strategy;
            String str;
            StringBuilder builder = new StringBuilder();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
            Admin_Data admin_data = new Admin_Data();

            logger.log(Logger.Level.INFO, "Auslesen des Textes aus: " + file.getAbsolutePath());

            rect = new Rectangle(Coordinates.GNUMMER.getX(), Coordinates.GNUMMER.getY(), Coordinates.GNUMMER.getWidth(), Coordinates.GNUMMER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            admin_data.setCase_no(str);

            rect = new Rectangle(Coordinates.SDATUMORT.getX(), Coordinates.SDATUMORT.getY(), Coordinates.SDATUMORT.getWidth(), Coordinates.SDATUMORT.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            if (extractClaimDateAndPlace(str).size() == 2) {
                List<String> list = extractClaimDateAndPlace(str);
                admin_data.setClaim_date(stringToLocalDateTime(list.get(0)));
                admin_data.setClaim_date_usertime(true);
                admin_data.setClaim_location(list.get(1));
            }
            if (extractClaimDateAndPlace(str).size() == 1) {
                List<String> list = extractClaimDateAndPlace(str);
                admin_data.setClaim_date(stringToLocalDateTime(list.get(0)));
                admin_data.setClaim_date_usertime(true);
            }

            rect = new Rectangle(Coordinates.SNUMMER.getX(), Coordinates.SNUMMER.getY(), Coordinates.SNUMMER.getWidth(), Coordinates.SNUMMER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            admin_data.setClaim_number(extractClaimNumber(str));


            rect = new Rectangle(Coordinates.ADATUM.getX(), Coordinates.ADATUM.getY(), Coordinates.ADATUM.getWidth(), Coordinates.ADATUM.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy) + "\n\n";
            if (str != "") {
                admin_data.setCase_date_of_order(stringToLocalDateTime(str));
                admin_data.setCase_date_of_order_usertime(true);
            }

            admin_data.setClaim_type(ClaimType.LIABILITYDAMAGE.getType());
            admin_data.setClaim_type_id(ClaimType.LIABILITYDAMAGE.getId());


            logger.log(Logger.Level.INFO, String.format("Auslesen der AdminData erfolgreich! Folgende Dinge wurden erfasst: Gutachtennummer: %s, Schadendatum: " +
                            "%s, Schadenort: %s, Schadennummer: %s, Auftragsdatum: %s, Auftragsart: %s ", admin_data.getCase_no(), admin_data.getClaim_date().toString(), admin_data.getClaim_location()
                    , admin_data.getClaim_number(), admin_data.getCase_date_of_order().toString(), admin_data.getClaim_type()));

            return admin_data;
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            return null;
        }

    }

    /**
     *
     * @param string
     * @returnGibt Liste mit Claim Date und Place zurück
     */
    private List<String> extractClaimDateAndPlace(String string) {
        List<String> list = Arrays.asList(string.split(",")
        );
        return list;
    }

    /**
     *
     * @param input
     * @return Gibt ClaimNumber als String zurück
     */
    private String extractClaimNumber(String input) {
        if (input.contains(",")) {
            char[] chars = input.toCharArray();
            StringBuilder builder = new StringBuilder();
            boolean containsComma = false;
            for (int i = 0; i < chars.length; i++) {
                if (containsComma == true) {
                    builder.append(chars[i]);
                }
                if (chars[i] == ',') {
                    containsComma = true;
                }
            }
            return builder.toString();
        } else {
            return input;
        }
    }

    /**
     *
     * @param time
     * @return Konvertiert das Time Format aus dem Aufnahmebogen zu einem Java kompatiblen
     */
    private LocalDateTime stringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateTime = time.replace("/", "-");
        char[] chars = dateTime.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i]) || chars[i] == '-') {
                builder.append(chars[i]);
            }
        }
        dateTime = builder.toString() + " 12:00:00";
        LocalDateTime date = LocalDateTime.parse(dateTime, formatter);
        return date;
    }


    public static void main(String[] args) {
        AdminDataExtractor invoiceTextExtractor = new AdminDataExtractor();
        try {
            invoiceTextExtractor.extractText(new File("/Users/lukegollenstede/Desktop/dynarex_order_assignment/TestDateien/text-2.pdf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

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
import constants.Unit;
import org.jboss.logging.Logger;
import textextractor.coordinates.Coordinates;
import xmlEntities.caseData.Admin_Data;
import xmlEntities.caseData.Vehicle;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VehicleDataExtractor implements TextExtractor {
    private final Logger logger = Logger.getLogger(VehicleDataExtractor.class);

    @Override
    public Vehicle extractText(File file) throws IOException {
        try {
            Rectangle rect;
            TextRegionEventFilter regionFilter;
            ITextExtractionStrategy strategy;
            String str;
            StringBuilder builder = new StringBuilder();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
            Vehicle vehicle = new Vehicle();

            logger.log(Logger.Level.INFO, "Auslesen des Textes aus: " + file.getAbsolutePath());

            rect = new Rectangle(Coordinates.KENNZEICHEN.getX(), Coordinates.KENNZEICHEN.getY(), Coordinates.KENNZEICHEN.getWidth(), Coordinates.KENNZEICHEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setPlate_number(formatLicencePlate(str));
            rect = new Rectangle(Coordinates.FHERSTELLER.getX(), Coordinates.FHERSTELLER.getY(), Coordinates.FHERSTELLER.getWidth(), Coordinates.FHERSTELLER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setManufaturer(str);
            rect = new Rectangle(Coordinates.FTYP.getX(), Coordinates.FTYP.getY(), Coordinates.FTYP.getWidth(), Coordinates.FTYP.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setModel(str);
            rect = new Rectangle(Coordinates.KENNZEICHENUG.getX(), Coordinates.KENNZEICHENUG.getY(), Coordinates.KENNZEICHENUG.getWidth(), Coordinates.KENNZEICHENUG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setPlate_number_opponent(formatLicencePlate(str));
            rect = new Rectangle(Coordinates.FHSN.getX(), Coordinates.FHSN.getY(), Coordinates.FHSN.getWidth(), Coordinates.FHSN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setKba_numbers(formatHsnTsn(str));
            rect = new Rectangle(Coordinates.FERSTZUL.getX(), Coordinates.FERSTZUL.getY(), Coordinates.FERSTZUL.getWidth(), Coordinates.FERSTZUL.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setFirst_registrations(formatDate(str));
            rect = new Rectangle(Coordinates.FIN.getX(), Coordinates.FIN.getY(), Coordinates.FIN.getWidth(), Coordinates.FIN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setVin(str);
            rect = new Rectangle(Coordinates.FKILOMETER.getX(), Coordinates.FKILOMETER.getY(), Coordinates.FKILOMETER.getWidth(), Coordinates.FKILOMETER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setMileage_read(Long.parseLong(formatMileage(str)));
            vehicle.setMileage_read_unit_code(Unit.KM);


            logger.log(Logger.Level.INFO, String.format("Auslesen der AdminData erfolgreich! Folgende Dinge wurden erfasst: Kennzeichen: %s, Fahrzeughersteller: " +
                            "%s, Fahrezugtyp: %s, Kennzeichen-UG: %s, FIN: %s, HSN/TSN: %s, Erstzulassung: %s, Laufleistung: %s %s ", vehicle.getPlate_number(), vehicle.getManufaturer()
                    , vehicle.getModel(), vehicle.getPlate_number_opponent(),vehicle.getVin(), vehicle.getKba_numbers(), vehicle.getFirst_registrations(), vehicle.getMileage_read(), vehicle.getMileage_read_unit_code().getUnit()));

            return vehicle;
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Auslesen nicht erfolgreich, folgender Fehler ist aufgetreten: " + e.getMessage());
            return null;
        }

    }

    private String formatLicencePlate(String plate) {
        char[] chars = plate.toCharArray();
        boolean hitMinus = false;
        boolean hitFirstDigit = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '-' && hitMinus == false) {
                builder.append(chars[i]);
            }
            if (chars[i] == '-') {
                hitMinus = true;
                builder.append("-");
            }
            if (hitMinus == true && Character.isLetter(chars[i])) {
                builder.append(chars[i]);
            }
            if (hitFirstDigit == false && Character.isDigit(chars[i])) {
                builder.append("-");
                builder.append(chars[i]);
                hitFirstDigit = true;
                continue;
            }
            if (Character.isDigit(chars[i]) && hitFirstDigit == true) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    private String formatDate(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateTime = time.replace("/", "-");
        char[] chars = dateTime.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i]) || chars[i] == '-') {
                builder.append(chars[i]);
            }
        }
        dateTime = builder.toString();
        LocalDate date = LocalDate.parse(dateTime, formatter);
        return date.toString();
    }

    private String formatHsnTsn(String hsn) {
        char[] chars = hsn.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i]) || Character.isDigit(chars[i])) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    private String formatMileage(String input) {
        char[] chars = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }


}

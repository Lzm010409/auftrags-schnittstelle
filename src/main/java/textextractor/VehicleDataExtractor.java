package textextractor;

import auftrag.entities.Fahrzeug;
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
    public Fahrzeug extractText(File file) throws IOException {
        try {
            Rectangle rect;
            TextRegionEventFilter regionFilter;
            ITextExtractionStrategy strategy;
            String str;
            StringBuilder builder = new StringBuilder();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(file));
            Fahrzeug vehicle = new Fahrzeug();

            logger.log(Logger.Level.INFO, "Auslesen des Textes aus: " + file.getAbsolutePath());

            rect = new Rectangle(Coordinates.KENNZEICHEN.getX(), Coordinates.KENNZEICHEN.getY(), Coordinates.KENNZEICHEN.getWidth(), Coordinates.KENNZEICHEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setAmtlKennzeichen(formatLicencePlate(str));
            rect = new Rectangle(Coordinates.FHERSTELLER.getX(), Coordinates.FHERSTELLER.getY(), Coordinates.FHERSTELLER.getWidth(), Coordinates.FHERSTELLER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setHersteller(str);
            rect = new Rectangle(Coordinates.FART.getX(), Coordinates.FART.getY(), Coordinates.FART.getWidth(), Coordinates.FART.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setFahrzeugArt(str);
            rect = new Rectangle(Coordinates.FTYP.getX(), Coordinates.FTYP.getY(), Coordinates.FTYP.getWidth(), Coordinates.FTYP.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setTyp(str);
            rect = new Rectangle(Coordinates.FHSN.getX(), Coordinates.FHSN.getY(), Coordinates.FHSN.getWidth(), Coordinates.FHSN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setHsntsn(formatHsnTsn(str));
            rect = new Rectangle(Coordinates.FERSTZUL.getX(), Coordinates.FERSTZUL.getY(), Coordinates.FERSTZUL.getWidth(), Coordinates.FERSTZUL.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setErstZulassung(str);
            rect = new Rectangle(Coordinates.FLETZTZUL.getX(), Coordinates.FLETZTZUL.getY(), Coordinates.FLETZTZUL.getWidth(), Coordinates.FLETZTZUL.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setLetztZulassung(str);
            rect = new Rectangle(Coordinates.FLEISTUNG.getX(), Coordinates.FLEISTUNG.getY(), Coordinates.FLEISTUNG.getWidth(), Coordinates.FLEISTUNG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setLeistung(Integer.valueOf(str));
            rect = new Rectangle(Coordinates.FHUBRAUM.getX(), Coordinates.FHUBRAUM.getY(), Coordinates.FHUBRAUM.getWidth(), Coordinates.FHUBRAUM.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setHubraum(Integer.valueOf(str));
            rect = new Rectangle(Coordinates.FHU.getX(), Coordinates.FHU.getY(), Coordinates.FHU.getWidth(), Coordinates.FHU.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setHu(str);
            rect = new Rectangle(Coordinates.FKILOMETER.getX(), Coordinates.FKILOMETER.getY(), Coordinates.FKILOMETER.getWidth(), Coordinates.FKILOMETER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setKmStand(Integer.valueOf(str));
           /* rect = new Rectangle(Coordinates.FFARBE.getX(), Coordinates.FFARBE.getY(), Coordinates.FFARBE.getWidth(), Coordinates.FFARBE.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setFarbe(str);*/
            rect = new Rectangle(Coordinates.FVBEREIFUNG.getX(), Coordinates.FVBEREIFUNG.getY(), Coordinates.FVBEREIFUNG.getWidth(), Coordinates.FVBEREIFUNG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setReifenVorne(str);
            rect = new Rectangle(Coordinates.FHBEREIFUNG.getX(), Coordinates.FHBEREIFUNG.getY(), Coordinates.FHBEREIFUNG.getWidth(), Coordinates.FHBEREIFUNG.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setReifenHinten(str);
            rect = new Rectangle(Coordinates.FREIFENHERSTELLER.getX(), Coordinates.FREIFENHERSTELLER.getY(), Coordinates.FREIFENHERSTELLER.getWidth(), Coordinates.FREIFENHERSTELLER.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setReifenHersteller(str);
            rect = new Rectangle(Coordinates.NBEHVORSCHADEN.getX(), Coordinates.NBEHVORSCHADEN.getY(), Coordinates.NBEHVORSCHADEN.getWidth(), Coordinates.NBEHVORSCHADEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setNichtBehSchäden(str);
            rect = new Rectangle(Coordinates.BEHVORSCHADEN.getX(), Coordinates.BEHVORSCHADEN.getY(), Coordinates.BEHVORSCHADEN.getWidth(), Coordinates.BEHVORSCHADEN.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setBehSchäden(str);
            rect = new Rectangle(Coordinates.FPROFILTIEFE.getX(), Coordinates.FPROFILTIEFE.getY(), Coordinates.FPROFILTIEFE.getWidth(), Coordinates.FPROFILTIEFE.getHeight());
            regionFilter = new TextRegionEventFilter(rect);
            strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), regionFilter);
            str = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy);
            vehicle.setProfilTiefe(str);

            /**
             * Hier müssen unebdingt noch alle Daten die Enums enthalten ergänzt werden. z.B.: Karosserie, Allgemeinzustand, etc.
             */

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
        boolean startCodon = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (startCodon == true) {
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
            } else {
                if (Character.isUpperCase(chars[i]) && Character.isUpperCase(chars[i + 1]) && chars[i + 2] == '-') {
                    startCodon = true;
                    builder.append(chars[i]);
                }
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

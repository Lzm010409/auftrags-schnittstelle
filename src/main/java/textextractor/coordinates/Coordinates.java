package textextractor.coordinates;

import java.util.List;

public enum Coordinates {
    GENDER(60, 677, 70, 20),
    AS(55, 649, 200, 32), TEL(62, 632, 85, 12), MAIL(190, 632, 160, 12), ADATUM(428, 782, 260, 23),
    SDATUMORT(428, 759, 260, 23), GNUMMER(428, 736, 260, 23), SNUMMER(428, 713, 260, 23),
    KENNZEICHENUG(428, 690, 260, 23), BORT(100, 582, 270, 35), BDATUM(375, 582, 270, 35), BUHRZEIT(375, 569, 270, 13),
    BANWESEND(375, 549, 270, 13), AP(100, 548, 200, 15), KENNZEICHEN(110, 531, 120, 13), FART(110, 514, 120, 13),
    FHERSTELLER(110, 497, 120, 13), FTYP(110, 480, 120, 13), FHSN(110, 463, 120, 13), FPAPIERE(110, 446, 120, 13),
    FIN(110, 438, 120, 13), FERSTZUL(110, 424, 120, 13), FLETZTZUL(110, 405, 120, 13), FLEISTUNG(110, 390, 120, 13),
    FHUBRAUM(110, 374, 120, 13), FHU(110, 358, 120, 13), FKILOMETER(375, 518, 120, 25),
    FFARBE(375, 503, 150, 13), FVBEREIFUNG(375, 486, 150, 13), FHBEREIFUNG(375, 469, 150, 13),
    FREIFENHERSTELLER(375, 452, 150, 13), FPROFILTIEFE(375, 435, 150, 13), FANTRIEBSART(375, 423, 150, 13),
    FUMWELTPLAKETTE(375, 399, 150, 13), FKRAFTSTOFF(375, 399, 150, 13), FSCHADSTOFFKLASSE(375, 382, 150, 13),
    FALLGZUSTAND(375, 368, 150, 13), FKARRZUSTAND(375, 353, 150, 13), FLACKZUSTAND(375, 339, 150, 13),
    FBZUSTAND(375, 321, 150, 13), FAUSSTATTUNG(110, 250, 185, 60), NBEHVORSCHADEN(110, 182, 185, 75),
    NOTIZEN(110, 36, 187, 110), SCHADENHERGANG(375, 266, 185, 45), PLAUSIBILITÄT(375, 251, 185, 15),
    BEHVORSCHADEN(375, 196, 185, 45), SCHICHTDICKENMESSUNG(375, 166, 80, 15), RECHTSANWALT(110, 57, 180, 20), DEFAULT(0, 0, 595, 841);


    float x, y, width, height;
    List<Coordinates> list;


    Coordinates(float a, float b, float c, float d) {
        this.x = a;
        this.y = b;
        this.width = c;
        this.height = d;


    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}

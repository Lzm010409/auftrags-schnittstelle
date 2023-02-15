package auftrag.entities;

import auftrag.entities.persons.Kunde;
import auftrag.entities.persons.Rechtsanwalt;
import auftrag.entities.persons.Versicherung;

public class Auftrag {
    private String auftragsDatum;
    private String schadenDatum;
    private String schadenOrt;
    private String gutachtenNummer;
    private String Schadennummer;
    private Versicherung versicherung;
    private String kennzeichenUG;
    private Kunde kunde;
    private String besichtigungsort;
    private String bedichtigungsDatum;
    private String besichtigungsUhrzeit;
    private String schadenhergang;
    private String auftragsBesonderheiten;
    private Rechtsanwalt rechtsanwalt;
    private Fahrzeug fahrzeug;

    public Auftrag() {
    }

    public String getAuftragsDatum() {
        return auftragsDatum;
    }

    public void setAuftragsDatum(String auftragsDatum) {
        this.auftragsDatum = auftragsDatum;
    }

    public String getSchadenDatum() {
        return schadenDatum;
    }

    public void setSchadenDatum(String schadenDatum) {
        this.schadenDatum = schadenDatum;
    }

    public String getSchadenOrt() {
        return schadenOrt;
    }

    public void setSchadenOrt(String schadenOrt) {
        this.schadenOrt = schadenOrt;
    }

    public String getGutachtenNummer() {
        return gutachtenNummer;
    }

    public void setGutachtenNummer(String gutachtenNummer) {
        this.gutachtenNummer = gutachtenNummer;
    }

    public String getSchadennummer() {
        return Schadennummer;
    }

    public void setSchadennummer(String schadennummer) {
        Schadennummer = schadennummer;
    }

    public Versicherung getVersicherung() {
        return versicherung;
    }

    public void setVersicherung(Versicherung versicherung) {
        this.versicherung = versicherung;
    }

    public String getKennzeichenUG() {
        return kennzeichenUG;
    }

    public void setKennzeichenUG(String kennzeichenUG) {
        this.kennzeichenUG = kennzeichenUG;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public String getBesichtigungsort() {
        return besichtigungsort;
    }

    public void setBesichtigungsort(String besichtigungsort) {
        this.besichtigungsort = besichtigungsort;
    }

    public String getBedichtigungsDatum() {
        return bedichtigungsDatum;
    }

    public void setBedichtigungsDatum(String bedichtigungsDatum) {
        this.bedichtigungsDatum = bedichtigungsDatum;
    }

    public String getBesichtigungsUhrzeit() {
        return besichtigungsUhrzeit;
    }

    public void setBesichtigungsUhrzeit(String besichtigungsUhrzeit) {
        this.besichtigungsUhrzeit = besichtigungsUhrzeit;
    }

    public String getSchadenhergang() {
        return schadenhergang;
    }

    public void setSchadenhergang(String schadenhergang) {
        this.schadenhergang = schadenhergang;
    }

    public String getAuftragsBesonderheiten() {
        return auftragsBesonderheiten;
    }

    public void setAuftragsBesonderheiten(String auftragsBesonderheiten) {
        this.auftragsBesonderheiten = auftragsBesonderheiten;
    }

    public Rechtsanwalt getRechtsanwalt() {
        return rechtsanwalt;
    }

    public void setRechtsanwalt(Rechtsanwalt rechtsanwalt) {
        this.rechtsanwalt = rechtsanwalt;
    }

    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }
}

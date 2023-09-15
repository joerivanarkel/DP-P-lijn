package dataandpersistency.P3.Models;

import java.sql.Date;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    
    public Reiziger() {
        
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
    }
    
    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
    }


    
    public int getId() {
        return id;
    }
    
    public String getVoorletters() {
        return voorletters;
    }
    
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    
    public String getAchternaam() {
        return achternaam;
    }
    
    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }
    
    public void setId(int id) {
        this.id = id;
    }    

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }
    
    public String getNaam() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(voorletters);
        stringBuilder.append(". ");
        stringBuilder.append(tussenvoegsel != null ? tussenvoegsel + " " : "");
        stringBuilder.append(achternaam);
        return stringBuilder.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        stringBuilder.append(id);
        stringBuilder.append(": ");
        stringBuilder.append(getNaam());
        stringBuilder.append(" (");
        stringBuilder.append(geboortedatum);
        stringBuilder.append("), ");
        stringBuilder.append(adres != null ? adres.toString() : "geen adres");
        return stringBuilder.toString();
    }
    
    
}

package dataandpersistency.P4.Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    // Relaties
    private Adres adres;
    private List<OVChipkaart> ovChipkaarten = new ArrayList<OVChipkaart>();
    
    public Reiziger() {
        
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
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
    
    public List<OVChipkaart> getOVChipkaarten() {
        return ovChipkaarten;
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

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }



    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        ovChipkaarten.add(ovChipkaart);
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
        stringBuilder.append(", ");
        for (OVChipkaart ovChipkaart : ovChipkaarten) {
            stringBuilder.append(ovChipkaart.toString());
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
    
    
}

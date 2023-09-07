package dataandpersistency.P2;

import java.sql.Date;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    
    public Reiziger() {
        
    }
    
    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
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
    
    public void setId(int id) {
        this.id = id;
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
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
    
    
}

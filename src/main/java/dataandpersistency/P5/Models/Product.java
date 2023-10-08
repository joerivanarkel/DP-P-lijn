package dataandpersistency.P5.Models;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    private List<OVChipkaart> ovChipkaarten = new ArrayList<OVChipkaart>();

    public Product() {

    }

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProduct_nummer() { return product_nummer; }
    public String getNaam() { return naam; }
    public String getBeschrijving() { return beschrijving; }
    public double getPrijs() { return prijs; }
    public List<OVChipkaart> getOvChipkaarten() { return ovChipkaarten; }

    public void setProduct_nummer(int product_nummer) { this.product_nummer = product_nummer; }
    public void setNaam(String naam) { this.naam = naam; }
    public void setBeschrijving(String beschrijving) { this.beschrijving = beschrijving; }
    public void setPrijs(double prijs) { this.prijs = prijs; }

    public void addOVChipkaart(OVChipkaart ovChipkaart) { this.ovChipkaarten.add(ovChipkaart); }

    @Override
    public String toString() {
        return "Product {#" + product_nummer + " " + naam + " " + beschrijving + " " + prijs + "}";
    }
}

package dataandpersistency.P5.Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
    private int reiziger_id;

    private List<Product> producten = new ArrayList<Product>();

    public OVChipkaart() {

    }

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public int getKaart_nummer() { return kaart_nummer; }
    public Date getGeldig_tot() { return geldig_tot; }
    public int getKlasse() { return klasse; }
    public double getSaldo() { return saldo; }
    public int getReiziger_id() { return reiziger_id; }
    public List<Product> getProducten() { return producten; }

    public void setKaart_nummer(int kaart_nummer) { this.kaart_nummer = kaart_nummer; }
    public void setGeldig_tot(Date geldig_tot) { this.geldig_tot = geldig_tot; }
    public void setKlasse(int klasse) { this.klasse = klasse; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setReiziger_id(int reiziger_id) { this.reiziger_id = reiziger_id; }
    public void setProducten(List<Product> producten) { this.producten = producten; }

    public void addProduct(Product product) { this.producten.add(product); }

    @Override
    public String toString() {
        return "OVChipkaart {#" + kaart_nummer + " " + geldig_tot + " " + klasse + " " + saldo + " " + reiziger_id + "}";
    }
}

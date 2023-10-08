package dataandpersistency.P5;

import dataandpersistency.P5.DAO.AdresDAOPsql;
import dataandpersistency.P5.DAO.OVChipkaartDAOPsql;
import dataandpersistency.P5.DAO.ProductDAOPsql;
import dataandpersistency.P5.DAO.ReizigerDAOPsql;
import dataandpersistency.P5.Models.Adres;
import dataandpersistency.P5.Models.OVChipkaart;
import dataandpersistency.P5.Models.Product;
import dataandpersistency.P5.Models.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class App {
    public Connection connection;
    public AdresDAOPsql adao;
    public ReizigerDAOPsql rdao;
    public OVChipkaartDAOPsql odao;
    public ProductDAOPsql pdao;

    public static void main(String[] args) throws SQLException {
        App app = new App();
        app.connection = app.getConnection(app.connection);

        app.adao = new AdresDAOPsql(app.getConnection(app.connection));
        app.rdao = new ReizigerDAOPsql(app.getConnection(app.connection));
        app.odao = new OVChipkaartDAOPsql(app.getConnection(app.connection));
        app.pdao = new ProductDAOPsql(app.getConnection(app.connection));


        app.adao.setRdao(app.rdao);
        app.rdao.setAdao(app.adao);

        app.rdao.setOdao(app.odao);
        app.odao.setRdao(app.rdao);

        app.testReizigerDAO(app.rdao);
        app.testAdresDAO(app.adao);
        app.testOVChipkaart(app.odao);
        app.testProductDAO(app.pdao);
        app.connection.close();
    }

    public Connection getConnection(Connection connection) throws SQLException {
        if (connection != null) return connection;
        Properties props = new Properties();
        props.setProperty("user", "bep2-huland-casino");
        props.setProperty("password", "bep2-huland-casino");
        props.setProperty("useSSL", "true");
        
        connection =  DriverManager.getConnection("jdbc:postgresql://localhost:15432/ovchip", props);
        return connection;
    }

    public void testReizigerDAO(ReizigerDAOPsql rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        int id = rdao.findAll().size() + 1;

        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(id, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update de nieuwe reiziger
        System.out.println("[Test] ReizigerDAO.update() geeft de volgende reiziger:");
        sietske.setTussenvoegsel("van");
        sietske.setAchternaam("Dijk");
        rdao.update(sietske);
        System.out.println(rdao.findById(sietske.getId()) + "\n");

        // Delete de nieuwe reiziger
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Haal alle reiziger 2 op uit de database
        System.out.println("[Test] ReizigerDAO.findById() geeft de volgende reiziger:");
        System.out.println(rdao.findById(2) + "\n");

        // Haal alle reizigers op met geboortedatum 2002-12-03 uit de database
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : rdao.findByGbdatum("2002-12-03")) {
            System.out.println(r);
        }
        System.out.println();
    }

    public void testAdresDAO(AdresDAOPsql adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        int reizigerId = rdao.findAll().size() + 1;
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(reizigerId, "S", "", "Boers", Date.valueOf(gbdatum));
        rdao.save(sietske);

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }

        // Maak een nieuw adres aan en persisteer deze in de database
        int id = adao.findAll().size() + 1;
        Adres adres = new Adres(id, "1234AB", "1", "Straat", "Woonplaats", reizigerId);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(adres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Update het nieuwe adres
        System.out.println("[Test] AdresDAO.update() geeft het volgende adres:");
        adres.setHuisnummer("2");
        adres.setStraat("Straatje");
        adres.setWoonplaats("Woonplaatsje");
        adao.update(adres);
        System.out.println(adao.findById(adres.getId()) + "\n");

        // Delete het nieuwe adres
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() ");
        adao.delete(adres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Haal alle adressen op met reiziger_id 1 uit de database
        System.out.println("[Test] AdresDAO.findByReizigerId() geeft de volgende adressen:");
        System.out.println(adao.findByReiziger(rdao.findById(1)));
        System.out.println();

        // Haal alle adressen op met reiziger_id 1 uit de database
        System.out.println("[Test] AdresDAO.findById() geeft het volgende adres:");
        System.out.println(adao.findById(1) + "\n");

    }

    public void testOVChipkaart(OVChipkaartDAOPsql ovChipkaartDAOPsql) throws SQLException
    {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        int reizigerId = rdao.findAll().size() + 1;
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(reizigerId, "S", "", "Boers", Date.valueOf(gbdatum));
        rdao.save(sietske);

        int adresId = adao.findAll().size() + 1;
        Adres adres = new Adres(adresId, "1234AB", "1", "Straat", "Woonplaats", reizigerId);
        adao.save(adres);

        int ovChipkaartId = odao.findAll().size() + 1;
        OVChipkaart ovChipkaart = new OVChipkaart(ovChipkaartId, Date.valueOf("2020-01-01"), 1, 25.00, reizigerId);
        ovChipkaartDAOPsql.save(ovChipkaart);

        // Haal alle OVChipkaarten op uit de database
        List<OVChipkaart> ovChipkaarten = ovChipkaartDAOPsql.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }

        // Maak een nieuwe OVChipkaart aan en persisteer deze in de database
        int id = ovChipkaartDAOPsql.findAll().size() + 1;
        OVChipkaart ovChipkaart1 = new OVChipkaart(id, Date.valueOf("2020-01-01"), 1, 25.00, reizigerId);
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.save() ");
        ovChipkaartDAOPsql.save(ovChipkaart1);
        ovChipkaarten = ovChipkaartDAOPsql.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");

        // Update de nieuwe OVChipkaart
        System.out.println("[Test] OVChipkaartDAO.update() geeft de volgende OVChipkaart:");
        ovChipkaart1.setSaldo(50.00);
        ovChipkaartDAOPsql.update(ovChipkaart1);
        List<OVChipkaart> ovChipkaarten1 = ovChipkaartDAOPsql.findAll();
        System.out.println(ovChipkaarten1.get(ovChipkaarten1.size() - 1) + "\n");
        
        // Delete de nieuwe OVChipkaart
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.delete() ");
        ovChipkaartDAOPsql.delete(ovChipkaart1);
        ovChipkaarten = ovChipkaartDAOPsql.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");

        // Haal alle OVChipkaarten op met reiziger_id 1 uit de database
        System.out.println("[Test] OVChipkaartDAO.findByReizigerId() geeft de volgende OVChipkaarten:");
        System.out.println(ovChipkaartDAOPsql.findByReiziger(rdao.findById(1)));
        System.out.println();

    }

    public void testProductDAO(ProductDAOPsql productDAOPsql) throws SQLException
    {
        System.out.println("\n---------- Test ProductDAO -------------");

        int reizigerId = rdao.findAll().size() + 1;
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(reizigerId, "S", "", "Boers", Date.valueOf(gbdatum));
        rdao.save(sietske);

        int adresId = adao.findAll().size() + 1;
        Adres adres = new Adres(adresId, "1234AB", "1", "Straat", "Woonplaats", reizigerId);
        adao.save(adres);

        int ovChipkaartId = odao.findAll().size() + 1;
        OVChipkaart ovChipkaart = new OVChipkaart(ovChipkaartId, Date.valueOf("2020-01-01"), 1, 25.00, reizigerId);
        odao.save(ovChipkaart);

        int productId = productDAOPsql.findAll().size() + 1;
        Product product = new Product(productId, "Product", "Product", 25.00);
        productDAOPsql.save(product);

        // Haal alle Producten op uit de database
        List<Product> producten = productDAOPsql.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende Producten:");
        for (Product p : producten) {
            System.out.println(p);
        }

        // Maak een nieuwe Product aan en persisteer deze in de database
        int id = productDAOPsql.findAll().size() + 1;
        Product product1 = new Product(id, "Product", "Product", 25.00);
        System.out.print("[Test] Eerst " + producten.size() + " Producten, na ProductDAO.save() ");
        productDAOPsql.save(product1);
        producten = productDAOPsql.findAll();
        System.out.println(producten.size() + " Producten\n");

        // Update de nieuwe Product
        System.out.println("[Test] ProductDAO.update() geeft de volgende Product:");
        product1.setPrijs(50.00);
        productDAOPsql.update(product1);
        List<Product> producten1 = productDAOPsql.findAll();
        System.out.println(producten1.get(producten1.size() - 1) + "\n");

        // Delete de nieuwe Product
        System.out.print("[Test] Eerst " + producten.size() + " Producten, na ProductDAO.delete() ");
        productDAOPsql.delete(product1);
        producten = productDAOPsql.findAll();
        System.out.println(producten.size() + " Producten\n");
    }
}



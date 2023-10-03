package dataandpersistency.P4;

import dataandpersistency.P4.DAO.AdresDAOPsql;
import dataandpersistency.P4.DAO.OVChipkaartDAOPsql;
import dataandpersistency.P4.DAO.ReizigerDAOPsql;
import dataandpersistency.P4.Models.Adres;
import dataandpersistency.P4.Models.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class App {
    public Connection connection;

    public static void main(String[] args) throws SQLException {
        App app = new App();
        app.connection = app.getConnection(app.connection);

        AdresDAOPsql adao = new AdresDAOPsql(app.getConnection(app.connection));
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(app.getConnection(app.connection));
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(app.getConnection(app.connection));

        adao.setRdao(rdao);
        rdao.setAdao(adao);

        rdao.setOdao(odao);
        odao.setRdao(rdao);

        app.testReizigerDAO(rdao);
        app.testAdresDAO(adao);
        app.connection.close();
    }

    public Connection getConnection(Connection connection) throws SQLException {
        if (connection != null) return connection;
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "geenidee");
        props.setProperty("useSSL", "true");
        connection =  DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", props);
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
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);

        int reizigerId = rdao.findAll().size() + 1;
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(reizigerId, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
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
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        AdresDAOPsql adao = new AdresDAOPsql(connection);
    }
}

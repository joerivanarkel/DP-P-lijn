package dataandpersistency.P2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class App {
    public Connection connection;
    
    public static void main(String[] args) {
        App app = new App();
        app.connection = app.getConnection();
        app.testReizigerDAO(new ReizigerDAOPsql(app.connection));
        app.closeConnection();
    }
    
    public Connection getConnection() {
        try {
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "geenidee");
            props.setProperty("useSSL", "true");
            return DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void closeConnection() {
        try {
            connection.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void testReizigerDAO(ReizigerDAOPsql rdao) {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        ArrayList<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        int id = rdao.findAll().size() + 1;

        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(id, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
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
}

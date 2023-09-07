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
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

    }
}

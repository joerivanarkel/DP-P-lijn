package dataandpersistency.P1;

import java.sql.*;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "geenidee");
            props.setProperty("useSSL", "true");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", props);
            
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM reiziger")) {
                    System.out.println("Alle reizigers:");
                    while (resultSet.next()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("     #");
                        stringBuilder.append(resultSet.getString("reiziger_id"));
                        stringBuilder.append(": ");
                        stringBuilder.append(resultSet.getString("voorletters"));
                        stringBuilder.append(". ");
                        if (resultSet.getString("tussenvoegsel") != null) {
                            stringBuilder.append(resultSet.getString("tussenvoegsel"));
                            stringBuilder.append(" ");
                        }
                        stringBuilder.append(resultSet.getString("achternaam"));
                        stringBuilder.append(" (");
                        stringBuilder.append(resultSet.getString("geboortedatum"));
                        stringBuilder.append(")");

                        System.out.println(stringBuilder.toString());
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
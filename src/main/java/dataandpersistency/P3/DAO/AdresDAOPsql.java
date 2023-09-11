package dataandpersistency.P3.DAO;

import dataandpersistency.P3.Models.Adres;
import dataandpersistency.P3.Models.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements IAdresDAO {
    private Connection conn;
    
    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public boolean save(Adres adres) {
        try {
            try (ResultSet resultSet = conn.createStatement()
                .executeQuery(
                    "INSERT INTO adres VALUES (" + 
                    adres.getId() + 
                    ", '" + 
                    adres.getPostcode() + 
                    "', '" + 
                    adres.getHuisnummer() + 
                    "', '" + 
                    adres.getStraat() + 
                    "', '" + 
                    adres.getWoonplaats() + 
                    "', '" + 
                    adres.getReiziger_id() +
                    "')"
                )) {
                return true;
            }
                
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            return new Adres();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<>();

        try {
            try (ResultSet resultSet = conn.createStatement()
                .executeQuery(
                    "SELECT * FROM adres"
                )) {
                while (resultSet.next()) {
                    adressen.add(
                        new Adres(
                            resultSet.getInt("adres_id"),
                            resultSet.getString("postcode"),
                            resultSet.getString("huisnummer"),
                            resultSet.getString("straat"),
                            resultSet.getString("woonplaats"),
                            resultSet.getInt("reiziger_id")
                        )
                    );
                }
            }
            return adressen;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean checkIfExists(Adres adres) {
        try {
            try (ResultSet resultSet = conn.createStatement()
                .executeQuery(
                    "SELECT * FROM adres WHERE adres_id = " + 
                    adres.getId()
                )) {
                if (resultSet.next()) { // If there is a next, the resultset is not empty
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

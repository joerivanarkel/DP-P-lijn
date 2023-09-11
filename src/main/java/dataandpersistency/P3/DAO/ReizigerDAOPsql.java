package dataandpersistency.P3.DAO;

import dataandpersistency.P3.Models.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReizigerDAOPsql implements IReizigerDAO {
    private Connection connection;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            try (ResultSet resultSet = connection.createStatement()
                .executeQuery(
                    "INSERT INTO reiziger VALUES (" + 
                    reiziger.getId() + 
                    ", '" + 
                    reiziger.getVoorletters() + 
                    "', '" + 
                    reiziger.getTussenvoegsel() + 
                    "', '" + 
                    reiziger.getAchternaam() + 
                    "', '" + 
                    reiziger.getGeboortedatum() + 
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
    public boolean update(Reiziger reiziger) {
        try {
            if (checkIfExists(reiziger)) return false;
            try (ResultSet resultSet = connection.createStatement()
                .executeQuery(
                    "UPDATE reiziger SET voorletters = '" + 
                    reiziger.getVoorletters() + 
                    "', tussenvoegsel = '" + 
                    reiziger.getTussenvoegsel() + 
                    "', achternaam = '" + 
                    reiziger.getAchternaam() + 
                    "', geboortedatum = '" + 
                    reiziger.getGeboortedatum() + 
                    "' WHERE reiziger_id = " + 
                    reiziger.getId()
                )) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            if (checkIfExists(reiziger)) return false;
            try (ResultSet resultSet = connection.createStatement()
                .executeQuery(
                    "DELETE FROM reiziger WHERE reiziger_id = " + 
                    reiziger.getId()
                )) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            try (ResultSet resultSet = connection.createStatement()
                .executeQuery(
                    "SELECT * FROM reiziger WHERE reiziger_id = " + 
                    id
                )) {
                if (resultSet.next()) {
                    Reiziger reiziger = new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")
                    );
                    return reiziger;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Reiziger> findByGbdatum(String datum) {
        ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();
        try {
            try (ResultSet resultSet = connection.createStatement()
                .executeQuery(
                    "SELECT * FROM reiziger WHERE geboortedatum = '" + 
                    datum + "'"
                )) {
                while (resultSet.next()) {
                    Reiziger reiziger = new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")
                    );
                    reizigers.add(reiziger);
                }
            }
            return reizigers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return reizigers;
        }
            
    }

    @Override
    public ArrayList<Reiziger> findAll() {
        ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();
        try {
            try (ResultSet resultSet = connection.createStatement()
                .executeQuery(
                    "SELECT * FROM reiziger"
                )) {
                while (resultSet.next()) {
                    Reiziger reiziger = new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")
                    );
                    reizigers.add(reiziger);
                }
            }
            return reizigers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return reizigers;
        }
    }

    private boolean checkIfExists(Reiziger reiziger) throws SQLException {
        try (ResultSet resultSet = connection.createStatement()
                .executeQuery(
                        "SELECT * FROM reiziger WHERE reiziger_id = " +
                                reiziger.getId())) {
            if (!resultSet.next()) { // If there is no next, the resultset is empty
                return true;
            }
        }
        return false;
    }
    
}

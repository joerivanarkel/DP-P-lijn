package dataandpersistency.P3.DAO;

import dataandpersistency.P3.DAO.Interfaces.IReizigerDAO;
import dataandpersistency.P3.Models.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reiziger VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, reiziger.getGeboortedatum());
            preparedStatement.executeUpdate();
            return true;

            // try (ResultSet resultSet = connection.createStatement()
            //     .executeQuery(
            //         "INSERT INTO reiziger VALUES (" + 
            //         reiziger.getId() + 
            //         ", '" + 
            //         reiziger.getVoorletters() + 
            //         "', '" + 
            //         reiziger.getTussenvoegsel() + 
            //         "', '" + 
            //         reiziger.getAchternaam() + 
            //         "', '" + 
            //         reiziger.getGeboortedatum() + 
            //         "')"
            //     )) {
            //     return true;
            // }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            if (checkIfExists(reiziger)) return false;
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?"
            );
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, reiziger.getGeboortedatum());
            preparedStatement.setInt(5, reiziger.getId());
            preparedStatement.executeUpdate();
            return true;

            // try (ResultSet resultSet = connection.createStatement()
            //     .executeQuery(
            //         "UPDATE reiziger SET voorletters = '" + 
            //         reiziger.getVoorletters() + 
            //         "', tussenvoegsel = '" + 
            //         reiziger.getTussenvoegsel() + 
            //         "', achternaam = '" + 
            //         reiziger.getAchternaam() + 
            //         "', geboortedatum = '" + 
            //         reiziger.getGeboortedatum() + 
            //         "' WHERE reiziger_id = " + 
            //         reiziger.getId()
            //     )) {
            //     return true;
            // }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            if (checkIfExists(reiziger)) return false;
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM reiziger WHERE reiziger_id = ?"
            );
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.executeUpdate();
            return true;

            // try (ResultSet resultSet = connection.createStatement()
            //     .executeQuery(
            //         "DELETE FROM reiziger WHERE reiziger_id = " + 
            //         reiziger.getId()
            //     )) {
            //     return true;
            // }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM reiziger WHERE reiziger_id = ?"
            );
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

            // try (ResultSet resultSet = connection.createStatement()
            //     .executeQuery(
            //         "SELECT * FROM reiziger WHERE reiziger_id = " + 
            //         id
            //     )) {
            //     if (resultSet.next()) {
            //         Reiziger reiziger = new Reiziger(
            //             resultSet.getInt("reiziger_id"),
            //             resultSet.getString("voorletters"),
            //             resultSet.getString("tussenvoegsel"),
            //             resultSet.getString("achternaam"),
            //             resultSet.getDate("geboortedatum")
            //         );
            //         return reiziger;
            //     }
            // }
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM reiziger WHERE geboortedatum = ?"
            );
            preparedStatement.setString(1, datum);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

            // try (ResultSet resultSet = connection.createStatement()
            //     .executeQuery(
            //         "SELECT * FROM reiziger WHERE geboortedatum = '" + 
            //         datum + "'"
            //     )) {
            //     while (resultSet.next()) {
            //         Reiziger reiziger = new Reiziger(
            //             resultSet.getInt("reiziger_id"),
            //             resultSet.getString("voorletters"),
            //             resultSet.getString("tussenvoegsel"),
            //             resultSet.getString("achternaam"),
            //             resultSet.getDate("geboortedatum")
            //         );
            //         reizigers.add(reiziger);
            //     }
            // }
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM reiziger"
            );
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

            // try (ResultSet resultSet = connection.createStatement()
            //     .executeQuery(
            //         "SELECT * FROM reiziger"
            //     )) {
            //     while (resultSet.next()) {
            //         Reiziger reiziger = new Reiziger(
            //             resultSet.getInt("reiziger_id"),
            //             resultSet.getString("voorletters"),
            //             resultSet.getString("tussenvoegsel"),
            //             resultSet.getString("achternaam"),
            //             resultSet.getDate("geboortedatum")
            //         );
            //         reizigers.add(reiziger);
            //     }
            // }
            return reizigers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return reizigers;
        }
    }

    private boolean checkIfExists(Reiziger reiziger) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT * FROM reiziger WHERE reiziger_id = ?"
        );
        preparedStatement.setInt(1, reiziger.getId());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) { // If there is no next, the resultset is empty
                return true;
            }
        }

        // try (ResultSet resultSet = connection.createStatement()
        //         .executeQuery(
        //                 "SELECT * FROM reiziger WHERE reiziger_id = " +
        //                         reiziger.getId())) {
        //     if (!resultSet.next()) { // If there is no next, the resultset is empty
        //         return true;
        //     }
        // }
        return false;
    }
    
}

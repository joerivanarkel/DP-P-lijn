package dataandpersistency.P4.DAO;

import dataandpersistency.P4.DAO.Interfaces.IAdresDAO;
import dataandpersistency.P4.Models.Adres;
import dataandpersistency.P4.Models.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements IAdresDAO {
    private Connection conn;
    private ReizigerDAOPsql rdao;
    
    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setRdao(ReizigerDAOPsql rdao) { this.rdao = rdao; }
    public ReizigerDAOPsql getRdao() { return rdao; }
    
    @Override
    public boolean save(Adres adres) throws SQLException {
        if (checkIfExists(adres)) throw new IllegalArgumentException("Adres bestaat al");
        
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO adres VALUES (?, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6, adres.getReiziger_id());
            preparedStatement.executeUpdate();

            conn.commit();

            return true;
                
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);
            throw new SQLException("Adres niet opgeslagen", e);
        }
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        if (checkIfExists(adres)) throw new IllegalArgumentException("Adres bestaat niet");
        try {
            conn.setAutoCommit(false);
            
            PreparedStatement preparedStatement = conn.prepareStatement(
                "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?"
            );
            preparedStatement.setString(1, adres.getPostcode());
            preparedStatement.setString(2, adres.getHuisnummer());
            preparedStatement.setString(3, adres.getStraat());
            preparedStatement.setString(4, adres.getWoonplaats());
            preparedStatement.setInt(5, adres.getReiziger_id());
            preparedStatement.setInt(6, adres.getId());
            preparedStatement.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            conn.rollback();
            conn.setAutoCommit(true);
            throw new SQLException("Adres niet geupdate", e);
        }
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        if (checkIfExists(adres)) throw new IllegalArgumentException("Adres bestaat niet");
        
        PreparedStatement preparedStatement = conn.prepareStatement(
            "DELETE FROM adres WHERE adres_id = ?"
        );
        preparedStatement.setInt(1, adres.getId());
        preparedStatement.executeUpdate();
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT * FROM adres WHERE reiziger_id = ?"
        );
        preparedStatement.setInt(1, reiziger.getId());
        preparedStatement.executeQuery();
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) { // If there is a next, the resultset is not empty
                return new Adres(
                    resultSet.getInt("adres_id"),
                    resultSet.getString("postcode"),
                    resultSet.getString("huisnummer"),
                    resultSet.getString("straat"),
                    resultSet.getString("woonplaats"),
                    resultSet.getInt("reiziger_id")
                );
            }
        }
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adressen = new ArrayList<>();

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
    }
    
    public Adres findById(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT * FROM adres WHERE adres_id = ?"
        );
        preparedStatement.setInt(1, id);
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) { // If there is a next, the resultset is not empty
                return new Adres(
                    resultSet.getInt("adres_id"),
                    resultSet.getString("postcode"),
                    resultSet.getString("huisnummer"),
                    resultSet.getString("straat"),
                    resultSet.getString("woonplaats"),
                    resultSet.getInt("reiziger_id")
                );
            }
        }
        return null;
    }
    
    private boolean checkIfExists(Adres adres) {
        try {
            // PreparedStatement preparedStatement = conn.prepareStatement(
            //     "SELECT * FROM adres WHERE adres_id = ?"
            // );
            // preparedStatement.setInt(1, adres.getId());
            // // preparedStatement.executeQuery();
            
            // try (ResultSet resultSet = preparedStatement.executeQuery()) {
            //     if (!resultSet.next()) { // If there is a next, the resultset is not empty
            //         return true;
            //     }
            // }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

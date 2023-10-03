package dataandpersistency.P4.DAO;

import dataandpersistency.P4.DAO.Interfaces.IAdresDAO;
import dataandpersistency.P4.DAO.Interfaces.IOVChipkaartDAO;
import dataandpersistency.P4.DAO.Interfaces.IReizigerDAO;
import dataandpersistency.P4.Models.Adres;
import dataandpersistency.P4.Models.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements IReizigerDAO {
    private Connection connection;

    private IAdresDAO adao;
    private IOVChipkaartDAO odao;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public void setAdao(IAdresDAO adao) { this.adao = adao; }
    public void setOdao(IOVChipkaartDAO odao) { this.odao = odao; }

    public IAdresDAO getAdao() { return adao; }
    public IOVChipkaartDAO getOdao() { return odao; }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        if (!checkIfExists(reiziger)) throw new IllegalArgumentException("Reiziger bestaat al");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reiziger VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, reiziger.getGeboortedatum());
            preparedStatement.executeUpdate();

            adao.save(reiziger.getAdres());
            odao.save(reiziger.getOVChipkaarten());

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new SQLException("Reiziger niet opgeslagen", e);
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        if (checkIfExists(reiziger)) throw new IllegalArgumentException("Reiziger bestaat niet");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?"
            );
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, reiziger.getGeboortedatum());
            preparedStatement.setInt(5, reiziger.getId());
            preparedStatement.executeUpdate();

            adao.update(reiziger.getAdres());
            odao.update(reiziger.getOVChipkaarten());

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new SQLException("Reiziger niet geupdate", e);
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        if (checkIfExists(reiziger)) return false;
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM reiziger WHERE reiziger_id = ?"
        );
        preparedStatement.setInt(1, reiziger.getId());
        preparedStatement.executeUpdate();

        adao.delete(reiziger.getAdres());
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
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
                reiziger.setAdres(adao.findByReiziger(reiziger));
                return reiziger;
            }
        }

        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
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
                reiziger.setAdres(adao.findByReiziger(reiziger));
                reizigers.add(reiziger);
            }
        }

        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
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
                reiziger.setAdres(adao.findByReiziger(reiziger));
                reizigers.add(reiziger);
            }
        }

        return reizigers;
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

        return false;
    }
    
}

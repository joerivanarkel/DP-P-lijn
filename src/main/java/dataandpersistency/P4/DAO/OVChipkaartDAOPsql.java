package dataandpersistency.P4.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataandpersistency.P4.DAO.Interfaces.IOVChipkaartDAO;
import dataandpersistency.P4.Models.OVChipkaart;
import dataandpersistency.P4.Models.Reiziger;

public class OVChipkaartDAOPsql implements IOVChipkaartDAO {
    private Connection conn;
    private ReizigerDAOPsql rdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setRdao(ReizigerDAOPsql rdao) { this.rdao = rdao; }
    public ReizigerDAOPsql getRdao() { return this.rdao; }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> ovChipkaarten = new ArrayList<OVChipkaart>();

        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?"
        );
        preparedStatement.setInt(1, reiziger.getId());
        preparedStatement.executeQuery();

        try (ResultSet resultSet = preparedStatement.getResultSet()) {
            while (resultSet.next()) {
                createOVChipkaart(reiziger, ovChipkaarten, resultSet);
            }
        }

        return ovChipkaarten;
    }

    @Override
    public boolean save(List<OVChipkaart> ovChipkaarten) throws SQLException {
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO ov_chipkaart VALUES (?, ?, ?, ?, ?)"
            );

            for (OVChipkaart ovChipkaart : ovChipkaarten) {
                preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
                preparedStatement.setDate(2, ovChipkaart.getGeldig_tot());
                preparedStatement.setInt(3, ovChipkaart.getKlasse());
                preparedStatement.setDouble(4, ovChipkaart.getSaldo());
                preparedStatement.setInt(5, ovChipkaart.getReiziger_id());
                preparedStatement.executeUpdate();
            }

            conn.commit();

            return true;
                
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);
            throw new SQLException("OVChipkaart niet opgeslagen", e);
        }
    }

    @Override
    public boolean update(List<OVChipkaart> ovChipkaarten) throws SQLException {
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(
                "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?"
            );

            for (OVChipkaart ovChipkaart : ovChipkaarten) {
                preparedStatement.setDate(1, ovChipkaart.getGeldig_tot());
                preparedStatement.setInt(2, ovChipkaart.getKlasse());
                preparedStatement.setDouble(3, ovChipkaart.getSaldo());
                preparedStatement.setInt(4, ovChipkaart.getReiziger_id());
                preparedStatement.setInt(5, ovChipkaart.getKaart_nummer());
                preparedStatement.executeUpdate();
            }

            conn.commit();

            return true;
                
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);
            throw new SQLException("OVChipkaart niet geupdate", e);
        }
    }

    private void createOVChipkaart(Reiziger reiziger, List<OVChipkaart> ovChipkaarten, ResultSet resultSet)
            throws SQLException {
        OVChipkaart ovChipkaart = new OVChipkaart(
            resultSet.getInt("kaart_nummer"),
            resultSet.getDate("geldig_tot"),
            resultSet.getInt("klasse"),
            resultSet.getDouble("saldo"),
            reiziger.getId()
        );
        reiziger.addOVChipkaart(ovChipkaart);
        ovChipkaarten.add(ovChipkaart);
    }
    
}

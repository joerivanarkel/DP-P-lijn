package dataandpersistency.P5.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataandpersistency.P5.DAO.Interfaces.IOVChipkaartDAO;
import dataandpersistency.P5.Models.OVChipkaart;
import dataandpersistency.P5.Models.Reiziger;
import dataandpersistency.P5.Models.Product;

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
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO ov_chipkaart VALUES (?, ?, ?, ?, ?)"
            );

            preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStatement.setDate(2, ovChipkaart.getGeldig_tot());
            preparedStatement.setInt(3, ovChipkaart.getKlasse());
            preparedStatement.setDouble(4, ovChipkaart.getSaldo());
            preparedStatement.setInt(5, ovChipkaart.getReiziger_id());
            preparedStatement.executeUpdate();

            for (Product product : ovChipkaart.getProducten()) {
                PreparedStatement preparedStatement2 = conn.prepareStatement(
                    "INSERT INTO ov_chipkaart_product VALUES (?, ?)"
                );
                preparedStatement2.setInt(1, ovChipkaart.getKaart_nummer());
                preparedStatement2.setInt(2, product.getProduct_nummer());
                preparedStatement2.executeUpdate();
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
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(
                "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?"
            );

            preparedStatement.setDate(1, ovChipkaart.getGeldig_tot());
            preparedStatement.setInt(2, ovChipkaart.getKlasse());
            preparedStatement.setDouble(3, ovChipkaart.getSaldo());
            preparedStatement.setInt(4, ovChipkaart.getReiziger_id());
            preparedStatement.setInt(5, ovChipkaart.getKaart_nummer());
            preparedStatement.executeUpdate();

            for (Product product : ovChipkaart.getProducten()) {
                PreparedStatement preparedStatement2 = conn.prepareStatement(
                    "INSERT INTO ov_chipkaart_product VALUES (?, ?)"
                );
                preparedStatement2.setInt(1, ovChipkaart.getKaart_nummer());
                preparedStatement2.setInt(2, product.getProduct_nummer());
                preparedStatement2.executeUpdate();
            }

            conn.commit();

            return true;
                
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);
            throw new SQLException("OVChipkaart niet geupdate", e);
        }
    }

    @Override
    public boolean delete(OVChipkaart t) throws SQLException {
        if (checkIfExists(t)) throw new IllegalArgumentException("OVChipkaart bestaat niet");

        PreparedStatement preparedStatement = conn.prepareStatement(
            "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?"
        );

        preparedStatement.setInt(1, t.getKaart_nummer());
        preparedStatement.executeUpdate();

        for (Product product : t.getProducten()) {
            PreparedStatement preparedStatement2 = conn.prepareStatement(
                "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ? AND product_nummer = ?"
            );
            preparedStatement2.setInt(1, t.getKaart_nummer());
            preparedStatement2.setInt(2, product.getProduct_nummer());
            preparedStatement2.executeUpdate();
        }

        return true;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> ovChipkaarten = new ArrayList<OVChipkaart>();

        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT * FROM ov_chipkaart"
        );
        preparedStatement.executeQuery();

        try (ResultSet resultSet = preparedStatement.getResultSet()) {
            while (resultSet.next()) {
                Reiziger reiziger = rdao.findById(resultSet.getInt("reiziger_id"));
                createOVChipkaart(reiziger, ovChipkaarten, resultSet);
            }
        }

        for (OVChipkaart ovChipkaart : ovChipkaarten) {
            PreparedStatement preparedStatement2 = conn.prepareStatement(
                "SELECT * FROM ov_chipkaart_product WHERE kaart_nummer = ?"
            );
            preparedStatement2.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStatement2.executeQuery();

            try (ResultSet resultSet2 = preparedStatement2.getResultSet()) {
                while (resultSet2.next()) {
                    PreparedStatement preparedStatement3 = conn.prepareStatement(
                        "SELECT * FROM product WHERE product_nummer = ?"
                    );
                    preparedStatement3.setInt(1, resultSet2.getInt("product_nummer"));
                    preparedStatement3.executeQuery();

                    try (ResultSet resultSet3 = preparedStatement3.getResultSet()) {
                        while (resultSet3.next()) {
                            ovChipkaart.addProduct(
                                new Product(
                                    resultSet3.getInt("product_nummer"),
                                    resultSet3.getString("naam"),
                                    resultSet3.getString("beschrijving"),
                                    resultSet3.getDouble("prijs")
                                )
                            );
                        }
                    }
                }
            }
        }

        return ovChipkaarten;
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
    
    private boolean checkIfExists(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?"
        );
        preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
        preparedStatement.executeQuery();

        try (ResultSet resultSet = preparedStatement.getResultSet()) {
            return !resultSet.next();
        }
    }
}

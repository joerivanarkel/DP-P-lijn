package dataandpersistency.P5.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataandpersistency.P5.DAO.Interfaces.IProductDAO;
import dataandpersistency.P5.Models.OVChipkaart;
import dataandpersistency.P5.Models.Product;

public class ProductDAOPsql implements IProductDAO {
    private Connection connection;

    public ProductDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        if (!checkIfExists(product)) throw new IllegalArgumentException("Product bestaat al");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, product.getProduct_nummer());
            preparedStatement.setString(2, product.getNaam());
            preparedStatement.setString(3, product.getBeschrijving());
            preparedStatement.setDouble(4, product.getPrijs());
            preparedStatement.executeUpdate();

            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "INSERT INTO ov_chipkaart_product VALUES (?, ?)"
                );
                preparedStatement2.setInt(1, ovChipkaart.getKaart_nummer());
                preparedStatement2.setInt(2, product.getProduct_nummer());
                preparedStatement2.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
    }

    @Override
    public boolean update(Product product) throws SQLException {
        if (checkIfExists(product)) throw new IllegalArgumentException("Product bestaat niet");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?"
            );
            preparedStatement.setString(1, product.getNaam());
            preparedStatement.setString(2, product.getBeschrijving());
            preparedStatement.setDouble(3, product.getPrijs());
            preparedStatement.setInt(4, product.getProduct_nummer());
            preparedStatement.executeUpdate();

            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "INSERT INTO ov_chipkaart_product VALUES (?, ?)"
                );
                preparedStatement2.setInt(1, ovChipkaart.getKaart_nummer());
                preparedStatement2.setInt(2, product.getProduct_nummer());
                preparedStatement2.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM product WHERE product_nummer = ?"
        );
        preparedStatement.setInt(1, product.getProduct_nummer());
        preparedStatement.executeUpdate();

        for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
            PreparedStatement preparedStatement2 = connection.prepareStatement(
                "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ? AND product_nummer = ?"
            );
            preparedStatement2.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStatement2.setInt(2, product.getProduct_nummer());
            preparedStatement2.executeUpdate();
        }

        return true;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<Product>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM product"
        );

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("product_nummer"),
                        resultSet.getString("naam"),
                        resultSet.getString("beschrijving"),
                        resultSet.getDouble("prijs")
                ));
            }
        }

        for (Product product : products) {
            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "SELECT * FROM ov_chipkaart_product WHERE product_nummer = ?"
            );
            preparedStatement2.setInt(1, product.getProduct_nummer());
            preparedStatement2.executeQuery();

            try (ResultSet resultSet2 = preparedStatement2.getResultSet()) {
                while (resultSet2.next()) {
                    PreparedStatement preparedStatement3 = connection.prepareStatement(
                            "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?"
                    );
                    
                    preparedStatement3.setInt(1, resultSet2.getInt("kaart_nummer"));
                    preparedStatement3.executeQuery();

                    try (ResultSet resultSet3 = preparedStatement3.getResultSet()) {
                        while (resultSet3.next()) {
                            product.addOVChipkaart(new OVChipkaart(
                                    resultSet3.getInt("kaart_nummer"),
                                    resultSet3.getDate("geldig_tot"),
                                    resultSet3.getInt("klasse"),
                                    resultSet3.getDouble("saldo"),
                                    resultSet3.getInt("reiziger_id")
                            ));
                        }
                    }
                }
            }
        }

        return products;
    }

    private boolean checkIfExists(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM product WHERE product_nummer = ?"
        );
        preparedStatement.setInt(1, product.getProduct_nummer());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) 
                return false;
        }

        return true;
    }
    
}

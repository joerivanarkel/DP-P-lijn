package dataandpersistency.P5.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

import dataandpersistency.P5.Models.OVChipkaart;
import dataandpersistency.P5.Models.Product;

public interface IProductDAO extends IDAO<Product> {
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
}

package dataandpersistency.P5.DAO.Interfaces;

public interface IProductDAO extends IDAO<Product> {
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    
}

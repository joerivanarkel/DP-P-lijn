package dataandpersistency.P4.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

import dataandpersistency.P4.Models.Adres;
import dataandpersistency.P4.Models.Reiziger;

public interface IAdresDAO {
    boolean save(Adres adres) throws SQLException;
    boolean update(Adres adres) throws SQLException;
    boolean delete(Adres adres) throws SQLException;
    List<Adres> findAll() throws SQLException;
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
}

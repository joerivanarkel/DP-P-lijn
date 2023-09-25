package dataandpersistency.P4.DAO.Interfaces;

import java.sql.SQLException;

import dataandpersistency.P4.Models.Adres;
import dataandpersistency.P4.Models.Reiziger;

public interface IAdresDAO extends IDAO<Adres> {
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
}

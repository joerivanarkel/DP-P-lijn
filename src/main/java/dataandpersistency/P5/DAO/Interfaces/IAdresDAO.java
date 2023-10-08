package dataandpersistency.P5.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

import dataandpersistency.P4.Models.Adres;
import dataandpersistency.P4.Models.Reiziger;

public interface IAdresDAO extends IDAO<Adres> {	
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
}

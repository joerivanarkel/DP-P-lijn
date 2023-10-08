package dataandpersistency.P5.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

import dataandpersistency.P5.Models.Adres;
import dataandpersistency.P5.Models.Reiziger;

public interface IAdresDAO extends IDAO<Adres> {	
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
}

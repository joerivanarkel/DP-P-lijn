package dataandpersistency.P3.DAO.Interfaces;

import dataandpersistency.P3.Models.Adres;
import dataandpersistency.P3.Models.Reiziger;

public interface IAdresDAO extends IDAO<Adres> {
    Adres findByReiziger(Reiziger reiziger);
}

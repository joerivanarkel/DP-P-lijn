package dataandpersistency.P2;

import java.util.ArrayList;

public interface IReizigerDAO {
    public boolean save(Reiziger reiziger);

    public boolean update(Reiziger reiziger);

    public boolean delete(Reiziger reiziger);

    public Reiziger findById(int id);

    public ArrayList<Reiziger> findByGbdatum(String datum);

    public ArrayList<Reiziger> findAll();
}

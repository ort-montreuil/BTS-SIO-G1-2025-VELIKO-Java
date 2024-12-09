package sio.demoprojetjava.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepositoryInterface <T,ID> {
    void create (T obj) throws SQLException;
    void update (T obj) throws SQLException;
    void delete (T obj) throws SQLException;
    T getById (int id) ;
    ArrayList<T> getAll() throws SQLException;


}

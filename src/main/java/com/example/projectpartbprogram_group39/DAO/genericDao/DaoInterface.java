package com.example.projectpartbprogram_group39.DAO.genericDao;

import java.io.IOException;
import java.util.List;

public interface DaoInterface<T> {
    void add(T object) throws IOException;
    List<T> getAll() throws IOException;
    void update(T oldObject, T newObject) throws IOException;
    void delete(T object) throws IOException;
    boolean exists(T object) throws IOException;
}

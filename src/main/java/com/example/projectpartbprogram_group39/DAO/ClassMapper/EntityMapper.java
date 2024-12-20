package com.example.projectpartbprogram_group39.DAO.ClassMapper;

public interface EntityMapper<T> {
    T fromString(String string);

    String toString(T object);

    boolean equals(T obj1, T obj2);
}

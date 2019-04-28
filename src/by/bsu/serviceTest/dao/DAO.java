package by.bsu.serviceTest.dao;

import java.util.List;

public interface DAO<T> {

    List<T> selectAll();

    T selectOne(long id);

    boolean exist(long id);

    void insert(T t);

    void update(T t, long id);

    void delete(long id);

    void deleteAll();
}

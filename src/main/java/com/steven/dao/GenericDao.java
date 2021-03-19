package com.steven.dao;

public interface GenericDao<T, I, S> {

    T findById(I id);

    int create(T t);

    int create(I i, S s);

    int update(T t);

    int delete(I i);

}

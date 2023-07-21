package com.romych.player.musicplayer.dao;

import java.util.List;

public interface AbstractDAO<K extends Number, T> {
    List<T> findAll();
    T findById(K id);
    boolean delete(K id);
    boolean create(T entity);
    T update(T entity);

}

package com.boot.demo1.service;

import com.boot.demo1.Do.Book;

import java.sql.SQLException;

public interface BookService {
    void saveBook(Book book) throws SQLException;
    Book getById(Integer id) throws SQLException;
}

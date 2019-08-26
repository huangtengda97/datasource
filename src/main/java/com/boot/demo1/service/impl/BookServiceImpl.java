package com.boot.demo1.service.impl;

import com.boot.demo1.Do.Book;
import com.boot.demo1.datasource.DataSource;
import com.boot.demo1.datasource.DataSourceType;
import com.boot.demo1.mapper.BookMapper;
import com.boot.demo1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;
//    @Autowired
//    private javax.sql.DataSource dataSource;

    @DataSource(DataSourceType.MASTER)
    @Transactional
    @Override
    public void saveBook(Book book) throws SQLException {
        System.out.println("=======save======进入主库操作========save======");
//        System.out.println(dataSource.getConnection());
        this.bookMapper.saveBook(book);
    }

    @DataSource(DataSourceType.SLAVE)
    @Override
    public Book getById(Integer id) throws SQLException {
        System.out.println("=======query======进入从库操作=========query=====");
//        System.out.println(dataSource.getConnection());
        Book book = bookMapper.getById(id);
        if (book != null) {
            System.out.println(book.toString());
        } else {
            System.out.println("4444444");
        }
        return book;
    }
}

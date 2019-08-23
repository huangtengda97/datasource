package com.boot.demo1.mapper;

import com.boot.demo1.Do.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookMapper {
    @Select("SELECT * FROM book WHERE id=#{id}")
    Book getById(Integer id);

    @Insert("insert into book(id,name,author)values(#{id}, #{name}, #{author})")
    void saveBook(Book book);
}

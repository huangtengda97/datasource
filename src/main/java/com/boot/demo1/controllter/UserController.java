package com.boot.demo1.controllter;


import com.boot.demo1.Do.Book;
import com.boot.demo1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@RestController
public class UserController {

//    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private BookService bookService;

    @GetMapping("/user")
    public void test () {
//        User user = userMapper.findByUsername("casuser");
//        System.out.println(user);
    }

    @RequestMapping("/login/s")
    public String login() {

        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("JSESSIONID");
        return "redirect:https://localhost:8080/logout?service=https://localhost:8087/";
    }

    @RequestMapping("/index")
    public String index() {

        return "index";
    }

    @RequestMapping("/admin")
    public String welcom() {

        return "admin";
    }


    @GetMapping(value = "/save")
    public String save() throws SQLException {
        Book book = new Book();
        book.setId(4);
        book.setAuthor("434234");
        book.setName("nosm");
       bookService.saveBook(book);
       return "success";
    }

    @GetMapping(value = "/find/{id}")
    public String getById(@PathVariable("id") Integer id) throws SQLException {
        bookService.getById(id);
        return "success";
    }
}

package au.edu.unsw.soacourse.hrSystem.dao;
import java.sql.*;  

import java.util.HashMap;
import java.util.Map;

import au.edu.unsw.soacourse.hrSystem.model.UserProfile;


public enum BooksDao {
    instance;

    private Map<String, UserProfile> contentStore = new HashMap<String, UserProfile>();

    private BooksDao() {

    	UserProfile b = new UserProfile("1", "RESTful Web Services","");
        b.setDetail("http://oreilly.com/catalog/9780596529260");
        contentStore.
        contentStore.put("1", b);
        b = new Book("2", "RESTful Java with JAX-RS");
        b.setDetail("http://oreilly.com/catalog/9780596158057");
        contentStore.put("2", b);
    }
    public Map<String, Book> getStore(){
        return contentStore;
    }

}
package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

public class Database {
    private static BasicDataSource dataSource;

    static  {
        dataSource = new  BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername("root");
        dataSource.setPassword("yearup");

    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }
}

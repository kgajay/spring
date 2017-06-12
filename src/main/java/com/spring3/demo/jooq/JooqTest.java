package com.spring3.demo.jooq;


import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;

import static com.spring3.demo.jooq.Tables.AUTHOR;

/**
 * @author ajay.kg created on 11/06/17.
 */
public class JooqTest {

    public static void main(String[] args) {

        String userName = "root";
        String password = "mysql";
        String url = "jdbc:mysql://localhost:3306/library";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try {

            Connection conn = DriverManager.getConnection(url, userName, password);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> result = create.select().from(AUTHOR).fetch();

            result.forEach(record -> {
                System.out.println("Id: " + record.getValue(AUTHOR.ID) + ", firstName: " + record.getValue(AUTHOR.FIRST_NAME) + "Id: " + record.getValue(AUTHOR.LAST_NAME));
            });
        } catch (Exception e) {
            // For the sake of this tutorial, let's keep exception handling simple
            e.printStackTrace();
        }

    }
}

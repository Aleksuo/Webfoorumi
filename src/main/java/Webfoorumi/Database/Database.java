/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.Database;

import Webfoorumi.Collectors.Collector;
import java.sql.*;
import java.util.*;



/**
 *
 * @author Aleksi
 */
public class Database<T> {
    private boolean debug;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Database(String address) throws Exception {
        this.connection = DriverManager.getConnection(address);
    }

    public void setDebugMode(boolean d) {
        debug = d;
    }

    public List<T> queryAndCollect(String query, Collector<T> col) throws SQLException {
        List<T> rows = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            if (debug) {
                System.out.println("---");
                System.out.println(query);
                debug(rs);
                System.out.println("---");
            }

            rows.add(col.collect(rs));
        }

        rs.close();
        stmt.close();
        return rows;
    }

    private void debug(ResultSet rs) throws SQLException {
        int columns = rs.getMetaData().getColumnCount();
        for (int i = 0; i < columns; i++) {
            System.out.print(
                    rs.getObject(i + 1) + ":"
                    + rs.getMetaData().getColumnName(i + 1) + "  ");
        }

        System.out.println();
    }
}

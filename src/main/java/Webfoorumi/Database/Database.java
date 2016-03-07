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
    private String address;

    public Connection getConnection()throws SQLException {
        return DriverManager.getConnection(this.address);
    }

    public Database(String address) throws Exception {
        this.address = address;
    }

    public void setDebugMode(boolean d) {
        debug = d;
    }

    public List<T> queryAndCollect(String query, Collector<T> col) throws SQLException {
        List<T> rows = new ArrayList<>();
        Statement stmt = getConnection().createStatement();
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

    public int update(String updateQuery, Object... params) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        if (debug) {
            System.out.println("---");
            System.out.println(updateQuery);
            System.out.println("Changed rows: " + changes);
            System.out.println("---");
        }
        stmt.close();

        return changes;
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

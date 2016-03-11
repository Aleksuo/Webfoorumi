/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.DAO;

import Webfoorumi.Database.Database;
import Webfoorumi.Dom.Kayttaja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author jappappi
 */
public class KayttajaDAO implements Dao<Kayttaja, Integer> {

    Database database;

    public KayttajaDAO(Database db) {
        this.database = db;
    }

    @Override
    public Kayttaja findOne(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja WHERE Kayttaja.id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Kayttaja kayttaja = new Kayttaja(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return kayttaja;
    }

    @Override
    public List<Kayttaja> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja");
        ResultSet rs = stmt.executeQuery();

        List<Kayttaja> kayttajat = new ArrayList<>();

        while (rs.next()) {

            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            Kayttaja a = new Kayttaja(id, nimi);
            kayttajat.add(a);
        }

        rs.close();
        stmt.close();
        connection.close();

        return kayttajat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Kayttaja WHERE Kayttaja.id = ?");
        stmt.setObject(1, key);
        int tulos = stmt.executeUpdate();
        System.out.println(tulos);

        stmt.close();
        connection.close();
    }

//    @Override
//    public List<Kayttaja> findAllIn(Collection<Integer> keys) throws SQLException {
//        if (keys.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        StringBuilder muuttujat = new StringBuilder("?");
//        for (int i = 1; i < keys.size(); i++) {
//            muuttujat.append(", ?");
//        }
//
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja WHERE id IN (" + muuttujat + ")");
//        int laskuri = 1;
//        for (int key : keys) {
//            stmt.setObject(laskuri, key);
//            laskuri++;
//        }
//
//        ResultSet rs = stmt.executeQuery();
//        List<Kayttaja> kayttajat = new ArrayList<>();
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            String nimi = rs.getString("nimi");
//
//            kayttajat.add(new Kayttaja(id, nimi));
//
//        }
//        return kayttajat;
//    }
}

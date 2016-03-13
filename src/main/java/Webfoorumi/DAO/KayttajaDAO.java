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
    

    @Override
    public Kayttaja lastInsert() throws SQLException {
            Connection c = database.getConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM Kayttaja WHERE id = (SELECT MAX(id) FROM Kayttaja)");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            c.close();
            rs.close();
            
            return new Kayttaja(id,nimi);
    }

    
    public void insert(String nimi) throws SQLException {
        database.update("INSERT INTO Kayttaja(nimi) VALUES(?)", nimi);
    }

    
}

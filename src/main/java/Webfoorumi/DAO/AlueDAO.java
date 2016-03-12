/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.DAO;

import Webfoorumi.Database.Database;
import Webfoorumi.Dom.Alue;
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
public class AlueDAO implements Dao<Alue, Integer> {

    private Database database;
    private KeskusteluDAO kdao;

    public AlueDAO(Database database, KeskusteluDAO kd) {
        this.database = database;
        this.kdao = kd;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE Alue.id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Alue alue = new Alue(id, nimi);
        alue.setKeskustelut(kdao.alueenKeskustelut(id));

        rs.close();
        stmt.close();
        connection.close();

        return alue;
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY nimi COLLATE NOCASE");
        ResultSet rs = stmt.executeQuery();

        List<Alue> alueet = new ArrayList<>();

        while (rs.next()) {

            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            Alue a = new Alue(id, nimi);
            a.setKeskustelut(kdao.alueenKeskustelut(id));
            
            if(!a.getKeskustelut().isEmpty()){
                a.setUusinviesti(a.getKeskustelut().get(0).getUusinviesti());
            }
            
            alueet.add(a);
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;

    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Alue WHERE Alue.id = ?");
        stmt.setObject(1, key);
        int tulos = stmt.executeUpdate();
        System.out.println(tulos);

        stmt.close();
        connection.close();

    }

//    @Override
//    public List<Alue> findAllIn(Collection<Integer> keys) throws SQLException {
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
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id IN (" + muuttujat + ")");
//        int laskuri = 1;
//        for (int key : keys) {
//            stmt.setObject(laskuri, key);
//            laskuri++;
//        }
//
//        ResultSet rs = stmt.executeQuery();
//        List<Alue> alueet = new ArrayList<>();
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            String nimi = rs.getString("nimi");
//
//            alueet.add(new Alue(id, nimi));
//
//        }
//        return alueet;
//    }

   
    

    @Override
    public Alue lastInsert() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void insert(String nimi) throws SQLException {
        database.update("INSERT INTO Alue(nimi) VALUES(?)", nimi);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.DAO;

import Webfoorumi.Database.Database;
import Webfoorumi.Dom.Alue;
import Webfoorumi.Dom.Kayttaja;
import Webfoorumi.Dom.Keskustelu;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aleksi
 */
public class KeskusteluDAO implements Dao<Keskustelu, Integer> {

    private Database database;
    private KayttajaDAO kdao;
    private AlueDAO adao;

    public KeskusteluDAO(Database db, KayttajaDAO kd, AlueDAO ad) {
        this.database = db;
        this.kdao = kd;
        this.adao = ad;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE Keskustelu.id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasone = rs.next();
        if (!hasone) {
            return null;
        }

        int id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        Date timestamp = rs.getDate("timestamp");
        int alue_id = rs.getInt("alue_id");
        int aloittaja_id = rs.getInt("aloittaja_id");

        Keskustelu kesk = new Keskustelu(id, nimi, timestamp);
        Alue alue = adao.findOne(alue_id);
        Kayttaja aloittaja = kdao.findOne(aloittaja_id);

        kesk.setAloittaja(aloittaja);
        kesk.setAlue(alue);

        rs.close();
        stmt.close();
        connection.close();

        return kesk;

    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");
        ResultSet rs = stmt.executeQuery();

        

        List<Keskustelu> keskustelut = new ArrayList<>();

        while (rs.next()) {
            
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Date timestamp = rs.getDate("timestamp");
            int alue_id = rs.getInt("alue_id");
            int aloittaja_id = rs.getInt("aloittaja_id");
            
            Keskustelu kesk = new Keskustelu(id,nimi,timestamp);
            kesk.setAloittaja(this.kdao.findOne(aloittaja_id));
            kesk.setAlue(this.adao.findOne(alue_id));

        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Keskustelu WHERE Keskustelu.id = ?");
        stmt.setObject(1, key);
        int tulos = stmt.executeUpdate();
        System.out.println(tulos);

        stmt.close();
        connection.close();
    }

}

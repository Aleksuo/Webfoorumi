/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.DAO;

import Webfoorumi.Database.Database;
import Webfoorumi.Dom.Viesti;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pete
 */
public class ViestiDAO implements Dao<Viesti, Integer>{
    
    private Database database;
    private KayttajaDAO kdao;
    private KeskusteluDAO keskdao;

    public ViestiDAO(Database database, KayttajaDAO kdao, KeskusteluDAO keskdao) {
        this.database = database;
        this.kdao = kdao;
        this.keskdao = keskdao;
    }

   
    
    

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
    }
    
     public List<Viesti> keskustelunViestit(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Viesti.keskustelu_id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();

        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String timestamp = rs.getString("timestamp");
            int keskustelu_id = rs.getInt("keskustelu_id");
            int lahettaja_id = rs.getInt("lahettaja_id");

            Viesti viest = new Viesti(id, sisalto, timestamp);
            viest.setLahettaja(this.kdao.findOne(lahettaja_id));
            viest.setKeskustelu(this.keskdao.findOne(keskustelu_id));
            viestit.add(viest);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;

    }
    
}

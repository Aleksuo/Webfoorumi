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

    public ViestiDAO(Database database, KayttajaDAO kdao) {
        this.database = database;
        this.kdao = kdao;
        
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
            viest.setKeskustelu(keskustelu_id);
            viestit.add(viest);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;

    }
     
    public int keskustelunViestitlkm(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS lukumaara FROM Viesti WHERE Viesti.keskustelu_id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        int lkm = rs.getInt("lukumaara");
        rs.close();
        stmt.close();
        connection.close();
        
                
        return lkm;
    }

    @Override
    public Viesti lastInsert() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void insert(String viesti, int keskustelu, int kayttaja, int vastaus ) throws SQLException {
        database.update("INSERT INTO Viesti(sisalto, keskustelu_id, lahettaja_id, vastaus_id) VALUES(?,?,?,?)", viesti, keskustelu, kayttaja, vastaus);
    }
    
}

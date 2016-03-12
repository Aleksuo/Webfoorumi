/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.Collectors;

import Webfoorumi.Dom.Keskustelu;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Pete
 */
public class KeskusteluCollector implements Collector<Keskustelu> {

    @Override
    public Keskustelu collect(ResultSet rs) throws SQLException {
        int tunniste = rs.getInt("id");
        String nimi = rs.getString("nimi");
        String pvm = rs.getString("timestamp");

        return new Keskustelu(tunniste, nimi, pvm);
    }

}

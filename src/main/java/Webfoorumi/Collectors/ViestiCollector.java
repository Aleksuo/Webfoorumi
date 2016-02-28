/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.Collectors;

import Webfoorumi.Dom.Viesti;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pete
 */
public class ViestiCollector implements Collector<Viesti> {

    @Override
    public Viesti collect(ResultSet rs) throws SQLException {
        int tunniste = rs.getInt("id");
        String sisalto = rs.getString("sisalto");
        Date pvm = rs.getDate("timestamp");

        return new Viesti(tunniste, sisalto, pvm);
    }

}

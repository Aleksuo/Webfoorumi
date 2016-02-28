/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.Collectors;

import Webfoorumi.Dom.Kayttaja;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pete
 */
public class KayttajaCollector implements Collector<Kayttaja> {

    @Override
    public Kayttaja collect(ResultSet rs) throws SQLException {
        int tunniste = rs.getInt("id");
        String nimi = rs.getString("nimi");

        return new Kayttaja(tunniste, nimi);
    }

}

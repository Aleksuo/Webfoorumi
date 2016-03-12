/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.Dom;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Aleksi
 */
public class Keskustelu {
    private int id;
    private String nimi;
    private String timestamp;
    
    private int alue;
    private Kayttaja aloittaja;
    private List<Viesti> viestit;
    private Viesti uusinviesti;
    
    public Keskustelu(int id , String nimi, String timestamp){
        this.id = id;
        this.nimi = nimi;
        this.timestamp = timestamp;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getAlue() {
        return alue;
    }

    public void setAlue(int alue) {
        this.alue = alue;
    }

    public Kayttaja getAloittaja() {
        return aloittaja;
    }

    public void setAloittaja(Kayttaja aloittaja) {
        this.aloittaja = aloittaja;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }
    
    public int viestitlkm(){
        return viestit.size();
    }

    public Viesti getUusinviesti() {
        return uusinviesti;
    }

    public void setUusinviesti(Viesti uusinviesti) {
        this.uusinviesti = uusinviesti;
    }
    
    
}

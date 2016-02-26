/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.Dom;

import java.sql.Date;

/**
 *
 * @author Aleksi
 */
public class Keskustelu {
    private int id;
    private String nimi;
    private Date timestamp;
    
    private Alue alue;
    private Kayttaja aloittaja;
    
    public Keskustelu(int id , String nimi, Date timestamp){
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Alue getAlue() {
        return alue;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
    }

    public Kayttaja getAloittaja() {
        return aloittaja;
    }

    public void setAloittaja(Kayttaja aloittaja) {
        this.aloittaja = aloittaja;
    }
    
    
}

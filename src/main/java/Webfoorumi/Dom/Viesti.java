/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi.Dom;

import java.sql.Date;

/**
 *
 * @author alsu
 */
public class Viesti {
    private int id;
    private String sisalto;
    private String timestamp;
    
    private Keskustelu keskustelu;
    private Kayttaja lahettaja;
    private Viesti vastaus;
    
    public Viesti(int id, String sisalto, String timestamp){
        this.id = id;
        this.sisalto = sisalto;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Keskustelu getKeskustelu() {
        return keskustelu;
    }

    public void setKeskustelu(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }

    public Kayttaja getLahettaja() {
        return lahettaja;
    }

    public void setLahettaja(Kayttaja lahettaja) {
        this.lahettaja = lahettaja;
    }

    public Viesti getVastaus() {
        return vastaus;
    }

    public void setVastaus(Viesti vastaus) {
        this.vastaus = vastaus;
    }
    
    
}

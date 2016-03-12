/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi;

import Webfoorumi.DAO.AlueDAO;
import Webfoorumi.DAO.KayttajaDAO;
import Webfoorumi.DAO.KeskusteluDAO;
import Webfoorumi.DAO.ViestiDAO;
import Webfoorumi.Database.Database;
import Webfoorumi.Dom.Keskustelu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.*;

/**
 *
 * @author Aleksi
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:testifoorumi.db");
        database.setDebugMode(true);
        AlueDAO aluedao = new AlueDAO(database);
        KayttajaDAO kayttajadao = new KayttajaDAO(database);
        KeskusteluDAO keskusteludao = new KeskusteluDAO(database, kayttajadao, aluedao);
        ViestiDAO viestidao = new ViestiDAO(database, kayttajadao, keskusteludao);

        //todo ääkköset ei jostain syystä toimi 
        //todo tarvitaan jotain jolla saadaan vika viesti viestit yht ym.
        get("/", (req, res) -> {

            HashMap map = new HashMap<>();
            map.put("alueet", aluedao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        //todo parempi alueen lisäys?
        //todo alueen poisto?
        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            database.update("INSERT INTO Alue(nimi) VALUES(?)", nimi);
            res.redirect("/");
            return null;
        });

        // todo: etsi ainoastaan tietyn alueen keskustelut
        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", aluedao.findOne(Integer.parseInt(req.params("id"))));
            map.put("keskustelut", keskusteludao.alueenKeskustelut(Integer.parseInt(req.params("id"))));
            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        //todo uuden keskustelun lisäys(luomiselle oma sivu?)
        post("/alue/:id", (req, res) -> {
            String otsikko = req.queryParams("otsikko");
            String viesti = req.queryParams("viesti");
            String nimi = req.queryParams("nimi");

            database.update("INSERT INTO Kayttaja(nimi) VALUES(?)", nimi);
            Connection c = database.getConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM Kayttaja WHERE id = (SELECT MAX(id) FROM Kayttaja)");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int kayttaja = rs.getInt("id");
            c.close();
            rs.close();

            database.update("INSERT INTO Keskustelu(nimi,alue_id,aloittaja_id) VALUES(?,?,?)", otsikko, Integer.parseInt(req.params("id")), kayttaja);
            c = database.getConnection();
            stmt = c.prepareStatement("SELECT * FROM Keskustelu WHERE id = (SELECT MAX(id) FROM Keskustelu)");
            rs = stmt.executeQuery();
            rs.next();
            int k_id = rs.getInt("id");
            c.close();
            rs.close();

            database.update("INSERT INTO Viesti(sisalto, keskustelu_id, lahettaja_id, vastaus_id) VALUES(?,?,?,?)", viesti, k_id, kayttaja, null);

            res.redirect("" + Integer.parseInt(req.params("id")));
            return null;
        });
        //todo näytä keskustelun viestit
        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelu", keskusteludao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestit", viestidao.keskustelunViestit(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
    }

}

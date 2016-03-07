/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi;

import Webfoorumi.DAO.AlueDAO;
import Webfoorumi.DAO.KayttajaDAO;
import Webfoorumi.DAO.KeskusteluDAO;
import Webfoorumi.Database.Database;
import Webfoorumi.Dom.Keskustelu;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.*;

/**
 *
 * @author Aleksi
 */
public class Main  {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:testifoorumi.db");
        database.setDebugMode(true);
        AlueDAO aluedao = new AlueDAO(database);
        KayttajaDAO kayttajadao= new KayttajaDAO(database);
        KeskusteluDAO keskusteludao = new KeskusteluDAO(database,kayttajadao,aluedao);
        
        //todo tarvitaan jotain jolla saadaan vika viesti viestit yht ym.
        get("/", (req, res) -> {

            HashMap map = new HashMap<>();
            map.put("alueet", aluedao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        //todo parempi alueen lisäys?
        //todo alueen poisto?
        post("/", (req, res)-> {
            String nimi = req.queryParams("nimi");
            database.update("INSERT INTO Alue(nimi) VALUES(?)", nimi); 
            res.redirect("/");
            return null;
        });
        // todo: etsi ainoastaan tietyn alueen keskustelut
        get("/alue/:id", (req,res) -> {
           HashMap map = new HashMap<>(); 
           map.put("alue", aluedao.findOne(Integer.parseInt(req.params("id"))));
           map.put("keskustelut", keskusteludao.findAll() );
           return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        //todo uuden keskustelun lisäys(luomiselle oma sivu?)
        
        //todo näytä keskustelun viestit
    }

}

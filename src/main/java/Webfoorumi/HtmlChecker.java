/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webfoorumi;

/**
 *
 * @author Aleksi
 */
public class HtmlChecker {
    public String stripHtml(String s){
        String stripped = s.replaceAll("\\<.*?\\>", "");
        return stripped;
    }
}

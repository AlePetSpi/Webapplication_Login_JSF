/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.LA_133_9960_Login;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.jdom2.JDOMException;

/**
 *
 * @author Alexander
 */
@Named(value = "login")
@SessionScoped
public class LoginUserBean implements Serializable {

    
    private final String path1 = "/secured/welcome.xhtml";
    private String inputuser;
    private String inputpassword;
    private boolean loggedIn = false;
    private String doLogout = "";
    private User user;
    private List<Eintrag> data;
    LoginDAO ldao = new LoginDAO(); 

    /**
     * Creates a new instance of LoginUserBean
     */
    public LoginUserBean() {
    }

    public String getInputuser() {
        return inputuser;
    }

    public void setInputuser(String inputuser) {
        this.inputuser = inputuser;
    }

    public String getInputpassword() {
        return inputpassword;
    }

    public void setInputpassword(String inputpassword) {
        this.inputpassword = inputpassword;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String DoLogout() {
        this.loggedIn = false;
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        //Controller erzeugen
        LoginUserBean login = new LoginUserBean();
        //vorherige Daten (notwendige) übertragen …
        //Session anlegen und Zugriffsname (hier log) festlegen
        externalContext.getSessionMap().put("log", login);
        doLogout = "/faces/start.xhtml";
        return doLogout;
    }

    public String getPath1() {
        this.user = ldao.check(inputuser, inputpassword);
        
        if (this.user != null) {
            this.loggedIn = true;
            return path1;
        }
        return "/faces/start.xhtml";   
    }

    public List<Eintrag> getData() throws JDOMException, IOException {
        return ldao.getData();   
    }
    
    public void eintagen() throws IOException{
        ldao.setEintrag();
    }
        
    

}

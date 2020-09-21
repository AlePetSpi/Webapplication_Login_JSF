/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.LA_133_9960_Login;

/**
 *
 * @author Alexander
 */
public class Input {
    private int ID;
    private String name;
    private String DatumUhrzeit;
    private String nachrich;
    private String plz;
    
    public Input(int ID, String name, String DatumUhrzeit, String nachrich, String plz) {
        this.ID = ID;
        this.name = name;
        this.DatumUhrzeit = DatumUhrzeit;
        this.nachrich = nachrich;
    }

    public int getID() {
        return ID;
    }

    public String getPlz() {
        return plz;
    }
    
    public String getName() {
        return name;
    }

    public String getDatumUhrzeit() {
        return DatumUhrzeit;
    }

    public String getNachrich() {
        return nachrich;
    }

    @Override
    public String toString(){
    return ID+name+nachrich+DatumUhrzeit;
    }
    
    
}

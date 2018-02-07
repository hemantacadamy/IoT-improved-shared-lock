package com.example.hemant.sharedlock;

/**
 * Created by Hemant on 26-03-2017.
 */

public class Cred {

    String id;
    String pass;
    String type;

    public Cred(String id,String pass,String type){
        this.id=id;
        this.pass=pass;
        this.type=type;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }
    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}

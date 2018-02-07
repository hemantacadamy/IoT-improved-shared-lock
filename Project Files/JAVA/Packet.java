package com.example.hemant.sharedlock;

/**
 * Created by Hemant on 17-03-2017.
 */
public class Packet {

    String id;
    String x;
    String y;

    public Packet(String id, String x, String y){
        this.id=id;
        this.x=x;
        this.y=y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

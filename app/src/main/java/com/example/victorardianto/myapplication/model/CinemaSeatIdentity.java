package com.example.victorardianto.myapplication.model;

/**
 * Created by victorardianto on 30/01/18.
 */

public class CinemaSeatIdentity {

    String number;
    int position;

    public CinemaSeatIdentity(String number, int position) {
        this.number = number;
        this.position = position;
    }

    public String getNumber() {
        return number;
    }

    public int getPosition() {
        return position;
    }

}

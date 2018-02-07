package com.example.victorardianto.myapplication.model;

import com.example.victorardianto.myapplication.R;

/**
 * Created by victorardianto on 30/01/18.
 */

public class CinemaSeat {
    CinemaSeatIdentity identity;

    public CinemaSeatIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(CinemaSeatIdentity identity) {
        this.identity = identity;
    }

    public int getTextColor() {
        return R.color.red;
    }

    public int getFillColor() {
        return R.color.yellow;
    }

}

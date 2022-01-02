package main.classes;

import java.io.Serializable;

public class SerializeXYR implements Serializable{
    private static final long serialVersionUID = 1L;

    private Double x;
    private Double y;
    private Double r;
    private Boolean res;

    public SerializeXYR(Double x, Double y, Double r, Boolean res) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = res;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getRes() {
        return res;
    }

    public void setRes(Boolean res) {
        this.res = res;
    }
}
package com.example.usandoacelerometro;

public class Acelerometro {

    private float eixoX;
    private float eixoY;
    private float eixoZ;

    public Acelerometro(float eixoX, float eixoY, float eixoZ) {
        this.eixoX = eixoX;
        this.eixoY = eixoY;
        this.eixoZ = eixoZ;
    }

    public Acelerometro() {
    }

    public float getEixoX() {
        return eixoX;
    }

    public void setEixoX(float eixoX) {
        this.eixoX = eixoX;
    }

    public float getEixoY() {
        return eixoY;
    }

    public void setEixoY(float eixoY) {
        this.eixoY = eixoY;
    }

    public float getEixoZ() {
        return eixoZ;
    }

    public void setEixoZ(float eixoZ) {
        this.eixoZ = eixoZ;
    }
}

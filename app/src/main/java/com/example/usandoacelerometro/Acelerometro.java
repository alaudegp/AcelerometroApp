package com.example.usandoacelerometro;

public class Acelerometro {

    private float eixoY;

    public Acelerometro(float eixoY) {
        this.eixoY = eixoY;
    }

    public Acelerometro() {
    }

    public float getEixoY() {
        return eixoY;
    }

    public void setEixoY(float eixoY) {
        this.eixoY = eixoY;
    }
    @Override
    public String toString() {
        return "Acelerometro{" +
                ", eixoY=" + eixoY +
                '}';
    }
}

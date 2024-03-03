package app.virus_simulation.impl;


import app.virus_simulation.inter.IVector;

import java.io.Serializable;

public class Vector2D implements IVector, Serializable {

    private double x;
    private double y;

    public Vector2D() {};

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(IVector other) {
        double[] tmp = other.getComponents();
        this.x = tmp[0];
        this.y = tmp[1];
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double distance(IVector vector) {
        double[] cord = vector.getComponents();
        return Math.sqrt((x - cord[0])*(x - cord[0]) + (y - cord[1])*(y - cord[1]));
    }

    @Override
    public double abs() {
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public IVector getVecDiff(IVector vector) {
        double[] tmp = vector.getComponents();
        return new Vector2D(tmp[0] - x, tmp[1] - y);
    }

    @Override
    public double[] getComponents() {
        return new double[]{x, y};
    }

    @Override
    public String toString() {
        return "X: " + x + "\nY: " + y + "\n";
    }


}

package com.zetcode;

public class Headpos {
    private int hx;
    private int hy;

    public Headpos(int x, int y) {
        this.hx = x;
        this.hy = y;
    }

    public void updatePos(int x, int y) {
        this.hx = x;
        this.hy = y;
    }

    public String getPos() {
        int[] pos = new int[2];
        pos[0] = this.hx;
        pos[1] = this.hy;

        return pos[0] + "," + pos[1];
    }

    public void printPos() {
        System.out.println(this.hx + "," + this.hy);
    }
}

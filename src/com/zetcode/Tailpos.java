package com.zetcode;

public class Tailpos {

    private int tx;
    private int ty;
    

    public Tailpos(int x, int y) {
        this.tx = x;
        this.ty = y;
    }

    public void updatePos(int x, int y) {
        this.tx = x;
        this.ty = y;
    }

    public String getPos() {
        int[] pos = new int[2];
        pos[0] = this.tx;
        pos[1] = this.ty;

        return pos[0] + "," + pos[1];
    }

    public void printPos() {
        System.out.println(this.tx + "," + this.ty);
    }
}

package com.yaving.yavingamap.model;

public class Position {
    int left;
    int top;

    public Position() {
    }

    public Position(int left, int top) {
        this.left = left;
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}

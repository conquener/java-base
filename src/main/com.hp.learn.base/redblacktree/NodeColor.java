package com.hp.learn.base.redblacktree;

public enum NodeColor {

    /**
     *
     */
    red(1,"red"),
    black(2,"black");

    int color;
    String desc;

    NodeColor(int color, String desc) {
        this.color = color;
        this.desc = desc;
    }

}

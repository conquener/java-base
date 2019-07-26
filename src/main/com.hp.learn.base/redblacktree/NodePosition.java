package com.hp.learn.base.redblacktree;

public enum NodePosition {
    /**
     *
     */
    left(1,"left node"),
    right(1,"right node");

    int position;
    String desc;

    NodePosition(int position, String desc) {
        this.position = position;
        this.desc = desc;
    }
}

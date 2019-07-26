package com.hp.learn.base.redblacktree;

public enum NodeType {
    /**
     *
     */
    root(0,"root node"),
    leaf(1,"leaf node"),
    normal(2,"normal node");

    int type;
    String desc;

    NodeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

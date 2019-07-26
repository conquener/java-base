package com.hp.learn.base.redblacktree;

/**
 * 设定一个节点 属性
 *
 * @author ritchey
 * @version v1.0
 * Description:
 * @date 2019/7/23 15:36
 */
public class RedBlackNode {

    /**
     * true 为红色 ，false 为 黑色
     * 对于新加入的节点 默认为红色，先插入之后再进行修复
     */
    NodeColor color = NodeColor.red;
    /**
     * 节点值 设定成 int
     */
    Integer value;

    /**
     * 父节点
     */
    RedBlackNode parentNode = null;
    /**
     * 左子节点
     */
    RedBlackNode leftNode = null;
    /**
     * 右子节点
     */
    RedBlackNode rightNode = null;

    /**
     * 0 为root 节点  1 为 leaf 节点  2为普通节点
     * 新插入的节点 默认先放到叶子上 最后进行树修复
     */
    NodeType type = NodeType.normal;
    /**
     * 节点所处位置 左右
     */
    NodePosition position = null;

    @Override
    public String toString() {
        return "{color=" + color + ", value=" + value + ", type=" + type + ", position=" + position + ",parentNode=" + (parentNode == null ? 0 : parentNode.value) + ",leftNode=" + (leftNode == null ? 0 : leftNode.value) + ",rightNode=" + (rightNode == null ? 0 : rightNode.value) +"}";
    }

    public String toSimpleString() {
        return "["+color.desc+","+ value +"," + position + "," + (parentNode == null ? 0 : parentNode.value) + "," + (leftNode == null ? 0 : leftNode.value) + "," + (rightNode == null ? 0 : rightNode.value) +"]";
    }
}

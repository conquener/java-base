package com.hp.learn.base.redblacktree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * declare
 *
 * @author ritchey
 * @version v1.0
 * Description:
 * @date 2019/7/23 15:35
 */
public class RedBlackTree {
    /**
     * 这作为根节点
     */
    volatile RedBlackNode root = null;


    void insertNode(int value) {

        if (root == null) {
            //此时第一个插入的值 即为根节点
            root = new RedBlackNode();
            root.color = NodeColor.black;
            root.type = NodeType.root;
            root.value = value;
        } else {
            //执行插入
            RedBlackNode newNode = insertValue(value);

            // 红黑树 树形修复
            repairTree(newNode);
        }
    }

    synchronized RedBlackNode insertValue(int value) {
        RedBlackNode node = getPosition(root, value);
        return node;
    }

    RedBlackNode getPosition(RedBlackNode node, int value) {
        RedBlackNode newNode;
        if (value < node.value) {
            //小于放左边
            if (node.leftNode == null) {
                newNode = new RedBlackNode();
                newNode.parentNode = node;
                newNode.position = NodePosition.left;
                newNode.value = value;
                node.leftNode = newNode;
                return newNode;
            } else {
                return getPosition(node.leftNode, value);
            }
        } else {
            //大于等于放右边
            if (node.rightNode == null) {
                newNode = new RedBlackNode();
                newNode.parentNode = node;
                newNode.position = NodePosition.right;
                newNode.value = value;
                node.rightNode = newNode;
                return newNode;
            } else {
                return getPosition(node.rightNode, value);
            }
        }
    }


    void repairTree(RedBlackNode node) {
        //1 n 为根节点
        if (node.type.equals(NodeType.root)) {
            node.color = NodeColor.black;
            return;
        }
        // 2 父节点为黑色 直接结束
        if (node.parentNode.color.equals(NodeColor.black)) {
            return;
        }

        if (node.parentNode.color.equals(NodeColor.red)) {
            RedBlackNode neigh = getNeighbor(node.parentNode);
            // 叔节点 也为红色
            if (neigh != null && neigh.color.equals(NodeColor.red)) {
                // 3. 如果 父节点 和 叔节点 都是红色 将父节点和叔节点 全部置为黑色  祖父节点置为 红色 然后祖父节点继续递归
                node.parentNode.color = NodeColor.black;
                neigh.color = NodeColor.black;
                RedBlackNode grandParent = getGrandParent(node);
                grandParent.color = NodeColor.red;
                repairTree(grandParent);
            } else {
                // 父节点是红色 叔节点是黑色 需要进行旋转
                if (node.position.equals(NodePosition.right) && node.parentNode.position.equals(NodePosition.left)) {
                    //父节点左旋
                    leftRotate(node.parentNode);
                    //将node 置为 当前父节点
                    node = node.leftNode;
                } else if (node.position.equals(NodePosition.left) && node.parentNode.position.equals(NodePosition.right)) {
                    //父节点右旋
                    rightRotate(node.parentNode);
                    node = node.rightNode;
                }
                //经过了上面的判断，能执行到这一步的 都是满足条件的
                step2(node);
            }
        }
    }


    /**
     * 节点和父节点都在一个方向
     * @param node
     */
    void step2(RedBlackNode node) {

        RedBlackNode p = node.parentNode;
        RedBlackNode g = node.parentNode.parentNode;

        if (node.position.equals(NodePosition.left)) {
            //将node 进行右旋
            rightRotate(g);
        } else {
            leftRotate(g);
        }
        p.color = NodeColor.black;
        g.color = NodeColor.red;
    }

    /**
     * 获取相邻节点
     */
    RedBlackNode getNeighbor(RedBlackNode node) {
        if (node.parentNode != null) {
            if (node.position.equals(NodePosition.left)) {
                return node.parentNode.rightNode;
            } else {
                return node.parentNode.leftNode;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取祖父节点
     *
     * @param node
     * @return
     */
    RedBlackNode getGrandParent(RedBlackNode node) {
        if (node.parentNode != null) {

            return node.parentNode.parentNode;
        }
        return null;
    }

    /**
     * 为了方便撸代码，传入的参数均是父节点
     * 当前节点的 右子节点  左旋转
     * 旋转不变更颜色
     */
    void leftRotate(RedBlackNode node) {
        RedBlackNode y = node.rightNode;
        //当前节点没有 右子节点 不能旋转
        assert y == null;

        NodeType change = y.type;
        y.type = node.type;
        node.type = change;

        y.position = node.position;
        node.position = NodePosition.left;

        node.rightNode = y.leftNode;

        y.leftNode = node;
        y.parentNode = node.parentNode;

        if (node.parentNode != null) {
            //如果node 是根节点
            if (y.position.equals(NodePosition.left)) {
                node.parentNode.leftNode = y;
            } else {
                node.parentNode.rightNode = y;
            }
        } else {
            //如果根节点已切换， 那么切换树中的root
            root = y;
        }
        if (node.rightNode != null) {
            node.rightNode.parentNode = node;
            node.rightNode.position = NodePosition.right;
        }
        node.parentNode = y;
    }

    /**
     * 当前节点的左子节点 右旋转
     * 旋转不变更颜色
     */
    void rightRotate(RedBlackNode node) {
        RedBlackNode y = node.leftNode;
        //当前节点没有左子节点
        assert y == null;

        NodeType change = y.type;
        y.type = node.type;
        node.type = change;

        y.position = node.position;
        node.position = NodePosition.right;

        node.leftNode = y.rightNode;
        y.rightNode = node;
        y.parentNode = node.parentNode;

        if (node.parentNode != null) {
            if (y.position.equals(NodePosition.left)) {
                node.parentNode.leftNode = y;
            } else {
                node.parentNode.rightNode = y;
            }
        } else {
            //如果node 是根节点，那么根节点变为y
            root = y;
        }
        if (node.leftNode != null) {
            node.leftNode.parentNode = node;
            node.leftNode.position = NodePosition.left;
        }
        node.parentNode = y;
    }

    /**
     * 删除节点
     *
     * @param node
     */
    void deleteNode(RedBlackNode node) {
        int n = 0;
        n += node.rightNode == null ? 0 : 1;
        n += node.leftNode == null ? 0 : 1;

        // 有两个子节点 不论这个节点 是红色 或者 黑色
        if (n == 2) {
            //一般来说 用左子树的最大数据 或者 右子树的做小数据代替 这样就行成了删除
            node.value = getLeftMaxValue(node);
        }

        //节点为红色 并且没有子节点
        if (node.color.equals(NodeColor.red) && n == 0) {
            condition1(node);
        }
        //节点 为黑色 并且没有子节点
        if (node.color.equals(NodeColor.black) && n == 0) {
            condition2(node, 1);
        }
        //节点为 黑色 并且 有一个的子节点  那么 这个子节点必定为红色
        if (node.color.equals(NodeColor.black) && n == 1) {
            condition3(node);
        }


    }


    /**
     * condition 1 节点为红色 ，并且没有子节点 不可能为根节点
     *
     * @param node
     */
    void condition1(RedBlackNode node) {
        //直接移除
        if (node.parentNode == null || node.type.equals(NodeType.root)) {
            root = null;
        } else {
            if (node.position.equals(NodePosition.left)) {
                node.parentNode.leftNode = null;
            } else {
                node.parentNode.rightNode = null;
            }
        }

    }

   public  static AtomicInteger count = new AtomicInteger(0);

    /**
     * condition 2 节点为黑色 ，并且没有子节点
     *
     * @param node
     * @param operator 1.删除  2，修复
     */
    void condition2(RedBlackNode node, int operator) {
        if (node.parentNode != null) {
            //节点为黑色 ，那么兄弟节点必定不为空
            RedBlackNode brother = getNeighbor(node);

            if (brother.color.equals(NodeColor.red)) {
                //3.2.1节点的兄弟节点为红色
                node.parentNode.color = NodeColor.red;
                brother.color = NodeColor.black;
                if (node.position.equals(NodePosition.left)) {
                    leftRotate(node.parentNode);
                } else {
                    rightRotate(node.parentNode);
                }
                condition2(node, operator);
            } else {
                //  在当前节点 无子节点的情况下 兄弟节点 如果有黑色的子节点 ，只有可能是null节点
                if (node.position.equals(NodePosition.left)) {
                    if (getColor(brother.rightNode).equals(NodeColor.red)) {
                        //3.2.2.1 节点的兄弟节点为黑色 并且 节点是左节点 兄弟节点的右子节点为红色
                        NodeColor color = brother.color;
                        brother.color = node.parentNode.color;
                        node.parentNode.color = color;

                        brother.rightNode.color = NodeColor.black;
                        leftRotate(node.parentNode);
                        if (operator == 1) {
                            removeNode(node, null);
                        }
                        return;
                    } else if (getColor(brother.leftNode).equals(NodeColor.red)) {
                        //3.2.2.2 节点的兄弟节点为黑色 并且 节点是左节点 兄弟节点的右子节点为黑色 并且 左子节点为红色
                        brother.color = NodeColor.red;
                        brother.leftNode.color = NodeColor.black;
                        rightRotate(brother);
                        //递归调用 这时候 会回归到3.2.2.2的情况
                        condition2(node, operator);
                        return;
                    }

                } else {
                    if (getColor(brother.leftNode).equals(NodeColor.red)) {
                        //3.2.2.1节点的兄弟节点为黑色 并且 节点是右节点 兄弟节点的左子节点为红色
                        NodeColor color = brother.color;
                        brother.color = node.parentNode.color;
                        node.parentNode.color = color;

                        brother.leftNode.color = NodeColor.black;
                        rightRotate(node.parentNode);
                        if (operator == 1) {
                            removeNode(node, null);
                        }
                        return;
                    } else if (getColor(brother.rightNode).equals(NodeColor.red)) {
                        brother.color = NodeColor.red;
                        brother.rightNode.color = NodeColor.black;
                        leftRotate(brother);
                        //递归调用 这时候 会回归到3.2.2.1的情况
                        condition2(node, operator);
                        return;
                    }
                }

                if (getColor(node.parentNode).equals(NodeColor.red)) {
                    //如果 父节点为红色 ，兄弟节点和 侄子节点 均为黑色  这里无需再判断兄弟节点，能执行到这里 说明全为黑色
                    //直接将 兄弟节点 置为红色即可，父节点置黑
                    brother.color = NodeColor.red;
                    node.parentNode.color = NodeColor.black;
                    if (operator == 1) {
                        removeNode(node, null);
                    }

                    return;
                } else {
                    //父节点 为黑色 将兄弟节点改为红色 然后执行修复
                    brother.color = NodeColor.red;
                    if (operator == 1) {
                        removeNode(node, null);
                    }
                    condition2(brother.parentNode, 2);
                    return;
                }
            }
        } else {
            //如果node 是根节点 ，并且 没有子节点 ，那么直接移除即可
            if(operator == 1){
                root = null;
            }


        }
    }

    /**
     * condition 3 节点为黑色 ，并且只有一个子节点
     *
     * @param node
     */
    void condition3(RedBlackNode node) {

        //单子节点的情况下 这个子节点必定是红色 否则是违背红黑数特性的 所以需要调整颜色
        //这种情况下  直接将该节点 替换成子节点即可
        RedBlackNode replaceNode;
        if (node.leftNode != null) {
            replaceNode = node.leftNode;
        } else {
            replaceNode = node.rightNode;
        }
        removeNode(node, replaceNode);
    }

    /**
     * 移除节点 父节点中的子节点关系 装换成 替换的节点即可
     *
     * @param node
     */
    void removeNode(RedBlackNode node, RedBlackNode replaceNode) {
        if (replaceNode != null) {
            replaceNode.color = node.color;
            replaceNode.parentNode = node.parentNode;
            replaceNode.position = node.position;
            replaceNode.type = node.type;
        }

        if (node.parentNode != null) {
            if (node.position.equals(NodePosition.left)) {
                node.parentNode.leftNode = replaceNode;
            } else {
                node.parentNode.rightNode = replaceNode;
            }
        } else {
            //root 节点
            root = replaceNode;
        }
    }

    List<RedBlackNode> queryNode(int value) {
        List<RedBlackNode> nodes = new ArrayList<>();
        recursionQuery(root, nodes, value);
        return nodes;
    }


    void recursionQuery(RedBlackNode node, List<RedBlackNode> nodes, int value) {
        if (node.value > value) {
            if (node.leftNode != null) {
                recursionQuery(node.leftNode, nodes, value);
            } else {
                return;
            }
        } else if (node.value < value) {
            if (node.rightNode != null) {
                recursionQuery(node.rightNode, nodes, value);
            } else {
                return;
            }
        } else {
            //如果是等于 那么 两边都要递归查询
            nodes.add(node);
            if (node.rightNode != null) {
                recursionQuery(node.rightNode, nodes, value);
            }
            if (node.leftNode != null) {
                recursionQuery(node.leftNode, nodes, value);
            }
            return;
        }
    }

    NodeColor getColor(RedBlackNode node) {
        if (node == null) {
            return NodeColor.black;
        } else {
            return node.color;
        }
    }

    Integer getLeftMaxValue(RedBlackNode node) {
        if (node.leftNode != null) {
            return getMaxValue(node.leftNode);
        } else {
            return null;
        }
    }

    Integer getRightMinValue(RedBlackNode node) {
        if (node.rightNode != null) {
            return getMinValue(node.rightNode);
        } else {
            return null;
        }
    }

    Integer getMaxValue(RedBlackNode node) {
        if (node != null) {
            if (node.rightNode != null) {
                return getMinValue(node.rightNode);
            } else {
                return node.value;
            }
        } else {
            return null;
        }
    }

    Integer getMinValue(RedBlackNode node) {
        if (node != null) {
            if (node.leftNode != null) {
                return getMinValue(node.leftNode);
            } else {
                return node.value;
            }

        } else {
            return null;
        }
    }


}

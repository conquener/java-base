package com.hp.learn.base.redblacktree;

import com.sun.deploy.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * declare
 *
 * @author ritchey
 * @version v1.0
 * Description:
 * @date 2019/7/23 16:41
 */
public class Treemain {

    public static void main(String[] args) {

        RedBlackTree tree = new RedBlackTree();
        insertData(tree);

        //先序遍历二叉树
        travesty(tree.root);
        //输出先序遍历 检查二叉树
        System.out.println(s.toString());
        printTree(tree.root, 1);

        int height = (treeMap.size() + 1) * 100;
        int width = treeMap.size() * 200;

        for (Integer i = 1; i <= treeMap.keySet().size(); i++) {

            //System.out.println(StringUtils.join(treeMap.get(i), "||"));
        }

        BufferedImage image = drawTree(tree.root, height, width);

        try {
            OutputStream os = new FileOutputStream("C:\\Users\\acer\\Desktop\\image\\test.png");
            ImageIO.write(image, "JPEG", os);

        } catch (IOException e) {
            e.printStackTrace();
        }
        treeMap.clear();
    }

    static StringBuffer s = new StringBuffer();

    static void travesty(RedBlackNode node) {

        if (node.leftNode != null) {
            travesty(node.leftNode);
        }

        s.append(node.value).append("->");

        if (node.rightNode != null) {
            travesty(node.rightNode);
        }
    }

    static void drawTreeSelf(RedBlackNode root) {
        printTree(root, 1);

        int height = (treeMap.size() + 1) * 100;
        int width = (treeMap.size() + 1) * 200;
        DateFormat df = new SimpleDateFormat("HHmmssSSS");


        for (Integer i = 1; i <= treeMap.keySet().size(); i++) {

            //System.out.println(StringUtils.join(treeMap.get(i), "||"));
        }

        BufferedImage image = drawTree(root, height, width);

        try {
            OutputStream os = new FileOutputStream("C:\\Users\\acer\\Desktop\\image\\drawSelf--" + df.format(new Date()) + ".png");
            ImageIO.write(image, "JPEG", os);

        } catch (IOException e) {
            e.printStackTrace();
        }
        treeMap.clear();
    }


    volatile static Map<Integer, List<String>> treeMap = new HashMap<>();

    static void printTree(RedBlackNode node, Integer hierarchy) {

        if (node != null) {
            if (node.leftNode != null) {
                printTree(node.leftNode, hierarchy + 1);
            }
            if (node.rightNode != null) {
                printTree(node.rightNode, hierarchy + 1);
            }

            List<String> list = treeMap.get(hierarchy);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(node.toString());
            treeMap.put(hierarchy, list);
        }
    }


    static BufferedImage drawTree(RedBlackNode root, int height, int width) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);


        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 0, height);
        g.drawLine(0, 0, width, 0);
        g.drawLine(width, height, 0, height);
        g.drawLine(width, height, width, 0);

        drawNode(root, 0, width / 2, 50, image);
        return image;
    }

    static void drawNode(RedBlackNode node, int upx, int x, int y, BufferedImage image) {
        int distance;
        if (upx > x) {
            distance = (upx - x) / 2;
        } else {
            distance = (x - upx) / 2;
        }

        Graphics2D g = (Graphics2D) image.getGraphics();
        if (node.color.color == 1) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        Shape circular = new Ellipse2D.Double(x, y, 25, 25);
        g.fill(circular);
        g.drawString(node.value + (node.position == null ? "" : (node.position.equals(NodePosition.left) ? "L" : "R")), x, y);

        g.setColor(Color.BLACK);

        if (node.leftNode != null) {
            g.drawLine(x, y, x - distance, y + 50);
            drawNode(node.leftNode, x, x - distance, y + 50, image);
        }
        if (node.rightNode != null) {
            g.drawLine(x, y, x + distance, y + 50);
            drawNode(node.rightNode, x, x + distance, y + 50, image);
        }
    }

    static Integer insertData(RedBlackTree tree) {
//        tree.insertNode(40);
//        tree.insertNode(58);
//        tree.insertNode(61);
//        tree.insertNode(80);
//        tree.insertNode(31);
//        tree.insertNode(20);
//        tree.insertNode(42);
//        tree.insertNode(53);
//        tree.insertNode(60);
//        tree.insertNode(99);
//        tree.insertNode(41);
//        tree.insertNode(13);
//        tree.insertNode(30);
//        tree.insertNode(53);
//        tree.insertNode(34);
//        tree.insertNode(94);
//        tree.insertNode(88);
//        tree.insertNode(79);
//        tree.insertNode(50);
//        tree.insertNode(3);
//        tree.insertNode(21);
//        tree.insertNode(60);
//        tree.insertNode(10);
//        tree.insertNode(91);
//        tree.insertNode(22);
//        tree.insertNode(45);
//        tree.insertNode(41);
//        tree.insertNode(86);
//        tree.insertNode(35);
//        tree.insertNode(99);
//        tree.insertNode(94);
//        tree.insertNode(45);
//        tree.insertNode(83);
//        tree.insertNode(85);
//        tree.insertNode(56);
//        tree.insertNode(27);
//        tree.insertNode(96);
//        tree.insertNode(46);
//        tree.insertNode(9);
//        tree.insertNode(5);
//        tree.insertNode(7);
//        tree.insertNode(76);
//        tree.insertNode(70);
//        tree.insertNode(59);
//        tree.insertNode(9);
//        tree.insertNode(24);
//        tree.insertNode(19);
//        tree.insertNode(64);
//        tree.insertNode(60);
//        tree.insertNode(32);
//        tree.insertNode(82);
//        tree.insertNode(16);
//        tree.insertNode(73);
//        tree.insertNode(81);
//        tree.insertNode(90);
//        tree.insertNode(97);
//        tree.insertNode(60);
//        tree.insertNode(0);
//        tree.insertNode(63);
//        tree.insertNode(69);
//        tree.insertNode(71);
//        tree.insertNode(41);
//        tree.insertNode(42);
//        tree.insertNode(26);
//        tree.insertNode(39);
//        tree.insertNode(70);
//        tree.insertNode(24);
//        tree.insertNode(43);
//        tree.insertNode(38);
//        tree.insertNode(61);
//        tree.insertNode(21);
//        tree.insertNode(24);
//        tree.insertNode(81);
//        tree.insertNode(15);
//        tree.insertNode(67);
//        tree.insertNode(84);
//        tree.insertNode(89);
//        tree.insertNode(46);
//        tree.insertNode(20);
//        tree.insertNode(22);
//        tree.insertNode(16);
//        tree.insertNode(65);
//        tree.insertNode(45);
//        tree.insertNode(79);
//        tree.insertNode(73);
//        tree.insertNode(29);
//        tree.insertNode(16);
//        tree.insertNode(54);
//        tree.insertNode(54);
//        tree.insertNode(79);
//        tree.insertNode(29);
//        tree.insertNode(67);
//        tree.insertNode(74);
//        tree.insertNode(2);
//        tree.insertNode(18);
//        tree.insertNode(64);
//        tree.insertNode(92);
//        tree.insertNode(84);
//        tree.insertNode(57);
//        tree.insertNode(88);
//
//        tree.deleteNode(tree.queryNode(42).get(1));
//        List<RedBlackNode> nodes = tree.queryNode(45);
//        tree.deleteNode(nodes.get(1));
//
//        tree.deleteNode(nodes.get(2));
//
//
//        tree.deleteNode(tree.queryNode(43).get(0));

        for (int j = 1; j <= 32; j++) {
            //int value = Double.valueOf((Math.random() * 100)).intValue();
            tree.insertNode(j);
            System.out.println("tree.insertNode(" + j + ");");
        }

        drawTreeSelf(tree.root);

        tree.deleteNode(tree.queryNode(3).get(0));


        return null;
    }

}
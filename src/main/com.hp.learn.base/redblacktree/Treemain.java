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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
        System.out.println("先序（前序）遍历二叉树：{" + s.toString() + "leaf==null}");
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

    static AtomicInteger count = new AtomicInteger(0);
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
        Shape circular = new Ellipse2D.Double(x - 10, y - 10, 25, 25);
        g.fill(circular);
        g.drawString(node.value + (node.position == null ? "" : (node.position.equals(NodePosition.left) ? "L" : "R")), x-10, y-10);

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

        for (int j = 0; j <= 105; j++) {
            int value = Double.valueOf((Math.random() * 100)).intValue();
            tree.insertNode(value);
            //System.out.println("tree.insertNode(" + j + ");");
        }

        return null;
    }

}
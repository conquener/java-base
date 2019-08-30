package com.hp.learn.base;

public class JvmTest {

    public static void main(String[] args) {

        //使用 javap -c ?.class > test.txt 命令 获取 class字节码文件的反编译文件
        int a = 103831;
        int b =123242412;
        int c = JvmTest.computer(a,b);

        JvmTest jt = new JvmTest();
        int d = jt.compter2(a,b);
        String s = "abcdefg";

    }

    public static int computer(int a ,int b){

        return a *= b+2;
    }

    public int compter2(int a,int b){

        return a+b;
    }


}

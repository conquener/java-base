package com.hp.learn.base;

import com.hp.learn.base.map.NewHashMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class BaseTest{

    @Test
    public void test1(){
        System.out.println(Math.round(-15.61));
    }



}

class Base {
    private void amethod(int iBase) {
        System.out.println("Base.amethod");
    }
}
class Over extends Base {
    public static void main(String args[]) {
        Over o = new Over();
        int iBase = 0 ;
        o.amethod(iBase) ;
    }
    public void amethod(int iOver) {
        System.out.println("Over.amethod");
    }
}



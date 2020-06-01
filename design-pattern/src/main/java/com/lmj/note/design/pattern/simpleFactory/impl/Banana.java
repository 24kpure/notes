package com.lmj.note.design.pattern.simpleFactory.impl;

import com.lmj.note.design.pattern.simpleFactory.Fruit;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:41 下午 2020/3/2
 **/
public class Banana implements Fruit {
    @Override
    public void eat() {
        System.out.println("吃吃吃 香蕉");
    }
}
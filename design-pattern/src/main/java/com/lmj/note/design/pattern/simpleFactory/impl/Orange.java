package com.lmj.note.design.pattern.simpleFactory.impl;

import com.lmj.note.design.pattern.simpleFactory.Fruit;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:42 下午 2020/3/2
 **/
public class Orange implements Fruit {
    @Override
    public void eat() {
        System.out.println("吃吃吃 橘子");
    }
}
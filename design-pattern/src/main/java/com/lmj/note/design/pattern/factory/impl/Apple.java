package com.lmj.note.design.pattern.factory.impl;

import com.lmj.note.design.pattern.factory.Fruit;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:52 下午 2020/3/3
 **/
public class Apple implements Fruit {
    @Override
    public void eat() {
        System.out.println("工厂里吃苹果");
    }
}
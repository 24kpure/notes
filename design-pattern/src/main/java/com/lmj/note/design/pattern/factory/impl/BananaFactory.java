package com.lmj.note.design.pattern.factory.impl;

import com.lmj.note.design.pattern.factory.Fruit;
import com.lmj.note.design.pattern.factory.FruitFactory;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:48 下午 2020/3/3
 **/
public class BananaFactory implements FruitFactory {
    @Override
    public Fruit create() {
        return new Banana();
    }
}
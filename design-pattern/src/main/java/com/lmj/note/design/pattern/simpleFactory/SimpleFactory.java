package com.lmj.note.design.pattern.simpleFactory;

import com.lmj.note.design.pattern.simpleFactory.impl.Apple;
import com.lmj.note.design.pattern.simpleFactory.impl.Banana;
import com.lmj.note.design.pattern.simpleFactory.impl.Orange;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:37 下午 2020/3/2
 **/
public class SimpleFactory {

    public static Fruit getFruit(String fruitName) {
        switch (fruitName) {
            case "apple":
                return new Apple();
            case "orange":
                return new Orange();
            case "banana":
                return new Banana();
            default:
                return () -> System.out.println("吃吃吃 没得吃");
        }
    }


    public static void main(String[] args) {
        Fruit fruit = getFruit("apple");
        fruit.eat();

        fruit = getFruit("orange");
        fruit.eat();

        fruit = getFruit("banana");
        fruit.eat();

        fruit = getFruit("watermelon");
        fruit.eat();
    }
}
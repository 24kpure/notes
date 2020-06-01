package com.lmj.note.design.pattern.factory;

import java.util.Properties;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:56 下午 2020/3/3
 **/
public class FactoryTest {
    public static void main(String[] args) throws Exception {
        //加载配置
        Properties properties = new Properties();

        properties.load(FactoryTest.class.getClassLoader().getResourceAsStream("factory.properties"));

        //获取工厂
        FruitFactory factory = (FruitFactory) Class.forName(properties.get("factory").toString()).newInstance();

        //获取产品
        Fruit fruit = factory.create();

        //产品使用
        fruit.eat();
    }
}
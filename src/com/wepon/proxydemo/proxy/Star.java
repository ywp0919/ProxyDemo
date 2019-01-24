package com.wepon.proxydemo.proxy;

import java.io.Serializable;

/**
 * desc: 明星 --- 真实对象
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:15
 */
public class Star implements ISkillAction, ILiveAction, Serializable {


    @Override
    public void sing() {
        System.out.println("明星开始唱歌了。");
    }

    @Override
    public void dance() {
        System.out.println("明星开始跳舞了。");
    }

    @Override
    public void eatBreakfast() {
        System.out.println("明星开始吃早餐了。");
    }
}

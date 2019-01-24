package com.wepon.proxydemo.proxy;

import java.io.Serializable;

/**
 * desc: 明星的经纪人 --- 代理
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:20
 */
public class Agent implements ISkillAction, ILiveAction, Serializable {

    /**
     * 经纪人是需要持有一份目标对象(真实对象---明星)的引用的。
     */
    private Star mStar;

    public Agent(Star star) {
        mStar = star;
    }

    @Override
    public void sing() {
        // 经纪人根据明星的行程计划做好本次的规划。
        System.out.println("经纪人首先要安排好本次行程计划。");
        // 明星唱歌前经纪人要跟活动商做好相关的布置。
        System.out.println("经纪人跟活动商进行唱歌前布置。");
        // 明星开始唱歌。
        mStar.sing();
        // 明星唱完歌后经纪人要做好收尾工作（例如把出演费收到账等等）。
        System.out.println("经纪人跟活动商做收尾工作（例如把出演费收到账等等）。");
        // 本次行程结束，经纪人需要更新行程计划
        System.out.println("本次行程结束，经纪人需要更新行程计划。");
    }

    @Override
    public void dance() {
        // 经纪人根据明星的行程计划做好本次的规划。
        System.out.println("经纪人首先要安排好本次行程计划。");
        // 明星开始跳舞。
        mStar.sing();
        // 本次行程结束，经纪人需要更新行程计划
        System.out.println("本次行程结束，经纪人需要更新行程计划。");
    }

    @Override
    public void eatBreakfast() {
        // 这里就以经纪人进行早餐预订做一个假设吧。
        System.out.println("经纪人帮明星预订早餐。");
        // 明星开始吃早餐了。
        mStar.eatBreakfast();
        // 吃完后经纪人去帮明星结账。
        System.out.println("经纪人去帮明星结账。");
    }
}

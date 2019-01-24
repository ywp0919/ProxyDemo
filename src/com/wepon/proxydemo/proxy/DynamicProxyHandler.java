package com.wepon.proxydemo.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * desc: 创建自己的调用处理器
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午6:52
 */
public class DynamicProxyHandler implements InvocationHandler, Serializable {

    /**
     * 持有一份真实对象的引用
     */
    private Object mObject;

    public DynamicProxyHandler(Object object) {
        mObject = object;
    }


    /**
     *
     * @param proxy 代理类对象
     * @param method 方法
     * @param args 方法参数
     * @return 方法返回
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("代理前的统一操作");
        Object invoke;
        // 这里可以根据不同方法名进行不同的操作
        if ("sing".equals(method.getName())) {
            // 拦截下唱歌
            // 明星唱歌前经纪人要跟活动商做好相关的布置。
            System.out.println("经纪人跟活动商进行唱歌前布置。");
            // 调用真实对象的行为
            invoke = method.invoke(mObject, args);
            // 明星唱完歌后经纪人要做好收尾工作（例如把出演费收到账等等）。
            System.out.println("经纪人跟活动商做收尾工作（例如把出演费收到账等等）。");
        }else{
            // 调用真实对象的行为
            invoke = method.invoke(mObject, args);
        }

        System.out.println("代理后的统一操作");

        return invoke;
    }
}

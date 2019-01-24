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

        // 调用真实对象的行为
        Object invoke = method.invoke(mObject, args);

        System.out.println("代理后的统一操作");

        return invoke;
    }
}

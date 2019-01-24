package com.wepon.proxydemo;

import com.wepon.proxydemo.proxy.Agent;
import com.wepon.proxydemo.proxy.DynamicProxyHandler;
import com.wepon.proxydemo.proxy.ILiveAction;
import com.wepon.proxydemo.proxy.ISkillAction;
import com.wepon.proxydemo.proxy.Star;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;

/**
 * desc: 用于测试
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:57
 */
public class JavaProxyTest {

    public static void main(String[] args) {

        // 使用静态代理模式
//        useStaticProxy();

        // 使用动态代理模式
        useDynamicProxy();

    }

    /**
     * 动态代理模式的使用
     */
    private static void useDynamicProxy() {

        // 设置这个变量为true之后可以把动态生成的代理类.class文件保存下来。
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");


        // 要先有一份真实对象（明星对象）
        Star star = new Star();
        // 然后要有一个自定义的处理器
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(star);
        // 然后通过jdk方式动态生成代理对象
        // 这里是一个Object类型，根据不同行为接口强转
        Object proxy = Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{ISkillAction.class, ILiveAction.class},
                dynamicProxyHandler
        );

        System.out.println("动态生成的代理类的Name："+proxy.getClass().getName());


        // 进行各个行为的代理，这里需要进行不同行为类型的转换
        ((ISkillAction) proxy).sing();
        ((ISkillAction) proxy).dance();

        ((ILiveAction) proxy).eatBreakfast();


        // 自己写代码生成代理类的.class文件保存。
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", star.getClass().getInterfaces());

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./$Proxy0.class");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(bytes);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }

    }

    /**
     * 静态代理模式的使用
     */
    private static void useStaticProxy() {
        // 要先有一份真实对象（明星对象）
        Star star = new Star();
        // 然后代理者要持有这个真实对象
        Agent agent = new Agent(star);
        // 这样代理者就可以进行这些行为的代理了
        // 就好比有些商业活动负责人先找到某明星经纪人，通过经纪人成功邀请到某明星来进行演出。
        agent.sing();
    }
}

# 什么是代理模式
这里我直接引用百度百科里面的一句话。

代理模式的定义：***为其他对象提供一种代理以控制对这个对象的访问。在某些情况下，一个对象不适合或者不能直接引用另一个对象，而代理对象可以在客户端和目标对象之间起到中介的作用。***

这些文字上的东西我也说的不太清楚，在下面我会尽可能使用代码来让大家明白静态代理和动态代理这两种方式的原理！说清楚原理之后我再说下代理模式能帮我们做什么？？

tip：这里我用的IntelliJ IDEA写的java项目来做的示例，有想做示例的同学千万不要用Android项目来写，源代码会有所不同！！！还有接下来文章比较长，希望大家能耐心看完，看完收获是肯定有的。


# 静态代理
这里我使用一个明星和经纪人关系的例子来做一个比较形象的代码说明，希望大家可以看代码就明白其中的关系。

## 静态代理必要的元素
首先，我们要实现一个代理模式，那有三个元素是一定要有的，且这三个元素不管是静态代理还是动态代理都是必须的，这里我先以静态代理为例做下说明。

### 1.行为接口类（接口，定义行为）
这个元素是一个行为接口类，主要是定义各种行为方法，这里我们以一位明星掌握的技能为例，如（某明星现在只有一个唱歌的技能）：
```
/**
 * desc: 定义一些明星拥有的技能行为
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:16
 */
public interface ISkillAction {

    /**
     * 唱歌
     */
    void sing();
}
```

### 2.真实对象（这里以明星为例）
这里我们需要一个真实的对象，主要是提供给代理者进行操作。
```
/**
 * desc: 明星 --- 真实对象
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:15
 */
public class Star implements ISkillAction {

    @Override
    public void sing() {
        System.out.println("明星开始唱歌了。");
    }
}
```

### 3.代理者（这里以经纪人为例）
这里是我们的代理者，也就是明星经纪人，需要负责明星的各种行程规划和安排，那么用编程的思想来说的话，我们肯定是要持有这个"明星"的一份引用的，当然了，现在我们的明星还只会唱歌这一项技能（sing()），那么我们现在需要代理的也就只有sing()这一个行为方法了，这里是需要跟明星一样实现共同的接口的（ISkillAction）：
```
/**
 * desc: 明星的经纪人 --- 代理
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:20
 */
public class Agent implements ISkillAction {

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
}
```


## 静态代理的使用
以上我们已经有了这三个必要的元素了，那我们可以开始进行代理模式的使用了，使用方式如下：
```
public class JavaProxyTest {

    public static void main(String[] args) {

        // 使用静态代理模式
        useStaticProxy();

    }

    /**
     * 静态代理模式的使用
     */
    private static void useStaticProxy() {
        // 要先有一份真实对象（明星对象）
        Star star = new Star();
        // 然后代理者（经纪人）要持有这个真实对象
        Agent agent = new Agent(star);
        // 这样代理者就可以进行这些接口行为的代理操作了
        // 就好比有些商业活动负责人先找到某明星经纪人，通过经纪人成功邀请到某明星来进行演出。
        agent.sing();
    }
}
```

这里我注释应该是比较清楚了的，首先我们肯定是要有一个真实对象的实例的（明星本人），然后我们可以通过代理者（经纪人）进行代理操作。

输出的日志如下：

![](https://user-gold-cdn.xitu.io/2019/1/23/1687a2dc1654d597?w=383&h=104&f=jpeg&s=17522)

这里我们可以看到，通过这个代理者经纪人，我们的活动商成功请到我们的明星进行了一次唱歌出演，我们的明星只需要负责唱歌这个行为，唱歌前后的布置安排或者其他一些行为的插入等等，都是可以通过代理者完成的。


## 静态代理的扩展
通过上面我们可以看到一个简单的静态代理就完成了，但是这个时候可能有些机智的同学会想到一些问题，如：
### 1.行为接口类方法增加
在正常情况下，我们的行为接口需要定义的方法是可能有多个的（明星可能会唱歌，会跳舞等等），那么我们上面提到的三个元素就会发生一点改变了，代码如下：
```
/**
 * desc: 定义一些明星拥有的技能行为
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:16
 */
public interface ISkillAction {

    /**
     * 唱歌
     */
    void sing();

    /**
     * 跳舞
     */
    void dance();
}
```

```
/**
 * desc: 明星 --- 真实对象
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:15
 */
public class Star implements ISkillAction {

    @Override
    public void sing() {
        System.out.println("明星开始唱歌了。");
    }

    @Override
    public void dance() {
        System.out.println("明星开始跳舞了。");
    }
}
```

```
/**
 * desc: 明星的经纪人 --- 代理
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:20
 */
public class Agent implements ISkillAction {

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
}
```

从上面我们可以发现，共同行为接口每多一个方法行为，那我们的代理者也就需要多实现一个代理方法，并且有可能我们每个代理方法前后都有同样的操作，那么我们就需要在每个方法里面重复写这些操作，这可以说是静态代理的一些缺点了，这些我会在介绍动态代理的时候通过代码进行对比说明。


### 2.增加行为接口类的数量

我们的示例中明星只实现了一个技能接口类，但是实际情况中一般不止实现一个，这里我们增加一个生活共同行为类，如下：
```
/**
 * desc: 定义一些生活上的行为方法
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午4:18
 */
public interface ILiveAction {

    /**
     * 吃早餐
     */
    void eatBreakfast();
}
```
然后我们的明星实现了这个接口：
```
public class Star implements ISkillAction, ILiveAction {


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
```

最后我们的代理者（经纪人）也需要实现这些共同行为接口类：
```
public class Agent implements ISkillAction, ILiveAction {

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
```


通过这里我们又可以发现，共同行为接口实现的越多，我们代理者要实现的也同样需要增多，这样还是挺麻烦的。


# 动态代理
动态代理我这里使用的是jdk提供的实现方式。

我们先实现一下动态代理的写法再来分析它的流程，在使用动态代理前我们要有以下元素：

## 共同行为接口类
首先我们使用下静态代理里面的共同行为接口类：
```
public interface ISkillAction {

    /**
     * 唱歌
     */
    void sing();

    /**
     * 跳舞
     */
    void dance();
}
```
```
public interface ILiveAction {

    /**
     * 吃早餐
     */
    void eatBreakfast();
}
```
## InvocationHandler实现类
这个是jdk中提供的，作用是通过实现InvocationHandler接口创建自己的调用处理器，这里我只对每个行为的调用前后进行一行日志输出，如下：
```
/**
 * desc: 创建自己的调用处理器
 *
 * @author Wepon.Yan
 * created at 2019/1/23 下午6:52
 */
public class DynamicProxyHandler implements InvocationHandler {

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

```




## 动态代理的使用
然后我们通过jdk提供的方式使用动态代理：

```
    /**
     * 动态代理模式的使用
     */
    private static void useDynamicProxy() {
        // 要先有一份真实对象（明星对象）
        Star star = new Star();
        // 然后要有一个自定义的处理器
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(star);
        // 然后通过jdk方式动态生成代理对象proxy
        // 这里是一个Object类型，根据不同行为接口强转
        Object proxy = Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{ISkillAction.class, ILiveAction.class},
                dynamicProxyHandler
        );

        // 进行各个行为的代理，这里需要进行不同行为类型的转换
        ((ISkillAction) proxy).sing();
        ((ISkillAction) proxy).dance();

        ((ILiveAction) proxy).eatBreakfast();

    }
```


然后我们在控制台看下日志的打印情况：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687f1197c48d4d7?w=401&h=197&f=png&s=40903)


从上面可以知道我们代理是成功了的，但是这里还没有完善，后面再补充，这里可能有同学会问了，为什么这么写就实现动态代理了呢？原理是什么呢？不慌，下面我们通过源码和反编译来追踪分析一下：

## 动态代理原理分析

首先，我们分析下这个Proxy.newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)源码，这个方法的注释开头是这么说的：

     * Returns an instance of a proxy class for the specified interfaces
     * that dispatches method invocations to the specified invocation
     * handler.
     * @param  loader  
                the class loader to define the proxy class
     * @param  interfaces   
                the list of interfaces for the proxy class
     *          to implement
     * @param  h   
                the invocation handler to dispatch method invocations to
     * @return  a proxy instance with the specified invocation handler of a
     *          proxy class that is defined by the specified class loader
     *          and that implements the specified interfaces
     
     简单翻译一下就是：
     * 返回指定接口的代理类的实例，将方法调用调度到指定的调用处理程序。
     * @param loader  用于定义代理类的类加载器
     * @param interfaces  要实现的代理类的接口列表
     * @param h  调度方法调用的调用处理程序
     * @return 具有代理类的指定调用处理程序的代理实例，
        该代理类的指定调用处理程序由指定的类装入器定义并实现指定的接口
     

重点：返回指定接口的代理类的实例，将方法调用调度到指定的调用处理程序。

从这句话我们结合代码可以看出，意思就是通过Proxy.newProxyInstance返回一个代理类的实例proxy，然后通过这个代理类proxy在进行方法调用的时候，实际又调度到了我们指定的DynamicProxyHandler的invoke方法里面进行处理。


### 问题来了：

***1. 那究竟是怎么生成的代理类呢？***

***2. 生成的代理类又是长什么样子的呢？***

***3. 代理类又是怎么将方法调用调度到我们指定的调用处理程序的呢（InvocationHandler实现类）？***

### 动态代理类是如何生成的
那么我们继续分析Proxy.newProxyInstance()方法的代码实现：
```

这里我把一些没用的注释和不用分析的catch代码去掉了，这里主要是利用反射实现，如下。

public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
    {
        Objects.requireNonNull(h);

        final Class<?>[] intfs = interfaces.clone();
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
        }
        
        //查找或生成指定的代理类Class对象。
        Class<?> cl = getProxyClass0(loader, intfs);
        try {
            if (sm != null) {
                checkNewProxyPermission(Reflection.getCallerClass(), cl);
            }
            // 获取代理类Class的构造函数。
            final Constructor<?> cons = cl.getConstructor(constructorParams);
            final InvocationHandler ih = h;
            if (!Modifier.isPublic(cl.getModifiers())) {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        // 如果作用域为私有，setAccessible为ture支持访问
                        cons.setAccessible(true);
                        return null;
                    }
                });
            }
            // 通过创建一个代理类的实例（这里构造函数中传入了我们的调用处理器，至于原因等下分析），并返回。
            return cons.newInstance(new Object[]{h});
            
            // catch部分的代码我全部删除了，不看。
        } catch (Exception e) {
           
        } 
    }

```

从上面的代码可以看到，最核心的是这一行代码，查找或生成指定的代理类Class对象，然后我们继续盯着这个方法走下去。
```
 Class<?> cl = getProxyClass0(loader, intfs);
```
```


    /**
     * Generate a proxy class.  Must call the checkProxyAccess method
     * to perform permission checks before calling this.
     */
    private static Class<?> getProxyClass0(ClassLoader loader,
                                           Class<?>... interfaces) {
        if (interfaces.length > 65535) {
            throw new IllegalArgumentException("interface limit exceeded");
        }

        // If the proxy class defined by the given loader implementing
        // the given interfaces exists, this will simply return the cached copy;
        // otherwise, it will create the proxy class via the ProxyClassFactory
        return proxyClassCache.get(loader, interfaces);
    }
```

继续看proxyClassCache的生成：
```
    /**
     * a cache of proxy classes
     */
    private static final WeakCache<ClassLoader, Class<?>[], Class<?>>
        proxyClassCache = new WeakCache<>(new KeyFactory(), new ProxyClassFactory());
```
上面有说通过ProxyClassFactory创建代理类，这里直接看ProxyClassFactory类的构造

![](https://user-gold-cdn.xitu.io/2019/1/24/1687e773e58bda10?w=992&h=174&f=jpeg&s=42742)
这里就一个apply方法，我们直接看apply方法，apply中关键点我用中文进行注释了三次，请看下面注释的地方，如：
```
/**
     * A factory function that generates, defines and returns the proxy class given
     * the ClassLoader and array of interfaces.
     */
    private static final class ProxyClassFactory
        implements BiFunction<ClassLoader, Class<?>[], Class<?>>
    {
        private static final String proxyClassNamePrefix = "$Proxy";
        private static final AtomicLong nextUniqueNumber = new AtomicLong();

        @Override
        public Class<?> apply(ClassLoader loader, Class<?>[] interfaces) {

            Map<Class<?>, Boolean> interfaceSet = new IdentityHashMap<>(interfaces.length);
            .......
            .......
            /*
             *这中间省略掉两个大的for循环代码，太多了，这里不需要进行分析
             */
            .......
            .......

            if (proxyPkg == null) {
                // if no non-public proxy interfaces, use com.sun.proxy package
                proxyPkg = ReflectUtil.PROXY_PACKAGE + ".";
            }
            
            long num = nextUniqueNumber.getAndIncrement();
             // 要生成的代理类的名称。下面生成代理类的时候要用到的。
            String proxyName = proxyPkg + proxyClassNamePrefix + num;

             // 以下代码是生成指定的代理类。
             // 以下代码是生成指定的代理类。
             // 以下代码是生成指定的代理类。
             
             // 生成代理类Class byte数组
            byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, interfaces, accessFlags);
            try {
            //  这里生成代理类Class返回
                return defineClass0(loader, proxyName,
                                    proxyClassFile, 0, proxyClassFile.length);
            } catch (ClassFormatError e) {
                throw new IllegalArgumentException(e.toString());
            }
        }
    }
```

到了这一步，我顺便Debug了一下这里的代码，如图：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687e8ba43c66b48?w=1204&h=231&f=png&s=73158)
这里我们看到proxyName，也就是我们生成的代理类的Name是com.sun.proxy.$Proxy0，为了验证一下，我在测试代码中打印了一下Log，如下：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687e8ee5829178f?w=677&h=247&f=png&s=61634)

![](https://user-gold-cdn.xitu.io/2019/1/24/1687e8f11c2941c0?w=378&h=177&f=png&s=36398)

然后我们继续看ProxyGenerator.generateProxyClass(); 和  defineClass0(); 这两个方法，发现在defineClass0()是一个native方法，那就没啥好看的了   
```
private static native Class<?> defineClass0(ClassLoader loader, String name,
                                                byte[] b, int off, int len);
                                                
```
这里最核心的方法是生成ProxyGenerator.generateProxyClass()这个，我们继续看下去（这里提示一下，下面追进去的源码显示的格式有问题，不是正常的源码了，而且有很多我不会再解释了，因为我也不能都看懂，只会用注释来解释一部分重要的地方，如果下面这部分看不懂或者不想看可以直接跳过，直接看后面写的动态生成的代理类的内容就ok，不会影响代理模式的理解）：
```
ProxyGenerator.generateProxyClass()追进去后往下看，下面是ProxyGenerator类中的部分内容：


    // 这个值是一个系统变量，这里是控制下面生成了代理类Class的时候是否要保存成文件 。
    private static final boolean saveGeneratedFiles = (Boolean)AccessController.doPrivileged(new GetBooleanAction("sun.misc.ProxyGenerator.saveGeneratedFiles"));
    

    public static byte[] generateProxyClass(String var0, Class<?>[] var1) {
        return generateProxyClass(var0, var1, 49);
    }

    public static byte[] generateProxyClass(final String var0, Class<?>[] var1, int var2) {
        ProxyGenerator var3 = new ProxyGenerator(var0, var1, var2);
        
        // 这里是生成代理类Class byte数组的地方
        final byte[] var4 = var3.generateClassFile();
        
        // 这里是判断是否要对这个文件进行保存
        if (saveGeneratedFiles) {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    try {
                        int var1 = var0.lastIndexOf(46);
                        Path var2;
                        if (var1 > 0) {
                            Path var3 = Paths.get(var0.substring(0, var1).replace('.', File.separatorChar));
                            Files.createDirectories(var3);
                            var2 = var3.resolve(var0.substring(var1 + 1, var0.length()) + ".class");
                        } else {
                            var2 = Paths.get(var0 + ".class");
                        }

                        Files.write(var2, var4, new OpenOption[0]);
                        return null;
                    } catch (IOException var4x) {
                        throw new InternalError("I/O exception saving generated file: " + var4x);
                    }
                }
            });
        }

        // 返回byte数组
        return var4;
    }

```

从上面我们看到有三条注释的地方，现在我们这里先看下生成代理类Class byte数组的地方，也就是generateClassFile()这个方法，这个方法超级长，还是一样，请注意我注释的地方就OK，我会标明大概意思，具体就不要太纠结了，头发都掉光了：
```

    private byte[] generateClassFile() {
        // 首先是添加 hashCode(),equals(),toString()这些方法
        this.addProxyMethod(hashCodeMethod, Object.class);
        this.addProxyMethod(equalsMethod, Object.class);
        this.addProxyMethod(toStringMethod, Object.class);
        
        // 添加接口中的方法
        Class[] var1 = this.interfaces;
        int var2 = var1.length;

        int var3;
        Class var4;
        for(var3 = 0; var3 < var2; ++var3) {
            var4 = var1[var3];
            Method[] var5 = var4.getMethods();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Method var8 = var5[var7];
                this.addProxyMethod(var8, var4);
            }
        }

        Iterator var11 = this.proxyMethods.values().iterator();

        List var12;
        while(var11.hasNext()) {
            var12 = (List)var11.next();
            checkReturnTypes(var12);
        }

        Iterator var15;
        try {
        // 添加构造方法
            this.methods.add(this.generateConstructor());
            var11 = this.proxyMethods.values().iterator();

            while(var11.hasNext()) {
                var12 = (List)var11.next();
                var15 = var12.iterator();

                while(var15.hasNext()) {
                    ProxyGenerator.ProxyMethod var16 = (ProxyGenerator.ProxyMethod)var15.next();
                    this.fields.add(new ProxyGenerator.FieldInfo(var16.methodFieldName, "Ljava/lang/reflect/Method;", 10));
                    this.methods.add(var16.generateMethod());
                }
            }
    
            // 添加静态初始化方法
            this.methods.add(this.generateStaticInitializer());
        } catch (IOException var10) {
            throw new InternalError("unexpected I/O Exception", var10);
        }

        if (this.methods.size() > 65535) {
            throw new IllegalArgumentException("method limit exceeded");
        } else if (this.fields.size() > 65535) {
            throw new IllegalArgumentException("field limit exceeded");
        } else {
            this.cp.getClass(dotToSlash(this.className));
            this.cp.getClass("java/lang/reflect/Proxy");
            var1 = this.interfaces;
            var2 = var1.length;

            for(var3 = 0; var3 < var2; ++var3) {
                var4 = var1[var3];
                this.cp.getClass(dotToSlash(var4.getName()));
            }
            
            // 到了这里要生成的代理类Class的基本信息就都完了，下面就是生成代理类的.class文件了，这里我特地在网上查过，但是没懂，涉及到Class文件的魔数这些东西，我也是刚接触这些知识点，还在看，总之下面就是生成Class文件用的了，看不懂了 - -!!!
            this.cp.setReadOnly();
            ByteArrayOutputStream var13 = new ByteArrayOutputStream();
            DataOutputStream var14 = new DataOutputStream(var13);

            try {
                var14.writeInt(-889275714);
                var14.writeShort(0);
                var14.writeShort(49);
                this.cp.write(var14);
                var14.writeShort(this.accessFlags);
                var14.writeShort(this.cp.getClass(dotToSlash(this.className)));
                var14.writeShort(this.cp.getClass("java/lang/reflect/Proxy"));
                var14.writeShort(this.interfaces.length);
                Class[] var17 = this.interfaces;
                int var18 = var17.length;

                for(int var19 = 0; var19 < var18; ++var19) {
                    Class var22 = var17[var19];
                    var14.writeShort(this.cp.getClass(dotToSlash(var22.getName())));
                }

                var14.writeShort(this.fields.size());
                var15 = this.fields.iterator();

                while(var15.hasNext()) {
                    ProxyGenerator.FieldInfo var20 = (ProxyGenerator.FieldInfo)var15.next();
                    var20.write(var14);
                }

                var14.writeShort(this.methods.size());
                var15 = this.methods.iterator();

                while(var15.hasNext()) {
                    ProxyGenerator.MethodInfo var21 = (ProxyGenerator.MethodInfo)var15.next();
                    var21.write(var14);
                }

                var14.writeShort(0);
                return var13.toByteArray();
            } catch (IOException var9) {
                throw new InternalError("unexpected I/O Exception", var9);
            }
        }
    }
```

上面的这个方法就是我能跟到最后的方法了，在这里拿到了Class 
byte数组，整个动态生成代理类的流程到这里我们就结束了！

***霸特！！！***
我们还没有看到生成的代理类长什么样子啊？总要给我们看下样子吧？

好好好，继续！！

然后我们再看下  if (saveGeneratedFiles) {}这个地方，看这里一堆var头疼，但是这里的代码意思是if true的话会保存一份文件（追到这里可以猜测，文件应该是生成的代理类的.class文件了），那我们还是Debug一下这个地方，看有没有进行保存，保存的文件又在哪里？这是就扯到怎么看查看生成的代理类的.class文件了，下面开个栏再说。

### 如何查看动态代理生成的代理类

#### 1.修改系统变量值生成代理类
刚说到我们要Debug一下 if (saveGeneratedFiles)这个地方，Debug结果如下：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ec1b4cb45dd0?w=365&h=206&f=png&s=28673)
这里是一个false，那就没有走到下面保存文件的地方了，这里这个变量我在网上查了下，是可以修改的，而且有意想不到的收获，这里慢点说，我们先把这个值改为true继续Debug看一下保存了什么？  

修改saveGeneratedFiles为true:

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ec41ebbd48d4?w=721&h=429&f=png&s=94485)

就这样就行了，记得放在前面，然后继续Debug看，生效了：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ec51d37587ac?w=313&h=183&f=png&s=26325)

然后我们看下这个方法里面：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ec59ddb2ecfd?w=826&h=317&f=png&s=62305)

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ec645963e6eb?w=1024&h=312&f=png&s=67894)

通过这里的代码和path，我们可以看到这里生成了一个\$Proxy0.class的文件保存在这个path：com/sun/proxy/$Proxy0.class，那是不是这样呢？

跑了一下，报错了！！不慌，看下：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ed66f18b477c?w=659&h=151&f=png&s=60213)

哦，这错还是很明显的，那我们把这些类实现下Serializable就好了：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ed72e46c5599?w=251&h=159&f=png&s=14578)


好了，再跑一下，我们可以看到确实有生成文件了，运行一下代码后可以看到我们的项目结构发生了改变：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687ec936a3ba2ea?w=297&h=360&f=png&s=35063)
这里多出了一个.class文件，这个文件就是动态生成的代理类的.class文件，之前是没有的，现在保存在了这个位置，我们直接双击打开看一下，不容易啊，追了大半天终于要看到你了！！！

这里代码如下，同样地，大家注意下我写的注释，大概就能明白这个类做了什么了：
```
public final class $Proxy0 extends Proxy implements ISkillAction, ILiveAction {
    // 保存的每个方法，利用反射获取，具体的代码在最下面的static{}块中
    private static Method m1;
    private static Method m5;
    private static Method m3;
    private static Method m2;
    private static Method m4;
    private static Method m0;

    // 首先这里的构造方法是传入了一个自定义的处理器，这里用的super，我们看下super中做了什么？ 其实就是设置属性h引用这个自定义的处理器，这个h很重要，在下面会用到。
    // protected Proxy(InvocationHandler h) {
    //    Objects.requireNonNull(h);
    //    this.h = h;
    // }

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    // 这个是我们的接口行为中的方法（吃早餐），当我们调用代理类的eatBreakfast()方法的时候就会走到这一步
    public final void eatBreakfast() throws  {
        try {
        // 这里我们看到调用了h.invoke方法，也就是调用了我们自定义的处理器的方法。以下方法都是这样的逻辑，就不一一注释了。
            super.h.invoke(this, m5, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void dance() throws  {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void sing() throws  {
        try {
            super.h.invoke(this, m4, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
    // 反射获取这些方法，包括接口的方法
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m5 = Class.forName("com.wepon.proxydemo.proxy.ILiveAction").getMethod("eatBreakfast");
            m3 = Class.forName("com.wepon.proxydemo.proxy.ISkillAction").getMethod("dance");
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m4 = Class.forName("com.wepon.proxydemo.proxy.ISkillAction").getMethod("sing");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
```

#### 获取代理类Class byte[]写入到文件
还记得之前源码有注释一个这样的方法吗？

            byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, interfaces, accessFlags);
                
我们可以通过这个方法拿到代理类Class的byte[]后自己保存成.class文件，代码如下：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687f127ac2f8802?w=809&h=585&f=png&s=135559)

运行一下后可以看到我们的项目中生成了一个文件 ：

![](https://user-gold-cdn.xitu.io/2019/1/24/1687eed359e2ee59?w=196&h=217&f=png&s=16637)

这个文件打开后跟上面修改系统变量生成的文件内容是一样的，不用怀疑我，是真的。

 
### 代理类怎么将方法调用调度到我们指定的调用处理程序的
从以上代码可以看到，我们调用代理类的方法的时候，最终会调用到我们自定义的处理器（我文中写的这个DynamicProxyHandler）的invoke(Object proxy, Method method, Object[] args)方法，这里我们终于就知道为什么会调用到我们写的处理器里面去了。

好了我们再回顾一下这个DynamicProxyHandler，加深每个元素之间的关联印象：
```
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

```


好了，分析到这里，就都结束了，我想大家通过这个流程和最终看到的结果应该是理解比较深刻了的，至少我希望是这样子的 ^_^，码字是真累啊（估计大家看的也累- -!!）。

对了，除去这种jdk提供的动态代理方式，还有一种cglib的代理方式，这里就不讲了，大家可以去了解下。



# 代理模式适用性
代理模式最典型的应用是AOP，关于AOP之后我会写一份文章作些说明，然后代理模式在一些RPC框架中也应用广泛。

从一份java23种设计模式pdf文档中摘抄一段代理模式适用性的描述：

    1.远程代理(RemoteProxy) 为一个对象在不同的地址空间提供局部代表。
    2.虚拟代理(VirtualProxy) 根据需要创建开销很大的对象。
    3.保护代理(ProtectionProxy) 控制对原始对象的访问。
    4.智能指引(SmartReference) 取代了简单的指针，它在访问对象时执行一 些附加操作。
    

最后我把文章中的源码上传到github了，地址如下，有问题欢迎交流：

[https://github.com/ywp0919/ProxyDemo](https://github.com/ywp0919/ProxyDemo)
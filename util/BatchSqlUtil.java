package cn.com.tcsl.fast.kds.server.util;


import cn.com.tcsl.fast.framework.context.SpringContextUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * <b>功能：</b>批量sql工具类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:10&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public class BatchSqlUtil {


    private static final SqlSessionTemplate sqlSessionTemplate = SpringContextUtil.getBean(SqlSessionTemplate.class);


    /**
     * @param sources           操作数据集
     * @param sqlSessionFactory sqlSessionFactory 为获取 SqlSession
     * @param classZZ           带执行Mapper实例对象
     * @param execute           执行方法
     * @param batchSize         分批次执行：每500 、 1000、 1500 等值
     * @param <T>               元素类型
     * @param <M>               Mapper对象类型
     * @return 是否成功
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T, M> Boolean batch(List<T> sources, SqlSessionFactory sqlSessionFactory, M classZZ, BiConsumer<M, T> execute, int batchSize) {
        SqlSession sqlSession = null;
        try {
            int size = batchSize == 0 ? 300 : batchSize;
            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            // 提取出真实的接口类型，因为直接通过getClass得到的是Spring代理过后的对象。
            Class<?> realClass = classZZ.getClass().getInterfaces()[0];

            M mapper = (M) sqlSession.getMapper(realClass);
            int count = 0;
            for (List<T> sourceList : Lists.partition(sources, size)) {
                for (T source : sourceList) {
                    execute.accept(mapper, source);
                    count++;
                    if (count % size == 0 || count == sources.size()) {
                        sqlSession.flushStatements();
                    }

                }
            }
            sqlSessionTemplate.clearCache();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return true;
    }

    @SneakyThrows
    public static <T, M> Boolean batch(List<T> sources, SqlSessionFactory sqlSessionFactory, M classZZ, BiConsumer<M, T> execute) {
        return batch(sources, sqlSessionFactory, classZZ, execute, 300);
    }


    @SneakyThrows
    public static <T, M> Boolean batch(List<T> sources, M classZZ, BiConsumer<M, T> execute) {
        return batch(sources, sqlSessionTemplate.getSqlSessionFactory(), classZZ, execute, 300);
    }


    /**
     * 获取 目标对象
     *
     * @param proxy 代理对象
     * @return 目标对象
     */
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            proxy = getJdkDynamicProxyTargetObject(proxy);
        } else {
            proxy = getCglibProxyTargetObject(proxy);
        }
        return getTarget(proxy);
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("mapperInterface");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }
}

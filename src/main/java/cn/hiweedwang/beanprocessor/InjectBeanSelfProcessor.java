package cn.hiweedwang.beanprocessor;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用于自动向实现BeanSelfAware接口的类注入对象获得自身的增强代理
 * 执行Aop后的织入方法
 */
@Component
public class InjectBeanSelfProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ////如果Bean没有实现BeanSelfAware标识接口 跳过
        if(!(bean instanceof BeanSelfAware)) {
            return bean;
        }
        //如果当前对象是AOP代理对象，直接注入
        if(AopUtils.isAopProxy(bean)) {
            ((BeanSelfAware) bean).setSelf(bean);
        } else {
            //如果当前对象不是AOP代理，则通过context.getBean(beanName)获取代理对象并注入
            //此种方式不适合解决prototype Bean的代理对象注入
            ((BeanSelfAware)bean).setSelf(context.getBean(beanName));
        }
        return bean;
    }
}

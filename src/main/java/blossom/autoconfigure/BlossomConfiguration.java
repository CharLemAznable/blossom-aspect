package blossom.autoconfigure;

import com.github.charlemaznable.gentle.spring.factory.SpringFactory;
import lombok.val;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@SpringFactory(EnableAutoConfiguration.class)
public class BlossomConfiguration {

    @Bean("BlossomAdvisor")
    public DefaultPointcutAdvisor blossomAdvisor() {
        val defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setPointcut(new BlossomMethodPointcut());
        defaultPointcutAdvisor.setAdvice(new BlossomMethodInterceptor());
        return defaultPointcutAdvisor;
    }

    @Bean("BlossomAdvisorAutoProxyCreator")
    @ConditionalOnMissingBean(AbstractAdvisorAutoProxyCreator.class)
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        val creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}

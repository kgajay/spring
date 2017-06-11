package com.spring3.demo.config;

import com.spring3.demo.scheduler.JobScheduler;
import com.spring3.demo.scheduler.LeaderSelectionService;
import com.spring3.demo.scheduler.LeaderSelectionServiceImpl;
import com.spring3.demo.utils.SpringProvider;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author ajay.kg created on 04/06/17.
 * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html
 */
@EnableWebMvc //mvc:annotation-driven
@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan({ "com.spring3.demo.web" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {


    @Value("${spring.app.name}")
    private String appName;

    @Value("${spring.job.a.cron}")
    private String jobACronExpression;

    @Value("${spring.job.b.cron}")
    private String jobBCronExpression;

    @Value("${spring.job.c.cron}")
    private String jobCCronExpression;

    @Value("${spring.zookeeper.conn.string}")
    private String zooKeeperConnectionString;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public String appName() {
        return appName;
    }

    @Bean
    public SchedulerFactory schedulerFactory() {
        return new StdSchedulerFactory();
    }

    @Bean
    public LeaderSelectionServiceImpl leaderSelectionService() {
        return new LeaderSelectionServiceImpl(zooKeeperConnectionString);
    }

    @Bean
    public JobScheduler jobScheduler(SchedulerFactory schedulerFactory, LeaderSelectionService leaderSelectionService) {
        return new JobScheduler(jobACronExpression, jobBCronExpression, jobCCronExpression, schedulerFactory, leaderSelectionService);
    }

    /**
     * Get bean of given type
     */
    public static <T> T getBean(Class<T> cls) {
        return SpringProvider.INSTANCE.getContext().getBean(cls);
    }
}

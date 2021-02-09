package top.crossoverjie.nows.nows.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.crossoverjie.nows.nows.filter.FilterProcess;
import top.crossoverjie.nows.nows.service.impl.totalsum.ExclusiveFilterProcess;
import top.crossoverjie.nows.nows.service.impl.totalsum.WrapFilterProcess;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/25 22:14
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    @Value("${app.thread}")
    private int corePoolSize = 2;

    @Bean
    public ExecutorService sendMessageExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-%d").build();

        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        return executor;
    }


    @Bean("numberFilterProcess")
    public FilterProcess numberFilterProcess() {
        return new WrapFilterProcess();
    }

    @Bean("exclusiveFilterProcess")
    public FilterProcess exclusiveFilterProcess() {
        return new ExclusiveFilterProcess();
    }
}

package top.crossoverjie.nows.nows.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.crossoverjie.nows.nows.config.AppConfig;
import top.crossoverjie.nows.nows.constants.BaseConstants;
import top.crossoverjie.nows.nows.filter.AbstractFilterProcess;
import top.crossoverjie.nows.nows.util.SpringBeanFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/25 23:55
 * @since JDK 1.8
 */
public class ScanTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ScanTask.class);

    private String path;

    private AbstractFilterProcess filterProcessManager;

    private AppConfig appConfig;


    public ScanTask(String path, AbstractFilterProcess filterProcessManager) {
        this.path = path;
        this.filterProcessManager = filterProcessManager;
        this.appConfig = SpringBeanFactory.getBean(AppConfig.class);
    }

    @Override
    public void run() {
        Stream<String> stringStream = null;
        try {
            stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("IOException", e);
        }

        if (appConfig.getAppModel().equals(BaseConstants.TOTAL_WORDS)) {
            List<String> collect = stringStream.collect(Collectors.toList());
            for (String msg : collect) {
                filterProcessManager.process(msg);
            }

        }
    }

}

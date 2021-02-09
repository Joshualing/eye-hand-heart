package top.crossoverjie.nows.nows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.crossoverjie.nows.nows.config.AppConfig;
import top.crossoverjie.nows.nows.constants.BaseConstants;
import top.crossoverjie.nows.nows.filter.AbstractFilterProcess;
import top.crossoverjie.nows.nows.filter.TotalSumFilterProcessManager;
import top.crossoverjie.nows.nows.scan.ScannerFile;
import top.crossoverjie.nows.nows.service.ResultService;
import top.crossoverjie.nows.nows.service.impl.totalsum.TotalSumResultServiceImpl;
import top.crossoverjie.nows.nows.thread.ScanTask;
import top.crossoverjie.nows.nows.util.SpringBeanFactory;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *统计app.downLoad.path下文件中的字数
 * 1.所有字数
 * 2.符合规则的字数
 *
 * 涉及知识
 * 1.spring boot
 * 2.多线程
 * 3.设计模式 策略模式
 *
 * 拓展
 * 1.责任链模式
 */
@SpringBootApplication
public class NowsApplication implements CommandLineRunner {


    private static Logger logger = LoggerFactory.getLogger(NowsApplication.class);

    private AbstractFilterProcess filterProcessManager;

    private ResultService resultService;

    @Autowired
    private AppConfig config;

    @Autowired
    private ScannerFile scannerFile;

    @Autowired
    private ExecutorService executorService;


    public static void main(String[] args) {
        SpringApplication.run(NowsApplication.class, "2");

    }

    @Override
    public void run(String... strings) throws Exception {
        if (config.getAppModel().equals(BaseConstants.TOTAL_WORDS)) {
            filterProcessManager = SpringBeanFactory.getBean(TotalSumFilterProcessManager.class);
            resultService = SpringBeanFactory.getBean(TotalSumResultServiceImpl.class);
            ((TotalSumResultServiceImpl) resultService).setCurrentTime();

        }


        Set<ScannerFile.FileInfo> allFile = scannerFile.getAllFile(config.getDownLoadPath());
        logger.info("allFile size = [{}]", allFile.size());

        for (ScannerFile.FileInfo msg : allFile) {
            executorService.execute(new ScanTask(msg.getFilePath(), filterProcessManager));
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            //logger.info("worker running");
        }

        resultService.end();
    }
}

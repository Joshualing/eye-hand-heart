package top.crossoverjie.nows.nows.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.crossoverjie.nows.nows.service.impl.totalsum.TotalWords;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/25 18:53
 * @since JDK 1.8
 */
@Service
public class ExclusiveSumFilterProcessManager extends AbstractFilterProcess {
    private static Logger logger = LoggerFactory.getLogger(ExclusiveSumFilterProcessManager.class);
    @Autowired
    private TotalWords totalWords;

    @Resource(name = "exclusiveFilterProcess")
    private FilterProcess exclusiveFilterProcess;

    @Autowired
    @Resource(name = "processLowerCaseFilterProcess")
    private FilterProcess processLowerCaseFilterProcess;

    private List<FilterProcess> filterProcesses = new ArrayList<>(10);


    @PostConstruct
    @Override
    public void start() {
        addProcess(exclusiveFilterProcess).addProcess(processLowerCaseFilterProcess);
    }

    @Override
    public AbstractFilterProcess addProcess(FilterProcess process) {
        filterProcesses.add(process);
        return this;
    }


    /**
     * 处理
     *
     * @param msg
     */
    @Override
    public String process(String msg) {
        for (FilterProcess filterProcess : filterProcesses) {
            msg = filterProcess.process(msg);
        }
        totalWords.sum(msg.toCharArray().length);
        return msg;
    }
}

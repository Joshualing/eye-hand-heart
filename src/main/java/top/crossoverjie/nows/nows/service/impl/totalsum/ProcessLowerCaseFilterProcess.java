package top.crossoverjie.nows.nows.service.impl.totalsum;

import org.springframework.stereotype.Component;
import top.crossoverjie.nows.nows.filter.FilterProcess;

@Component
public class ProcessLowerCaseFilterProcess implements FilterProcess {
    @Override
    public String process(String msg) {
        msg = msg.replaceAll("[a-z]", "AAA");
        return msg;
    }
}

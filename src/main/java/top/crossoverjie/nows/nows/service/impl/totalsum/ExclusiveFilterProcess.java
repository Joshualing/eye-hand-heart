package top.crossoverjie.nows.nows.service.impl.totalsum;

import top.crossoverjie.nows.nows.filter.FilterProcess;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/25 18:52
 * @since JDK 1.8
 */
public class ExclusiveFilterProcess implements FilterProcess {


    @Override
    public String process(String msg) {

        /***将msg中的空白字符，包括空格、制表符、换页符去除***/
        msg = msg.replaceAll("\\s*", "");

        msg = msg.replaceAll("\\,", "");
        msg = msg.replaceAll("\\.", "");

        return msg;
    }
}

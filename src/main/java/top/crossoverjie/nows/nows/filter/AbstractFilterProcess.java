package top.crossoverjie.nows.nows.filter;

/**
 * Function:
 *
 * @author joshua
 * @since JDK 1.8
 */
public abstract class AbstractFilterProcess {


    protected abstract void start();

    /**
     * 加入责任链
     *
     * @param process
     * @return
     */
    protected abstract AbstractFilterProcess addProcess(FilterProcess process);

    /**
     * 执行数据处理
     *
     * @param msg
     * @return
     */
    public abstract String process(String msg);
}

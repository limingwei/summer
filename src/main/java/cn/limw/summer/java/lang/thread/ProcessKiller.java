package cn.limw.summer.java.lang.thread;

/**
 * @author li
 * @version 1 (2015年1月27日 上午9:15:10)
 * @since Java7
 */
public class ProcessKiller extends Thread {
    private Process process;

    public ProcessKiller(Process process) {
        this.process = process;
    }

    public void run() {
        process.destroy();
    }
}
package utils;

public class SystemCache extends Thread {

    public static volatile long currentTimeMillis = System.currentTimeMillis();

    public SystemCache() {
        setDaemon(true);
    }

    static {
        new SystemCache().start();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                // TODO: handle exception
            }

            currentTimeMillis = System.currentTimeMillis();
        }
    }
}

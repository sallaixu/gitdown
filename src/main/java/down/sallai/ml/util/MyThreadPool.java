package down.sallai.ml.util;

import java.util.concurrent.*;

public class MyThreadPool {
    private  static volatile ThreadPoolExecutor threadPoolExecutor = null;

    private MyThreadPool(){

    }

    public static ThreadPoolExecutor getThreadPoolExecutor(){
        if(threadPoolExecutor==null){
            synchronized (MyThreadPool.class){
                if(threadPoolExecutor==null){
                    threadPoolExecutor = new ThreadPoolExecutor(12,
                            30,600,
                            TimeUnit.SECONDS,
                            new ArrayBlockingQueue<>(20), Executors.defaultThreadFactory()
                    );
                }
            }

        }
        return threadPoolExecutor;
    }



}

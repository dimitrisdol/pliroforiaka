package com.example.Networks;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.io.*;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class SpringAsyncConfiguration implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(1000);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                try {
                    FileWriter writer = new FileWriter("test.txt", true );
                    writer.write("Throwable Exception message : " + ex.getMessage());
                    writer.write("<br>" + "New error found: " + "<br>" + "\n");
                    for (Object param : params) {
                        writer.write("Problem with adding values       : " + param + " " + "\n" +" <br> <br> <br>");
                    }
                    writer.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
               // System.out.println("Throwable Exception message : " + ex.getMessage());
              //  System.out.println("Method name                 : " + method.getName());
              //  for (Object param : params) {
              //      System.out.println("Problem with adding values       : " + param);
              //  }
              //  System.out.println("stack Trace ");
              //  ex.printStackTrace();
            }

        };
    }
}
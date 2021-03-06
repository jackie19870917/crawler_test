package com.web.jackie.crawler.processor.demo;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

public class LevelLimitScheduler extends PriorityScheduler {

    private int levelLimit = 3;

    public LevelLimitScheduler(){

    }

    public LevelLimitScheduler(int levelLimit) {
        this.levelLimit = levelLimit;
    }

    @Override
    public synchronized void push(Request request, Task task) {
        if (((Integer) request.getExtra("_level")) <= levelLimit) {
            super.push(request, task);
        }
    }
}
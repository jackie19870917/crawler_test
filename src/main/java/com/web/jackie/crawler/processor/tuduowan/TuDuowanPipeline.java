package com.web.jackie.crawler.processor.tuduowan;

import com.google.common.collect.Lists;
import com.web.jackie.crawler.util.FileHelper;
import org.apache.http.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

@ThreadSafe
public class TuDuowanPipeline extends FilePersistentBase implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public TuDuowanPipeline() {
        setPath("/data/webmagic/");
    }

    public TuDuowanPipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            Request request = resultItems.getRequest();
            FileHelper.downloadFile(this.path, Lists.newArrayList(request.getUrl()), ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("write file error", e);
        }
    }
}
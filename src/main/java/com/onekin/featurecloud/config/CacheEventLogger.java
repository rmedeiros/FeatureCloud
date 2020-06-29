package com.onekin.featurecloud.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;


public class CacheEventLogger
        implements CacheEventListener<Object, Object> {


    private static final Logger logger = LogManager.getLogger(CacheEventListener.class);

    @Override
    public void onEvent(
            CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        logger.info("Cache event: ",
                cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
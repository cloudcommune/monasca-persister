/*
 * Copyright (c) 2014 Hewlett-Packard Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hpcloud.mon.persister.consumer;

import com.hpcloud.mon.persister.disruptor.ManagedDisruptor;

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.lifecycle.Managed;

public class Consumer<T> implements Managed {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final KafkaConsumer consumer;
    private final ManagedDisruptor<T> disruptor;

    @Inject
    public Consumer(KafkaConsumer kafkaConsumer, ManagedDisruptor<T> disruptor) {
        this.consumer = kafkaConsumer;
        this.disruptor = disruptor;
    }

    @Override
    public void start() throws Exception {
        logger.debug("start");
        consumer.run();
    }

    @Override
    public void stop() throws Exception {
        logger.debug("stop");
        consumer.stop();
        disruptor.shutdown();
    }
}

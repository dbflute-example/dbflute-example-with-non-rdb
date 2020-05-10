/*
 * Copyright(c) u-next.
 */
package org.dbflute.kvs.core.delegator;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.StopWatch;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.unit.UnitNonrdbTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class KvsRedisPoolTest extends UnitNonrdbTestCase {

    private static final Logger logger = LoggerFactory.getLogger(KvsRedisPoolTest.class);

    private static final int DATA_SIZE = 100;

    @Resource
    private KvsRedisPool kvsRedisPool;

    public void test_hash() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try (Jedis jedis = kvsRedisPool.getResource()) {
            IntStream.range(0, DATA_SIZE).forEach(index -> {
                Map<String, String> map = DfCollectionUtil.newLinkedHashMap();
                IntStream.range(0, 10).forEach(fieldIndex -> {
                    map.put("field" + fieldIndex, "value" + index + "_" + fieldIndex);
                    jedis.hmset("test_hmset_key" + 1, map);
                });
            });
        }
        stopWatch.stop();
        logger.debug("test_hash_set={}ms", stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        try (Jedis jedis = kvsRedisPool.getResource()) {
            List<List<String>> list = DfCollectionUtil.newArrayList();
            IntStream.range(0, DATA_SIZE).forEach(index -> {
                Map<String, String> map = DfCollectionUtil.newLinkedHashMap();
                IntStream.range(0, 10).forEach(fieldIndex -> {
                    map.put("field" + fieldIndex, "value" + index + "_" + fieldIndex);
                });
                list.add(jedis.hmget("test_hmset_key" + 1, map.keySet().toArray(new String[] {})));
            });

            list.stream().forEach(data -> {
                //logger.debug("{}", data);
            });
        }

        stopWatch.stop();
        logger.debug("test_hash_get={}ms", stopWatch.getTime());
    }

    public void test_hash_pipelined() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try (Jedis jedis = kvsRedisPool.getResource()) {
            try (Pipeline pipelined = jedis.pipelined()) {
                IntStream.range(0, DATA_SIZE).forEach(index -> {
                    Map<String, String> map = DfCollectionUtil.newLinkedHashMap();
                    IntStream.range(0, 10).forEach(fieldIndex -> {
                        map.put("field" + fieldIndex, "value" + index + "_" + fieldIndex);
                        pipelined.hmset("test_hmset_pipelined_key" + 1, map);
                    });
                    pipelined.sync();
                });
            }
        }
        stopWatch.stop();
        logger.debug("test_hash_set_pipelined={}ms", stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        try (Jedis jedis = kvsRedisPool.getResource()) {
            List<Response<List<String>>> list = DfCollectionUtil.newArrayList();
            try (Pipeline pipelined = jedis.pipelined()) {
                IntStream.range(0, DATA_SIZE).forEach(index -> {
                    Map<String, String> map = DfCollectionUtil.newLinkedHashMap();
                    IntStream.range(0, 10).forEach(fieldIndex -> {
                        map.put("field" + fieldIndex, "value" + index + "_" + fieldIndex);
                    });
                    list.add(pipelined.hmget("test_hmset_pipelined_key" + 1, map.keySet().toArray(new String[] {})));
                    pipelined.sync();
                });
            }

            list.stream().forEach(response -> {
                List<String> data = response.get();
                logger.debug("{}", data);
            });
        }
        stopWatch.stop();
        logger.debug("test_hash_get_pipelined={}ms", stopWatch.getTime());
    }
}
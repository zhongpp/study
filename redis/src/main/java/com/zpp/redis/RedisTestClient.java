package com.zpp.redis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

public class RedisTestClient {
    public static String host = "192.168.201.77";
    public static int port = 6379;
    public static int totalRecords = 10000;
    public static int loop = 1;
    public static int dataSize = 1024;
    public static int threadCount = 10;
    public static int multiSize = 10;
    public static int maxListSetSize = 1000;
    private static byte[] baValue = getBaValue(dataSize);
    private static Map<Integer, byte[]> baValueMap = getBaValueMap();
    private static byte[][] hmFields = getHmFields();
    private static byte[] hmFieldValue = getBaValue(dataSize / 10);
    private static Map<byte[], byte[]> hmValue = getHmValue();
    private static JedisPool pool;
    public static String types = "1,2,3,4,5,6,7,8,9";

    public static class TYPE {
        public final static int BYTES = 1;
        public final static int MAP = 2;
        public final static int MAP_UPD = 3;
        public final static int LIST = 4;
        public final static int LIST_UPD = 5;
        public final static int SET = 6;
        public final static int SORTED_SET = 7;
        public final static int MULTI = 8;
        public final static int PIPELINE = 9;

        public static String desc(int t) {
            switch (t) {
                case BYTES:
                    return "byte[](set/get)";
                case MAP:
                    return "map(hmset/hmget)";
                case MAP_UPD:
                    return "map(hset/hget)";
                case LIST:
                    return "list(rpush/lpop)";
                case LIST_UPD:
                    return "list(lset/lindex)";
                case SET:
                    return "set(sadd/srandmember)";
                case SORTED_SET:
                    return "sorted-set(zadd/zrange)";
                case MULTI:
                    return "byte[](mset/mget)";
                case PIPELINE:
                    return "byte[](pipeline)";
                default:
                    return "undefined";
            }
        }
    }

    private static byte[] getBaValue(int size) {
        StringBuilder sb = new StringBuilder(size);
        sb.append(String.valueOf(size));
        for (int idx = 0; idx < (size - String.valueOf(size).length()); idx++) {
            sb.append("0");
        }
        return sb.toString().getBytes();
    }

    private static Map<Integer, byte[]> getBaValueMap() {
        Map<Integer, byte[]> map = new HashMap<Integer, byte[]>();
        for (int idx = 0; idx < maxListSetSize; idx++) {
            map.put(idx, getBaValue(dataSize));
        }
        return map;
    }

    public static byte[][] getHmFields() {
        byte[][] hmFields = new byte[10][];
        byte[] field = null;
        for (int idx = 0; idx < 10; idx++) {
            field = String.valueOf(idx).getBytes();
            hmFields[idx] = field;
        }
        return hmFields;
    }

    public static Map<byte[], byte[]> getHmValue() {
        Map<byte[], byte[]> hmValue = new HashMap<byte[], byte[]>(10);
        byte[] value = getBaValue(dataSize / 10);
        for (int idx = 0; idx < 10; idx++) {
            hmValue.put(String.valueOf(idx).getBytes(), value);
        }
        return hmValue;
    }

    public static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(-1);
        config.setMaxIdle(10);
        config.setBlockWhenExhausted(true);
        config.setMaxWaitMillis(30000);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        pool = new JedisPool(config, host, port, 30000);
        //System.out.println("create pool DB" + dbIndex);
    }

    public static void destroyPool() {
        pool.destroy();
    }

    public static void flushDB() {
        System.out.println("======================");
        initPool();
        Jedis jedis = pool.getResource();
        jedis.flushDB();
        System.out.println("flush DB");
        pool.returnResource(jedis);
        destroyPool();
    }

    public static int getKeysCount() {
        Jedis jedis = pool.getResource();
        int count = jedis.keys("*".getBytes()).size();
        pool.returnResource(jedis);
        return count;
    }

    public static void showListSize() {
        Jedis jedis = pool.getResource();
        Set<byte[]> keys = jedis.keys("*".getBytes());
        if (keys == null || keys.isEmpty()) {
            return;
        }
        long len = 0L;
        for (byte[] key : keys) {
            len += jedis.llen(key);
        }
        int dataSize = jedis.lindex(keys.iterator().next(), 0).length;
        pool.returnResource(jedis);
        System.out.println("average list size:" + (len / keys.size()) + ", data size:" + dataSize);
    }

    public static void showSortedSetSize() {
        Jedis jedis = pool.getResource();
        Set<byte[]> keys = jedis.keys("*".getBytes());
        if (keys == null || keys.isEmpty()) {
            return;
        }
        long len = 0L;
        for (byte[] key : keys) {
            len += jedis.zcount(key, 0, totalRecords + 1);
        }
        int dataSize = jedis.zrange(keys.iterator().next(), 0, 0).iterator().next().length;
        pool.returnResource(jedis);
        System.out.println("average set size:" + (len / keys.size()) + ", data size:" + dataSize);
    }

    public static class SetThread implements Runnable {
        private int idx;
        private int typeVal;
        private byte[][] mKeyValues;//mset
        private Map<byte[], byte[]> pKeyValues;//pipeline

        public SetThread(int idx, int typeVal) {
            this.idx = idx;
            this.typeVal = typeVal;
        }

        public SetThread(byte[][] mKeyValues) {
            this.mKeyValues = mKeyValues;
            this.typeVal = TYPE.MULTI;
        }

        public SetThread(Map<byte[], byte[]> pKeyValues) {
            this.pKeyValues = pKeyValues;
            this.typeVal = TYPE.PIPELINE;
        }

        public void run() {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                switch (typeVal) {
                    case TYPE.BYTES:
                        jedis.set(String.valueOf(idx).getBytes(), baValue);
                        break;
                    case TYPE.MAP:
                        jedis.hmset(String.valueOf(idx).getBytes(), hmValue);
                        break;
                    case TYPE.MAP_UPD:
                        byte[] hsetKey = String.valueOf(idx).getBytes();
                        byte[] hsetField = String.valueOf(idx % 10).getBytes();
                        jedis.hset(hsetKey, hsetField, hmFieldValue);
                        break;
                    case TYPE.LIST:
                        byte[] rpushKey = String.valueOf(idx / maxListSetSize).getBytes();
                        jedis.rpush(rpushKey, baValue);
                        break;
                    case TYPE.LIST_UPD:
                        byte[] lsetKey = String.valueOf(idx / maxListSetSize).getBytes();
                        int lsetIdx = new Random().nextInt(maxListSetSize);
                        jedis.lset(lsetKey, lsetIdx, baValue);
                        break;
                    case TYPE.SET:
                        byte[] saddKey = String.valueOf(idx / maxListSetSize).getBytes();
                        byte[] saddBaValue = baValueMap.get(idx % maxListSetSize);
                        byte[] saddIdxBytes = ("0000000000".substring(String.valueOf(idx).length()) + idx).getBytes();
                        for (int sIdx = 0; sIdx < saddIdxBytes.length; sIdx++) {
                            saddBaValue[sIdx] = saddIdxBytes[sIdx];
                        }
                        jedis.sadd(saddKey, saddBaValue);
                        break;
                    case TYPE.SORTED_SET:
                        byte[] zaddKey = String.valueOf(idx / maxListSetSize).getBytes();
                        byte[] zaddBaValue = baValueMap.get(idx % maxListSetSize);
                        byte[] zaddIdxBytes = ("0000000000".substring(String.valueOf(idx).length()) + idx).getBytes();
                        for (int sIdx = 0; sIdx < zaddIdxBytes.length; sIdx++) {
                            zaddBaValue[sIdx] = zaddIdxBytes[sIdx];
                        }
                        jedis.zadd(zaddKey, idx, zaddBaValue);
                        break;
                    case TYPE.MULTI:
                        jedis.mset(mKeyValues);
                        break;
                    case TYPE.PIPELINE:
                        Pipeline pipe = jedis.pipelined();
                        for (byte[] key : pKeyValues.keySet()) {
                            pipe.set(key, pKeyValues.get(key));
                        }
                        pipe.sync();
                        break;
                }
                pool.returnResource(jedis);
                jedis = null;
            } catch (Exception ex) {
                System.err.println("SetThread " + ex);
                if (jedis != null) {
                    pool.returnBrokenResource(jedis);
                }
            }
        }
    }

    public static class GetThread implements Runnable {
        private int idx;
        private int typeVal;
        private byte[][] mKeys;//mget
        private Set<byte[]> pKeys;//pipeline

        public GetThread(int idx, int typeVal) {
            this.idx = idx;
            this.typeVal = typeVal;
        }

        public GetThread(byte[][] mKeys) {
            this.mKeys = mKeys;
            this.typeVal = TYPE.MULTI;
        }

        public GetThread(Set<byte[]> pKeys) {
            this.pKeys = pKeys;
            this.typeVal = TYPE.PIPELINE;
        }

        public void run() {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                switch (typeVal) {
                    case TYPE.BYTES:
                        byte[] get = jedis.get(String.valueOf(idx).getBytes());
                        break;
                    case TYPE.MAP:
                        List<byte[]> hmget = jedis.hmget(String.valueOf(idx).getBytes(), hmFields);
                        break;
                    case TYPE.MAP_UPD:
                        byte[] hget = jedis.hget(String.valueOf(idx).getBytes(), String.valueOf(idx % 10).getBytes());
                        break;
                    case TYPE.LIST:
                        byte[] lpop = jedis.lpop(String.valueOf(idx / maxListSetSize).getBytes());
                        break;
                    case TYPE.LIST_UPD:
                        int itemIdx = new Random().nextInt(maxListSetSize);
                        byte[] lindex = jedis.lindex(String.valueOf(idx / maxListSetSize).getBytes(), itemIdx);
                        break;
                    case TYPE.SET:
                        byte[] srandmember = jedis.srandmember(String.valueOf(idx / maxListSetSize).getBytes());
                        break;
                    case TYPE.SORTED_SET:
                        int rangeIdx = new Random().nextInt(maxListSetSize);
                        Set<byte[]> zrange = jedis.zrange(String.valueOf(idx / maxListSetSize).getBytes(), rangeIdx, rangeIdx);
                        break;
                    case TYPE.MULTI:
                        jedis.mget(mKeys);
                        break;
                    case TYPE.PIPELINE:
                        Pipeline pipe = jedis.pipelined();
                        for (byte[] key : pKeys) {
                            pipe.get(key);
                        }
                        pipe.sync();
                        break;
                }
                pool.returnResource(jedis);
                jedis = null;
            } catch (Exception ex) {
                System.err.println("GetThread " + ex);
                if (jedis != null) {
                    pool.returnBrokenResource(jedis);
                }
            }
        }
    }

    public static class SetWrapThread extends Thread {
        private int type;

        public SetWrapThread(int type) {
            this.type = type;
        }

        public void run() {
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            long t0 = System.currentTimeMillis();
            switch (type) {
                case TYPE.SET:
                case TYPE.SORTED_SET:
                case TYPE.BYTES:
                case TYPE.MAP:
                case TYPE.MAP_UPD:
                case TYPE.LIST:
                case TYPE.LIST_UPD:
                    for (int n = 0; n < loop; n++) {
                        for (int idx = 0; idx < totalRecords; idx++) {
                            executor.execute(new SetThread(idx, type));
                        }
                    }
                    break;
                case TYPE.MULTI:
                    for (int n = 0; n < loop; n++) {
                        for (int idx = 0; idx < totalRecords; idx += multiSize) {
                            byte[][] keyvalues = new byte[multiSize * 2][];
                            for (int i = 0; i < multiSize; i++) {
                                keyvalues[i * 2] = String.valueOf(idx + i).getBytes();
                                keyvalues[i * 2 + 1] = baValue;
                            }
                            executor.execute(new SetThread(keyvalues));
                        }
                    }
                    break;
                case TYPE.PIPELINE:
                    for (int n = 0; n < loop; n++) {
                        for (int idx = 0; idx < totalRecords; idx += multiSize) {
                            Map<byte[], byte[]> data = new HashMap<byte[], byte[]>();
                            for (int i = 0; i < multiSize; i++) {
                                data.put(String.valueOf(idx + i).getBytes(), baValue);
                            }
                            executor.execute(new SetThread(data));
                        }
                    }
                    break;
            }
            try {
                executor.shutdown();
                executor.awaitTermination(30, TimeUnit.MINUTES);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            long t2 = System.currentTimeMillis();
            long rpsTotal = (long) ((((double) (totalRecords * loop)) / (t2 - t0)) * 1000);
            System.out.println(TYPE.desc(type) + " set/s:" + rpsTotal);
        }
    }

    public static class GetWrapThread extends Thread {
        private int type;

        public GetWrapThread(int type) {
            this.type = type;
        }

        public void run() {
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            long t0 = System.currentTimeMillis();
            switch (type) {
                case TYPE.LIST:
                case TYPE.SET:
                case TYPE.SORTED_SET:
                case TYPE.BYTES:
                case TYPE.MAP:
                case TYPE.MAP_UPD:
                case TYPE.LIST_UPD:
                    for (int n = 0; n < loop; n++) {
                        for (int idx = 0; idx < totalRecords; idx++) {
                            executor.execute(new GetThread(idx, type));
                        }
                    }
                    break;
                case TYPE.MULTI:
                    for (int n = 0; n < loop; n++) {
                        for (int idx = 0; idx < totalRecords; idx += multiSize) {
                            byte[][] keys = new byte[multiSize][];
                            for (int i = 0; i < multiSize; i++) {
                                keys[i] = String.valueOf(idx + i).getBytes();
                            }
                            executor.execute(new GetThread(keys));
                        }
                    }
                    break;
                case TYPE.PIPELINE:
                    for (int n = 0; n < loop; n++) {
                        for (int idx = 0; idx < totalRecords; idx += multiSize) {
                            Set<byte[]> data = new HashSet<byte[]>();
                            for (int i = 0; i < multiSize; i++) {
                                data.add(String.valueOf(idx + i).getBytes());
                            }
                            executor.execute(new GetThread(data));
                        }
                    }
                    break;
            }
            try {
                executor.shutdown();
                executor.awaitTermination(30, TimeUnit.MINUTES);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            long t2 = System.currentTimeMillis();
            long rpsTotal = (long) ((((double) (totalRecords * loop)) / (t2 - t0)) * 1000);
            System.out.println(TYPE.desc(type) + " get/s:" + rpsTotal);
        }
    }

    public static void testSet(int type, String title) {
        System.out.println("==========" + title + "==========");
        testSetGet(type, true, false);
    }

    public static void testGet(int type, String title) {
        System.out.println("==========" + title + "==========");
        testSetGet(type, false, true);
    }

    public static void testSetGet(int type, String title) {
        System.out.println("==========" + title + "==========");
        testSetGet(type, true, true);
    }

    private static void testSetGet(int type, boolean set, boolean get) {
        initPool();
        System.out.println(getKeysCount() + " keys");
        SetWrapThread sth1 = new SetWrapThread(type);
        if (set) {
            sth1.start();
        }
        GetWrapThread gth1 = new GetWrapThread(type);
        if (get) {
            gth1.start();
        }
        while ((set && sth1.isAlive()) || (get && gth1.isAlive())) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getKeysCount() + " keys");
        if (type == TYPE.LIST || type == TYPE.LIST_UPD) {
            showListSize();
        }
        if (type == TYPE.SORTED_SET) {
            showSortedSetSize();
        }
        destroyPool();
    }

    public static void main(String[] args) {
        System.out.println("[START]");
        for (String arg : args) {
            System.out.println("[PARA]" + arg);
            String[] kv = arg.split("=");
            if (kv.length != 2) {
                continue;
            }
            String key = kv[0];
            String value = kv[1];
            if (key.equalsIgnoreCase("totalRecords")) {
                totalRecords = Integer.parseInt(value);
            } else if (key.equalsIgnoreCase("dataSize")) {
                dataSize = Integer.parseInt(value);
            } else if (key.equalsIgnoreCase("threadCount")) {
                threadCount = Integer.parseInt(value);
            } else if (key.equalsIgnoreCase("host")) {
                host = value;
            } else if (key.equalsIgnoreCase("port")) {
                port = Integer.parseInt(value);
            } else if (key.equalsIgnoreCase("multiSize")) {
                multiSize = Integer.parseInt(value);
            } else if (key.equalsIgnoreCase("loop")) {
                loop = Integer.parseInt(value);
            } else if (key.equalsIgnoreCase("types")) {
                types = value;
            }
        }

        if (dataSize != 1024) {
            baValue = getBaValue(dataSize);
            baValueMap = getBaValueMap();
            hmFields = getHmFields();
            hmFieldValue = getBaValue(dataSize / 10);
            hmValue = getHmValue();
        }

        int origThreadCount = threadCount;

        List<String> typeList = Arrays.asList(types.split(","));
        for (String type : typeList) {
            int t = Integer.valueOf(type);
            threadCount = origThreadCount;
            flushDB();
            if (t == TYPE.MAP_UPD) {
                testSet(TYPE.MAP, TYPE.desc(TYPE.MAP));
            }
            if (t == TYPE.LIST_UPD) {
                testSet(TYPE.LIST, TYPE.desc(TYPE.LIST));
            }
            testSet(t, TYPE.desc(t));
            testGet(t, TYPE.desc(t));
            if (t == TYPE.LIST) {
                testSet(t, TYPE.desc(t));
            }
            threadCount = origThreadCount / 2;
            testSetGet(t, TYPE.desc(t));
        }

//		if (typeList.contains(String.valueOf(TYPE.LIST))
//				|| typeList.contains(String.valueOf(TYPE.LIST_UPD))) {
//			threadCount = origThreadCount;
//			flushDB();
//			if (typeList.contains(String.valueOf(TYPE.LIST_UPD))) {
//				testSet(TYPE.LIST, TYPE.desc(TYPE.LIST));
//				threadCount = origThreadCount / 2;
//				testSetGet(TYPE.LIST_UPD, TYPE.desc(TYPE.LIST_UPD));
//			}
//			if (typeList.contains(String.valueOf(TYPE.LIST))) {
//				threadCount = origThreadCount;
//				testSet(TYPE.LIST, TYPE.desc(TYPE.LIST));
//				testGet(TYPE.LIST, TYPE.desc(TYPE.LIST));
//				threadCount = origThreadCount / 2;
//				testSetGet(TYPE.LIST, TYPE.desc(TYPE.LIST));
//			}
//		}
        System.out.println("[END]");
    }
}
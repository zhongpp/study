package com.baomidou.mybatisplus.samples.deluxe.algorithm;

import cn.hutool.core.date.DateUtil;
import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * Created by tuyu on 1/11/17.
 */
public class SingleKeyByDateTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Date> {
    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
        String value = cn.hutool.core.date.DateUtil.format(shardingValue.getValue(), "YYYYMMdd");
        return "user_" + value;
    }

    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
        return null;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>();
        Range range = shardingValue.getValueRange();
        Date beginDate = (Date) range.lowerEndpoint();
        Date endDate = (Date) range.upperEndpoint();
        long days = DateUtil.betweenDay(beginDate, endDate, true);
        for (int i = 0; i <= days; i++) {
            String date = DateUtil.format(DateUtil.offsetDay(beginDate, i), "YYYYMMdd");
            result.add("user_" + date);
        }
        return result;
    }

}

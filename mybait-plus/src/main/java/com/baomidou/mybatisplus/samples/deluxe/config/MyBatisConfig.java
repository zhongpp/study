package com.baomidou.mybatisplus.samples.deluxe.config;

import com.baomidou.mybatisplus.samples.deluxe.algorithm.SingleKeyByDateTableShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import lombok.Data;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * Created by tuyu on 1/11/17.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement
@Data
public class MyBatisConfig {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    private String acDriverClassName;
    private String acUrl;
    private String acUsername;
    private String acPassword;

    @Autowired
    private Environment environment;

    @Bean
    @Primary
    public DataSource shardingDataSource() throws Exception {

        //dataSource
        Properties properties = new Properties();
        properties.put("driverClassName", driverClassName);
        properties.put("url", url);
        properties.put("username", username);
        properties.put("password", password);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);

        //dataSourceRule
        Map<String, DataSource> dataSourceMap = new HashedMap();
        dataSourceMap.put("aclog", dataSource);
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);

        //tableShardingStrategy
        TableShardingStrategy tableShardingStrategy = new TableShardingStrategy("created_at", new SingleKeyByDateTableShardingAlgorithm());

        //tableRule
        TableRule tableRule = new TableRule.TableRuleBuilder("user")
                .dynamic(true)
                .dataSourceRule(dataSourceRule)
                .autoIncrementColumns("id")
                .tableIdGenerator(CommonSelfIdGenerator.class)
                .build();

        //shardingRule
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(tableRule))
                .tableShardingStrategy(tableShardingStrategy)
                .build();

        return ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(shardingDataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.baomidou.mybatisplus.samples.deluxe.entity");

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {

        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() throws Exception {

        return new DataSourceTransactionManager(shardingDataSource());
    }


    @Bean
    public DataSource acDataSource() throws Exception {
        Properties properties = new Properties();
        properties.put("driverClassName",acDriverClassName);
        properties.put("url", acUrl);
        properties.put("username", acUsername);
        properties.put("password", acPassword);
        return BasicDataSourceFactory.createDataSource(properties);
    }


}

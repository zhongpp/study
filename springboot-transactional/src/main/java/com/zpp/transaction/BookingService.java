package com.zpp.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookingService {

    private final static Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final JdbcTemplate jdbcTemplate;

    public BookingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    AfterTransactionOpt afterTransactionOpt;

    @Transactional
    public void book(String... persons) throws Exception {
        int i = 0;
        for (String person : persons) {
            logger.info("Booking " + person + " in a seat...");
            jdbcTemplate.update("insert into BOOKINGS(FIRST_NAME) values (?)", person);
            //testTransaction(i);
            i++;
        }
        afterTransactionOpt.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("--after 开始执行后置操作5s....");
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Transactional
    public void testTransaction(int i) throws Exception {
        jdbcTemplate.update("insert into BOOKINGS(FIRST_NAME) values (?)", "Test_transaction" + 0);
        if (i == 1) {
            throw new Exception("失败了");
        }
    }

    public List<String> findAllBookings() {
        return jdbcTemplate.query("select FIRST_NAME from BOOKINGS",
                (rs, rowNum) -> rs.getString("FIRST_NAME"));
    }

}

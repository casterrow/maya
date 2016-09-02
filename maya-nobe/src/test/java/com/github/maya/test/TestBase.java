package com.github.maya.test;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author ste7en.liu@gmail.com
 * @since 2016/6/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@Transactional(transactionManager = "transactionManager")
@Rollback(value = false)
public class TestBase extends AbstractTransactionalJUnit4SpringContextTests {

}

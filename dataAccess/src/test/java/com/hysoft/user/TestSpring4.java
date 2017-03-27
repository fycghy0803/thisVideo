package com.hysoft.user;

import com.hysoft.video.user.service.BeanFactoryAware1;
import com.hysoft.video.user.service.BeanFactoryAware1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yulifan on 2017/3/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class TestSpring4 {
    @Autowired
    BeanFactoryAware1 beanFactoryAware1;

    @Test
    public void testSpring4() {

    }
}

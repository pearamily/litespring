package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTest {
    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

}

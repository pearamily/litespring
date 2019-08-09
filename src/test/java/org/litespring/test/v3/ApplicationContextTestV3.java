package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;

import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v3.AccountDao;
import org.litespring.dao.v3.ItemDao;
import org.litespring.service.v3.PetStoreService;

import static org.junit.Assert.assertNotNull;

public class ApplicationContextTestV3 {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());
        assertNotNull(petStore.getVersion());

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao()  instanceof ItemDao);
//
//        assertEquals("liuxin", petStore.getOwner());
//        assertEquals(2, petStore.getVersion());//test int type of petstore--> this place shoud use type converter



    }
}

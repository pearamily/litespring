package org.litespring.test.v2;

import org.junit.Test;
import static  org.junit.Assert.*;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;

public class ApplicationContextTestV2 {
    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao()  instanceof ItemDao);

        assertEquals("liuxin", petStore.getOwner());
        assertEquals(2, petStore.getVersion());//test int type of petstore--> this place shoud use type converter



    }




}

package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.annotation.AutoWiredAnnotationProcessor;
import org.litespring.beans.factory.annotation.AutoWiredFieldElement;
import org.litespring.beans.factory.annotation.InjectionElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.List;

public class AutoWiredAnnotationProcessTest {


    //mock type
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();

    DefaultBeanfactory factory = new  DefaultBeanfactory() {

                @Override
                public Object resolveDependency(DependencyDescriptor descriptor) {
                    if (descriptor.getDependencyType().equals(AccountDao.class)) {
                        return accountDao;
                    }
                    if (descriptor.getDependencyType().equals(ItemDao.class)) {
                        return itemDao;
                    }
                    throw new RuntimeException("Can't support types except AccountDao and ItemDao");
                }
            };

@Test
    public void testGetInjectionMetadata() {
        AutoWiredAnnotationProcessor processor = new AutoWiredAnnotationProcessor();
//        processor.setBeanFactory(factory);

        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements, "accountDao");
        assertFieldExists(elements, "itemDao");

//    PetStoreService petStore = new PetStoreService();
//    injectionMetadata.inject(petStore);
//
//    Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
//    Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);

}

    private void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for (InjectionElement ele : elements) {
            AutoWiredFieldElement fieldEle = (AutoWiredFieldElement) ele;
            Field f = fieldEle.getField();
            if (f.getName().equals(fieldName)) {
                return;
            }

        }
        Assert.fail(fieldName + "does not exist!");

    }
}

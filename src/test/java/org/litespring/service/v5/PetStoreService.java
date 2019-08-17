package org.litespring.service.v5;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.stereotype.Component;
import org.litespring.util.MessageTracker;

@Component(value = "petStore")
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void placeOrder() {
        System.out.println("place holder");
        MessageTracker.addMsg("place holder");

    }
}

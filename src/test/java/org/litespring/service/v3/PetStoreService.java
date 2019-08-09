package org.litespring.service.v3;


import org.litespring.dao.v3.AccountDao;
import org.litespring.dao.v3.ItemDao;

public class PetStoreService {

    private AccountDao accountDao;
    private ItemDao itemDao;
    private int version;


    public PetStoreService(AccountDao accoutDao, ItemDao itemDao, int version) {
        this.accountDao = accoutDao;
        this.itemDao = itemDao;
        this.version = version;
    }
    public PetStoreService(AccountDao accoutDao, ItemDao itemDao) {
        this.accountDao = accoutDao;
        this.itemDao = itemDao;
        this.version = -1;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public int getVersion() {
        return version;
    }
}

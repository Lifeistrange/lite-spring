package com.lifeiscoding.test.service;

import com.lifeiscoding.test.dao.AccountDao;
import com.lifeiscoding.test.dao.ItemDao;

public class PetStoreService {

    private AccountDao accountDao;

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    private ItemDao itemDao;


}

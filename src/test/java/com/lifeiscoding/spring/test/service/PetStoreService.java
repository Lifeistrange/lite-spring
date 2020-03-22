package com.lifeiscoding.spring.test.service;


import com.lifeiscoding.spring.test.dao.AccountDao;
import com.lifeiscoding.spring.test.dao.ItemDao;

public class PetStoreService {

    private AccountDao accountDao;
    private ItemDao itemDao;
    private String owner;
    private int version;
    private boolean aSwitch;

    public void PetStoreService() {}

    public void PetStoreService(AccountDao accountDao, ItemDao itemDao, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean getaSwitch() {
        return aSwitch;
    }

    public void setaSwitch(boolean aSwitch) {
        this.aSwitch = aSwitch;
    }
}

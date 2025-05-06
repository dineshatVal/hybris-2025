package com.sample.module.core.customer.impl;

import com.sample.module.core.customer.CustomerLocModelService;
import com.sample.module.core.customer.dao.CustomCustomerLocDao;

import javax.annotation.Resource;

public class DefaultCustomerLocModelService implements CustomerLocModelService {

    @Resource(name = "customCustomerLocDao")
    private CustomCustomerLocDao customCustomerLocDao;

    @Override
    public int createCustomCustomerLocUid() {
        int nextUid = customCustomerLocDao.getMaxUid() + 1;
        return nextUid;
    }
}

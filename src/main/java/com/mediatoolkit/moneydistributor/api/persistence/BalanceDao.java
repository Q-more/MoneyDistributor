package com.mediatoolkit.moneydistributor.api.persistence;

import com.mediatoolkit.moneydistributor.api.persistence.entity.Balance;
import com.mediatoolkit.moneydistributor.api.persistence.entity.GroupBalance;
import com.mediatoolkit.moneydistributor.api.persistence.impl.BalanceDaoImpl;

import java.util.List;

public interface BalanceDao {

    void increment(Long firstUserId, Long secondUserId, Double amount);

    void increment(List<BalanceDaoImpl.BalanceDaoEntry> balanceEntries);

    List<Balance> getBalancesAsLender(Long lenderId);

    List<Balance> getBalancesAsBorrower(Long borrowerId);

    List<GroupBalance> getBalancesBetweenUsers(List<Long> users);
}

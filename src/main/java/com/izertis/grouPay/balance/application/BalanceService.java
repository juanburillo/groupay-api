package com.izertis.grouPay.balance.application;

import com.izertis.grouPay.balance.domain.Balance;
import com.izertis.grouPay.expense.application.ExpenseService;
import com.izertis.grouPay.expense.domain.Expense;
import com.izertis.grouPay.friend.application.FriendService;
import com.izertis.grouPay.friend.domain.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService {

    private final FriendService friendService;
    private final ExpenseService expenseService;

    @Autowired
    public BalanceService(FriendService friendService, ExpenseService expenseService) {
        this.friendService = friendService;
        this.expenseService = expenseService;
    }

    public List<Balance> getBalances() {
        List<Balance> balances = new ArrayList<>();
        List<Friend> friends = friendService.getFriends();

        for (Friend friend : friends) {
            balances.add(
                    this.getBalance(friend.getId())
            );
        }

        return balances;
    }

    public Balance getBalance(Long friendId) {
        double friendExpenses = 0.0;
        double totalExpenses = 0.0;

        for (Expense expense : expenseService.getExpenses()) {
            double amount = expense.getAmount();
            if (expense.getFriend().getId().equals(friendId)) {
                friendExpenses += amount;
            }
            totalExpenses += amount;
        }

        double result = friendExpenses - (totalExpenses / friendService.getFriends().size());
        result = Math.round(result * 100.0) / 100.0;

        return new Balance(friendService.getFriend(friendId), result);
    }

}

package com.izertis.grouPay.transaction.application;

import com.izertis.grouPay.balance.application.BalanceService;
import com.izertis.grouPay.balance.domain.Balance;
import com.izertis.grouPay.transaction.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final BalanceService balanceService;

    @Autowired
    public TransactionService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        List<Balance> balances = balanceService.getBalances();
        for (Balance balance : balances) {
            double balanceAmount = balance.getAmount();
            if (balanceAmount < 0.0) { // Friend owes money
                for (Balance otherBalance : balances) {
                    double otherBalanceAmount = otherBalance.getAmount();
                    if (otherBalanceAmount > 0.0) { // Friend is owed money
                        double transactionAmount = Math.min(Math.abs(balanceAmount), otherBalanceAmount);
                        transactions.add(new Transaction(
                                balance.getFriend(), // sender
                                otherBalance.getFriend(), // recipient
                                transactionAmount // amount
                        ));
                        balanceAmount += transactionAmount;

                        // Adjust the recipient's balance
                        otherBalance.setAmount(otherBalanceAmount - transactionAmount);

                        if (balanceAmount >= 0) break; // Debt cleared for this friend
                    }
                }
            }
        }

        // Round the amount in each transaction
        for (Transaction transaction : transactions) {
            transaction.setAmount(Math.round(transaction.getAmount() * 100.0) / 100.0);
        }

        return transactions;
    }

}

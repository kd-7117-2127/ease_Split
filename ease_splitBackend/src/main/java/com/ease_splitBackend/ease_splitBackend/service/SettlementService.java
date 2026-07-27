package com.ease_splitBackend.ease_splitBackend.service;

import com.ease_splitBackend.ease_splitBackend.dto.BalanceResponse;
import com.ease_splitBackend.ease_splitBackend.dto.SettlementResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class SettlementService {

    private final BalanceService balanceService;

    public SettlementService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public List<SettlementResponse> calculateSettlements(
            Long eventId) {

        List<BalanceResponse> balances =
                balanceService.calculateBalances(eventId);

        PriorityQueue<BalanceEntry> creditors =
                new PriorityQueue<>(
                        Comparator.comparing(
                                BalanceEntry::getAmount
                        ).reversed()
                );

        PriorityQueue<BalanceEntry> debtors =
                new PriorityQueue<>(
                        Comparator.comparing(
                                BalanceEntry::getAmount
                        ).reversed()
                );

        for (BalanceResponse balance : balances) {

            int comparison =
                    balance.getBalance()
                            .compareTo(BigDecimal.ZERO);

            if (comparison > 0) {

                creditors.add(
                        new BalanceEntry(
                                balance.getUserId(),
                                balance.getName(),
                                balance.getBalance()
                        )
                );

            } else if (comparison < 0) {

                debtors.add(
                        new BalanceEntry(
                                balance.getUserId(),
                                balance.getName(),
                                balance.getBalance().abs()
                        )
                );
            }
        }

        List<SettlementResponse> settlements =
                new ArrayList<>();

        while (!creditors.isEmpty()
                && !debtors.isEmpty()) {

            BalanceEntry creditor = creditors.poll();
            BalanceEntry debtor = debtors.poll();

            BigDecimal settlementAmount =
                    creditor.getAmount()
                            .min(debtor.getAmount());

            settlements.add(
                    new SettlementResponse(
                            debtor.getUserId(),
                            debtor.getName(),
                            creditor.getUserId(),
                            creditor.getName(),
                            settlementAmount
                    )
            );

            BigDecimal remainingCredit =
                    creditor.getAmount()
                            .subtract(settlementAmount);

            BigDecimal remainingDebt =
                    debtor.getAmount()
                            .subtract(settlementAmount);

            if (remainingCredit
                    .compareTo(BigDecimal.ZERO) > 0) {

                creditor.setAmount(remainingCredit);
                creditors.add(creditor);
            }

            if (remainingDebt
                    .compareTo(BigDecimal.ZERO) > 0) {

                debtor.setAmount(remainingDebt);
                debtors.add(debtor);
            }
        }

        return settlements;
    }
}

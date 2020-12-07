package pets.service.utils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pets.models.model.RefMerchant;
import pets.models.model.RefMerchantFilters;
import pets.models.model.RefMerchantResponse;
import pets.models.model.Transaction;

import static java.lang.Character.isAlphabetic;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;
import static pets.service.utils.Constants.SYSTEM_DEPENDENT_MERCHANTS;

public class MerchantHelper {

    public static RefMerchantResponse applyFilters(RefMerchantResponse refMerchantResponse, RefMerchantFilters refMerchantFilters,
                                                   List<Transaction> transactions) {
        List<RefMerchant> merchants = refMerchantResponse.getRefMerchants();
        Set<String> refMerchantsFilterList = refMerchantResponse.getRefMerchantsFilterList();

        if (refMerchantFilters.isNotUsedInTransactionsOnly() && !isEmpty(transactions)) {
            merchants = applyMerchantNotUsedInTransactionFilter(merchants, transactions);
        } else if (hasText(refMerchantFilters.getFirstChar())) {
            merchants = applyMerchantNameBeginsWithFilter(merchants, refMerchantFilters.getFirstChar().charAt(0));
        }

        return RefMerchantResponse.builder()
                .refMerchants(merchants)
                .refMerchantsFilterList(refMerchantsFilterList)
                .build();
    }

    public static Set<String> getRefMerchantsFilterListByName(RefMerchantResponse refMerchantResponse) {
        Set<String> firstLetters = new TreeSet<>();

        refMerchantResponse.getRefMerchants()
                .forEach(refMerchant -> {
                    if (isAlphabetic(refMerchant.getDescription().charAt(0))) {
                        firstLetters.add(valueOf(refMerchant.getDescription().charAt(0)));
                    } else {
                        firstLetters.add("#");
                    }
                });

        return firstLetters;
    }

    public static void setSystemDependentMerchants(RefMerchantResponse refMerchantResponse) {
        refMerchantResponse.getRefMerchants()
                .forEach(refMerchant -> {
                    if (SYSTEM_DEPENDENT_MERCHANTS.contains(refMerchant.getId())) {
                        refMerchant.setNotEditable(true);
                    }
                });
    }

    private static List<RefMerchant> applyMerchantNameBeginsWithFilter(List<RefMerchant> merchantList, char firstChar) {
        if (isAlphabetic(firstChar)) {
            return merchantList.stream()
                    .filter(refMerchant -> refMerchant.getDescription().charAt(0) == firstChar)
                    .collect(toList());
        } else {
            return merchantList.stream()
                    .filter(refMerchant -> !isAlphabetic(refMerchant.getDescription().charAt(0)))
                    .collect(toList());
        }
    }

    private static List<RefMerchant> applyMerchantNotUsedInTransactionFilter(List<RefMerchant> merchantList,
                                                                             List<Transaction> transactions) {
        Set<String> merchantIdsInTransactions = transactions.stream()
                .map(transaction -> transaction.getRefMerchant().getId())
                .collect(toSet());

        return merchantList.stream()
                .filter(refMerchant -> !merchantIdsInTransactions.contains(refMerchant.getId()))
                .collect(toList());
    }
}

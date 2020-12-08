package pets.database.utils;

import static java.util.stream.Collectors.toList;

import java.util.List;

import lombok.NonNull;
import pets.database.model.AccountDto;
import pets.database.model.RefAccountTypeDto;
import pets.database.model.RefBankDto;
import pets.database.model.RefCategoryDto;
import pets.database.model.RefCategoryTypeDto;
import pets.database.model.RefMerchantDto;
import pets.database.model.RefTransactionTypeDto;
import pets.database.model.TransactionDto;
import pets.database.model.UserDto;
import pets.models.model.Account;
import pets.models.model.RefAccountType;
import pets.models.model.RefBank;
import pets.models.model.RefCategory;
import pets.models.model.RefCategoryType;
import pets.models.model.RefMerchant;
import pets.models.model.RefTransactionType;
import pets.models.model.Transaction;
import pets.models.model.User;

public class Commons {
	
	public static List<Account> convertDtoToEntityAccounts(final @NonNull List<AccountDto> accountDtos) {
		return accountDtos.stream()
				.map(accountDto -> convertDtoToEntityAccount(accountDto))
				.collect(toList());
	}
	
	private static Account convertDtoToEntityAccount(final AccountDto accountDto) {
		if (accountDto == null) {
			return Account.builder()
					.build();
		}
		
		return Account.builder()
				.id(accountDto.getId())
				.refAccountType(convertDtoToEntityRefAccountType(accountDto.getRefAccountType()))
				.refBank(convertDtoToEntityRefBank(accountDto.getRefBank()))
				.description(accountDto.getDescription())
				.user(convertDtoToEntityUser(accountDto.getUser()))
				.openingBalance(accountDto.getOpeningBalance())
				.status(accountDto.getStatus())
				.creationDate(accountDto.getCreationDate())
				.lastModified(accountDto.getLastModified())
				.build();
	}
	
	public static List<RefAccountType> convertDtoToEntityRefAccountTypes(final @NonNull List<RefAccountTypeDto> refAccountTypeDtos) {
		return refAccountTypeDtos.stream()
				.map(refAccountTypeDto -> convertDtoToEntityRefAccountType(refAccountTypeDto))
				.collect(toList());
	}
	
	private static RefAccountType convertDtoToEntityRefAccountType(final RefAccountTypeDto refAccountTypeDto) {
		if (refAccountTypeDto == null) {
			return RefAccountType.builder()
					.build();
		}
		
		return RefAccountType.builder()
				.id(refAccountTypeDto.getId())
				.description(refAccountTypeDto.getDescription())
				.creationDate(refAccountTypeDto.getCreationDate())
				.lastModified(refAccountTypeDto.getLastModified())
				.build();
	}
	
	public static List<RefBank> convertDtoToEntityRefBanks(final @NonNull List<RefBankDto> refBankDtos) {
		return refBankDtos.stream()
				.map(refBankDto -> convertDtoToEntityRefBank(refBankDto))
				.collect(toList());
	}
	
	private static RefBank convertDtoToEntityRefBank(final RefBankDto refBankDto) {
		if (refBankDto == null) {
			return RefBank.builder()
					.build();
		}
		
		return RefBank.builder()
				.id(refBankDto.getId())
				.description(refBankDto.getDescription())
				.creationDate(refBankDto.getCreationDate())
				.lastModified(refBankDto.getLastModified())
				.build();
	}
	
	public static List<RefCategory> convertDtoToEntityRefCategories(final @NonNull List<RefCategoryDto> refCategoryDtos) {
		return refCategoryDtos.stream()
				.map(refCategoryDto -> convertDtoToEntityRefCategory(refCategoryDto))
				.collect(toList());
	}
	
	private static RefCategory convertDtoToEntityRefCategory(final RefCategoryDto refCategoryDto) {
		if (refCategoryDto == null) {
			return RefCategory.builder()
					.build();
		}
		
		return RefCategory.builder()
				.id(refCategoryDto.getId())
				.description(refCategoryDto.getDescription())
				.refCategoryType(convertDtoToEntityRefCategoryType(refCategoryDto.getRefCategoryType()))
				.creationDate(refCategoryDto.getCreationDate())
				.lastModified(refCategoryDto.getLastModified())
				.build();
	}
	
	public static List<RefCategoryType> convertDtoToEntityRefCategoryTypes(final @NonNull List<RefCategoryTypeDto> refCategoryTypeDtos) {
		return refCategoryTypeDtos.stream()
				.map(refCategoryTypeDto -> convertDtoToEntityRefCategoryType(refCategoryTypeDto))
				.collect(toList());
	}
	
	private static RefCategoryType convertDtoToEntityRefCategoryType(final RefCategoryTypeDto refCategoryTypeDto) {
		if (refCategoryTypeDto == null) {
			return RefCategoryType.builder()
					.build();
		}
		
		return RefCategoryType.builder()
				.id(refCategoryTypeDto.getId())
				.description(refCategoryTypeDto.getDescription())
				.creationDate(refCategoryTypeDto.getCreationDate())
				.lastModified(refCategoryTypeDto.getLastModified())
				.build();
	}
	
	public static List<RefMerchant> convertDtoToEntityRefMerchants(final @NonNull List<RefMerchantDto> refMerchantDtos) {
		return refMerchantDtos.stream()
				.map(refMerchantDto -> convertDtoToEntityRefMerchant(refMerchantDto))
				.collect(toList());
	}
	
	private static RefMerchant convertDtoToEntityRefMerchant(final RefMerchantDto refMerchantDto) {
		if (refMerchantDto == null) {
			return RefMerchant.builder()
					.build();
		}
		
		return RefMerchant.builder()
				.id(refMerchantDto.getId())
				.description(refMerchantDto.getDescription())
				.user(convertDtoToEntityUser(refMerchantDto.getUser()))
				.creationDate(refMerchantDto.getCreationDate())
				.lastModified(refMerchantDto.getLastModified())
				.build();
	}
	
	public static List<RefTransactionType> convertDtoToEntityRefTransactionTypes(final @NonNull List<RefTransactionTypeDto> refTransactionTypeDtos) {
		return refTransactionTypeDtos.stream()
				.map(refTransactionTypeDto -> convertDtoToEntityRefTransactionType(refTransactionTypeDto))
				.collect(toList());
	}
	
	private static RefTransactionType convertDtoToEntityRefTransactionType(final RefTransactionTypeDto refTransactionTypeDto) {
		if (refTransactionTypeDto == null) {
			return RefTransactionType.builder()
					.build();
		}
		
		return RefTransactionType.builder()
				.id(refTransactionTypeDto.getId())
				.description(refTransactionTypeDto.getDescription())
				.creationDate(refTransactionTypeDto.getCreationDate())
				.lastModified(refTransactionTypeDto.getLastModified())
				.build();
	}
	
	public static List<Transaction> convertDtoToEntityTransactions(final @NonNull List<TransactionDto> transactionDtos) {
		return transactionDtos.stream()
				.map(transactionDto -> convertDtoToEntityTransaction(transactionDto))
				.collect(toList());
	}
	
	private static Transaction convertDtoToEntityTransaction(final TransactionDto transactionDto) {
		if (transactionDto == null) {
			return Transaction.builder()
					.build();
		}
		
		return Transaction.builder()
				.id(transactionDto.getId())
				.description(transactionDto.getDescription())
				.account(convertDtoToEntityAccount(transactionDto.getAccount()))
				.trfAccount(convertDtoToEntityAccount(transactionDto.getTrfAccount()))
				.refTransactionType(convertDtoToEntityRefTransactionType(transactionDto.getRefTransactionType()))
				.refCategory(convertDtoToEntityRefCategory(transactionDto.getRefCategory()))
				.refMerchant(convertDtoToEntityRefMerchant(transactionDto.getRefMerchant()))
				.user(convertDtoToEntityUser(transactionDto.getUser()))
				.date(transactionDto.getDate())
				.amount(transactionDto.getAmount())
				.regular(transactionDto.getRegular())
				.necessary(transactionDto.getNecessary())
				.build();
	}
	
	public static List<User> convertDtoToEntityUsers(final @NonNull List<UserDto> userDtos) {
		return userDtos.stream()
				.map(userDto -> convertDtoToEntityUser(userDto))
				.collect(toList());
	}
	
	private static User convertDtoToEntityUser(final UserDto userDto) {
		if (userDto == null) return null;
		
		return User.builder()
				.id(userDto.getId())
				.username(userDto.getUsername())
				.password(userDto.getPassword())
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.streetAddress(userDto.getStreetAddress())
				.city(userDto.getCity())
				.state(userDto.getState())
				.zipcode(userDto.getZipcode())
				.email(userDto.getEmail())
				.phone(userDto.getPhone())
				.status(userDto.getStatus())
				.creationDate(userDto.getCreationDate())
				.lastModified(userDto.getLastModified())
				.build();
	}
}

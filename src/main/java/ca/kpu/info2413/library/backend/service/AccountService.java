package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.repository.AccountRepository;
import ca.kpu.info2413.library.backend.repository.LibraryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService
{
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    LibraryCardRepository libraryCardRepository;

    public List<Account> findAll()
    {
        return accountRepository.findAll();
    }

    public List<Account> findByAccountId(Integer accountId)
    {
        return accountRepository.findByAccountId(accountId);
    }

    public Account save(Account account)
    {
        return accountRepository.save(account);
    }

    public void deleteByAccountId(Integer accountId)
    {
        accountRepository.deleteById(accountId);
    }

    /// ////////////////////


    //redo using service later
    public List<Account> findByLibraryCard(Integer libraryCard)
    {
        return accountRepository.findByAccountId(libraryCardRepository.findByCardNumber(libraryCard).getFirst().getAccountIdAccount());
    }

    public List<Account> findByFullName(String fullName)
    {
        return accountRepository.findByFullName(fullName);
    }

    public List<Account> findByPhoneNumber(String phoneNumber)
    {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }

    public List<Account> findByNotificationEmail(String notificationEmail)
    {
        return accountRepository.findByNotificationEmail(notificationEmail);
    }

    public List<Account> findByNotificationEmailIgnoreCase(String notificationEmail)
    {
        return accountRepository.findByNotificationEmailIgnoreCase(notificationEmail);
    }

}


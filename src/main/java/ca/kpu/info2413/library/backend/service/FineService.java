package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Borrow;
import ca.kpu.info2413.library.backend.model.Configuration;
import ca.kpu.info2413.library.backend.model.Fine;
import ca.kpu.info2413.library.backend.repository.BorrowRepository;
import ca.kpu.info2413.library.backend.repository.ConfigurationRepository;
import ca.kpu.info2413.library.backend.repository.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class FineService
{
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    private FineRepository fineRepository;


    public List<Fine> findAll()
    {
        return fineRepository.findAll();
    }

    public List<Fine> findByFineId(Integer fineId)
    {
        return fineRepository.findByFineId(fineId);
    }

    public Fine save(Fine fine)
    {
        return fineRepository.save(fine);
    }

    public void deleteByFineId(Integer fineId)
    {
        fineRepository.deleteById(fineId);
    }

    /// ////////////////////


    //redo using service later
    public List<Fine> findByBorrowIdBorrow(Integer borrowIdBorrow)
    {
        return fineRepository.findByBorrowIdBorrow(borrowIdBorrow);
    }

    //To search if an account has a fine or list accounts that have fines??
    public List<Fine> findByAccountId(Integer accountId)
    {
        ArrayList<Fine> fineList = new ArrayList<>();

        for (Borrow b : borrowRepository.findByAccountIdAccount(accountId))
        {
            fineList.addAll(fineRepository.findByBorrowIdBorrow(b.getBorrowId()));
        }

        return fineList;
    }


    // just the scheduled job
    @Scheduled(cron = "0 0 * * * *")
    //@Scheduled(fixedRate = 60000)
    public void updateFineOnSchedule()
    {
        updateAllFines();
    }

    // check all borrow records
    //      insert new records into Fine if overdue
    //      if there is already a fine then recalculate
    public void updateAllFines()
    {
        LocalDate today = LocalDate.now();
        // get all overdue borrows where status is still "Borrowed"
        List<Borrow> overdueBorrows = borrowRepository.findByDueDateBeforeAndStatus(today, "Borrowed");
        Optional<Configuration> config = configurationRepository.findById("fineMultiplier");
        int multiplier;

        multiplier = config.map(configuration -> Integer.parseInt(configuration.getConfigValue())).orElse(1);
        //System.err.println("MULTIPLIER: " + multiplier);
        for (Borrow borrow : overdueBorrows)
        {
            long fineDays = DAYS.between(borrow.getDueDate(), today);
            int fineAmount = (int) fineDays * multiplier;

            // check if Fine already exists for this borrowId
            List<Fine> existingFine = fineRepository.findByBorrowIdBorrow(borrow.getBorrowId());

            if (existingFine.isEmpty())
            {
                // create new record in Fine
                Fine newFine = new Fine();
                newFine.setBorrowIdBorrow(borrow.getBorrowId());
                newFine.setFineAmount(fineAmount);
                newFine.setIssueDate(borrow.getDueDate().plusDays(1));
                newFine.setWaivedReversed(false);

                fineRepository.save(newFine);

            }
            else
            { // if already exists
                // update Fine records in Fine table
                Fine fine = existingFine.getFirst();
                fine.setFineAmount(fineAmount);

                fineRepository.save(fine);
            }
        }
    }
}

package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Borrow;
import ca.kpu.info2413.library.backend.repository.BookCopyRepository;
import ca.kpu.info2413.library.backend.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService
{

    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    AccountPublicationService accountPublicationService;

    public List<Borrow> findAll()
    {
        return borrowRepository.findAll();
    }

    public List<Borrow> findByBorrowId(Integer borrowId)
    {
        return borrowRepository.findByBorrowId(borrowId);
    }

    public Borrow save(Borrow borrow)
    {
        bookCopyRepository.findBySerialBarcode(borrow.getSerialBarcodeBookCopy()).ifPresent((copy) -> {
            accountPublicationService.processWaitlistForPublication(copy.getPublication().getIsbn13());
        });
        return borrowRepository.save(borrow);
    }

    public void deleteByBorrowId(Integer borrowId)
    {
        borrowRepository.deleteByBorrowId(borrowId);
    }

    public List<Borrow> findBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy)
    {
        return borrowRepository.findBySerialBarcodeBookCopy(serialBarcodeBookCopy);
    }

    public List<Borrow> findByAccountIdAccount(Integer accountIdAccount)
    {
        return borrowRepository.findByAccountIdAccount(accountIdAccount);
    }
}
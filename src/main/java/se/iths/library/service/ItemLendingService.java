package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.dto.BorrowedItemsDTO;
import se.iths.library.repository.ItemLendingRepository;
import java.util.List;

@Service
public class ItemLendingService {

    @Autowired
    ItemLendingRepository itemLendingRepository;

    public List<BorrowedItemsDTO> findBorrowedItemsAndCreationDueDateByUserEmail(String email){
        return itemLendingRepository.findByBorrowedItemByUserEmail(email);
    }
    public List<BorrowedItemsDTO> findBorrowedItemsAndCreationDueDateByUserId(Long id){
        return itemLendingRepository.findByBorrowedItemByUserId(id);
    }
}

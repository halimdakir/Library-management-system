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

    public List<BorrowedItemsDTO> findBorrowedItemsAndCreationDueDateByUserId(String email){
        return itemLendingRepository.findByItem_Id(email);
    }
}

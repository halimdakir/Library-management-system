package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.dto.BorrowedItemsDTO;
import se.iths.library.entity.ItemLending;
import se.iths.library.entity.User;
import se.iths.library.repository.ItemLendingRepository;
import java.util.List;
import java.util.Optional;

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
    public List<BorrowedItemsDTO> findReservedItemsAndCreationDueDateByUserId(Long id){
        return itemLendingRepository.findByReservedItemByUserId(id);
    }
    public ItemLending findReservedItemByBarCode(String reservedItemBarCode) {
        return itemLendingRepository.findByItem_BarCode(reservedItemBarCode);
    }
    public void deleteReservedItem(Long id){
        Optional<ItemLending> itemLending = itemLendingRepository.findById(id);
        if (itemLending.isPresent()){
            itemLendingRepository.deleteById(id);
        }
    }
}

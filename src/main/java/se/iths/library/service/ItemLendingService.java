package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.dto.BorrowedItemsDTO;
import se.iths.library.dto.ReservedItemDTO;
import se.iths.library.entity.ItemLending;
import se.iths.library.exception.NotFoundException;
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
    public List<ReservedItemDTO> getAllReservedItems(){
        return itemLendingRepository.findAllReservedItemsByUserId();
    }
    public ItemLending getReservedItemById(Long id){
        return itemLendingRepository.findItemLendingById(id);
    }

    public ItemLending updateBorrowedItem(ItemLending itemLending, Long id){
        return itemLendingRepository.findById(id)
                .map(itemLending1 -> {
                    itemLending1.setCreationDate(itemLending.getCreationDate());
                    itemLending1.setDueDate(itemLending.getDueDate());
                    itemLending1.setReturned(itemLending.isReturned());
                    itemLending1.setConfirmed(itemLending.isConfirmed());
                    return itemLendingRepository.save(itemLending1);
                })
                .orElseThrow(() -> new NotFoundException("The borrowed item not found with id :" + id)
                );
    }
    public ItemLending findItemLendingById(Long id){
        return itemLendingRepository.findItemLendingById(id);
    }
    public void returnBorrowedItem(Long id){
      itemLendingRepository.findById(id)
                .map(itemLending1 -> {
                    itemLending1.setReturned(true);
                     return itemLendingRepository.save(itemLending1);
                })
                .orElseThrow(() -> new NotFoundException("The borrowed item not found with id :" + id));
    }

}

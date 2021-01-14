package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Item;
import se.iths.library.exception.NotFoundException;
import se.iths.library.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(Item item){
        return itemRepository.save(item);
    }
    public void deleteItemById(Long id){
        Optional<Item> foundItem = itemRepository.findById(id);
        itemRepository.deleteById(foundItem.get().getId());
    }
    public Optional<Item> getItemById(Long id){
        return itemRepository.findById(id);
    }
    public Iterable<Item> getAllItems(){
        return itemRepository.findAll();
    }
    public Iterable<Item> findItemByTitle(String title){
        return itemRepository.findItemByTitle(title);
    }
    public Iterable<Item> findItemByAuthorName(String fullName){
        return itemRepository.findItemByAuthorName(fullName);
    }
    public Item updateItem(Item newItem, Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setBarCode(newItem.getBarCode());
                    item.setTitle(newItem.getTitle());
                    item.setDescription(newItem.getDescription());
                    return itemRepository.save(item);
                })
                .orElseThrow(() -> new NotFoundException("Item not found with id :" + id)
                );
    }
    public Item findItemByBarCode(String barCode){
        return itemRepository.findItemByBarCode(barCode);
    }
    public Item findItemById(Long id){
        return itemRepository.findItemById(id);
    }
}

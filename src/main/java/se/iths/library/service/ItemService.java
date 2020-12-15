package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Item;
import se.iths.library.repository.ItemRepository;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
           }

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
    public Iterable<Item>findLendingItemsByUserId(Long id){
        return itemRepository.findLendingItemsByUserId(id);
    }
}

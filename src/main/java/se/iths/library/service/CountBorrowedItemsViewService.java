package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.repository.CountBorrowedItemsViewRepository;
import se.iths.library.entity.CountBorrowedItemsView;

import java.util.List;

@Service
public class CountBorrowedItemsViewService {
    @Autowired
    private CountBorrowedItemsViewRepository repository;


    public List<CountBorrowedItemsView> countBorrowedItemsViewList(){
        return (List<CountBorrowedItemsView>) repository.findAll();
    }
}

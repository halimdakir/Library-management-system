package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.repository.ItemLendingRepository;

@Service
public class ItemLendingService {

    @Autowired
    ItemLendingRepository itemLendingRepository;
}

package se.iths.library.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.iths.library.models.Categories;
import se.iths.library.models.Roles;
import se.iths.library.entity.*;
import se.iths.library.repository.*;

@Configuration
public class SetUpDataBase {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner initDatabase(ItemRepository itemRepository, AuthorRepository authorRepository, UserRepository userRepository, ItemLendingRepository itemLendingRepository, StockRepository stockRepository, CategoryRepository categoryRepository) {
        return args -> {
            if (itemRepository.count() == 0 && authorRepository.count() == 0 && userRepository.count() == 0 && itemLendingRepository.count()==0 && stockRepository.count()==0 && categoryRepository.count()==0) {
                var item1 = new Item("ABC123NNM", "Wood", "Den här boken vill upphöja våra vardagliga och högtidliga stunder och göra dem vackrare och varmare...");
                var item2 = new Item("RNM999THE", "Gå med mig till hörnet", "Elise är en bra bit över 50, hon lever i en stabil och ordnad tillvaro, gift med Henrik sedan 25...");
                var item3 = new Item("AAA000BBB", "Mitt framgångsår", "\"Mitt framgångsår\" är en inspirerande metod- och anteckningsbok där framgångsexperten Alexander...");
                var item4 = new Item("BAR444OBA", "Ett förlovat land", "En gripande, djupt personlig redogörelse om ögonblicken när historia skrivs - av presidenten som inspirerade...");
                var item5 = new Item("OVE555LEV", "Överlevarna", "Han trycker telefonen hårt mot örat. Varför ingriper han inte? Han ser ner för slänten som mynnar ut i sjön. Han ser ...");

                var author1 = new Author("Barack Obama", "1961/08/04");
                var author2 = new Author("Alex Schulman", "1976/02/17");
                var author3= new Author("Alexander Pärleros", "1985/03/09");
                var author4 = new Author("Anneli Furmark", "1962/04/01");


                var user1 = new User("Halim Dakir", "2000/11/11", "Maroc", new Login("halim@gmail.com", passwordEncoder.encode("123456"),true, Roles.ROLE_USER));
                var user2 = new User("Salim Maroc", "2000/11/11", "Maroc", new Login("salim@gmail.com", passwordEncoder.encode("654789"), true, Roles.ROLE_ADMIN));
                var user3 = new User("Simo Elyou", "2000/11/11", "Maroc", new Login("simo@gmail.com", passwordEncoder.encode("123456"), false, Roles.ROLE_USER));



                item1.getAuthors().add(author3);
                item1.getAuthors().add(author2);
                item2.getAuthors().add(author2);
                item4.getAuthors().add(author1);
                item3.getAuthors().add(author4);
                item5.getAuthors().add(author3);

                author3.getItems().add(item1);
                author2.getItems().add(item1);
                author2.getItems().add(item2);
                author1.getItems().add(item4);
                author4.getItems().add(item3);
                author3.getItems().add(item5);


                itemRepository.save(item1);
                itemRepository.save(item2);
                itemRepository.save(item3);
                itemRepository.save(item4);
                itemRepository.save(item5);

                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);

                itemLendingRepository.save(new ItemLending("2020-12-02", "2020-12-20", true, false, user1, item1));
                itemLendingRepository.save(new ItemLending("2020-12-03", "2020-12-21", true, false, user1, item2));
                itemLendingRepository.save(new ItemLending("2020-12-02", "2020-12-20", true, false, user3, item5));


                stockRepository.save(new Stock(20, item1));
                stockRepository.save(new Stock(15, item2));
                stockRepository.save(new Stock(30, item3));
                stockRepository.save(new Stock(8, item4));
                stockRepository.save(new Stock(16, item5));

                categoryRepository.save(new Category(Categories.BOOK, item1));
                categoryRepository.save(new Category(Categories.BOOK, item2));
                categoryRepository.save(new Category(Categories.DIGITAL_BOOK, item3));
                categoryRepository.save(new Category(Categories.DVD, item4));
                categoryRepository.save(new Category(Categories.DVD, item5));
            }
        };
    }
}

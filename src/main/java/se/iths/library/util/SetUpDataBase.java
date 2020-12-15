package se.iths.library.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.iths.library.entity.*;
import se.iths.library.repository.AuthorRepository;
import se.iths.library.repository.ItemLendingRepository;
import se.iths.library.repository.ItemRepository;
import se.iths.library.repository.UserRepository;

@Configuration
public class SetUpDataBase {

    @Bean
    CommandLineRunner initDatabase(ItemRepository itemRepository, AuthorRepository authorRepository, UserRepository userRepository, ItemLendingRepository itemLendingRepository) {
        return args -> {
            if (itemRepository.count() == 0 && authorRepository.count() == 0 && userRepository.count() == 0 && itemLendingRepository.count()==0) {
                var item1 = new Item("ABC123NNM", "Wood", "Den här boken vill upphöja våra vardagliga och högtidliga stunder och göra dem vackrare och varmare...");
                var item2 = new Item("RNM999THE", "Gå med mig till hörnet", "Elise är en bra bit över 50, hon lever i en stabil och ordnad tillvaro, gift med Henrik sedan 25...");
                var item3 = new Item("AAA000BBB", "Mitt framgångsår", "\"Mitt framgångsår\" är en inspirerande metod- och anteckningsbok där framgångsexperten Alexander...");
                var item4 = new Item("BAR444OBA", "Ett förlovat land", "En gripande, djupt personlig redogörelse om ögonblicken när historia skrivs - av presidenten som inspirerade...");
                var item5 = new Item("OVE555LEV", "Överlevarna", "Han trycker telefonen hårt mot örat. Varför ingriper han inte? Han ser ner för slänten som mynnar ut i sjön. Han ser ...");

                var author1 = new Author("Barack Obama", "1961/08/04");
                var author2 = new Author("Alex Schulman", "1976/02/17");
                var author3= new Author("Alexander Pärleros", "1985/03/09");
                var author4 = new Author("Anneli Furmark", "1962/04/01");


                var user1 = new User("Halim Dakir", "2000/11/11", "Maroc");
                var user2 = new User("Salim Salim", "2000/11/11", "Maroc");
                var user3 = new User("Dimo Simo", "2000/11/11", "Maroc");

                var login1 = new Login("Halim@gmail.com", "123456", false);
                var login2 = new Login("Salim@gmail.com", "654789", true);
                var login3 = new Login("Dimo@gmail.com", "9514862", false);

                var itemLending1 = new ItemLending();
                var itemLending2 = new ItemLending();
                var itemLending3 = new ItemLending();
                var itemLending4 = new ItemLending();
                var itemLending5 = new ItemLending();


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

                user1.setLogin(login1);
                login1.setUser(user1);

                user2.setLogin(login2);
                login2.setUser(user2);

                user3.setLogin(login3);
                login3.setUser(user3);

                itemRepository.save(item1);
                itemRepository.save(item2);
                itemRepository.save(item3);
                itemRepository.save(item4);
                itemRepository.save(item5);

                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);

                itemLendingRepository.save(new ItemLending("2020/12/02", "2020/12/20", "2020/12/15", login1));
                itemLendingRepository.save(new ItemLending("2020/12/03", "2020/12/21", "2020/12/13", login1));
                itemLendingRepository.save(new ItemLending("2020/12/02", "2020/12/20", "2020/12/15", login2));


            }
        };
    }
}

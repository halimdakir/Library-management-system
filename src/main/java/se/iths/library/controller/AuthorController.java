package se.iths.library.controller;

import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Author;
import se.iths.library.entity.Item;
import se.iths.library.service.AuthorService;

import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/id/{id}")
    public Optional<Author> getOneAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }
    @GetMapping("/all")
    public Iterable<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }
    @PostMapping("/new")
    public Author createNewAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }
    @DeleteMapping("/id/{id}")
    public void deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthorById(id);
    }
}

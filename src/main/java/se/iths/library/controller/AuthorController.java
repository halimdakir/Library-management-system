package se.iths.library.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Author;
import se.iths.library.entity.Item;
import se.iths.library.entity.User;
import se.iths.library.service.AuthorService;

import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/id/{id}")
    public Optional<Author> getOneAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public Iterable<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public Author createNewAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/id/{id}")
    public Author updateAuthor(@RequestBody Author author, @PathVariable Long id) {
        return authorService.updateAuthor(author, id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/id/{id}")
    public void deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthorById(id);
    }
}

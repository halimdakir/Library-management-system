package se.iths.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Author;
import se.iths.library.exception.DeleteDetails;
import se.iths.library.exception.NotFoundException;
import se.iths.library.exception.UnprocessableEntityException;
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
        var author =  authorService.getAuthorById(id);
        if (author.isPresent()){
            return author;
        }else {
            throw new NotFoundException("Author not found with id :" + id);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public Iterable<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public Author createNewAuthor(@RequestBody Author author) {
        if (author.getBirthDate() != null && author.getFullName() != null){
            if (author.getBirthDate().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")){
                return authorService.createAuthor(author);
            }else {
                throw new UnprocessableEntityException("Correct date format is : yyyy-MM-dd");
            }
        }else {
            throw new UnprocessableEntityException("Full name & date of birth are required!");
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/id/{id}")
    public Author updateAuthor(@RequestBody Author author, @PathVariable Long id) {
        return authorService.updateAuthor(author, id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<DeleteDetails> deleteAuthorById(@PathVariable Long id){
        var author =  authorService.getAuthorById(id);
        if (author.isPresent()){
            authorService.deleteAuthorById(id);
            return new ResponseEntity<>(new DeleteDetails("Delete request", "Author with id :"+id+" is successfully deleted!"), HttpStatus.OK);
        }else {
            throw new NotFoundException("Author not found with id :" + id);
        }
    }
}

package se.iths.library.controller;

import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Librarian;
import se.iths.library.entity.Member;
import se.iths.library.service.LibrarianService;

import java.util.Optional;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {
    private LibrarianService librarianService;

    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }
    @GetMapping("/id/{id}")
    public Optional<Librarian> getOneLibrarianById(@PathVariable Long id){
        return librarianService.getLibrarianById(id);
    }
    @GetMapping("/all")
    public Iterable<Librarian> getAllLibrarians(){
        return librarianService.getAllLibrarians();
    }
    @PostMapping("/new")
    public Librarian createNewLibrarian(@RequestBody Librarian librarian){
        return librarianService.createLibrarian(librarian);
    }
    @DeleteMapping("/id/{id}")
    public void deleteLibrarianById(@PathVariable Long id){
        librarianService.deleteLibrarianById(id);
    }
}

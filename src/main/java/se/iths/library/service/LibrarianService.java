package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Librarian;
import se.iths.library.entity.Member;
import se.iths.library.repository.LibrarianRepository;

import java.util.Optional;

@Service
public class LibrarianService {

    @Autowired
    LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public Librarian createLibrarian(Librarian librarian){
        return librarianRepository.save(librarian);
    }
    public void deleteLibrarianById(Long id){
        Optional<Librarian> foundLibrarian = librarianRepository.findById(id);
        librarianRepository.deleteById(foundLibrarian.get().getId());
    }
    public Optional<Librarian> getLibrarianById(Long id){
        return librarianRepository.findById(id);
    }
    public Iterable<Librarian> getAllLibrarians(){
        return librarianRepository.findAll();
    }
}

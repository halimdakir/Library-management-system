package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Author;
import se.iths.library.entity.Library;
import se.iths.library.repository.LibraryRepository;

import java.util.Optional;

@Service
public class LibraryService {
    @Autowired
    LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }
    public Library createLibrary(Library library){
        return libraryRepository.save(library);
    }
    public void deleteLibraryById(Long id){
        Optional<Library> foundLibrary = libraryRepository.findById(id);
        libraryRepository.deleteById(foundLibrary.get().getId());
    }
    public Optional<Library> getLibraryById(Long id){
        return libraryRepository.findById(id);
    }
    public Iterable<Library> getAllLibraries(){
        return libraryRepository.findAll();
    }
}

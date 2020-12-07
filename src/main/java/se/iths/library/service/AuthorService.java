package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Author;
import se.iths.library.repository.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }
    public void deleteAuthorById(Long id){
        Optional<Author> foundMember = authorRepository.findById(id);
        authorRepository.deleteById(foundMember.get().getId());
    }
    public Optional<Author> getAuthorById(Long id){
        return authorRepository.findById(id);
    }
    public Iterable<Author> getAllAuthors(){
        return authorRepository.findAll();
    }
}

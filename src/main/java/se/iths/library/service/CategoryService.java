package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Category;
import se.iths.library.models.Categories;
import se.iths.library.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    public Category createLibrary(Category category){
        return categoryRepository.save(category);
    }
    public void deleteCategoryById(Long id){
        Optional<Category> foundCategory = categoryRepository.findById(id);
        foundCategory.ifPresent(category -> categoryRepository.deleteById(category.getId()));
    }
    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }
    public List<Category> getAllCategories(){
        return (List<Category>) categoryRepository.findAll();
    }

    public List<Category> getItemByCategory(Categories categories){
        return categoryRepository.findAllByCategories(categories);
    }
}

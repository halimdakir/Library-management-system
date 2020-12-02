package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Login;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {
}
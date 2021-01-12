package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Login;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {
    Optional <Login> findByEmail(String email);
    Login findLoginByEmail(String email);
}

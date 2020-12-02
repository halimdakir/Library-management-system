package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
}

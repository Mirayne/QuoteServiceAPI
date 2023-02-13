package kameleoon.trialtask.quoteserviceapi.database.repository;

import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UsersTable, Long> {
}

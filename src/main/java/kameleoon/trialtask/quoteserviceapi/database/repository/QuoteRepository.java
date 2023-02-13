package kameleoon.trialtask.quoteserviceapi.database.repository;

import kameleoon.trialtask.quoteserviceapi.database.QuotesTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends CrudRepository<QuotesTable, Long> {

    @Query(value = "SELECT ID, CONTENT, DATE_OF_CREATION, IS_MODIFIED, SUM(RATING) as RATING, AUTHOR FROM QUOTES GROUP BY ID ORDER BY RATING DESC LIMIT ?1", nativeQuery = true)
    Optional<List<QuotesTable>> findBestQuotes(int limit);

    @Query(value = "SELECT ID, CONTENT, DATE_OF_CREATION, IS_MODIFIED, SUM(RATING) as RATING, AUTHOR  FROM QUOTES GROUP BY ID ORDER BY RATING LIMIT ?1", nativeQuery = true)
    Optional<List<QuotesTable>> findWorstQuotes(int limit);
}

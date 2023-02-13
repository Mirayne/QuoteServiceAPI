package kameleoon.trialtask.quoteserviceapi.database.repository;

import kameleoon.trialtask.quoteserviceapi.database.QuotesTable;
import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import kameleoon.trialtask.quoteserviceapi.database.VotesTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends CrudRepository<VotesTable, Long> {
    Optional<VotesTable> findByQuoteAndVoter(QuotesTable quote, UsersTable voter);

//    @Query(value = "SELECT COALESCE(sum(CASE WHEN IS_UPVOTE THEN 1 ELSE -1 END),0) FROM VOTES WHERE QUOTE = ?1", nativeQuery = true)
//    VotesTable getQuoteRating(Long quoteId);

    Optional<List<VotesTable>> findAllByQuote(QuotesTable quote);
}

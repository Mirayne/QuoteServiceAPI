package kameleoon.trialtask.quoteserviceapi.service;

import kameleoon.trialtask.quoteserviceapi.database.QuotesTable;
import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import kameleoon.trialtask.quoteserviceapi.database.VotesTable;
import kameleoon.trialtask.quoteserviceapi.database.repository.QuoteRepository;
import kameleoon.trialtask.quoteserviceapi.database.repository.VoteRepository;
import kameleoon.trialtask.quoteserviceapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    public VotesTable vote(QuotesTable quote, UsersTable user, boolean isUpvote) {
        VotesTable voteRecord = voteRepository.findByQuoteAndVoter(quote, user).orElse(new VotesTable(quote, user));
        voteRecord.setUpvote(isUpvote);
        //quote.updateRating(isUpvote);
        voteRepository.save(voteRecord);
        calculateRating(quote);
        quoteRepository.save(quote);

        return voteRecord;
    }

    public List<QuotesTable> getBestQuotes(int limit) {
        return quoteRepository.findBestQuotes(limit).orElseThrow(
                () -> new ResourceNotFoundException("Quotes doesn't found.")
        );
    }

    public List<QuotesTable> getWorstQuotes(int limit) {
        return quoteRepository.findWorstQuotes(limit).orElseThrow(
                () -> new ResourceNotFoundException("Quotes doesn't found.")
        );
    }

    public List<VotesTable> findAllByQuote(QuotesTable quote) {
        return voteRepository.findAllByQuote(quote).orElseThrow(
                () -> new ResourceNotFoundException("Quote doesn't have votes yet.")
        );
    }

    public void calculateRating(QuotesTable quote) {
        List<VotesTable> listOfVotes = voteRepository.findAllByQuote(quote).orElse(null);
        if (listOfVotes == null) return;

        long upvotes = listOfVotes.stream().filter(VotesTable::isUpvote).count();
        long downvotes = listOfVotes.stream().filter(s -> !s.isUpvote()).count();
        long rating = upvotes - downvotes;

        if (quote.getRating() != rating) {
            quote.setRating(rating);
            quoteRepository.save(quote);
        }
    }
}

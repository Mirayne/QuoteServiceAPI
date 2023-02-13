package kameleoon.trialtask.quoteserviceapi.service;

import kameleoon.trialtask.quoteserviceapi.database.QuotesTable;
import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import kameleoon.trialtask.quoteserviceapi.exceptions.EmptyFieldException;
import kameleoon.trialtask.quoteserviceapi.exceptions.ResourceNotFoundException;
import kameleoon.trialtask.quoteserviceapi.database.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepository;

    public QuotesTable createQuote(String content, UsersTable user) {
        if (content.isEmpty()) {
            throw new EmptyFieldException("Field can't be empty");
        }
        QuotesTable newQuote = new QuotesTable(content, user);
        return quoteRepository.save(newQuote);
    }

    public QuotesTable findById(Long id) {
        return quoteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Quote with id " + id + " not found.")
        );
    }

    public QuotesTable updateQuote(Long id, String newContent) {
        QuotesTable quote = findById(id);
        if (newContent.isEmpty()) {
            throw new EmptyFieldException("Field can't be empty");
        }
        return quoteRepository.save(quote.updateQuote(newContent));
    }

    public void deleteById(Long id) {
        QuotesTable quote = findById(id);
        quoteRepository.delete(quote);
    }

    public QuotesTable getRandomQuote() {
        long min = 1;
        long max = quoteRepository.count();
        long randomValue = (long)(Math.random() * ((max - min) + 1)) + min;

        return findById(randomValue);
    }
}

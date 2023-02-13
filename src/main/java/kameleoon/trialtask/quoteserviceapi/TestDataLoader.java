package kameleoon.trialtask.quoteserviceapi;

import kameleoon.trialtask.quoteserviceapi.database.QuotesTable;
import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import kameleoon.trialtask.quoteserviceapi.database.VotesTable;
import kameleoon.trialtask.quoteserviceapi.database.repository.QuoteRepository;
import kameleoon.trialtask.quoteserviceapi.database.repository.UsersRepository;
import kameleoon.trialtask.quoteserviceapi.database.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class TestDataLoader implements ApplicationRunner {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private VoteRepository voteRepository;

    public void run(ApplicationArguments args) {
        /* USERS */
        usersRepository.save(new UsersTable("Alina", "Alina@gmail.com", "qwerty"));
        usersRepository.save(new UsersTable("Ilya", "Ilya@mail.ru", "123456"));
        usersRepository.save(new UsersTable("killer228", "killa@yandex.ru", "asdzxc"));
        usersRepository.save(new UsersTable("Pushka", "baaam@test1.com", "cxzvbn"));
        usersRepository.save(new UsersTable("NaoNao", "iamnao@gmail.com", "asdqwwe"));
        usersRepository.save(new UsersTable("test1", "test1@test1.com", "nth132421d"));

        /* QUOTES */
        quoteRepository.save(new QuotesTable("Cut the bullshit!", usersRepository.findById(1L).get()));
        quoteRepository.save(new QuotesTable("You will open your mouth at the dentist.", usersRepository.findById(1L).get()));
        quoteRepository.save(new QuotesTable("Now or never!", usersRepository.findById(2L).get()));
        quoteRepository.save(new QuotesTable("Music is soul of language.", usersRepository.findById(3L).get()));
        quoteRepository.save(new QuotesTable("When you start thinking a lot about your past, it becomes your present and you can’t see your future without it. ", usersRepository.findById(4L).get()));
        quoteRepository.save(new QuotesTable("Advice is like snow; the softer it falls the longer it dwells upon, and the deeper it sinks into the mind.", usersRepository.findById(5L).get()));
        quoteRepository.save(new QuotesTable("Temporary happiness is like waiting for a knife.", usersRepository.findById(6L).get()));
        quoteRepository.save(new QuotesTable("I am kind and sweet ... just not with everyone!", usersRepository.findById(4L).get()));
        quoteRepository.save(new QuotesTable("Everyone has one’s own path.", usersRepository.findById(3L).get()));
        quoteRepository.save(new QuotesTable("Don’t let your mind kill your heart and soul.", usersRepository.findById(5L).get()));
        quoteRepository.save(new QuotesTable("Patience, of course, is a very powerful weapon, but sometimes I start to regret that it is not a firearm.", usersRepository.findById(3L).get()));

        /* VOTES */
        voteRepository.save(new VotesTable(quoteRepository.findById(1L).get(), usersRepository.findById(1L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(1L).get(), usersRepository.findById(2L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(2L).get(), usersRepository.findById(3L).get(), false, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(2L).get(), usersRepository.findById(4L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(3L).get(), usersRepository.findById(5L).get(), false, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(3L).get(), usersRepository.findById(6L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(4L).get(), usersRepository.findById(1L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(4L).get(), usersRepository.findById(2L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(5L).get(), usersRepository.findById(3L).get(), false, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(5L).get(), usersRepository.findById(4L).get(), false, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(6L).get(), usersRepository.findById(5L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(6L).get(), usersRepository.findById(6L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(1L).get(), usersRepository.findById(1L).get(), false, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(2L).get(), usersRepository.findById(2L).get(), false, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(3L).get(), usersRepository.findById(3L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(4L).get(), usersRepository.findById(4L).get(), false, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(5L).get(), usersRepository.findById(5L).get(), true, Date.from(Instant.now())));
        voteRepository.save(new VotesTable(quoteRepository.findById(6L).get(), usersRepository.findById(6L).get(), true, Date.from(Instant.now())));

        for (Long i = 1L; i < 12L; i++) {
            QuotesTable quotesTable = quoteRepository.findById(i).get();
            calculateRating(quotesTable);
        }
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

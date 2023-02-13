package kameleoon.trialtask.quoteserviceapi.controllers;

import kameleoon.trialtask.quoteserviceapi.database.QuotesTable;
import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import kameleoon.trialtask.quoteserviceapi.database.VotesTable;
import kameleoon.trialtask.quoteserviceapi.service.QuoteService;
import kameleoon.trialtask.quoteserviceapi.service.UserService;
import kameleoon.trialtask.quoteserviceapi.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="", produces = "application/json")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private UserService userService;

    @PostMapping("/quote/vote")
    public VotesTable vote(@RequestParam("quoteId") Long quoteId,
                           @RequestParam("userId") Long userId,
                           @RequestParam("isUpvote") boolean isUpvote) {
        QuotesTable quote = quoteService.findById(quoteId);
        UsersTable user = userService.findById(userId);
        return voteService.vote(quote, user, isUpvote);
    }

    @GetMapping("/quote/best/{limit}")
    public List<QuotesTable> getBestQuotes(@PathVariable int limit) {
        return voteService.getBestQuotes(limit);
    }

    @GetMapping("/quote/worst/{limit}")
    public List<QuotesTable> getWorstQuotes(@PathVariable int limit) {
        return voteService.getWorstQuotes(limit);
    }

    @GetMapping("/quote/{id}/voteinfo")
    public List<VotesTable> getVoteInfo(@PathVariable Long id) {
        QuotesTable quote = quoteService.findById(id);
        return voteService.findAllByQuote(quote);
    }
}

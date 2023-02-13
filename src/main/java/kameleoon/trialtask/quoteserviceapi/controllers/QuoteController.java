package kameleoon.trialtask.quoteserviceapi.controllers;

import kameleoon.trialtask.quoteserviceapi.database.QuotesTable;
import kameleoon.trialtask.quoteserviceapi.service.QuoteService;
import kameleoon.trialtask.quoteserviceapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "", produces = "application/json")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private UserService userService;

    @PostMapping("/quote/create")
    public QuotesTable createQuote(@RequestParam("content") String content,
                                   @RequestParam("userid") Long id) {
        return quoteService.createQuote(content, userService.findById(id));
    }

    @GetMapping("/quote/get/{id}")
    public QuotesTable getQuote(@PathVariable Long id) {
        return quoteService.findById(id);
    }

    @PatchMapping("/quote/update")
    public QuotesTable updateQuote(@RequestParam("id") Long id,
                                   @RequestParam("newContent") String newContent) {
        return quoteService.updateQuote(id, newContent);
    }

    @DeleteMapping("/quote/delete/{id}")
    public void deleteQuote(@PathVariable Long id) {
        quoteService.deleteById(id);
    }

    @GetMapping("/quote/random")
    public QuotesTable randomQuote() {
        return quoteService.getRandomQuote();
    }
}

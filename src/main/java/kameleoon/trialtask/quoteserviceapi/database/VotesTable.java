package kameleoon.trialtask.quoteserviceapi.database;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "votes")
public class VotesTable {
    /**
     * Columns
     */

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuotesTable quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsersTable voter;

    @Column(name = "is_upvote")
    private boolean isUpvote;

    @Column(name = "date_of_vote")
    private Date dateOfVote = Date.from(Instant.now());

    /**
     * Constructors
     */

    public VotesTable() {
    }

    public VotesTable(QuotesTable quote, UsersTable voter, boolean isUpvote, Date dateOfVote) {
        this.quote = quote;
        this.voter = voter;
        this.isUpvote = isUpvote;
        this.dateOfVote = dateOfVote;
    }

    public VotesTable(QuotesTable quote, UsersTable voter) {
        this.quote = quote;
        this.voter = voter;
    }

    /**
     * Getters and setters
     */

    public QuotesTable getQuote() {
        return quote;
    }

    public void setQuote(QuotesTable quote) {
        this.quote = quote;
    }

    public UsersTable getVoter() {
        return voter;
    }

    public void setVoter(UsersTable voter) {
        this.voter = voter;
    }

    public boolean isUpvote() {
        return isUpvote;
    }

    public void setUpvote(boolean upvote) {
        isUpvote = upvote;
    }

    public Date getDateOfVote() {
        return dateOfVote;
    }

    public void setDateOfVote(Date dateOfVote) {
        this.dateOfVote = dateOfVote;
    }
}

package kameleoon.trialtask.quoteserviceapi.database;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "quotes")
public class QuotesTable {
    /**
     * Columns
     */
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "date_of_creation")
    private Date dateOfCreation = Date.from(Instant.now());

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsersTable author;

    @Column(name = "is_modified")
    private boolean isModified = false;

    @Column(name = "rating")
    private Long rating = 0L;

    /**
     * Constructors
     */

    public QuotesTable() {
    }

    public QuotesTable(String content, UsersTable author) {
        this.content = content;
        this.dateOfCreation = Date.from(Instant.now());
        this.author = author;
    }

    /**
     * Getters and setters
     */

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public UsersTable getAuthor() {
        return author;
    }

    private void setAuthor(UsersTable author) {
        this.author = author;
    }

    public boolean isModified() {
        return isModified;
    }

    private void setModified() {
        this.isModified = true;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public void updateRating(boolean isUpvote) {
        if (isUpvote) {
            this.rating++;
        } else {
            this.rating--;
        }
    }

    /**
     * Allows to modify a quote. Automatically changes date of creation and field "isModified" when used
     * @param newContent
     * Contains updated text of quote
     */
    public QuotesTable updateQuote(String newContent) {
        this.setContent(newContent);
        this.setModified();
        this.setDateOfCreation(Date.from(Instant.now()));
        return this;
    }
}

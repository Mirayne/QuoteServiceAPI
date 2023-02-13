package kameleoon.trialtask.quoteserviceapi.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import kameleoon.trialtask.quoteserviceapi.database.other.AttributeEncryptor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name="users")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UsersTable implements Serializable {

    /**
     * Columns
     */
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    @Convert(converter = AttributeEncryptor.class)
    private String password;

    @Column(name = "date_of_creation")
    private Date dateOfCreation = Date.from(Instant.now());

    /**
     * Constructors
     */
    public UsersTable() {
    }

    public UsersTable(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Getters and Setters
     */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

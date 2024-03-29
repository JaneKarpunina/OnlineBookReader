package bookreader.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.Objects;

@Document
public class Book {

    @Transient
    public static final String SEQUENCE_NAME = "books_sequence";

    @Id
    long id;

    public Book() {
    }
    public Book(String title, String author, String link, String comment) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.comment = comment;
    }

    String title;

    String author;

    String link;

    String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(link, book.link) && Objects.equals(comment, book.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, link, comment);
    }
}

package bookreader.service;


import bookreader.model.Book;
import bookreader.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    BookRepository bookRepository;

    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> find(long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAllBooks(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return bookRepository.findAll();
        } else {
            return bookRepository.findByTitleContaining(stringFilter);
        }
    }

    public long countBooks() {
        return bookRepository.count();
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public void saveBook(Book book) {
        if (book == null) {
            System.err.println("Book is null. Are you sure you have connected your form to the application?");
            return;
        }
        bookRepository.save(book);
    }

}

package bookreader;

import bookreader.model.Book;
import bookreader.repository.UserRepository;
import bookreader.service.BookService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

//@EnableAutoConfiguration
//@Configuration
//@ComponentScan(basePackages = {"bookreader.*"})
@SpringBootApplication
@EnableMongoRepositories(basePackages = "bookreader.repository")
public class Application extends AbstractMongoClientConfiguration {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        /*ApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);

        BookService bookService = ((BookService)context.getBean("bookService"));
        bookService.saveBook(new Book("title1", "author1", "link1/.../", "comment1"));
        bookService.saveBook(new Book("title2", "author2", "link2/.../", "comment2"));
        bookService.saveBook(new Book("title3", "author3", "link3/.../", "comment3"));*/
    }

    @Override
    protected String getDatabaseName() {
        return "onlinebookreader";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/onlinebookreader");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("bookreader.model");
    }
}

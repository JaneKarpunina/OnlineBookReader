package bookreader.events;

import bookreader.model.Book;
import bookreader.service.SequenceGeneratorService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class BookModelListener extends AbstractMongoEventListener<Book> {

    private SequenceGeneratorService sequenceGenerator;

    //TODO:without autowire!!!
    public BookModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Book.SEQUENCE_NAME));
        }
    }
}

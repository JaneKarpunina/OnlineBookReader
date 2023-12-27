package bookreader.view;


import bookreader.model.Book;
import bookreader.service.BookService;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;

@PermitAll
@Route(value = "bookviewer/:id?", layout = MainLayout.class) // <1>
@PageTitle("Bookviewer | Vaadin CRM")
public class BookView extends VerticalLayout implements BeforeEnterObserver {

    private static final String DEFAULT_BOOK_ID = "-1";

    private final BookService service;

    public BookView(BookService service) { // <2>
        this.service = service;
        addClassName("bookviewer-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // <3>
    }

    public PdfViewer getPdfViewer(String bookId) {

     //TODO: null check!!!
     Optional<Book> book = service.find(Long.parseLong(bookId));

     StreamResource resource;

        resource = book.map(value -> new StreamResource(value.getTitle(),
                () -> getClass().getResourceAsStream(value.getLink()))).orElseGet(() -> new StreamResource("sample.pdf",
                () -> getClass().getResourceAsStream("/pdf/sample.pdf")));
     PdfViewer pdfViewer = new PdfViewer();
     pdfViewer.setSrc(resource);

     return pdfViewer;

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String bookId = event.getRouteParameters().get("id").
                orElse(DEFAULT_BOOK_ID);

        add(getPdfViewer(bookId));
    }
}

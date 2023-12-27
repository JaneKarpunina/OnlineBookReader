package bookreader.view;


import bookreader.service.BookService;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = "bookviewer", layout = MainLayout.class) // <1>
@PageTitle("Bookviewer | Vaadin CRM")
public class BookView extends VerticalLayout {

    private final BookService service;

    public BookView(BookService service) { // <2>
        this.service = service;
        addClassName("bookviewer-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // <3>

        add(getPdfViewer());
    }

    public PdfViewer getPdfViewer() {
     PdfViewer pdfViewer = new PdfViewer();
     StreamResource resource = new StreamResource("sample.pdf",
             () -> getClass().getResourceAsStream("/pdf/sample.pdf"));
     pdfViewer.setSrc(resource);

     return pdfViewer;
    }
}

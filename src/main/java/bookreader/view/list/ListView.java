package bookreader.view.list;

import bookreader.model.Book;
import bookreader.service.BookService;
import bookreader.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Books | Book Reader")
public class ListView extends VerticalLayout {
    Grid<Book> grid = new Grid<>(Book.class);
    TextField filterText = new TextField();
    BookForm form;
    BookService service;

    public ListView(BookService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        //fillGrid();
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new BookForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveBook); // <1>
        form.addDeleteListener(this::deleteBook); // <2>
        form.addCloseListener(e -> closeEditor()); // <3>
        form.addOpenListener(this::openFile);
    }

    private void saveBook(BookForm.SaveEvent event) {
        service.saveBook(event.getBook());
        updateList();
        closeEditor();
    }

    private void deleteBook(BookForm.DeleteEvent event) {
        service.deleteBook(event.getBook());
        updateList();
        closeEditor();
    }

    private void openFile(BookForm.OpenEvent event) {

    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();

        //String title, String author, String link, String comment
        grid.setColumns("title", "author", "link", "comment");
        //grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        //grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editBook(event.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by title...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add book");
        addContactButton.addClickListener(click -> addBook());

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editBook(Book book) {
        if (book == null) {
            closeEditor();
        } else {
            form.setBook(book);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setBook(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
    }

    private void fillGrid() {
        service.saveBook(new Book("title1", "author1", "link1/.../", "comment1"));
        service.saveBook(new Book("title2", "author2", "link2/.../", "comment2"));
        service.saveBook(new Book("title3", "author3", "link3/.../", "comment3"));
    }


    private void updateList() {
        grid.setItems(service.findAllBooks(filterText.getValue()));
    }


}

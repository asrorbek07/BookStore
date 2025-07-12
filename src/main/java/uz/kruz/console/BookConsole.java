package uz.kruz.console;

import uz.kruz.dto.BookDTO;
import uz.kruz.service.BookService;
import uz.kruz.service.impl.BookServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.List;
import java.util.Optional;

public class BookConsole {
    
    private BookService<BookDTO> bookService;
    
    private ConsoleUtil consoleUtil;
    private Narrator narrator;
    
    public BookConsole() {
        this.bookService = new BookServiceImpl<>();
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }
    
    public void register() {
        while (true) {
            String title = consoleUtil.getValueOf("\n new book's title(0.Book menu)");
            if (title.equals("0")) {
                return;
            }
            
            String isbn = consoleUtil.getValueOf(" ISBN");
            String description = consoleUtil.getValueOf(" description");
            String price = consoleUtil.getValueOf(" price");
            String stock = consoleUtil.getValueOf(" stock");
            String publisherId = consoleUtil.getValueOf(" publisher ID");
            String categoryId = consoleUtil.getValueOf(" category ID");
            
            try {
                BookDTO newBook = new BookDTO();
                newBook.setTitle(title);
                newBook.setIsbn(isbn);
                newBook.setDescription(description);
                newBook.setPrice(Double.parseDouble(price));
                newBook.setStock(Integer.parseInt(stock));
                newBook.setPublisherId(Integer.parseInt(publisherId));
                newBook.setCategoryId(Integer.parseInt(categoryId));
                
                bookService.register(newBook);
                narrator.sayln("\n Registered book: " + newBook.toString());
                
            } catch (Exception e) {
                narrator.sayln(e.getMessage());
            }
        }
    }
    
    public void find() {
        while (true) {
            String id = consoleUtil.getValueOf("\n book's ID to find(0.Book menu)");
            if (id.equals("0")) {
                return;
            }
            
            try {
                Optional<BookDTO> bookFound = bookService.findById(Integer.parseInt(id));
                if (bookFound.isPresent()) {
                    narrator.sayln("Found book: " + bookFound.get().toString());
                } else {
                    narrator.sayln("Book not found with ID: " + id);
                }
            } catch (Exception e) {
                narrator.sayln(e.getMessage());
            }
        }
    }
    
    public BookDTO findOne() {
        BookDTO bookFound = null;
        while (true) {
            String id = consoleUtil.getValueOf("\n book's ID to find(0.Book menu)");
            if (id.equals("0")) {
                return null;
            }
            
            try {
                Optional<BookDTO> found = bookService.findById(Integer.parseInt(id));
                if (found.isPresent()) {
                    bookFound = found.get();
                    narrator.sayln("Found book: " + bookFound.toString());
                    break;
                } else {
                    narrator.sayln("Book not found with ID: " + id);
                }
            } catch (Exception e) {
                narrator.sayln(e.getMessage());
            }
        }
        return bookFound;
    }
    
    public void findByTitle() {
        while (true) {
            String title = consoleUtil.getValueOf("\n book's title to find(0.Book menu)");
            if (title.equals("0")) {
                return;
            }
            
            try {
                List<BookDTO> books = bookService.findByTitle(title);
                if (!books.isEmpty()) {
                    narrator.sayln("==== Found Book List ====");
                    books.forEach(book -> narrator.sayln(book.toString()));
                } else {
                    narrator.sayln("No books found with title: " + title);
                }
            } catch (Exception e) {
                narrator.sayln(e.getMessage());
            }
        }
    }
    
    public void findByIsbn() {
        while (true) {
            String isbn = consoleUtil.getValueOf("\n book's ISBN to find(0.Book menu)");
            if (isbn.equals("0")) {
                return;
            }
            
            try {
                Optional<BookDTO> bookFound = bookService.findByIsbn(isbn);
                if (bookFound.isPresent()) {
                    narrator.sayln("Found book: " + bookFound.get().toString());
                } else {
                    narrator.sayln("Book not found with ISBN: " + isbn);
                }
            } catch (Exception e) {
                narrator.sayln(e.getMessage());
            }
        }
    }
    
    public void modify() {
        BookDTO targetBook = findOne();
        if (targetBook == null) {
            return;
        }
        
        String newTitle = consoleUtil.getValueOf(" new title(Enter. no change)");
        if (!newTitle.isEmpty()) {
            targetBook.setTitle(newTitle);
        }
        
        String newDescription = consoleUtil.getValueOf(" new description(Enter. no change)");
        if (!newDescription.isEmpty()) {
            targetBook.setDescription(newDescription);
        }
        
        String newPrice = consoleUtil.getValueOf(" new price(Enter. no change)");
        if (!newPrice.isEmpty()) {
            targetBook.setPrice(Double.parseDouble(newPrice));
        }
        
        String newStock = consoleUtil.getValueOf(" new stock(Enter. no change)");
        if (!newStock.isEmpty()) {
            targetBook.setStock(Integer.parseInt(newStock));
        }
        
        try {
            bookService.modify(targetBook);
            narrator.sayln("\t > Modified book: " + targetBook);
        } catch (Exception e) {
            narrator.sayln(e.getMessage());
        }
    }
    
    public void remove() {
        BookDTO targetBook = findOne();
        if (targetBook == null) {
            return;
        }
        
        String confirmStr = consoleUtil.getValueOf("Remove this book? (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            narrator.sayln("Removing book --> " + targetBook.getTitle());
            bookService.removeById(targetBook.getId());
            narrator.sayln("Book removed successfully.");
        } else {
            narrator.sayln("Remove cancelled, book is safe. --> " + targetBook.getTitle());
        }
    }
}
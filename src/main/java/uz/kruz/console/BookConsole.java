package uz.kruz.console;

import uz.kruz.domain.Book;
import uz.kruz.dto.BookDTO;
import uz.kruz.repository.impl.BookRepositoryImpl;
import uz.kruz.service.BookService;
import uz.kruz.service.impl.BookServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.StringUtil;
import uz.kruz.util.TalkingAt;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

import java.math.BigDecimal;

public class BookConsole {
    private BookService bookService;
    private final ConsoleUtil consoleUtil;
    private Narrator narrator;

    public BookConsole() {
        this.bookService = new BookServiceImpl(new BookRepositoryImpl());
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void register() {
        while (true) {
            String bookTitle = consoleUtil.getValueOf("\n(0. Book menu)\nEnter the book title");
            if (bookTitle.equals("0")) {
                return;
            }
            String bookIsbn = consoleUtil.getValueOf("\nEnter the bookIsbn");
            if (bookIsbn.equals("0")) {
                return;
            }
            BigDecimal price = consoleUtil.getValueOfBigDecimal("\nEnter the price");
            if (price.equals(BigDecimal.ZERO)) {
                return;
            }
            Integer stock = consoleUtil.getValueOfInteger("\nEnter the stock");
            if (stock.equals(0)) {
                return;
            }
            Integer publisherYear = consoleUtil.getValueOfInteger("\nEnter the publisher year");
            if (publisherYear.equals(0)) {
                return;
            }
            Integer categoryId = consoleUtil.getValueOfInteger("\nEnter the category id");
            if (categoryId.equals(0)) {
                return;
            }
            Integer publisherId = consoleUtil.getValueOfInteger("\nEnter the publisher id");
            if (publisherId.equals(0)) {
                return;
            }

            try {
                BookDTO bookDTO = BookDTO.builder()
                        .title(bookTitle)
                        .isbn(bookIsbn)
                        .price(price)
                        .stock(stock)
                        .publishedYear(publisherYear)
                        .categoryId(categoryId)
                        .publisherId(publisherId)
                        .build();
                bookService.register(bookDTO);
                narrator.say("\nBook registered successfully: " + bookDTO.toString());
            } catch (IllegalArgumentException | ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
    }

    //title, isbn, price, stock, published_year, category_id, publisher_id
    public Book findOne() {
        Book bookFound = null;
        while (true) {
            String bookName = consoleUtil.getValueOf("\n(0. Book menu)\nEnter the book title");
            if (bookName.equals("0")) {
                break;
            }
            try {
                bookFound = (Book) bookService.findByTitle(bookName);
                if (bookFound != null) {
                    narrator.sayln("\t > Found book by title: " + bookName);
                }
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
        return bookFound;
    }

    public void modify() {
        Book targetBook = findOne();
        if (targetBook == null) {
            return;
        }
        String newName = consoleUtil.getValueOf("\n(0. Book menu)\nEnter the new book title");
        if (newName.equals("0")) {
            return;
        }
        if (!StringUtil.isEmpty(newName)) {
            targetBook.setTitle(newName);
        }
        String bookIsbn = consoleUtil.getValueOf("\nEnter the new bookIsbn");
        if (bookIsbn.equals("0")) {
            return;
        }
        if (!StringUtil.isEmpty(bookIsbn)) {
            targetBook.setIsbn(bookIsbn);
        }
        BigDecimal price = consoleUtil.getValueOfBigDecimal("\nEnter the new price");
        if (price.equals(BigDecimal.ZERO)) {
            return;
        }
        if (!StringUtil.isEmpty(String.valueOf(price))) {
            targetBook.setPrice(price);
        }
        Integer stock = consoleUtil.getValueOfInteger("\nEnter the new stock");
        if (stock.equals(0)) {
            return;
        }
        if (!StringUtil.isEmpty(String.valueOf(stock))) {
            targetBook.setStock(stock);
        }
        Integer publisherYear = consoleUtil.getValueOfInteger("\nEnter the publisher new year");
        if (publisherYear.equals(0)) {
            return;
        }
        if (!StringUtil.isEmpty(String.valueOf(publisherYear))) {
            targetBook.setPublishedYear(publisherYear);
        }
        Integer categoryId = consoleUtil.getValueOfInteger("\nEnter the category new id");
        if (categoryId.equals(0)) {
            return;
        }
        if (!StringUtil.isEmpty(String.valueOf(categoryId))) {
            targetBook.setCategoryId(categoryId);
        }
        Integer publisherId = consoleUtil.getValueOfInteger("\nEnter the publisher new id");
        if (publisherId.equals(0)) {
            return;
        }
        if (!StringUtil.isEmpty(String.valueOf(publisherId))) {
            targetBook.setPublisherId(publisherId);
        }
        BookDTO bookDTO = BookDTO.builder()
                .title(targetBook.getTitle())
                .isbn(targetBook.getIsbn())
                .price(targetBook.getPrice())
                .stock(targetBook.getStock())
                .publishedYear(targetBook.getPublishedYear())
                .categoryId(targetBook.getCategoryId())
                .publisherId(targetBook.getPublisherId())
                .build();
        try {
            bookService.modify(bookDTO, targetBook.getId());
            narrator.sayln("\t > Modified book: " + targetBook);
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }
    public void remove() {
        Book targetBook = findOne();
        if (targetBook == null) {
                return;
        }

    }


}

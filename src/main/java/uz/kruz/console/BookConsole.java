package uz.kruz.console;

import uz.kruz.repository.impl.BookRepositoryImpl;
import uz.kruz.service.BookService;
import uz.kruz.service.impl.BookServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

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

    //title, isbn, price, stock, published_year, category_id, publisher_id
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
//            BigDecimal price = consoleUtil.getValueOfBigDecimal("\nEnter the price");
//            if (price.equals("0")) {
//                return;
//            }
            String stock = consoleUtil.getValueOf("\nEnter the stock");
            if (stock.equals("0")) {
                return;
            }
            String publisherYear = consoleUtil.getValueOf("\nEnter the publisher year");
            if (publisherYear.equals("0")) {
                return;
            }
            String categoryId = consoleUtil.getValueOf("\nEnter the category id");
            if (categoryId.equals("0")) {
                return;
            }
            String publisherId = consoleUtil.getValueOf("\nEnter the publisher id");
            if (publisherId.equals("0")) {
                return;
            }


        }
    }

}

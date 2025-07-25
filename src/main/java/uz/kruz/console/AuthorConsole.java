package uz.kruz.console;

import uz.kruz.domain.Author;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.repository.impl.AuthorRepositoryImpl;
import uz.kruz.repository.impl.BookRepositoryImpl;
import uz.kruz.service.AuthorService;
import uz.kruz.service.impl.AuthorServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

import java.util.List;

public class AuthorConsole {
    private AuthorService authorService;
    private ConsoleUtil consoleUtil;
    private final Narrator narrator;

    public AuthorConsole() {
        this.authorService = new AuthorServiceImpl(new AuthorRepositoryImpl(), new BookRepositoryImpl());
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void showAll() {
        try {
            narrator.sayln(String.format("\n\t > All Authors (%d): ", authorService.count()));
            for (Author author : authorService.findAll()) {
                narrator.sayln("\t > " + author.toString());
            }

        } catch (ServiceException | RepositoryException e) {
            narrator.sayln("Error: " + e.getMessage());
        }
    }

    public void register() {
        //
        String authorName = consoleUtil.getValueOf("\n author name(1.Author menu) ");
        if (authorName.equals("0")) {
            return;
        }
        try {
            Validator.validateString(authorName, "Name");
            AuthorDTO authorDTO = AuthorDTO.builder()
                    .fullName(authorName)
                    .build();
            Author registeredAuthor = authorService.register(authorDTO);
            narrator.say("\n Registered Author: " + registeredAuthor.toString());

        } catch (IllegalArgumentException | ServiceException | RepositoryException e) {
            //
            narrator.sayln("Error: " + e.getMessage());
        }
    }

    private Author findOne() {
        //
        Author authorFound = null;
        while (true) {
            //
            List<Author> authors = authorService.findAll();
            for (int i = 0; i < authors.size(); i++) {
                narrator.sayln("\t > " + (i + 1) + ". " + authors.get(i).toString());
            }
            try {
                String authorIndex = consoleUtil.getValueOf("\n select author index(0.Author menu) ");
                int index = Integer.parseInt(authorIndex);
                if (index == 0) return null;
                if (!(index >= 0 && index <= authorService.count())) {
                    narrator.sayln("Invalid index, please try again.");
                    continue;
                }
                authorFound = authors.get(index - 1);
                break;
            } catch (NumberFormatException e) {
                narrator.sayln("Invalid input, please enter a number.");
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
        return authorFound;
    }

    public void modify() {
        //
        Author targetAuthor = findOne();
        if (targetAuthor == null) {
            return;
        }
        String newName = consoleUtil.getValueOf("\n new author name(0.Author menu , Enter. no Change)");
        if (newName.equals("0")) {
            return;
        }
        if (newName.isBlank()) newName=null;
        else {
            Validator.validateString(newName, "Name");
        }
        if (newName==null){
            narrator.sayln("No changes made to the author name.");
            return;
        }
        AuthorDTO authorDTO = AuthorDTO.builder()
                .fullName(newName)
                .build();
        try {
            Author modifiedAuthor = authorService.modify(authorDTO, targetAuthor.getId());
            narrator.sayln("\t > Modified Author " + modifiedAuthor);
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln("Error: " + e.getMessage());
        }

    }

    public void remove() {
        //
        Author targetAuthor = findOne();
        if (targetAuthor == null) {
            return;
        }


        String confirmStr = consoleUtil.getValueOf("Remove this author ? , (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            narrator.sayln("\n\t > Removing this Author " + targetAuthor.getFullName());
            authorService.removeById(targetAuthor.getId());
        } else {
            narrator.sayln("\n\t > Remove cancelled --> , your author  is safe. --> " + targetAuthor.getFullName());

        }
    }
}
package uz.kruz.console;

import uz.kruz.domain.Author;
import uz.kruz.domain.Book;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.dto.BookDTO;
import uz.kruz.repository.impl.AuthorRepositoryImpl;
import uz.kruz.repository.impl.BookRepositoryImpl;
import uz.kruz.service.AuthorService;
import uz.kruz.service.BookService;
import uz.kruz.service.impl.AuthorServiceImpl;
import uz.kruz.service.impl.BookServiceImpl;
import uz.kruz.util.*;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AuthorConsole {
    private AuthorService authorService;
    private ConsoleUtil consoleUtil;
    private final Narrator narrator;
    public AuthorConsole(){
        this.authorService = new AuthorServiceImpl(new AuthorRepositoryImpl(),new BookRepositoryImpl());
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil =  new ConsoleUtil(narrator);
    }
    public void showAll(){
        try{
            narrator.sayln("\n\t > All Authors: ");
            for (Author author : authorService.findAll()) {
                narrator.sayln("\t > " + author.toString());
            }

        }catch(ServiceException | RepositoryException e){
            narrator.sayln("Error: " + e.getMessage());
        }
    }
    public void register(){
        //
        String authorName = consoleUtil.getValueOf("\n author name(1.Author menu) ");
        if (authorName.equals("0")){
            return;
        }
        try {
            Validator.validateString(authorName, "Name");
            AuthorDTO authorDTO = AuthorDTO.builder()
                    .fullName(authorName)
                    .build();
            authorService.register(authorDTO);
            narrator.say("\n Registered Author: " + authorDTO.toString());

        }catch(IllegalArgumentException | ServiceException | RepositoryException e){
            //
            narrator.sayln("Error: " + e.getMessage());
        }
    }
    public Author find() {
        Author authorFound = null;
        while (true) {
            //
            String authorName = consoleUtil.getValueOf("\n author name(1.Author menu) ");
            if (authorName.equals("0")) {
                break;
            }
            try {
                if (authorFound != null) {
                    narrator.sayln("\t > found Author: " + authorFound);
                }
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }

        }

        return authorFound;
    }

    private Author findOne(){
        //
        Author authorFound = null;
        while(true){
            narrator.sayln("\n\t > Found Author: " + authorFound);
            break;
        }
        try {
            authorFound = (Author) authorService.findByName(authorFound.getFullName());
            narrator.sayln("\t > Found Author: " + authorFound);

        }
        catch (ServiceException | RepositoryException e){
            narrator.sayln("Error: " + e.getMessage());
        }
        return authorFound;
    }
    public void modify(Author author){
        //
        Author targetAuthor = findOne();
        if (targetAuthor == null){
            return;
        }
        String   newName = consoleUtil.getValueOf("\n new category name(0.Category menu , Enter. no Change)");
        if(newName.equals("0")){
            return;
        }
        AuthorDTO  authorDTO =  AuthorDTO.builder()
                .fullName(newName)
                .build();
        try{
            authorService.modify(authorDTO, targetAuthor.getId());
            narrator.sayln("\t > Modified category " + targetAuthor);
        }catch(ServiceException | RepositoryException e){
            narrator.sayln("Error: " + e.getMessage());
        }

    }
    public void remove(){
        //
        Author targetAuthor = findOne();
        if (targetAuthor == null){
            return;
        }


        String confirmStr =  consoleUtil.getValueOf("Remove this catogory ? , (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")){
            narrator.sayln("\n\t > Remove this category " + targetAuthor.getFullName());
        }else{
            narrator.sayln("\n\t > Remove cancelled --> , your category  is safe. --> " +  targetAuthor.getFullName());

        }
    }
}
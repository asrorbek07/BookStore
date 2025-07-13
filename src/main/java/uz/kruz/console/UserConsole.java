package uz.kruz.console;

import uz.kruz.domain.User;
import uz.kruz.service.UserService;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;

public class UserConsole {
    private final UserService userService;
    private final ConsoleUtil consoleUtil;
    private final Narrator narrator;

    public UserConsole(UserService userService, ConsoleUtil consoleUtil, Narrator narrator) {
        this.userService = userService;
        this.consoleUtil = consoleUtil;
        this.narrator = narrator;
    }

    public void showAll(){
        try{
            narrator.sayln(String.format("\n\t>All users (%d): ", userService.count()));
            for (User user:userService.findAll()){
                narrator.sayln("\t > "+user.toString());
            }
        }
    }
}

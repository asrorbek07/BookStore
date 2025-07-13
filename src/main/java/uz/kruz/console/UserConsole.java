package uz.kruz.console;

import uz.kruz.domain.User;
import uz.kruz.dto.CategoryDTO;
import uz.kruz.dto.UserDTO;
import uz.kruz.service.UserService;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

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
        }catch (ServiceException | RepositoryException e){
            narrator.sayln(e.getMessage());
        }
    }
    public void register(){
        while(true){
            String userName=consoleUtil.getValueOf("\n user full_name(0. Exit)");
            if (userName.equals("0")) {return;}
            String email= consoleUtil.getValueOf("Email: ");
            if (email.equals("0")) {return;}
            String password=consoleUtil.getValueOf("Password: ");
            if (password.equals("0")) {return;}
            String phone=consoleUtil.getValueOf("Phone: ");
            if (phone.equals("0")) {return;}
            String role=consoleUtil.getValueOf("Role (ADMIN/CUSTOMER): ");
            try {
                Validator.validateString(userName, "User name");
                Validator.validateString(email, "Email");
                Validator.validateString(password, "Password");
                Validator.validateString(phone, "Phone");
                UserDTO userDTO = UserDTO.builder()
                        .fullName(userName)
                        .email(email)
                        .phoneNumber(phone)
                        .password(password)
                        .build();
                User registeredUser=userService.register(userDTO);
                narrator.say("\n Registered user: "+registeredUser.toString());
            }catch (IllegalArgumentException | ServiceException | RepositoryException e){
                narrator.sayln(e.getMessage());
            }
        }
    }
    public User findone(){
        User userFound=null;
        while(true){
            String userName=consoleUtil.getValueOf("\n enter the user name(0. Exit)");
            if (userName.equals("0")){
                break;
            }
            try {
                userFound=(User) userService.findByName(userName);
                if(userFound!=null){
                    narrator.say("\n User: "+userName);
                }
            }catch (ServiceException | RepositoryException e){
                narrator.sayln(e.getMessage());
            }
        }return userFound;
    }

}

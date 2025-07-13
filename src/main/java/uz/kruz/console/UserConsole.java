package uz.kruz.console;

import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.dto.CategoryDTO;
import uz.kruz.dto.UserDTO;
import uz.kruz.repository.impl.UserRepositoryImpl;
import uz.kruz.service.UserService;
import uz.kruz.service.impl.UserServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

public class UserConsole {
    private UserService userService;
    private ConsoleUtil consoleUtil;
    private Narrator narrator;


    public UserConsole() {
        this.userService = new UserServiceImpl(new UserRepositoryImpl());
        this.consoleUtil = new ConsoleUtil(narrator);
        this.narrator = new Narrator(this, TalkingAt.Left);
    }


    public void showAll() {
        try {
            narrator.sayln(String.format("\n\t>All users (%d): ", userService.count()));
            for (User user : userService.findAll()) {
                narrator.sayln("\t > " + user.toString());
            }
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }

    public void register() {
        while (true) {
            String userName = consoleUtil.getValueOf("\n user full_name(0. Exit)");
            if (userName.equals("0")) return;
            String email = consoleUtil.getValueOf("Email: ");
            if (email.equals("0")) return;
            String password = consoleUtil.getValueOf("Password: ");
            if (password.equals("0")) return;
            String phone = consoleUtil.getValueOf("Phone: ");
            if (phone.equals("0")) return;
            String role = consoleUtil.getValueOf("Role (ADMIN/CUSTOMER): ");
            if (role.equals("0")) return;


            try {
                Validator.validateString(userName, "User name");
                Validator.validateString(email, "Email");
                Validator.validateString(password, "Password");
                Validator.validateString(phone, "Phone");
                Validator.validateString(role, "Role");
                UserDTO userDTO = UserDTO.builder()
                        .fullName(userName)
                        .email(email)
                        .phoneNumber(phone)
                        .password(password)
                        .role(UserRole.valueOf(role))
                        .build();
                User registeredUser = userService.register(userDTO);
                narrator.say("\n Registered user: " + registeredUser.toString());
            } catch (IllegalArgumentException | ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
    }

    public User findOne() {
        User userFound = null;
        while (true) {
            String userName = consoleUtil.getValueOf("\n enter the user name(0. Exit)");
            if (userName.equals("0")) {
                break;
            }
            try {
                userFound = (User) userService.findByName(userName);
                if (userFound != null) {
                    narrator.say("\n User: " + userName);
                }
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
        return userFound;
    }

    public void modify() {
        User targetUser = findOne();
        if (targetUser == null) return;

        String newName = consoleUtil.getValueOf("\n enter the new user name(0. Exit)");
        if (newName.equals("0")) return;
        if (newName.isBlank()) {
            newName = null;
        } else {
            Validator.validateString(newName, "New user name");
        }
        String newEmail = consoleUtil.getValueOf("\n enter the new email(0. Exit)");
        if (newEmail.equals("0")) return;
        if (newEmail.isBlank()) {
            newEmail = null;
        } else {
            Validator.validateString(newEmail, "New email");
        }
        String newPassword = consoleUtil.getValueOf("\n enter the new password(0. Exit)");
        if (newPassword.equals("0")) return;
        if (newPassword.isBlank()) {
            newPassword = null;
        } else {
            Validator.validateString(newPassword, "New password");
        }
        String newPhone = consoleUtil.getValueOf("\n enter the new phone number(0. Exit)");
        if (newPhone.equals("0")) return;
        if (newPhone.isBlank()) {
            newPhone = null;
        } else {
            Validator.validateString(newPhone, "New phone number");
        }
        String newRole = consoleUtil.getValueOf("\n enter the new role(0. Exit)");
        if (newRole.equals("0")) return;
        if (newRole.isBlank()) {
            newRole = null;
        }

        if (newName == null && newEmail == null && newPassword == null && newPhone == null && newRole == null) {
            narrator.sayln("No changes made to the user");
            return;
        }
        UserDTO dto = UserDTO.builder()
                .fullName(newName)
                .email(newEmail)
                .password(newPassword)
                .phoneNumber(newPhone)
                .role(UserRole.valueOf(newRole))
                .build();

        try {
            User modifiedUser = userService.modify(dto, targetUser.getId());
            narrator.sayln("\t > Modified user: " + modifiedUser);
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }

    public void remove() {
        User targetUser = findOne();
        if (targetUser == null) return;

        String confirm = consoleUtil.getValueOf("Remove this publisher? (Y:yes, N:no): ");
        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            try {
                userService.removeById(targetUser.getId());
                narrator.sayln("Removed user: " + targetUser.getFullName());
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        } else {
            narrator.sayln("Remove cancelled! " + targetUser.getFullName());
        }
    }
}

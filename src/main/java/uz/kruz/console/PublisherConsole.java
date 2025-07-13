package uz.kruz.console;

import uz.kruz.domain.Publisher;
import uz.kruz.dto.PublisherDTO;
import uz.kruz.repository.impl.PublisherRepositoryImpl;
import uz.kruz.service.PublisherService;
import uz.kruz.service.impl.PublisherServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

import java.util.Optional;

public class PublisherConsole {
    private PublisherService publisherService;
    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public PublisherConsole() {
        this.publisherService = new PublisherServiceImpl(new PublisherRepositoryImpl());
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void register() {
        while (true) {
            try {
                String publisherName = consoleUtil.getValueOf("\n Publisher name (0. publisher menu)");
                if (publisherName.equals("0")) return;
                Validator.validateString(publisherName, "Name");
                String publisherContactEmail = consoleUtil.getValueOf("\n Publisher contact email (0. publisher menu, -1. Skip contact email)");
                if (publisherContactEmail.equals("0")) return;
                if (publisherContactEmail.equals("-1")) {
                    publisherContactEmail = null; // Skip contact email
                } else {
                    Validator.validateString(publisherContactEmail, "Contact Email");
                }
                String publisherPhone = consoleUtil.getValueOf("\n Publisher phone (0. publisher menu -1. Skip phone)");
                if (publisherPhone.equals("0")) return;
                if (publisherPhone.equals("-1")) {
                    publisherPhone = null; // Skip phone
                } else {
                    Validator.validateString(publisherPhone, "Phone");
                }

                PublisherDTO publisherDTO = PublisherDTO.builder()
                        .name(publisherName)
                        .contactEmail(publisherContactEmail)
                        .phone(publisherPhone)
                        .build();

                Publisher registeredPublisher = publisherService.register(publisherDTO);
                narrator.sayln("\n Registered publisher: " + registeredPublisher.toString());
            } catch (IllegalArgumentException | ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
    }

    public Optional<Publisher> find() {
        Optional<Publisher> publisherFound = Optional.empty();

        while (true) {
            String publisherName = consoleUtil.getValueOf("\n Publisher name (0. publisher menu): ");
            if (publisherName.equals("0")) break;

            try {
                publisherFound = publisherService.findByName(publisherName);
                if (publisherFound.isPresent()) {
                    narrator.sayln("\t > Found publisher: " + publisherFound.get());
                } else {
                    narrator.sayln("\t > Publisher not found.");
                }
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }

        return publisherFound;
    }

    public Publisher findOne() {
        Publisher publisherFound = null;

        while (true) {
            String publisherName = consoleUtil.getValueOf("\n Publisher name (0. publisher menu): ");
            if (publisherName.equals("0")) break;

            try {
                publisherFound = publisherService.findByName(publisherName).orElse(null);
                if (publisherFound != null) {
                    narrator.sayln("\t > Found publisher: " + publisherFound);
                    break;
                } else {
                    narrator.sayln("\t > Publisher not found.");
                }
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }

        return publisherFound;
    }

    public void modify() {
        Publisher targetPublisher = findOne();
        if (targetPublisher == null) return;

        String newName = consoleUtil.getValueOf("\n New publisher name (0.cancel, Enter. no change): ");
        if (newName.equals("0")) return;
        if (!newName.isBlank()) {
            targetPublisher.setName(newName);
        }

        PublisherDTO dto = PublisherDTO.builder()
                .name(targetPublisher.getName())
                .build();

        try {
            publisherService.modify(dto, targetPublisher.getId());
            narrator.sayln("\t > Modified publisher: " + targetPublisher);
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }

    public void remove() {
        Publisher targetPublisher = findOne();
        if (targetPublisher == null) return;

        String confirm = consoleUtil.getValueOf("Remove this publisher? (Y:yes, N:no): ");
        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            try {
                publisherService.removeById(targetPublisher.getId());
                narrator.sayln("Removed publisher: " + targetPublisher.getName());
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        } else {
            narrator.sayln("Remove cancelled. Publisher is safe: " + targetPublisher.getName());
        }
    }

    public void showAll() {
        try {
            narrator.sayln(String.format("\n\t > All Publishers (%d): ", publisherService.count()));
            for (Publisher publisher : publisherService.findAll()) {
                narrator.sayln("\t > " + publisher.toString());
            }
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }
}

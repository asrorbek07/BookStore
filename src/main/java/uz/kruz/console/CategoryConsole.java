package uz.kruz.console;


import uz.kruz.domain.Category;
import uz.kruz.dto.CategoryDTO;
import uz.kruz.repository.impl.CategoryRepositoryImpl;
import uz.kruz.service.CategoryService;
import uz.kruz.service.impl.CategoryServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

public class CategoryConsole {
    //
    private CategoryService categoryService;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public CategoryConsole() {
        //
        this.categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void showAll() {
        //
        try {
            //
            narrator.sayln("\n\t > All categories: ");
            for (Category category : categoryService.findAll()) {
                narrator.sayln("\t > " + category.toString());
            }
        } catch (ServiceException | RepositoryException e) {
            //
            narrator.sayln(e.getMessage());
        }
    }

    public void register() {
        //
        while (true) {
            //
            String categoryName = consoleUtil.getValueOf("\n category name(0.Category menu)");
            if (categoryName.equals("0")) {
                return;
            }

            try {
                //
                CategoryDTO categoryDTO = CategoryDTO.builder()
                        .name(categoryName)
                        .build();

                categoryService.register(categoryDTO);
                narrator.say("\n Registered category: " + categoryDTO.toString());
            } catch (IllegalArgumentException | ServiceException | RepositoryException e) {
                //
                narrator.sayln(e.getMessage());
            }
        }
    }

    public Category find() {
        //
        Category categoryFound = null;
        while (true) {
            //
            String categoryName = consoleUtil.getValueOf("\n category name to find(0.Category menu) ");
            if (categoryName.equals("0")) {
                break;
            }

            try {
                categoryFound = categoryService.findByName(categoryName);
                if (categoryFound!=null){
                    narrator.sayln("\t > Found category: " + categoryFound);
                }
            } catch ( ServiceException | RepositoryException  e) {
                narrator.sayln(e.getMessage());
            }
        }
        return categoryFound;
    }

    public Category findOne() {
        //
        Category categoryFound = null;
        while (true) {
            //
            String categoryName = consoleUtil.getValueOf("\n category name to find(0.Category menu) ");
            if (categoryName.equals("0")) {
                break;
            }

            try {
                categoryFound = categoryService.findByName(categoryName);
                narrator.sayln("\t > Found category: " + categoryFound);
                break;
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
        return categoryFound;
    }

    public void modify() {
        //
        Category targetCategory = findOne();
        if (targetCategory == null) {
            return;
        }

        String newName = consoleUtil.getValueOf("\n new category name(0.Category menu, Enter. no change)");
        if (newName.equals("0")) {
            return;
        }
        targetCategory.setName(newName);

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name(targetCategory.getName())
                .build();
        try {
            categoryService.modify(categoryDTO, targetCategory.getId());
            narrator.sayln("\t > Modified category: " + targetCategory);
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }

    public void remove() {
        //
        Category targetCategory = findOne();
        if (targetCategory == null) {
            return;
        }

        String confirmStr = consoleUtil.getValueOf("Remove this category? (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            narrator.sayln("Removing a category --> " + targetCategory.getName());
            categoryService.removeById(targetCategory.getId());
        } else {
            narrator.sayln("Remove cancelled, your category is safe. --> " + targetCategory.getName());
        }
    }
}

package tyrkanych_marriageagency.ui;

import tyrkanych_marriageagency.model.Admin;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.User;
import tyrkanych_marriageagency.repository.AdminRepository;
import tyrkanych_marriageagency.repository.impl.AdminRepositoryImpl;
import tyrkanych_marriageagency.service.AuthService;
import tyrkanych_marriageagency.service.MatcherService;
import tyrkanych_marriageagency.service.MessageService;
import tyrkanych_marriageagency.service.UserService;
import tyrkanych_marriageagency.ui.pages.AdminMenuView;
import tyrkanych_marriageagency.ui.pages.AuthView;
import tyrkanych_marriageagency.ui.pages.MainMenuView;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;

public class ConsoleApp {

    public static void start() {
        UnitOfWork uow = new UnitOfWork();
        UserService userService = new UserService(uow.clients());

        AdminRepository adminRepository = new AdminRepositoryImpl();

        AuthService authService = new AuthService(uow.clients(), adminRepository);

        MatcherService matcherService = new MatcherService();
        MessageService messageService = new MessageService();

        var authView = new AuthView(userService, authService, uow, adminRepository);

        while (true) {
            User user = authView.show();

            if (user != null) {
                if (user instanceof Admin) {
                    new AdminMenuView(uow, matcherService).show();
                } else if (user instanceof Client) {
                    new MainMenuView((Client) user, uow, matcherService, messageService).show();
                }
            }
        }
    }
}

package tyrkanych_marriageagency.ui;

import tyrkanych_marriageagency.service.AuthService;
import tyrkanych_marriageagency.service.MatcherService;
import tyrkanych_marriageagency.service.UserService;
import tyrkanych_marriageagency.ui.pages.AuthView;
import tyrkanych_marriageagency.ui.pages.MainMenuView;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;

public class ConsoleApp {

    public static void start() {
        UnitOfWork uow = new UnitOfWork();
        UserService userService = new UserService(uow.clients());
        AuthService authService = new AuthService(uow.clients());
        MatcherService matcherService = new MatcherService();

        while (true) {
            var authView = new AuthView(userService, authService, uow);
            var user = authView.show(); // повертає Client після входу або реєстрації
            if (user != null) {
                new MainMenuView(user, uow,
                      matcherService).show();
            }
        }
    }
}

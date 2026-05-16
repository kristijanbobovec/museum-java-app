package me.kristijanbobovec.museum.factory;

import me.kristijanbobovec.museum.controller.HomeController;
import me.kristijanbobovec.museum.controller.LoginController;
import me.kristijanbobovec.museum.controller.MuseumDetailController;
import me.kristijanbobovec.museum.controller.RegisterController;
import me.kristijanbobovec.museum.service.AuthService;
import me.kristijanbobovec.museum.service.ExhibitService;
import me.kristijanbobovec.museum.service.MuseumService;

public class ControllerFactory {
    private final AuthService authService;
    private final MuseumService museumService;

    private final ExhibitService exhibitService;

    public ControllerFactory(AuthService authService, MuseumService museumService, ExhibitService exhibitService) {
        this.authService = authService;
        this.museumService = museumService;
        this.exhibitService = exhibitService;
    }

    public Object create(Class<?> controllerClass) {
        if (controllerClass == LoginController.class) {
            return new LoginController(authService);
        }

        if (controllerClass == RegisterController.class) {
            return new RegisterController(authService);
        }

        if (controllerClass == HomeController.class) {
            return new HomeController(museumService);
        }

        if (controllerClass == MuseumDetailController.class) {
            return new MuseumDetailController(exhibitService);
        }

        try {
            return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate controller: " + controllerClass, e);
        }
    }
}

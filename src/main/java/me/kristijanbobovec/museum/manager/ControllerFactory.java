package me.kristijanbobovec.museum.manager;

import me.kristijanbobovec.museum.controller.LoginController;
import me.kristijanbobovec.museum.controller.RegisterController;
import me.kristijanbobovec.museum.service.AuthService;

public class ControllerFactory {
    private final AuthService authService;

    public ControllerFactory(AuthService authService) {
        this.authService = authService;
    }

    public Object create(Class<?> controllerClass) {
        if (controllerClass == LoginController.class) {
            return new LoginController(authService);
        }

        if (controllerClass == RegisterController.class) {
            return new RegisterController(authService);
        }

        try {
            return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate controller: " + controllerClass, e);
        }
    }
}

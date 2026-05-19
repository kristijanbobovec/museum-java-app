package me.kristijanbobovec.museum.factory;

import me.kristijanbobovec.museum.controller.*;
import me.kristijanbobovec.museum.service.*;

public class ControllerFactory {
    private final AuthService authService;
    private final MuseumService museumService;

    private final ExhibitService exhibitService;
    private final MuseumCollectionService museumCollectionService;
    private final CountryService countryService;

    public ControllerFactory(AuthService authService, MuseumService museumService, ExhibitService exhibitService, MuseumCollectionService museumCollectionService,
                             CountryService countryService) {
        this.authService = authService;
        this.museumService = museumService;
        this.exhibitService = exhibitService;
        this.museumCollectionService = museumCollectionService;
        this.countryService = countryService;
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
            return new MuseumDetailController(exhibitService, museumCollectionService);
        }

        if (controllerClass == CountryController.class) {
            return new CountryController(countryService);
        }

        try {
            return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate controller: " + controllerClass, e);
        }
    }
}

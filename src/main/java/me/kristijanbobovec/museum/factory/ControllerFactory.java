package me.kristijanbobovec.museum.factory;

import me.kristijanbobovec.museum.controller.*;
import me.kristijanbobovec.museum.exception.ControllerInstantiationException;
import me.kristijanbobovec.museum.service.*;

public class ControllerFactory {
    private final AuthService authService;
    private final MuseumService museumService;

    private final ExhibitService exhibitService;
    private final MuseumCollectionService museumCollectionService;
    private final CountryService countryService;
    private final CityService cityService;
    private final ImagePathService imagePathService;
    private final CuratorCollectionService curatorCollectionService;
    private final CuratorService curatorService;

    public ControllerFactory(AuthService authService, MuseumService museumService, ExhibitService exhibitService, MuseumCollectionService museumCollectionService,
                             CountryService countryService, CityService cityService, ImagePathService imagePathService,
                             CuratorCollectionService curatorCollectionService, CuratorService curatorService) {
        this.authService = authService;
        this.museumService = museumService;
        this.exhibitService = exhibitService;
        this.museumCollectionService = museumCollectionService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.imagePathService = imagePathService;
        this.curatorCollectionService = curatorCollectionService;
        this.curatorService = curatorService;
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

        if (controllerClass == CityController.class) {
            return new CityController(countryService, cityService);
        }

        if (controllerClass == MuseumCollectionController.class) {
            return new MuseumCollectionController(museumCollectionService);
        }

        if (controllerClass == MuseumController.class) {
            return new MuseumController(museumService, cityService, imagePathService);
        }

        if (controllerClass == ExhibitController.class) {
            return new ExhibitController(exhibitService, museumService, museumCollectionService, imagePathService);
        }


        if (controllerClass == CuratorController.class) {
            return new CuratorController(curatorService, museumService, curatorCollectionService, museumCollectionService);
        }

        try {
            return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ControllerInstantiationException(controllerClass, e);
        }
    }
}

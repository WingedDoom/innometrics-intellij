package services.impl;

import com.intellij.openapi.components.ServiceManager;
import core.Constants;
import core.InnometricsCoreRepository;
import models.Activity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import services.ActivitiesStorageService;
import services.InnometricsCoreService;
import services.TokenStorageService;

public class InnometricsCoreServiceImpl implements InnometricsCoreService {
    private InnometricsCoreRepository coreRepository;

    public InnometricsCoreServiceImpl() {
        TokenStorageService tokenStorageService = ServiceManager.getService(TokenStorageService.class);
        coreRepository = new InnometricsCoreRepository(
                tokenStorageService,
                ServiceManager.getService(ActivitiesStorageService.class)
        );

        String baseURL = tokenStorageService.getBaseURL();
        if (baseURL != null)
            coreRepository.setBaseURL(baseURL);
    }

    @Override
    public void addActivity(Activity newActivity) {
        coreRepository.addActivity(newActivity);
    }

    @Override
    public void submitActivities() {
        //coreRepository.submitActivities();
    }

    @Override
    public void login(@NotNull String login, @NotNull String password, @Nullable String baseURL) throws Exception {
        String url = Constants.DEFAULT_BASE_URL;
        if (baseURL != null && !baseURL.isEmpty())
            url = baseURL;

        coreRepository.setBaseURL(url);
        coreRepository.login(login, password);

        TokenStorageService service = ServiceManager.getService(TokenStorageService.class);
        if (url.equals(baseURL))
            // custom url was used
            service.setBaseURL(baseURL);
        else
            // default url was used
            service.setBaseURL(null);
    }

    @Override
    public void logout() {
        ServiceManager.getService(TokenStorageService.class).setBaseURL(null);
        coreRepository.logout();
    }
}

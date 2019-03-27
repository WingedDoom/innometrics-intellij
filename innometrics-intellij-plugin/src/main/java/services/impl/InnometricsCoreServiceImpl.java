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
        coreRepository = new InnometricsCoreRepository(
                ServiceManager.getService(TokenStorageService.class),
                ServiceManager.getService(ActivitiesStorageService.class)
        );
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
    }

    @Override
    public void logout() {

    }
}

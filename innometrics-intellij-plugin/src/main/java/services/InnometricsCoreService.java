package services;

import com.intellij.openapi.components.ServiceManager;
import models.Activity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InnometricsCoreService {
    static InnometricsCoreService getInstance() {
        return ServiceManager.getService(InnometricsCoreService.class);
    }

    void addActivity(Activity newActivity);
    void submitActivities();
    void login(@NotNull String login, @NotNull String password, @Nullable String baseURL) throws Exception;
    void logout();
}

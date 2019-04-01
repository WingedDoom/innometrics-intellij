package services;

import com.intellij.openapi.components.ServiceManager;
import storage.AuthTokenStorage;

import javax.annotation.Nullable;

public interface TokenStorageService extends AuthTokenStorage {
    static TokenStorageService getInstance() {
        return ServiceManager.getService(TokenStorageService.class);
    }

    @Nullable String getBaseURL();
    void setBaseURL(@Nullable String newBaseURL);
}

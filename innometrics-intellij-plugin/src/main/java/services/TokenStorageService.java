package services;

import com.intellij.openapi.components.ServiceManager;
import storage.AuthTokenStorage;

public interface TokenStorageService extends AuthTokenStorage {
    static TokenStorageService getInstance() {
        return ServiceManager.getService(TokenStorageService.class);
    }
}

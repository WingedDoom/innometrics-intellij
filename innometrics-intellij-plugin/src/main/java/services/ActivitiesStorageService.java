package services;

import com.intellij.openapi.components.ServiceManager;
import storage.PersistentActivitiesStorage;

public interface ActivitiesStorageService extends PersistentActivitiesStorage {
    static ActivitiesStorageService getInstance() {
        return ServiceManager.getService(ActivitiesStorageService.class);
    }
}

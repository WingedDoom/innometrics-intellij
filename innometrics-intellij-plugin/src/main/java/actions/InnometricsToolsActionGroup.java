package actions;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import services.ActivitiesStorageService;
import services.TokenStorageService;

import java.util.ArrayList;
import java.util.List;

public class InnometricsToolsActionGroup extends ActionGroup {
    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        boolean loggedIn = ServiceManager.getService(TokenStorageService.class).getToken() != null;
        ActivitiesStorageService activitiesService = ServiceManager.getService(ActivitiesStorageService.class);
        List<AnAction> actions = new ArrayList<>();

        // decide which actions to show
        // auth action (login or logout)
        AnAction authAction;
        if (!loggedIn)
            authAction = new InnometricsLoginAction();
        else
            authAction = new InnometricsLogoutAction();
        actions.add(authAction);

        if (!activitiesService.getActivitiesBatch(1).isEmpty()) {
            // clearing existing measurements
            actions.add(new InnometricsDeleteMeasurementsAction());

            // submitting existing measurements
            if (loggedIn)
                actions.add(new InnometricsSubmitMeasurementsAction());
        }

        return actions.toArray(new AnAction[0]);
    }
}

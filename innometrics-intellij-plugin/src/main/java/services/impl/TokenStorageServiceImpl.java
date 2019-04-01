package services.impl;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.ide.passwordSafe.PasswordSafe;
import org.jetbrains.annotations.NotNull;
import services.TokenStorageService;

import javax.annotation.Nullable;

/**
 * Uses {@link com.intellij.ide.passwordSafe.PasswordSafe} to implement its methods.
 */
public class TokenStorageServiceImpl implements TokenStorageService {
    private static final String API_TOKEN_KEY = "innometrics-api-token";
    private static final String BASE_URL_KEY = "innometrics-api-base-url";

    @Override
    public void setToken(String token) {
        PasswordSafe.getInstance().setPassword(createCredentialAttributes(API_TOKEN_KEY), token);
    }

    @Override
    public String getToken() {
        return PasswordSafe.getInstance().getPassword(createCredentialAttributes(API_TOKEN_KEY));
    }

    @Nullable
    @Override
    public String getBaseURL() {
        return PasswordSafe.getInstance().getPassword(createCredentialAttributes(BASE_URL_KEY));
    }

    @Override
    public void setBaseURL(@Nullable String newBaseURL) {
        PasswordSafe.getInstance().setPassword(createCredentialAttributes(BASE_URL_KEY), newBaseURL);
    }

    @NotNull
    private CredentialAttributes createCredentialAttributes(@NotNull String key) {
        return new CredentialAttributes(CredentialAttributesKt.generateServiceName("Innometrics", key));
    }
}

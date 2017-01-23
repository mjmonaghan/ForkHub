package com.github.mobile.accounts;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;

import static com.github.mobile.accounts.AccountConstants.ACCOUNT_TYPE;

/**
 * Created by a7142_000 on 1/16/2017.
 */

public class AccountAuthenticatorState {


    private static final String TAG = "AccountAuthenticatorState";

    private static boolean AUTHENTICATOR_CHECKED;

    private static boolean HAS_AUTHENTICATOR;

    /**
     * Verify authenticator registered for account type matches the package name
     * of this application
     *
     * @param manager
     * @return true is authenticator registered, false otherwise
     */
    public static boolean hasAuthenticator(final AccountManager manager) {
        if (!AUTHENTICATOR_CHECKED) {
            final AuthenticatorDescription[] types = manager
                    .getAuthenticatorTypes();
            if (types != null && types.length > 0)
                for (AuthenticatorDescription descriptor : types)
                    if (descriptor != null
                            && ACCOUNT_TYPE.equals(descriptor.type)) {
                        HAS_AUTHENTICATOR = "jp.forkhub"
                                .equals(descriptor.packageName);
                        break;
                    }
            AUTHENTICATOR_CHECKED = true;
        }

        return HAS_AUTHENTICATOR;
    }
}

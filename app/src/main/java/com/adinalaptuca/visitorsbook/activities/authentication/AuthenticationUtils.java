package com.adinalaptuca.visitorsbook.activities.authentication;

import android.app.AlertDialog;
import android.widget.EditText;
import com.adinalaptuca.visitorsbook.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationUtils {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateCredentials(EditText txtUsername, EditText txtPassword, EditText txtRetypePassword) {

        if (txtUsername.getText().toString().trim().isEmpty()) {
            txtUsername.requestFocus();
            txtUsername.setError(txtUsername.getContext().getResources().getString(R.string.cant_be_empty));
            return false;
        }

        if (!AuthenticationUtils.validateEmail(txtUsername.getText().toString().trim())) {
            txtUsername.requestFocus();
            txtUsername.setError(txtUsername.getContext().getResources().getString(R.string.email_not_valid));
            return false;
        }

        if (txtPassword != null) {
            if (txtPassword.getText().toString().isEmpty()) {
                txtPassword.requestFocus();
                txtPassword.setError(txtUsername.getContext().getResources().getString(R.string.cant_be_empty));
                return false;
            }

            if (txtPassword.getText().toString().length() < 6) {
                txtPassword.requestFocus();
                txtPassword.setError(txtUsername.getContext().getResources().getString(R.string.password_length));
                return false;
            }
        }

        if (txtRetypePassword != null && !txtPassword.getText().toString().equals(txtRetypePassword.getText().toString()))
        {
            new AlertDialog.Builder(txtUsername.getContext())
                    .setMessage(R.string.passwords_dont_match)
                    .setNegativeButton(R.string.OK, null)
                    .show();
            return false;
        }

        return true;
    }
}

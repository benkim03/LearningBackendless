package com.mistershorr.learningbackendless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText username, password, confirmPassword, firstName, lastName, email;

    public static final String EXTRA_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        wirewidgets();
        setListeners();
        Intent i = getIntent();


    }

    private void register() {
        if (password.getText().toString().equals(confirmPassword.getText().toString())) {
            final BackendlessUser user = new BackendlessUser();
            user.setProperty("username", username.getText().toString());
            user.setProperty("firstName", firstName.getText().toString());
            user.setProperty("lastName", lastName.getText().toString());
            user.setProperty("email", email.getText().toString());
            user.setPassword(password.getText().toString());
            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser registeredUser) {
                    Intent i = new Intent();
                    i.putExtra(EXTRA_USERNAME, username.getText().toString());
                    setResult(RESULT_OK, i);
                    finish();
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });

        }



    }

    private void wirewidgets() {
        register = (Button) findViewById(R.id.button_Register);
        username = findViewById(R.id.usernameEdit);
        password = findViewById(R.id.passwordEdit);
        confirmPassword = findViewById(R.id.confirmPasswordEdit);
        firstName = findViewById(R.id.firstNameEdit);
        lastName = findViewById(R.id.lastNameEdit);
        email = findViewById(R.id.emailEdit);
    }

    private void setListeners() {
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_Register:
                register();
                break;
        }
    }
}

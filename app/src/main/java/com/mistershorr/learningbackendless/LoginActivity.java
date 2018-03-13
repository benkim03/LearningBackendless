package com.mistershorr.learningbackendless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView createNewAccount;
    private EditText passwordEdit, usernameEdit;
    private ProgressBar loadingBar;
    private Button loginButton;

public static final int DORABETES = 7;
    public static final String EXTRA_MESSAGE1 = "hi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wirewidgets();

        Backendless.initApp(this, BackendSettings.APP_ID, BackendSettings.API_KEY);
        setListener();

    }

    private void wirewidgets() {
        usernameEdit = findViewById(R.id.usernameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        createNewAccount = findViewById(R.id.createNewAccountTextView);
        loadingBar = (ProgressBar) findViewById(R.id.progressBar);
        loginButton = (Button) findViewById(R.id.button_Login);
        loadingBar.setVisibility(View.INVISIBLE);
    }

    private void setListener() {

        createNewAccount.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && resultCode==DORABETES) {
            //prefill the username
            usernameEdit.setText(data.getStringExtra(RegistrationActivity.EXTRA_USERNAME));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createNewAccountTextView:
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                i.putExtra(EXTRA_MESSAGE1, "hi");
                startActivity(i);
                startActivityForResult(i, DORABETES);
                break;
            case R.id.button_Login:
                loadingBar.setVisibility(View.VISIBLE);
                Backendless.UserService.login(usernameEdit.getText().toString(), passwordEdit.getText().toString(), new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        //success
                        String user = (String) response.getProperty("username");
                        Toast.makeText(LoginActivity.this, user + " has logged on.", Toast.LENGTH_SHORT).show();
                        loadingBar.setVisibility(View.INVISIBLE);
                        //testDataRetrieval();
                        testDataManipulation();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        //failure
                        Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingBar.setVisibility(View.INVISIBLE);
                    }
                });

                break;
        }
    }

    private void testDataRetrieval() {
//        String whereClause = "name = 'Gus''s BBQ'";

        String id = Backendless.UserService.CurrentUser().getUserId();
        String whereClause = "ownerId = '" + id + "'";
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause(whereClause);
        Backendless.Data.of(Restaurant.class).find(dataQuery, new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(List<Restaurant> response) {
                createNewAccount.setText(response.toString());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



//        Backendless.Persistence.of(Restaurant.class).find(new AsyncCallback<List<Restaurant>>() {
//            @Override
//            public void handleResponse(List<Restaurant> response) {
//                createNewAccount.setText(response.toString());
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void testDataManipulation() {
        //create
        Restaurant r = new Restaurant("Food Barn", "Generic Food", "The Barn down the street", 5, 1);
        Backendless.Persistence.save(r, new AsyncCallback<Restaurant>() {
            @Override
            public void handleResponse(Restaurant response) {
                Toast.makeText(LoginActivity.this, "CREATED NEW", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //update
        r.setObjectId("7F0152A8-3C10-3287-FF0A-258F72116300");
        r.setOwnerId(Backendless.UserService.CurrentUser().getUserId());
        r.setName("SOMETHING DIFFERENT");
        Backendless.Persistence.save(r, new AsyncCallback<Restaurant>() {
            @Override
            public void handleResponse(Restaurant response) {
                Toast.makeText(LoginActivity.this, "CREATED NEW", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}

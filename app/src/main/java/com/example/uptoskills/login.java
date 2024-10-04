package com.example.uptoskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.uptoskills.util.GoogleSignInHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class login extends AppCompatActivity {
    private static FirebaseAuth mAuth;
    SharedPreferences s;
    private static final String LOGIN_URL = "https://uptoskills.com/wp-login.php";
    static String USERNAME = ""; // Replace with your username
    private static String PASSWORD = ""; // Replace with your password

    FirebaseUser user;
    static public String email = "0";
    private static final String TAG = "PhoneAuthActivity";
    static String check = "0";
    int password = 0;
    static String global = "";
    private static final int RC_SIGN_IN = 9001;


    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        ImageButton google = findViewById(R.id.google);
        Button log = findViewById(R.id.log);
        Button reg = findViewById(R.id.reg);
        final RelativeLayout loginpage = findViewById(R.id.layoutlog);
        final FrameLayout registerpage = findViewById(R.id.layoutreg);
        ImageView back = findViewById(R.id.back);
        ImageView glogo = findViewById(R.id.glogo);
        final TextInputEditText e = findViewById(R.id.email);
        final TextInputEditText p = findViewById(R.id.password);

        final TextInputEditText crtUsername = findViewById(R.id.crtUsername);
        final TextInputEditText crtemail = findViewById(R.id.crtemail);
        final TextInputEditText crtpass = findViewById(R.id.crtpass);
        final TextInputEditText mobileNo = findViewById(R.id.mobileNo);
        CardView sign = findViewById(R.id.SignU);
        s = getSharedPreferences("db1", MODE_PRIVATE);
        check = s.getString("login", "");
        Log.d("okay", "app opening" + check);
        if (check.equals("1")) {
            finish();
            startActivity(new Intent(login.this, Main2Activity.class));
        }
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(crtUsername.getText()).toString().isEmpty() || Objects.requireNonNull(crtemail.getText()).toString().isEmpty() ||
                        Objects.requireNonNull(crtpass.getText()).toString().isEmpty() || Objects.requireNonNull(mobileNo.getText()).toString().isEmpty()) {
                    Toast.makeText(login.this, "Fill all columns", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(login.this, Main2Activity.class));
                    s = getSharedPreferences("db1", MODE_PRIVATE);
                    SharedPreferences.Editor edit = s.edit();
                    edit.putString("login", "1");
                    edit.apply();
                    finish();
                    Toast.makeText(login.this, "Signup Successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });
        CardView lo = findViewById(R.id.SignInWithEmail);


        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ii = 0;
                if (Objects.requireNonNull(e.getText()).toString().isEmpty() || Objects.requireNonNull(p.getText()).toString().isEmpty()) {
                    Toast.makeText(login.this, "Fill Both Column", Toast.LENGTH_SHORT).show();
                } else {


//                    for (int i = 0; i < userdatabase.username.size(); i++) {
//                        if(userdatabase.username.get(i).equals(e.getText().toString()) || userdatabase.email.get(i).equals(e.getText().toString())){
//                            if(p.getText().toString().equals(userdatabase.password.get(i))){
//                                password = i;
//                                s  = getSharedPreferences("db1",MODE_PRIVATE);
//                                SharedPreferences.Editor edit = s.edit();
//                                edit.putString("login", "1");
//                                edit.putString("androidemail" , userdatabase.username.get(i));
//                                edit.apply();
//                                finish();
//                                ii = 1;
//                                startActivity(new Intent(login.this,Main2Activity.class));
//                                Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//
//                            }
//
//
//
//                        }
//                        else {
//                        }
//
//                    }
                    //check
                    USERNAME = e.getText().toString();
                    PASSWORD = p.getText().toString();

                    new LoginTask().execute();


                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerpage.setVisibility(View.INVISIBLE);
                loginpage.setVisibility(View.VISIBLE);
            }
        });
        glogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(login.this, gso);
                signIn();
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerpage.setVisibility(View.VISIBLE);
                loginpage.setVisibility(View.INVISIBLE);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(login.this, gso);
                signIn();
            }
        });
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                global = verificationId;
            }
        };

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        login GoogleSignInHelper = null;
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInHelper.newInstance(this).handleSignInResult(data);
        }
    }

    private Object newInstance(login login) {
        return null;
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            s = getSharedPreferences("db1", MODE_PRIVATE);
                            SharedPreferences.Editor edit = s.edit();
                            edit.putString("login", "1");
                            edit.putString("android name", user.getDisplayName());
                            edit.putString("android email", user.getEmail());

                            edit.apply();
                            finish();

                            Log.d("okay", "google sign" + check);

                            Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login.this, Main2Activity.class));
                        }
                    }
                });
    }

    @SuppressLint("StaticFieldLeak")
    public static class LoginTask {
        private final AppCompatActivity mActivity;

        public LoginTask() {
            AppCompatActivity activity = null;
            this.mActivity = null;
        }

        public void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            final Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // Background work here
                    final boolean loginSuccessful = performLogin();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // UI Thread work here
                            onPostExecute(loginSuccessful);
                        }
                    });
                }
            });
        }

        private boolean performLogin() {
            // Perform login logic here
            // Return true if login is successful, false otherwise
            return true; // Replace with actual login logic
        }

        protected void onPostExecute(Boolean loginSuccessful) {
            if (loginSuccessful) {
                // Login successful
                assert mActivity != null;
                SharedPreferences s = mActivity.getSharedPreferences("db1", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = s.edit();
                edit.putString("login", "1");
                edit.putString("android email", "USERNAME"); // Replace USERNAME with actual username
                edit.apply();
                mActivity.finish();
                mActivity.startActivity(new Intent(mActivity, Main2Activity.class));
                Toast.makeText(mActivity, "Login Successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Login failed
                Toast.makeText(mActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }


}



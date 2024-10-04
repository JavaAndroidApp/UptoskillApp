package com.example.uptoskills;

import static com.example.uptoskills.R.*;
import static com.example.uptoskills.R.id.*;
import static com.google.android.gms.auth.api.signin.GoogleSignIn.*;


import android.content.Context;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityResultLauncher<Intent> signInLauncher;


    @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Task<GoogleSignInAccount> task = getSignedInAccountFromIntent(data);
                        handleSignInResult(task);
                   }
                }
            }

//            // Configure sign-in to request the user's ID, email address, and basic profile.
//            final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                   .requestEmail()
//                    .build();
//
//
//            // Build a GoogleSignInClient with the options specified by gso.
//            mGoogleSignInClient =GoogleSignIn.getClient(this,gso);
//
//            // Set the sign-in button click listener
//           void findViewById(id.sign_in_button).void setOnClickListener(v -> void signIn());

        });


        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list
        ImageView play = findViewById(id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment b = new BlankFragment();
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(id.frame, b).addToBackStack(null).commit();
            }
        });
        ImageView imageView = findViewById(id.sc1);
        TextView textView1 = findViewById(id.sc2);
        TextView textView2 = findViewById(id.sc3);
        TextView textView3 = findViewById(id.sc4);
        final ScrollView scrollView = findViewById(id.scroll);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0); // Scroll to the top
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0); // Scroll to the top

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0); // Scroll to the top

            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0); // Scroll to the top

            }
        });
        ImageView b1 = findViewById(id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blog_view.vlog_position = -99;
                startActivity(new Intent(MainActivity.this, clickvlogviewer.class));
            }
        });

        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/1.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/4.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/5.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/6.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/9.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/13.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/14.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/18.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/17.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/20.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://uptoskills.com/wp-content/uploads/2023/05/21-1.png", ScaleTypes.CENTER_CROP));


        ImageSlider imageSlider = findViewById(id.image_slider);
        imageSlider.setImageList(imageList);
        Button blog_view = findViewById(id.blogview);
        blog_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.example.uptoskills.blog_view.class));
            }
        });

        ImageView option = findViewById(id.option);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());

                // Set item click listener for the menu items
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item click event here
                        if (item.getItemId() == id.profile) {
                            Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        }

                        if (item.getItemId() == id.signout) {
                            SharedPreferences s;
                            s = getSharedPreferences("db1", MODE_PRIVATE);
                            SharedPreferences.Editor edit = s.edit();
                            edit.putString("login", "0");
                            edit.apply();
                            Log.d("okay", "app opening" + s.getString("login", "") + " ");

                            finish();
                            startActivity(new Intent(MainActivity.this, login.class));

                        }
//                        return switch (item.getItemId()) {
//                            case 1 ->
//                                // Action for menu item 1
//                                    true;
//                            case 2 ->
//                                // Action for menu item 2
//                                    true;
//                            // Add more cases for other menu items if needed
//                            default -> false;
//                        };

                        return false;
                    }
                });

                // Show the popup menu
                popupMenu.show();
            }
        });


        ArrayList<SlideModel> imageList2 = new ArrayList<>(); // Create image list
        imageList2.add(new SlideModel(R.drawable.is1, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.is2, ScaleTypes.CENTER_CROP));
        imageList2.add(new SlideModel(R.drawable.is3, ScaleTypes.CENTER_CROP));
        ImageSlider imageSlider2 = findViewById(id.image_slider2);
        imageSlider2.setImageList(imageList2);
        imageSlider2.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                // You can listen here.

            }

            @Override
            public void doubleClick(int position) {
                // Do not use onItemSelected if you are using a double click listener at the same time.
                // It's just added for specific cases.
                // Listen for clicks under 250 milliseconds.
            }
        });
        final BottomNavigationView bn = findViewById(id.bottomNavigation);

        bn.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                @SuppressLint("CommitTransaction") FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId() == id.Learn) {
                    // Handle item 2 click
                    Toast.makeText(MainActivity.this, "Learn", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == id.Discover) {
                    // Handle Discover item click
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, bn);
                    popupMenu.getMenuInflater().inflate(R.menu.discover_submenu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            // Handle submenu item click
                            if (menuItem.getItemId() == id.submenu_item1) {
                                startActivity(new Intent(MainActivity.this, Aboutus.class));
                            }
                            if (menuItem.getItemId() == id.submenu_item2) {
                                startActivity(new Intent(MainActivity.this, com.example.uptoskills.blog_view.class));
                            }
                            if (menuItem.getItemId() == id.submenu_item4) {
                                startActivity(new Intent(MainActivity.this, contactus.class));
                            }
                            return true;
                        }
                    });

                    popupMenu.show();
                }

                return true;
            }
        });


    }

//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        googleSignInLauncher.launch(signInIntent);
//    }
//
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.d("GoogleSignIn", "Sign-in successful: " + account.getEmail());
            // You can proceed with authenticated user actions
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w("GoogleSignIn", "Sign-in failed: " + e.getStatusCode());
        }
    }
//    protected void onStart() {
//        super.onStart();
//        // Check for existing Google Sign-In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
//            Log.d("GoogleSignIn", "Already signed in: " + account.getEmail());
//            // Update UI or proceed with signed-in user
//        }
//    }
}



//public class FunApp extends AppCompatActivity {
//
//    NavController navController;
//    Context context;
//    GoogleSignInViewModel googleSignInViewModel;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        context = this;
//        googleSignInViewModel = new GoogleSignInViewModel();
//
//        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_home);
//    }
//
//    public void HomeScreen(View view) {
//        googleSignInViewModel.handleGoogleSignIn(context, navController);
//    }
//
//    public void ProfileScreen(View view) {
//        googleSignInViewModel.ProfileScreen(googleSignInViewModel);
//    }
//
//}


package com.example.projectinsurance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectinsurance.ui.gallery.GalleryFragment;
import com.example.projectinsurance.ui.home.HomeFragment;
import com.example.projectinsurance.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectinsurance.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;


//    private CircleImageView img;
//    //Permission Constant
//    private static final int CAMERA_PERMISSION_CODE = 100;
//    private static final int STORAGE_PERMISSION_CODE = 200;
//    private static final int IMAGE_FROM_GALLERY_CODE = 300;
//    private static final int IMAGE_FROM_CAMERA_CODE = 400;
//
//    //String array of permission
//    private String[] cameraPermission;
//    private String[] storagePermission;
//
//    //image Uri variable
//    Uri imageUri;


    private String UserID;
     CircleImageView img1;
    private TextView txtNameView,txtEmailView;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Uri filePath;
    Bitmap bitmap;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseUser user;
    User User;

    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);


//        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("EXIT", true);
//        startActivity(intent);

        img1=findViewById(R.id.imgCircle);
        txtNameView=findViewById(R.id.txtNameView);
        txtEmailView=findViewById(R.id.txtEMailView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user=auth.getCurrentUser();
        showCurrentUser(user.getUid());

//        img1.setOnClickListener(v -> {
//
//            uploadEvent();
//        });
//user image upload
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_home, R.id.nav_policy, R.id.nav_claim, R.id.nav_signOut)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                Intent intent = getIntent();
                if (intent.getStringExtra("fragment") != null) {
                    if(intent.getStringExtra("fragment") == "home"){
                        selectedFragment = new HomeFragment();
                    }
//                    Plane_id = (intent.getStringExtra("fragment"));

                }


                switch (item.getItemId()) {
                    case R.id.nav_home1:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_policy1:
                        selectedFragment = new GalleryFragment();
                        break;
                    case R.id.nav_claim1:
                        selectedFragment = new SlideshowFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, selectedFragment).commit();

                return true;
            }
        });

//Side bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }
        NavigationView navigationView1 = findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                Intent intent = getIntent();
                if (intent.getStringExtra("fragment") != null) {
                    if(intent.getStringExtra("fragment") == "home"){
                        selectedFragment = new HomeFragment();
                    }else if (intent.getStringExtra("fragment") == "profile"){
                        selectedFragment = new ProfileFragment();
                    }else if(intent.getStringExtra("fragment") == "about"){
                        selectedFragment = new ProfileFragment();
                    }else{

                    }
                }

                switch (item.getItemId()) {

                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new HomeFragment()).commit();
                        break;
                    case R.id.nav_policy:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new GalleryFragment()).commit();
                        break;
                    case R.id.nav_claim:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new SlideshowFragment()).commit();
                        break;
                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, new ProfileFragment()).commit();
                        break;
                    case R.id.nav_signOut:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("Are You Sure You Want To Sign Out");
                        builder1.setIcon(R.drawable.ic_warning);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        auth.signOut();
                                        startActivity(new Intent(MainActivity.this, GetStart.class));
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    //user
    private void showCurrentUser(String uid) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    txtNameView=findViewById(R.id.txtNameView);
                    txtEmailView=findViewById(R.id.txtEMailView);

                    img1=findViewById(R.id.imgCircle);
                        txtNameView.setText(String.valueOf(snapshot.child("name").getValue()));
                        txtEmailView.setText(String.valueOf(snapshot.child("email").getValue()));

                    img1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(MainActivity.this, "img clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,200);
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    //    User Image
    private void uploadEvent() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Image Sending...");
        dialog.setMessage("Please wait while uploading your Image...");
        dialog.show();

        user=auth.getCurrentUser();
        storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference("User_image_"+new Random().nextInt(89999)+user.getUid());
        uploader.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference = FirebaseDatabase.getInstance().getReference("Users");
                                HashMap map = new HashMap();
                                map.put("imgUri",uri.toString());
                                reference.child(user.getUid()).setValue(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    dialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Something went wrong...."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200 && resultCode == RESULT_OK){
            filePath = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img1.setImageBitmap(bitmap);
                uploadEvent();
            }catch (Exception ex)
            {

            }
        }
    }
//User Image
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}


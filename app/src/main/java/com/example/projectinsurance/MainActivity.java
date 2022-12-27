package com.example.projectinsurance;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    private CircleImageView img;
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
    User eventModel;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

//        //init permission
//
//        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//
//        //        init view
//        img = findViewById(R.id.img);
//
//        //image
////        img.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                showImagePickerDialog();
////
////            }
////        });

//user image upload
        img=findViewById(R.id.img);
        txtNameView=findViewById(R.id.txtNameView);
        txtEmailView=findViewById(R.id.txtEMailView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        showCurrentUser();

//        img.setOnClickListener(v -> {
//
//            uploadEvent();
//        });

//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent,200);
//            }
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

//    private void showImagePickerDialog() {
//        //option for dialog
//        String options[] = {"Camera", "Gallery"};
//
//        //Alert Dialog Builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        //set title
//        builder.setTitle("Choose An option");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                //handle item click
//                if (i == 0) { //start from 0 index
//                    //camera selected
//                    if (!checkCameraPermission()) {
//                        //request camera permission
//                        requestCameraPermission();
//                    } else {
//                        pickFromCamera();
//                    }
//
//                } else if (i == 1) {
//                    //Gallery selected
//                    if (!checkStoragePermission()) {
//                        //request storage permission
//                        requestStoragePermission();
//                    } else {
//                        pickFromGallery();
//                    }
//                }
//
//            }
//        }).create().show();
//    }
//
//    private void pickFromGallery() {
//        //intent for taking image from Gallery
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//        galleryIntent.setType("image/*"); // only image
//
//        startActivityForResult(galleryIntent, IMAGE_FROM_GALLERY_CODE);
//    }
//
//    private void pickFromCamera() {
//
//        //contentValues for image info
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "IMAGE_TITLE");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "IMAGE_DETAILS");
//
//        // save image Uri
//        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        // intent to open camera
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//
//        startActivityForResult(cameraIntent, IMAGE_FROM_CAMERA_CODE);
//
//    }
//
//    private boolean checkStoragePermission() {
//        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
//        return result;
//
//    }
//
//    private void requestStoragePermission() {
//        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_PERMISSION_CODE);
//
//    }
//
//    private boolean checkCameraPermission() {
//        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
//        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
//        return result & result1;
//
//    }
//
//    private void requestCameraPermission() {
//        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_PERMISSION_CODE); //Handle request permission on override method
//
//    }
//
//    //handle request permission
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case CAMERA_PERMISSION_CODE:
//                if (grantResults.length > 0) {
//
//                    //if all permission allowed return true, otherwise false
//                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (cameraAccepted && storageAccepted) {
//                        //Both permission granted
//                        pickFromCamera();
//                    } else {
//                        // permission not granted
//                        Toast.makeText(getApplicationContext(), "Camera & Storage Needed..", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//
//            case STORAGE_PERMISSION_CODE:
//                if (grantResults.length > 0) {
//
//                    //if all permission allowed return true, otherwise false
//                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (storageAccepted) {
//                        //Storage permission granted
//                        pickFromGallery();
//                    } else {
//                        // permission not granted
//                        Toast.makeText(getApplicationContext(), "Storage Needed..", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (resultCode == IMAGE_FROM_GALLERY_CODE) {
//                // Picked image from gallery
//                //crop image
//            }
//        }
//    }


    //user
    private void showCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    User userProfile = snapshot.getValue(User.class);
                    if (userProfile != null) {
                        txtNameView.setText(userProfile.getUser_name());
                        txtEmailView.setText(userProfile.getUser_email());
                    }
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
        StorageReference uploader=storage.getReference("User_image_"+new Random().nextInt(6000)+user.getUid());
        uploader.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        reference = FirebaseDatabase.getInstance().getReference();
                        user=auth.getCurrentUser();
                        String key = reference.push().getKey();
                        reference.child("Events").child(key).setValue(eventModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    dialog.dismiss();
                                    reference.child("Events").child(key).child("Participant").setValue(eventModel);
                                    Toast.makeText(MainActivity.this, "Image Sent Successfully...", Toast.LENGTH_SHORT).show();
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
                img.setImageBitmap(bitmap);
                img.setVisibility(View.VISIBLE);
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


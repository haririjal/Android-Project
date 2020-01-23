package com.sujan.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sujan.myapplication.category.Category;
import com.sujan.myapplication.category.CategoryActivity;
import com.sujan.myapplication.home.DashboardActivity;
import com.sujan.myapplication.util.Prefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmailAddress, edtPassword;
    private Button btnSubmit;
    private Dialog originalDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private Toolbar toolbar;
    private ImageView imgPhoto;
    private Uri picUri;
    private String currentImagePath;
    private Bitmap bitmap;
    private String type = "";
    private ArrayList<Category> categoryList = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (item.getItemId() == R.id.call) {
            checkCallPermission();
            return true;

        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_call, menu);
        return true;
    }

    private void checkCallPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //   is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this, R.style.DialogTheme)
                        .setTitle("Call Permission Needed")
                        .setMessage("This app needs the Call  permission permission , please accept to use Call functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", "com.sujan.myapplication", null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9864831976"));
            startActivity(intent);
        }
    }

    private void checkImagePermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //   is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this, R.style.DialogTheme)
                        .setTitle("Call Permission Needed")
                        .setMessage("This app needs the Camera and external storage  permission permission , please accept to use camera & gallery functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", "com.sujan.myapplication", null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        } else {
            if (type == "Camera")
                openCamera();
            else
                openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9864831976"));
                        startActivity(intent);
                    }
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", "com.sujan.myapplication", null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                break;
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9864831976"));
//                        startActivity(intent);
                        if (type == "Camera")
                            openCamera();
                        else
                            openGallery();
                    }
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", "com.sujan.myapplication", null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                break;
        }


    }

    private void openGallery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 200);
        } else {
            checkImagePermission();
        }
    }

    private void openCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                if (photoFile != null) {
                    String path = getPackageName() + ".fileprovider";
                    Log.d("Path", path);
                    picUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            picUri);
                    startActivityForResult(pictureIntent,
                            101);
                }

            }
        } else {
            checkImagePermission();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                File imgFile = new File(currentImagePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(imgFile), null, options);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imgPhoto.setImageBitmap(bitmap);
            }
        } else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    picUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                        imgPhoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "JPEG_" + timeStamp,
                ".jpg",
                storageDir
        );

        currentImagePath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniToolbar();
        setData();
        edtEmailAddress = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        imgPhoto = findViewById(R.id.imgPhoto);
        btnSubmit.setOnClickListener(this);
        edtEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = edtEmailAddress.getText().toString();
                Log.d("TextChanged", text);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        edtPassword.setOnClickListener(this);
//       btnSubmit.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//           }
//       });
    }

    private void iniToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BIM 5th sem");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (!TextUtils.isEmpty(edtEmailAddress.getText()) && !TextUtils.isEmpty(edtPassword.getText())) {
//                Prefs prefs = new Prefs(this);
//                prefs.saveString("EmailAddress", edtEmailAddress.getText().toString().trim());
//                prefs.saveString("Password", edtPassword.getText().toString().trim());
//                prefs.saveBoolean("IsLogin", true);
                getDataDb(edtEmailAddress.getText().toString().trim(), edtPassword.getText().toString().trim());


            }
//            selectImage();
//
        } else if (view.getId() == R.id.edtPassword) {
            Log.d("EditPassword", "Edit password has new state.");
        }

    }

    private void selectImage() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.upload_photo, null);
        TextView txtTakePhoto = dialogView.findViewById(R.id.txtTakePhoto);
        TextView txtChooseGallery = dialogView.findViewById(R.id.txtChooseGallery);
        TextView txtCancel = dialogView.findViewById(R.id.txtCancel);
        alertDialogBuilder.setView(dialogView);
        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Camera";
                originalDialog.dismiss();
                openCamera();
            }
        });
        txtChooseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Gallery";
                originalDialog.dismiss();
                openGallery();
            }
        });
        originalDialog = alertDialogBuilder.create();
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalDialog.dismiss();
            }
        });
        originalDialog.getWindow().setDimAmount(0.7f);
        originalDialog.setCanceledOnTouchOutside(true);
        originalDialog.show();
    }

    private void getDataDb(String email, String password) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("email", email).equalTo("password", password).findFirst();
        if (user != null) {
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra("Name", "LoginActivity");
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Invalid email and password", Toast.LENGTH_SHORT).show();
        }
        realm.commitTransaction();
    }
    private void setData() {
        Random rand = new Random();
        Category cate1 = new Category();
        Category cate4 = new Category();
        Category cate2 = new Category();
        Category cate5 = new Category();
        Category cate3 = new Category();
        Category cate6 = new Category();
        cate1.setTitle("Non-Veg Restaurant");
        cate1.setId(rand.nextInt(1000));
        cate4.setTitle("Non-Veg Restaurant");
        cate2.setId(rand.nextInt(1000));
        cate3.setId(rand.nextInt(1000));
        cate4.setId(rand.nextInt(1000));
        cate5.setId(rand.nextInt(1000));
        cate6.setId(rand.nextInt(1000));

        cate2.setTitle("Veg Restaurant");
        cate5.setTitle("Veg Restaurant");
        cate3.setTitle("Drinks");
        cate6.setTitle("Drinks");
        cate1.setDescription("Order your favorite food from Restaurant.");
        cate4.setDescription("Order your favorite food from Restaurant.");
        cate2.setDescription("Pure vegetarian Restaurant");
        cate5.setDescription("Pure vegetarian Restaurant");
        cate3.setDescription("Drinks home delivery.");
        cate6.setDescription("Drinks home delivery.");
        categoryList.add(cate1);
        categoryList.add(cate2);
        categoryList.add(cate3);
        categoryList.add(cate4);
        categoryList.add(cate5);
        categoryList.add(cate6);
        saveDataDb();
//        adapter.notifyDataSetChanged();

    }

    private void saveDataDb(){
        Realm realm= Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(Category.class);
        realm.copyToRealmOrUpdate(categoryList);
        realm.commitTransaction();
    }
}

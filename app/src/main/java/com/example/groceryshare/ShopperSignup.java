package com.example.groceryshare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface ShopperSignupEventListener {
    // this can be any type of method
    void uploadData(String id);
}

public class ShopperSignup extends AppCompatActivity implements AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener {

    private ShopperSignupEventListener mListener; // listener field

    // setting the listener
    public void registerShopperSignupEventListener(ShopperSignupEventListener mListener) {
        this.mListener = mListener;
    }

    //Profile Pic Content Start
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    private int REQUEST_CAMERA = 0;
    private String userChoosenTask;
    Bitmap imageBitmap;
    private StorageReference StorageRef;
    private static final String TAG = "ShopperSignup";
    //Profile Pic Content End

    //TextField Data Collection Start
    String profileImage, email, password, password2, firstName, lastName, address, phoneNumber, birthday, frequency;

    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordInput2;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText addressInput;
    private EditText phoneNumberInput;
    private TextView birthdayInput;
    private Spinner frequencyspinner;
    private FirebaseAuth mAuth;

    DatabaseReference databaseShoppers;

    Button logInButton;
    Button joinButton;
    //    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_signup);

        //Async to upload photo
        ShopperSignupEventListener mListener = new ShopperSignup.UploadData();
        registerShopperSignupEventListener(mListener);

        databaseShoppers = FirebaseDatabase.getInstance().getReference("Shoppers");

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        StorageRef = FirebaseStorage.getInstance().getReference();
        ProfileImage = findViewById(R.id.profile_image);

        emailInput = (EditText) findViewById(R.id.EmailInput);
        passwordInput = (EditText) findViewById(R.id.PasswordInput);
        passwordInput2 = (EditText) findViewById(R.id.PasswordInput2);

        mAuth = FirebaseAuth.getInstance();

        Button datePickerButton = (Button) findViewById(R.id.datePicker);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        firstNameInput = (EditText) findViewById(R.id.FirstNameInput);
        lastNameInput = (EditText) findViewById(R.id.LastNameInput);
        addressInput = (EditText) findViewById(R.id.AddressInput);
        phoneNumberInput = (EditText) findViewById(R.id.PhoneInput);
        frequencyspinner = (Spinner) findViewById(R.id.FrequencyInput);

        addListenerOnSpinnerItemSelection();

        logInButton = (Button) findViewById(R.id.LogInbtn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn();
            }
        });

        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                password2 = passwordInput2.getText().toString();

                firstName = firstNameInput.getText().toString();
                lastName = lastNameInput.getText().toString();
                address = addressInput.getText().toString();
                if (birthdayInput != null) {
                    birthday = birthdayInput.getText().toString();
                }
                phoneNumber = phoneNumberInput.getText().toString();
                frequency = frequencyspinner.getSelectedItem().toString();


                //email verification
                //^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$
                //[a-z0-9]+([-+._][a-z0-9]+){0,2}@.*?(\.(a(?:[cdefgilmnoqrstuwxz]|ero|(?:rp|si)a)|b(?:[abdefghijmnorstvwyz]iz)|c(?:[acdfghiklmnoruvxyz]|at|o(?:m|op))|d[ejkmoz]|e(?:[ceghrstu]|du)|f[ijkmor]|g(?:[abdefghilmnpqrstuwy]|ov)|h[kmnrtu]|i(?:[delmnoqrst]|n(?:fo|t))|j(?:[emop]|obs)|k[eghimnprwyz]|l[abcikrstuvy]|m(?:[acdeghklmnopqrstuvwxyz]|il|obi|useum)|n(?:[acefgilopruz]|ame|et)|o(?:m|rg)|p(?:[aefghklmnrstwy]|ro)|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|t(?:[cdfghjklmnoprtvwz]|(?:rav)?el)|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw])\b){1,2}
                Pattern pattern = Pattern.compile("[a-z0-9]+([-+._][a-z0-9]+){0,2}@.*?(\\.(a(?:[cdefgilmnoqrstuwxz]|ero|(?:rp|si)a)|b(?:[abdefghijmnorstvwyz]iz)|c(?:[acdfghiklmnoruvxyz]|at|o(?:m|op))|d[ejkmoz]|e(?:[ceghrstu]|du)|f[ijkmor]|g(?:[abdefghilmnpqrstuwy]|ov)|h[kmnrtu]|i(?:[delmnoqrst]|n(?:fo|t))|j(?:[emop]|obs)|k[eghimnprwyz]|l[abcikrstuvy]|m(?:[acdeghklmnopqrstuvwxyz]|il|obi|useum)|n(?:[acefgilopruz]|ame|et)|o(?:m|rg)|p(?:[aefghklmnrstwy]|ro)|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|t(?:[cdfghjklmnoprtvwz]|(?:rav)?el)|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw])\\b){1,2}");
                Matcher emailVerified = pattern.matcher(email);
                if (!emailVerified.find()) {
                    emailInput.setError("Please enter a valid Email Address!");
                    email = "";
                } else
                    emailInput.setError(null);
                if (!password.equals(password2)) {
                    passwordInput.setError("Passwords don't match!");
                    password = "";
                } else if (password.length() < 6) {
                    passwordInput.setError("Passwords must be at least 6 characters!");
                    password = "";
                } else {
                    passwordInput.setError(null);
                }
                double[] verification = new double[0];
                try {
                    verification = DistanceCalculator.addressToLonLat(address);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (verification == null) {
                    addressInput.setError("Please Enter a Real Address!");
                    address = "";
                } else {
                    addressInput.setError(null);
                }
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(birthday))) {
                    addShopperCredentials();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all of the fields!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showDatePickerDialog(){
        final Calendar min = Calendar.getInstance();
        final Calendar max = Calendar.getInstance();


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        min.add(Calendar.DATE, -43800);
        datePickerDialog.getDatePicker().setMinDate(min.getTimeInMillis());
        max.add(Calendar.DATE, -4745);
        datePickerDialog.getDatePicker().setMaxDate(max.getTimeInMillis());
        datePickerDialog.show();
    }

    private void addShopperCredentials(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(ShopperSignup.this, "Account Created", Toast.LENGTH_LONG).show();
                            String id = user.getUid();

                            //upload the image
                            handleUpload(imageBitmap, user);

                            Toast.makeText(ShopperSignup.this, "New Shopper Added!", Toast.LENGTH_LONG).show();
                            updateUI(id);
                        } else {
                            Toast.makeText(ShopperSignup.this, "Account not created!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    class UploadData implements ShopperSignupEventListener {
        @Override
        public void uploadData(String id) {
            newShopperCreds shopper = new newShopperCreds(id, email, firstName, lastName, address, phoneNumber, birthday, frequency);
            databaseShoppers.child(id).setValue(shopper);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
        birthdayInput = findViewById(R.id.BirthdayInput);
        birthdayInput.setText(currentDateString);
        birthdayInput.setGravity(Gravity.CENTER_HORIZONTAL);
        birthdayInput.setGravity(Gravity.CENTER_VERTICAL);
    }

    private void updateUI(String id) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        intent.putExtra("USER_ID", id);
        startActivity(intent);
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, ShoppingTripsAvailable.class);
        startActivity(intent);
    }

    //used to navigate back to the Login Screen
    public void goLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

  private void onCaptureImageResult(Intent data) {
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProfileImage.setImageBitmap(bm);
        imageBitmap = bm;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ProfileImage.setImageBitmap(bm);
        imageBitmap = bm;

    }

    public void galleryIntent() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void handleImageClick(View view) {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ShopperSignup.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(ShopperSignup.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void handleUpload(Bitmap bitmap, final FirebaseUser user) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(user.getUid() + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        new Thread(new Runnable() {
                            public void run() {
                                getDownloadUrl(reference, user);

                                if (mListener != null) {
                                    mListener.uploadData(user.getUid());
                                }
                            }
                        }).start();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e.getCause());
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference, final FirebaseUser user) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        profileImage = uri.toString();
                        setUserProfileUrl(uri, user);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri, FirebaseUser user) {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ShopperSignup.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShopperSignup.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void addListenerOnSpinnerItemSelection() {
        frequencyspinner = (Spinner) findViewById(R.id.FrequencyInput);
        frequencyspinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
package com.example;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.HealthSector;
import com.example.recovery.MainActivity;
import com.example.recovery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PatientRegistrationActivity extends AppCompatActivity {

    private TextInputEditText registrationFullName, registrationIdNumber,
            registrationPhoneNumber,loginEmail, loginPassword;

    private Uri resultUri;

    private FirebaseAuth mAuth;
    private DatabaseReference userDataRef;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_patient_registration );

        TextView regPageQuestion = findViewById ( R.id.regPageQuestion );
        regPageQuestion.setOnClickListener ( v -> {
            Intent intent = new Intent ( PatientRegistrationActivity.this, HealthSector.class );
            startActivity ( intent );
        } );

        registrationFullName = findViewById ( R.id.registrationFullName );
        registrationIdNumber = findViewById ( R.id.registrationIdNumber );
        registrationPhoneNumber = findViewById ( R.id.registrationPhoneNumber );
        loginEmail = findViewById ( R.id.loginEmail );
        loginPassword = findViewById ( R.id.loginPassword );
        Button regButton = findViewById ( R.id.regButton );

        regButton.setOnClickListener ( v -> {
            final String email = Objects.requireNonNull ( loginEmail.getText () ).toString ().trim ();
            final String password = Objects.requireNonNull ( loginPassword.getText () ).toString ().trim ();
            final String fullName = Objects.requireNonNull ( registrationFullName.getText () ).toString ().trim ();
            final String idNumber = Objects.requireNonNull ( registrationIdNumber.getText () ).toString ().trim ();
            final String phoneNumber = Objects.requireNonNull ( registrationPhoneNumber.getText () ).toString ().trim ();

            loader = new ProgressDialog ( this );
            mAuth = FirebaseAuth.getInstance ();

            if (TextUtils.isDigitsOnly ( email )) {
                loginEmail.setError ( "Email is required" );
                return;
            }
            if (TextUtils.isDigitsOnly ( password )) {
                loginEmail.setError ( "Password is required" );
                return;
            }
            if (TextUtils.isDigitsOnly ( fullName )) {
                loginEmail.setError ( "Full name is required" );
                return;
            }
            if (TextUtils.isDigitsOnly ( idNumber )) {
                loginEmail.setError ( "ID number is required" );
                return;
            }
            if (TextUtils.isDigitsOnly ( phoneNumber )) {
                loginEmail.setError ( "Phone number is required" );
                return;
            }
            if (resultUri ==null){
                Toast.makeText (PatientRegistrationActivity.this, "Profile is required",
                        Toast.LENGTH_SHORT).show ();
            }
            else {
                loader.setMessage("Registration in progress...");
                loader.setCanceledOnTouchOutside ( false );
                loader.show ();

                mAuth.createUserWithEmailAndPassword ( email, password ).addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()){
                            String error = task.getException ().toString ();
                            Toast.makeText ( PatientRegistrationActivity.this,
                                    "Error occured:" + error, Toast.LENGTH_SHORT).show ();
                        }
                        else {
                            String currentUserId = mAuth.getCurrentUser ().getUid ();
                            userDataRef = FirebaseDatabase.getInstance ().getReference ()
                                    .child ( "users" ).child ( currentUserId );
                            HashMap<String, Object> userInfo = new HashMap<> ();
                            userInfo.put ("name", fullName);
                            userInfo.put ("email", email);
                            userInfo.put ("idnumber", idNumber );
                            userInfo.put ("phonenumber", phoneNumber);
                            userInfo.put ("type", "patient");

                            userDataRef.updateChildren ( userInfo ).
                                    addOnCompleteListener ( new OnCompleteListener () {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isSuccessful ()){
                                                Toast.makeText ( PatientRegistrationActivity.this,
                                                        "Details set successfully", Toast.LENGTH_SHORT).show ();
                                            }else {
                                                Toast.makeText ( PatientRegistrationActivity.this,
                                                        task.getException ().toString (), Toast.LENGTH_SHORT).show ();
                                            }
                                            finish ();
                                            loader.dismiss ();
                                        }
                                    } );
                                    if (resultUri !=null){
                                        final StorageReference filepath =
                                                FirebaseStorage.getInstance ().getReference ().child ( "profile pictures" )
                                                .child ( currentUserId );
                                        Bitmap bitmap = null;
                                        try {
                                            bitmap = MediaStore.Images.Media.getBitmap ( getApplication ().
                                                    getContentResolver (), resultUri);
                                        }catch (IOException e) {
                                            e.printStackTrace ();
                                        }

                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
                                        bitmap.compress ( Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream );
                                        byte[] data = byteArrayOutputStream.toByteArray ();

                                        UploadTask uploadTask = filepath.putBytes ( data );

                                        uploadTask.addOnFailureListener ( new OnFailureListener () {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                finish ();
                                                return;
                                            }
                                        } );
                                        uploadTask.addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
                                            @Override
                                            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                                if (taskSnapshot.getMetadata () !=null){
                                                    Task<Uri> result = taskSnapshot.getStorage (). getDownloadUrl ();
                                                    result.addOnSuccessListener ( new OnSuccessListener<Uri> () {
                                                        @Override
                                                        public void onSuccess(@NonNull Uri uri) {
                                                            String imageUri = uri.toString ();
                                                            Map newImageMap = new HashMap ();
                                                            newImageMap.put ( "profilepicture", imageUri );

                                                            userDataRef.updateChildren ( newImageMap ).addOnCompleteListener ( new OnCompleteListener () {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if (task.isSuccessful ()){
                                                                        Toast.makeText ( PatientRegistrationActivity.this,
                                                                                "Reg success", Toast.LENGTH_SHORT).show ();
                                                                    }else {
                                                                        Toast.makeText ( PatientRegistrationActivity.this,
                                                                                task.getException ().toString (),
                                                                                Toast.LENGTH_SHORT).show ();
                                                                    }
                                                                }
                                                            } );
                                                            finish ();
                                                        }
                                                    } );
                                                }
                                            }
                                        } );
                                        Intent intent = new Intent (PatientRegistrationActivity.this,
                                                MainActivity.class );
                                        startActivity ( intent );
                                        finish ();
                                        loader.dismiss ();
                                    }
                        }
                    }
                } );
            }

        } );

    }
}
package co.realinventor.schoolbus.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Admin.ViewStudentActivity;
import co.realinventor.schoolbus.Common.ValidUsers;
import co.realinventor.schoolbus.Driver.DriverHomeActivity;
import co.realinventor.schoolbus.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ParentLoginActivity extends AppCompatActivity {
    private TextInputEditText parentPhoneInput, parentOtpInput;
    private TextInputLayout otpLayout;
    private MaterialButton sendOtpButton, parentLoginButton;
    private DatabaseReference ref;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    final String TAG = "ParentLoginActivity";
    private String phone_no, USER_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        USER_TYPE = getIntent().getStringExtra("usertype");



        parentPhoneInput = findViewById(R.id.parentPhoneInput);
        parentOtpInput = findViewById(R.id.parentOtpInput);
        otpLayout = findViewById(R.id.otpLayout);
        sendOtpButton = findViewById(R.id.sendOtpButton);
        parentLoginButton = findViewById(R.id.parentLoginButton);
        ref = FirebaseDatabase.getInstance().getReference();

        parentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = parentOtpInput.getText().toString();
                if(TextUtils.isEmpty(otp)){
                    Toast.makeText(ParentLoginActivity.this, "The OTP field is empty!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ParentLoginActivity.this, "Checking the credentials!", Toast.LENGTH_SHORT).show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = parentPhoneInput.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(ParentLoginActivity.this, "Please enter a phone number!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(phone.length() == 10){
                        Query rfidQuery = ref.child("valid_users").orderByChild("contact").equalTo(phone);
                        rfidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ValidUsers user = dataSnapshot.getValue(ValidUsers.class);
                                if(user!=null){
                                    Toast.makeText(ParentLoginActivity.this, "Sending OTP!", Toast.LENGTH_SHORT).show();
                                    sendOtp(phone);
                                }
                                else{
                                    Toast.makeText(ParentLoginActivity.this, "Not a registered "+USER_TYPE+"!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                    else{
                        Toast.makeText(ParentLoginActivity.this, "Please enter an valid phone number!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendOtp(String phone){
        phone_no = phone;

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                //Enable otp field
                otpLayout.setVisibility(View.VISIBLE);
                parentLoginButton.setVisibility(View.VISIBLE);
                parentPhoneInput.setEnabled(false);
                parentOtpInput.requestFocus();


                // ...
            }
        };





        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(ParentLoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = task.getResult().getUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(phone_no)
                                    .build();

                            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });

                            Intent intent = null;
                            if(USER_TYPE.equals("parent"))
                                intent = new Intent(ParentLoginActivity.this, ParentHomeActivity.class);
                            else
                                intent = new Intent(ParentLoginActivity.this, DriverHomeActivity.class);
                            intent.putExtra("phone", phone_no);
                            startActivity(intent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(ParentLoginActivity.this, "Invalid OTP!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}

package com.example.sonushahuji.a36ghrd;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.sonushahuji.a36ghrd.activity.OTP;
import com.example.sonushahuji.a36ghrd.activity.Registeration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneAuthActivity extends AppCompatActivity
{

    @BindView(R.id.field_phone_number)
    EditText fieldPhoneNumber;
    @BindView(R.id.field_verification_code)
    EditText fieldVerificationCode;
    @BindView(R.id.button_start_verification)
    Button buttonStartVerification;
    @BindView(R.id.button_verify_phone)
    Button buttonVerifyPhone;
    @BindView(R.id.button_resend)
    Button buttonResend;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;
    private static final String TAG = "PhoneAuthActivity";


    private ProgressDialog progressBar;

    private EditText field_phone_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        field_phone_number=(EditText) findViewById(R.id.field_phone_number);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("", "onVerificationCompleted:" + credential);
                System.out.println("sonu"+credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("", "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    fieldPhoneNumber.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {

                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Log.d("", "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;

                //System.out.println("sonutoken"+token);

                hideProgress();
            }
        };

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //System.out.print("tokenalok"+task);
                            //Log.d(TAG, "signInWithCredential gggggg:success");
                            FirebaseUser user = task.getResult().getUser();
                            String getmobile=fieldPhoneNumber.getText().toString();
                            Log.d(TAG, "signInWithCredential:success");
                            //String  pritesh = String.valueOf(user.getToken(true));
                            getmobile = getmobile.substring(3);
                            Intent intent = new Intent(PhoneAuthActivity.this,Registeration.class);
                            intent.putExtra("contactNo", getmobile);
                            startActivity(intent);
                            finish();

                            //Log.d(TAG, "pritesh1234"+pritesh+getmobile);
                            //System.out.print("don123890"+pritesh);
                            //String getmobile=fieldPhoneNumber.getText().toString();
                            //=field_phone_number.getText().toString();
                            //System.out.print("tokenalok"+user);
                            //System.out.print("dindon123"+getmobile);
                            hideProgress();
                            //startActivity(new Intent(PhoneAuthActivity.this, HomeActivity.class).putExtra("phone", user.getPhoneNumber()));
                            //finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                fieldVerificationCode.setError("Invalid code.");
                            }
                        }
                    }
                });
    }

    private void getOtp(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    @OnClick(R.id.button_start_verification)
    public void clickStartVerification(){
        showProgressBar();
        getOtp(fieldPhoneNumber.getText().toString());
    }

    @OnClick(R.id.button_verify_phone)
    public void clickVerifyPhone(){
        showProgressBar();
        String code = fieldVerificationCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            fieldVerificationCode.setError("Cannot be empty.");
            return;
        }
        verifyPhoneNumberWithCode(mVerificationId, code);
    }

    @OnClick(R.id.button_resend)
    public void clickResend(){
        resendVerificationCode(fieldPhoneNumber.getText().toString(), mResendToken);
    }

    private void showProgressBar(){

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Verifying your phone number");
        progressBar.show();
    }

    private void hideProgress(){
        if(progressBar!=null && progressBar.isShowing()){
            progressBar.hide();
        }
    }

}

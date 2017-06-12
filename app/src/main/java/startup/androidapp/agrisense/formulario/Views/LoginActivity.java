package startup.androidapp.agrisense.formulario.Views;

import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.plus.model.people.Person;

import android.Manifest;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import startup.androidapp.agrisense.formulario.Controllers.FingerprintHandler;
import startup.androidapp.agrisense.formulario.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    private LinearLayout prof_section;
    private Button btn_logout;
    private SignInButton btn_login;
    private TextView Name,Email;
    private ImageView prof_pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "example_key";
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        prof_section = (LinearLayout)findViewById(R.id.prof_section);
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_login =(SignInButton)findViewById(R.id.btn_login);
        Name =(TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        prof_pic = (ImageView)findViewById(R.id.prof_pic);
        btn_login.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        prof_section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
        keyguardManager =  (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {

            Toast.makeText(this,
                    "Lock screen security not enabled in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                    "Fingerprint authentication permission not enabled",
                    Toast.LENGTH_LONG).show();

            return;
        }



        if (!fingerprintManager.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }
        generateKey();

        if (cipherInit()) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            FingerprintHandler helper = new FingerprintHandler(this);
            helper.startAuth(fingerprintManager, cryptoObject);

        }




    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_login:
                signIn();
                break;
            case R.id.btn_logout:
                signOut();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    private void signIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }
    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUi(false);
            }
        });



    }

    private void handleResult(GoogleSignInResult result)
    {
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            Name.setText(name);
            Email.setText(email);
            updateUi(true);


        } else {
            updateUi(false);
        }





    }
    private void updateUi(boolean isLogin)
    {
        if (isLogin){
            prof_section.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);




        } else {
            prof_section.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }



    }
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }



    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }
        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }



}

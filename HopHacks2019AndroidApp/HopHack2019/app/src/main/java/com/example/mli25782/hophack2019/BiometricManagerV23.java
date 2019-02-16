package com.example.mli25782.hophack2019;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback;
import android.hardware.biometrics.BiometricPrompt;
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

import static com.an.biometric.BiometricManagerV23.KEY_NAME;

public class BiometricManagerV23 {
    private void generateKey() {
        try {

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
        }
    }

    private boolean initCipher() {
        try {
            Cipher cipher = Cipher.getInstance(
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

    /*
     * Step 1: Instantiate a FingerprintManagerCompat and call the authenticate method.
     * The authenticate method requires the:
     * 1. cryptoObject
     * 2. The second parameter is always zero.
     *    The Android documentation identifies this as set of flags and is most likely
     *    reserved for future use.
     * 3. The third parameter, cancellationSignal is an object used to turn off the
     *    fingerprint scanner and cancel the current request.
     * 4. The fourth parameteris a class that subclasses the AuthenticationCallback abstract class.
     *    This will be the same as the BiometricAuthenticationCallback
     * 5. The fifth parameter is an optional Handler instance.
     *    If a Handler object is provided, the FingerprintManager will use the Looper from that
     *    object when processing the messages from the fingerprint hardware.
     */
    FingerprintManagerCompat.CryptoObject cryptoObject = new FingerprintManagerCompat.CryptoObject(cipher);

    FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);

            fingerprintManagerCompat.authenticate(cryptoObject, 0, new CancellationSignal(),
                    new AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            updateStatus(String.valueOf(errString));
            biometricCallback.onAuthenticationError(errMsgId, errString);
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            updateStatus(String.valueOf(helpString));
            biometricCallback.onAuthenticationHelp(helpMsgId, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            dismissDialog();
            biometricCallback.onAuthenticationSuccessful();
        }


        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            updateStatus(context.getString(R.string.biometric_failed));
            biometricCallback.onAuthenticationFailed();
        }
    }, null);
}

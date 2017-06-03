package jpass.ui.helper;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import jpass.crypt.io.CryptOutputStream;
import jpass.ui.MessageDialog;


/**
 * @since 03 June 2017
 * @author ryoji
 *
 */
public class AesComparison {

    @Test
    public void testAesJre() throws Exception {
        final String expectedEncryptedText = "1jtxJXKYfEVUdyfElupdGOTMCchUGhwRLL/TXnqhxhjMfDNIw0X3phrICB1yr1D/nnmTU50xqcDaHTHnN7ulg7Vg6MYL1lE9wmwLhWvwX3Scgi0cJk/KAYi5v2Oeq6w9";
        
        String salt = "2ce80bd5-9fde-4336-b484-7607caf70d84";
        String password      = "password";
        String dataToEncrypt = "datadatadataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        byte[] hash = MessageDialog.generateHash(password.toCharArray(), salt.getBytes("UTF-8"));
        System.out.println("Salt:              " + salt);
        System.out.println("Password:          " + password);
        System.out.println("Data to encrypt:   " + dataToEncrypt);
        System.out.println("Hash:              " + Base64.getEncoder().encodeToString(hash));
        SecretKeySpec secret = new SecretKeySpec(hash, "AES");
        Enc enc = encrypt(dataToEncrypt, secret);
        final String oraclesEncryptionResult = enc.getCiphertext();
        System.out.println("Encryption Vector: " + enc.getVector());
        System.out.println("Encrypted Value:   " + oraclesEncryptionResult);
        Assert.assertThat(oraclesEncryptionResult, IsEqual.equalTo(expectedEncryptedText));
        String dec = decrypt(enc.getCiphertext(), enc.getVector(), secret);
        System.out.println("Data decrypted:    " + dec);
        
        try (
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            CryptOutputStream     co = new CryptOutputStream(bo, hash, Base64.getDecoder().decode(enc.getVector()));
        ) {
            co.write(dataToEncrypt.getBytes("UTF-8"));
            co.close(); //has to call .finishEncryption() to push the last encrypted block.
            co.flush();
            bo.flush();
            final String softwaresEncryptionResult = Base64.getEncoder().encodeToString(bo.toByteArray());
            System.out.println("Encrypted Value:   " + softwaresEncryptionResult);
            Assert.assertThat(softwaresEncryptionResult, IsEqual.equalTo(expectedEncryptedText));
        }
    }
    
    public String decrypt(final String encryptedData, final String initialVector, final SecretKey secret /*is equivalent to password and salt*/) throws Exception {
        /* Decrypt the message, given derived key and initialization vector. */
        Base64.Decoder decoder = Base64.getDecoder();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(decoder.decode(initialVector)));
        return new String(cipher.doFinal(decoder.decode(encryptedData)), "UTF-8");
    }

    public Enc encrypt(final String data, final SecretKey secret /*is equivalent to password and salt*/) throws Exception {
        /* Encrypt the message. */
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] ciphertext = cipher.doFinal(data.getBytes("UTF-8"));
        return new Enc(iv, ciphertext);
    }

//    public static SecretKey generateSecret(final String password, final String salt) throws Exception {
//        /* Derive the key, given password and salt. */
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
//        SecretKey tmp = factory.generateSecret(spec);
//        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
//        return secretKey;
//    }

    static class Enc {
        private final byte[] vector;
        private final byte[] ciphertext;
        Enc(byte[] vector, byte[] ciphertext) {
            this.vector = vector;
            this.ciphertext = ciphertext;
        }
        public String getVector() {
            return Base64.getEncoder().encodeToString(vector);
        }
        public String getCiphertext() {
            return Base64.getEncoder().encodeToString(ciphertext);
        }
    }
}

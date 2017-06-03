package jpass.ui.helper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * http://stackoverflow.com/questions/992019/java-256-bit-aes-password-based-encryption
 * Created by Ryoji Kodakari on 08/11/16.
 */
public class AES256 {

    public static void main(String[] args) throws Exception {
        AES256 aes256 = new AES256();
        String salt = Long.toString(new Date().getTime());
        String password      = "password";
        String dataToEncrypt = "datadatadata";
        System.out.println("Salt:              " + salt);
        System.out.println("Password:          " + password);
        System.out.println("Data to encrypt:   " + dataToEncrypt);
        SecretKey secret = AES256.generateSecret(password, salt);
        Enc enc = aes256.encrypt(dataToEncrypt, secret);
        //Random. The contents of this vector differs every time.
        System.out.println("Encryption Vector: " + enc.getVector());
        System.out.println("Encrypted Value:   " + enc.getCiphertext());
        String dec = aes256.decrypt(enc.getCiphertext(), enc.getVector(), secret);
        System.out.println("Data decrypted:    " + dec);
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

    public static SecretKey generateSecret(final String password, final String salt) throws Exception {
        /* Derive the key, given password and salt. */
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        return secretKey;
    }

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

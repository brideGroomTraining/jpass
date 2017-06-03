package jpass.crypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 * http://jexp.ru/index.php/Java_Tutorial/Security/RSA_algorithm
 * https://hondou.homedns.org/pukiwiki/pukiwiki.php?JavaSE%20RSA%B0%C5%B9%E6
 * http://crypto.stackexchange.com/questions/3608/why-is-padding-used-for-rsa-encryption-given-that-it-is-not-a-block-cipher
 * http://stackoverflow.com/questions/32033804/which-padding-is-used-by-javax-crypto-cipher-for-rsa/32033938
 * https://cwe.mitre.org/data/definitions/780.html
 * @author Ryoji Kodakari
 * @since 3 June 2017
 */
public class RsaOaep {
    
    public PublicKey deserializePublicKey(final String base64) throws Exception {
        byte[] data = Base64.getDecoder().decode(base64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }
    
    public PrivateKey deserializePrivateKey(final String base64) throws Exception {
        byte[] data = Base64.getDecoder().decode(base64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }
    
    public KeyPair createKeyPair(final SecureRandom random) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(4096 /*length of the key*/, random);
        return keygen.generateKeyPair();
    }
    
    public String decrypt(final Key deckey, final String data) throws Exception {
        //Use OAEP.
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, deckey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
    }
    
    public String encrypt(final Key enckey, final String data, final SecureRandom random) throws Exception {
        //Use OAEP.
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, enckey, random);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }
}
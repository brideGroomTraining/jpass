package jpass.ui.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import javax.crypto.SecretKey;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import jpass.ui.helper.AES256.Enc;

public class FileHelperTest {
    @Test
    //@Ignore
    public void cbcTest() throws Exception {
        AES256 aes256 = new AES256();
        String salt = Long.toString(new Date().getTime());
        String password      = "password";
        String dataToEncrypt = FileUtils.readFileToString(new File("datadata.txt"), "UTF-8");
        System.out.println("Salt:              " + salt);
        System.out.println("Password:          " + password);
        System.out.println("Data to encrypt:   " + dataToEncrypt);
        SecretKey secret = AES256.generateSecret(password, salt);
        Enc enc = aes256.encrypt(dataToEncrypt, secret);
        //Random. The contents of this vector differs every time.
        System.out.println("Encryption Vector: " + enc.getVector());
        System.out.println("Encrypted Value:   " + enc.getCiphertext());
        IOUtils.write(Base64.getDecoder().decode(enc.getCiphertext()), new GZIPOutputStream(new FileOutputStream(new File("datadata.txt.enc2"))));
        String dec = aes256.decrypt(enc.getCiphertext(), enc.getVector(), secret);
        System.out.println("Data decrypted:    " + dec);
        FileHelper.encryptFile(new File("datadata.txt"), "password", Base64.getDecoder().decode(enc.getVector()));
        //0c    fb  e2  7e  dc  ea  82  bf  93  00  a1  d9  ab  f9  6b  d3
        //33  64  67  ce  85  03  97  2e  94  ac  80  a3  d7  43  92  67
        //70  f5  f1  06  00  b2  64  56  fb  7a  87  b6  fb  45  a3  dd
        //9f  89  ca  2a  f9  7c  2c  65  ac  e6  2f  6a  e3  16  bb  ce
    }
}

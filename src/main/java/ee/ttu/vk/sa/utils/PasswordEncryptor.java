package ee.ttu.vk.sa.utils;


import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Created by strukov on 3/2/16.
 */
public class PasswordEncryptor {

private BasicTextEncryptor passwordEncryptor;

    public PasswordEncryptor(){
        passwordEncryptor = new BasicTextEncryptor();
        passwordEncryptor.setPassword("jasypt");
    }

    public  String encryptPassword(String password){
        return passwordEncryptor.encrypt(password);
    }

    public String decryptPassword(String encryptedPassword){
        return passwordEncryptor.decrypt(encryptedPassword);
    }
}

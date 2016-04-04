package ee.ttu.vk.attendance.utils;


import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor {

private BasicTextEncryptor passwordEncryptor;

    public PasswordEncryptor(){
        passwordEncryptor = new BasicTextEncryptor();
        passwordEncryptor.setPassword("jasypt");
    }

    public String encryptPassword(String password){
        return passwordEncryptor.encrypt(password);
    }

    public String decryptPassword(String encryptedPassword){
        return passwordEncryptor.decrypt(encryptedPassword);
    }
}

// 代码生成时间: 2025-08-20 15:01:05
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptionDecryptionService {

    private static final String ALGORITHM = "AES";
    private static final String UNICODE_FORMAT = "UTF8";
    private static final String TRANSFORMATION = "AES";

    // Error message constants
    private static final String CIPHER_EXCEPTION_MSG = "Error occurred during cipher operation: ";
    private static final String KEY_GENERATION_EXCEPTION_MSG = "Error occurred during key generation: ";

    // Generate a secret key
    public SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128, new SecureRandom()); // Initialize with 128-bit AES key
        return keyGen.generateKey();
    }

    // Encrypt the password
    public String encrypt(String plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(UNICODE_FORMAT));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the password
    public String decrypt(String ciphertext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = Base64.getDecoder().decode(ciphertext);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, UNICODE_FORMAT);
    }

    // Usage example
    public static void main(String[] args) {
        try {
            PasswordEncryptionDecryptionService service = new PasswordEncryptionDecryptionService();
            SecretKey key = service.generateKey();
            String originalPassword = "mySecretPassword";

            // Encrypt the password
            String encryptedPassword = service.encrypt(originalPassword, key);
            System.out.println("Encrypted Password: " + encryptedPassword);

            // Decrypt the password
            String decryptedPassword = service.decrypt(encryptedPassword, key);
            System.out.println("Decrypted Password: " + decryptedPassword);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

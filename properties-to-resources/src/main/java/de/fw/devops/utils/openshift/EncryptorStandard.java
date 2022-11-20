package de.fw.devops.utils.openshift;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * 
 * @author Felix Werner
 */
public class EncryptorStandard implements TextEncryptor {
  
  private static final String SALT = "fa04387396e3756f";
  
  private String masterSecret;
  
  /**
   * @param masterSecret
   */
  private EncryptorStandard(String masterSecret) {
    this.masterSecret = masterSecret;
  }
  
  public static TextEncryptor fromMasterSecret(String masterSecret) {
    return new EncryptorStandard(masterSecret);
  }
  
  @Override
  public String encrypt(String text) {
    TextEncryptor encryptor = Encryptors.text(masterSecret, SALT);
    return encryptor.encrypt(text);
  }
  
  @Override
  public String decrypt(String encryptedText) {
    TextEncryptor decryptor = Encryptors.text(masterSecret, SALT);
    return decryptor.decrypt(encryptedText);
  }
  
}

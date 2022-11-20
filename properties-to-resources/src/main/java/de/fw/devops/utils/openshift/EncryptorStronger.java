package de.fw.devops.utils.openshift;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * 
 * 
 * @author Felix Werner
 */
public class EncryptorStronger implements TextEncryptor {
  
  private static final String SALT = "5382ff0ef32995ecdceead0352be76c6";
  
  private String masterSecret;
  
  /**
   * @param masterSecret
   */
  private EncryptorStronger(String masterSecret) {
    this.masterSecret = masterSecret;
  }
  
  public static TextEncryptor fromMasterSecret(String masterSecret) {
    return new EncryptorStronger(masterSecret);
  }
  
  @Override
  public String encrypt(String text) {
    TextEncryptor encryptor = Encryptors.delux(masterSecret, SALT);
    return encryptor.encrypt(text);
  }
  
  @Override
  public String decrypt(String encryptedText) {
    TextEncryptor decryptor = Encryptors.delux(masterSecret, SALT);
    return decryptor.decrypt(encryptedText);
  }
  
}

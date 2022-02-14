package de.fw.devops.utils.openshift;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * experimentel
 * 
 * @author Felix Werner
 */
public class OcApplyFileExecutor {
  private static Logger logger = LogManager.getLogger(OcApplyFileExecutor.class);
  
  private Path workingDirectory;
  private String script;
  
  private String username;
  private String password;
  
  /**
   * @param script = cmd/sh file
   * @param workDir
   * @return this
   */
  public static OcApplyFileExecutor fromScriptAndWorkingDirectory(String script, String workDir) {
    return new OcApplyFileExecutor().script(script).workingDirectory(workDir);
  }
  
  /**
   * @param workingDirectory the workingDirectory to set
   * @return this
   */
  public OcApplyFileExecutor workingDirectory(String workingDirectory) {
    this.workingDirectory = Paths.get(workingDirectory);
    return this;
  }
  
  /**
   * @param script the script to set
   * @return this
   */
  public OcApplyFileExecutor script(String script) {
    this.script = script;
    return this;
  }
  
  /**
   * @param username the username to set
   * @return this
   */
  public OcApplyFileExecutor username(String username) {
    this.username = username;
    return this;
  }
  
  /**
   * @param password the password to set
   * @return this
   */
  public OcApplyFileExecutor password(String password) {
    this.password = password;
    return this;
  }
  
  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    if (args == null || args.length < 2) {
      logger.info("Bitte sh/cmd-script und workingDir angeben!");
      return;
    }
    new OcApplyFileExecutor().script(args[0]).workingDirectory(args[1]).process();
  }
  
  /**
   * In diesen Main kann man Benutzernamen/Password verschlüsseln und in Jenkins als Environmentvaribalen zur Verfügung stellen:
   * OPENSHIFT_OC_PASSWORD=ENC(verschlüsselt) OPENSHIFT_OC_USERNAME=ENC(verschlüsselt)
   * 
   * wird experimentel für FT verwendet
   * 
   * @param args
   * @throws Exception
   */
  public static void main2(String[] args) throws Exception {
    OcApplyFileExecutor ocexec = new OcApplyFileExecutor();
    String s = ocexec.encrypt("N000XXX");
    logger.info(s);
    logger.info(ocexec.decrypt(s));
    String p = ocexec.encrypt("P@ssW@rd");
    logger.info(p);
    logger.info(ocexec.decrypt(p));
    String getenv = "ENC(aefgsrtherj)";
    logger.info(getenv.substring(getenv.indexOf("ENC(") + 4, getenv.length() - 1));
  }
  
  int process() throws Exception {
    logger.info("start executing: {}", getScript());
    ProcessBuilder processBuilder = new ProcessBuilder(getScript(), getUsername(), getPassword());
    
    processBuilder.directory(workingDirectory.toFile());
    Process process = processBuilder.start();
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.contains("--password")) {
        line = line.substring(0, line.indexOf("--password"));
        logger.info("{} --password=*********", line);
      } else {
        logger.info(line);
      }
    }
    logger.info("executing {} finished! ", getScript());
    return process.waitFor();
  }
  
  /**
   * @return the script
   */
  private String getScript() {
    return script;
  }
  
  /**
   * @return the password
   */
  public String getPassword() {
    if (password == null) {
      return getFromEnv(System.getenv("OPENSHIFT_OC_PASSWORD"));
    } else {
      return password;
    }
  }
  
  /**
   * @return the username
   */
  public String getUsername() {
    if (username == null) {
      return getFromEnv(System.getenv("OPENSHIFT_OC_USERNAME"));
    } else {
      return username;
    }
  }
  
  private String getFromEnv(String getenv) {
    if (getenv != null && getenv.startsWith("ENC(")) {
      return decrypt(getenv.substring(getenv.indexOf("ENC(") + 4, getenv.length() - 1));
    } else {
      return getenv;
    }
  }
  
  /**
   * @param plain
   * @return string encrypted
   */
  public String encrypt(String plain) {
    return encrypt(plain, getMasterSecret());
  }
  
  private String getMasterSecret() {
    if (System.getenv("OPENSHIFT_OC_MASTERSECRET") != null) {
      return System.getenv("OPENSHIFT_OC_MASTERSECRET");
    } else {
      return "6h7KDS8UCd32jJEN";
    }
  }
  
  /**
   * @param secret
   * @return plain string
   */
  public String decrypt(String secret) {
    return decrypt(secret, getMasterSecret());
  }
  
  // Verschlüsselungsmethoden mit spring-security-crypto
  private static final String SALT = "fa04387396e3756f";
  
  private String encrypt(String strToEncrypt, String masterSecret) {
    TextEncryptor encryptor = Encryptors.text(masterSecret, SALT);
    return encryptor.encrypt(strToEncrypt);
  }
  
  private String decrypt(String strToDecrypt, String masterSecret) {
    TextEncryptor decryptor = Encryptors.text(masterSecret, SALT);
    return decryptor.decrypt(strToDecrypt);
  }
  
}

package de.fw.devops.utils.openshift;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * 
 * Diese Klasse wird verwendet um sh(cmd)-Skripte aufzurufen, die yaml-Files gegen OpenShift mit Hilfe von oc-Tool ausfuehrt: "oc apply x.yaml" siehe:
 * src/main/resources/templates/openshift
 * 
 * oc muss sich im PATH befinden.
 * 
 * Benutzername/Pass@ord wird ueber Environment-Variablen bezogen OPENSHIFT_OC_USERNAME/OPENSHIFT_OC_PASSWORD und an sh/cmd Skripte weitergegeben. (oc login
 * https://api.cntr.sv.loc:6443/ --username=$1 --password=$2)
 * 
 * Benutzername/Pass@ord koennen verschlueselt(sind sie auch auf Jenkins)bereitgestellt werden(rg.springframework.security.crypto.encrypt). Siehe
 * properties-to-resources/src/test/java/de/svi/devops/utils/OcApplyFileExecutorTest.java
 * 
 * Beispiel-Aufruf über maven-exec-plugin:
 *     <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>3.1.0</version>
        <executions>
          <execution>
            <id>executing yaml to svi-ew-svis</id>
            <phase>package</phase>
            <goals>
              <goal>java</goal>
            </goals>
            <configuration>
              <mainClass>de.svi.devops.utils.openshift.OcApplyFileExecutor</mainClass>
              <workingDirectory>${yaml-oc-apply.yamlPathEW}</workingDirectory>
              <arguments>
                <argument>${yaml-oc-apply.yamlPathEW}/${yaml-oc-apply.executableScript}</argument>
                <argument>${yaml-oc-apply.yamlPathEW}</argument>
              </arguments>
            </configuration>
          </execution>
        </executions>
      </plugin>
 * 
 * Siehe auch: svis-maven-tiles\tile.xml Maven-Profil: yaml-oc-apply
 * 
 * @author Felix Werner
 */
public class OcApplyFileExecutor {
  private static Logger logger = LogManager.getLogger(OcApplyFileExecutor.class);
  
  private static final String ENCRYPTED_VALUE_PREFIX = "ENC(";
  private static final String ENCRYPTED_VALUE_SUFFIX = ")";
  
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
    if (args == null || args.length < 1) {
      logger.info("Bitte sh/cmd-script und/oder workingDir angeben!");
      return;
    }
    if(args.length==1) {
      logger.info("with exit code! {}", new OcApplyFileExecutor().workingDirectory(args[0]).chmod());
    }else {
      logger.info("with exit code! {}", new OcApplyFileExecutor().script(args[0]).workingDirectory(args[1]).process());
    }
    
  }
  
  public int process() throws Exception {
    logger.info("start executing: {}", getScript());
    
    if(System.getenv().get("OSTYPE")!=null && System.getenv().get("OSTYPE").toLowerCase().startsWith("linux")) {
      chmod();
    }else {
      logger.info("attention obviously executing on windows!");
    }
    
    ProcessBuilder processBuilder = new ProcessBuilder(getScript(), getUsername(), getPassword());
    
    processBuilder.directory(workingDirectory.toFile());
    Process process = processBuilder.start();
    
    {
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
    }
    
    { //eventuell auch fehler ausgeben, sonst gehen sie leise unter
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      String line;
      List<String> errors = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        errors.add(line);
      }
      if(logger.isDebugEnabled()) {
        for(String l: errors) {
          logger.debug(l);
        }
      }else if(!errors.isEmpty()){
        logger.info("attention! there are errors in error stream! lines read: {}", errors.size());
      }
    }
    logger.info("executing {} finished! ", getScript());
    return process.waitFor();
  }
  
  public int chmod() throws Exception{
    ProcessBuilder processBuilder = new ProcessBuilder("chmod", "-R", "775", workingDirectory.toAbsolutePath().toString());
    
    processBuilder.directory(workingDirectory.toFile());
    Process process = processBuilder.start();
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
     logger.info(line);
    }
    logger.info("executing chmod 775 on {} finished !", workingDirectory.toAbsolutePath().toString());
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
  

  private static boolean isEncryptedValue(String propertyValue) {
    return propertyValue.startsWith(ENCRYPTED_VALUE_PREFIX) && propertyValue.endsWith(ENCRYPTED_VALUE_SUFFIX);
  }
  
  private String getFromEnv(String getenv) {
    if (isEncryptedValue(getenv)) {
   // ohne Pre and Suffix, also z.B. ohne "ENC(" und ohne ")"
      final String secretWithoutPreAndSuffix = getenv.substring(ENCRYPTED_VALUE_PREFIX.length(),
          getenv.length() - ENCRYPTED_VALUE_SUFFIX.length());
      return decrypt(secretWithoutPreAndSuffix);
    } else {
      return getenv;
    }
  }
  
  /**
   * @param plain
   * @return string encrypted
   */
  public String encrypt(String plain) {
    return getEncryptor().encrypt(plain);
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
    return getEncryptor().decrypt(secret);
  }
  
  private TextEncryptor encryptor = EncryptorStandard.fromMasterSecret(getMasterSecret());
  
  /**
   * @param encryptor the encryptor to set
   * @return this
   */
  public OcApplyFileExecutor encryptor(TextEncryptor encryptor) {
    this.encryptor = encryptor;
    return this;
  }
  
  /**
   * @return TextEncryptor
   */
  public TextEncryptor getEncryptor() {
    String encryptionType = System.getProperty("password.based.encryption", "standard");
    
    if (encryptionType.equals("stronger")) {
      encryptor = EncryptorStronger.fromMasterSecret(getMasterSecret());
    }
    logger.trace("encrypting with {}", encryptor);
    return encryptor;
  }
  
}

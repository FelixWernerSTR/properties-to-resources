package de.fw.devops.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import de.fw.devops.utils.openshift.OcApplyFileExecutor;

/**
 * 
 * @author Felix Werner
 */
public class OcApplyFileExecutorTest {
  
  private static Logger logger = LogManager.getLogger(OcApplyFileExecutor.class);
  
  /**
   * 
   * Funktionalität im Unit-Test
   * 
   * In diesem Test kann man Benutzernamen/Password verschlüsseln und in Jenkins als Environmentvaribalen zur Verfügung stellen:
   * OPENSHIFT_OC_PASSWORD=ENC(verschlüsselt) OPENSHIFT_OC_USERNAME=ENC(verschlüsselt)
   * 
   * @throws IOException
   */
  @Test
  public void testEncryption() throws IOException {
    String testUserNamePlain = "N000XXX";
    String testUserNameEncrypted = OcApplyFileExecutor.fromScriptAndWorkingDirectory(".", ".").encrypt(testUserNamePlain);
    logger.info("testUserNameEncrypted: {}", testUserNameEncrypted);
    String testUserNameDecrypted = OcApplyFileExecutor.fromScriptAndWorkingDirectory(".", ".").decrypt(testUserNameEncrypted);
    assertThat(testUserNamePlain).isEqualTo(testUserNameDecrypted);
    
    String testUserPasswordPlain = "P@ssW@rd";
    String testUserPasswordEncrypted = OcApplyFileExecutor.fromScriptAndWorkingDirectory(".", ".").encrypt(testUserPasswordPlain);
    logger.info("testUserPasswordEncrypted: {}", testUserPasswordEncrypted);
    String testUserPasswordDecrypted = OcApplyFileExecutor.fromScriptAndWorkingDirectory(".", ".").decrypt(testUserPasswordEncrypted);
    assertThat(testUserPasswordPlain).isEqualTo(testUserPasswordDecrypted);
    
    testUserPasswordDecrypted = OcApplyFileExecutor.fromScriptAndWorkingDirectory(".", ".")
        .decrypt(PropertiesReader.read(Paths.get("src/test/resources/OcApplyFileExecutorTest.properties")).getProperty(testUserPasswordPlain));
    assertThat(testUserPasswordPlain).isEqualTo(testUserPasswordDecrypted);
  }
  
}

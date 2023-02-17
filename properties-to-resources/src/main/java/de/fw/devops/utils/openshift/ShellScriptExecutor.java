package de.fw.devops.utils.openshift;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class ShellScriptExecutor {
  private static Logger logger = LogManager.getLogger(ShellScriptExecutor.class);
  
  private Path workingDirectory;
  private String script;
  
  /**
   * @param script = cmd/sh file
   * @param workDir
   * @return this
   */
  public static ShellScriptExecutor fromScriptAndWorkingDirectory(String script, String workDir) {
    return new ShellScriptExecutor().script(script).workingDirectory(workDir);
  }
  
  /**
   * @param workingDirectory the workingDirectory to set
   * @return this
   */
  public ShellScriptExecutor workingDirectory(String workingDirectory) {
    this.workingDirectory = Paths.get(workingDirectory);
    return this;
  }
  
  /**
   * @param script the script to set
   * @return this
   */
  public ShellScriptExecutor script(String script) {
    this.script = script;
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
      logger.info("with exit code! {}", new ShellScriptExecutor().workingDirectory(args[0]).chmod());
    }else {
      logger.info("with exit code! {}", new ShellScriptExecutor().script(args[0]).workingDirectory(args[1]).process());
    }
    
  }
  
  public int process() throws Exception {
    logger.info("start executing: {}", getScript());
    
    if(System.getenv().get("OSTYPE")!=null && System.getenv().get("OSTYPE").toLowerCase().startsWith("linux")) {
      chmod();
    }else {
      logger.info("attention obviously executing on windows!");
    }
    
    ProcessBuilder processBuilder = new ProcessBuilder(getScript());
    
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
  
}

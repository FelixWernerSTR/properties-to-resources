call ${deployment.appName}_setEnvironment.cmd

<#if deployment.version??>    
call %JAVA_HOME%/bin/java -jar ${deployment.appName}:${deployment.version}.jar "$@"
<#else>    
call %JAVA_HOME%/bin/java -jar ${deployment.appName}.jar "$@"
</#if>


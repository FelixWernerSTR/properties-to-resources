#!/bin/sh

./${deployment.appName}_setEnvironment.sh

<#if deployment.version??>    
exec java -jar ${deployment.appName}:${deployment.version}.jar "$@"
<#else>    
exec java -jar ${deployment.appName}.jar "$@"
</#if>


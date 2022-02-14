rem oc muss in PATH vorhanden sein
<#if deployment.ocBinaryPath??> 
set PATH=%PATH%;${deployment.ocBinaryPath}
</#if>
oc login https://api.cntr.sv.loc:6443/ --username=%1 --password=%2
oc project ${deployment.namespace}
oc apply -f ${deployment.appName}_Deployment_v1.yaml

#!/bin/bash
<#if deployment.ocBinaryPath??> 
export PATH=$PATH:${deployment.ocBinaryPath}
<#else>    
export PATH=$PATH:/home/n0009271/oc
</#if>
<#if deployment.ocServerUrl??> 
oc login ${deployment.ocServerUrl} --username=$1 --password=$2
<#else>    
oc login https://api.cntr.sv.loc:6443/ --username=$1 --password=$2
</#if>
oc project ${deployment.namespace}
oc apply -f ${deployment.appName}_ConfigMap_v1.yaml
oc apply -f ${deployment.appName}_Deployment_v1.yaml
oc apply -f ${deployment.appName}_Service_v1.yaml
oc apply -f ${deployment.appName}_Route_v1.yaml

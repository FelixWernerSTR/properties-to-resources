#!/bin/bash
<#if deployment.ocBinaryPath??> 
export PATH=$PATH:${deployment.ocBinaryPath}
<#else>    
export PATH=$PATH:/home/n0009271/oc
</#if>
oc login https://api.cntr.sv.loc:6443/ --username=$1 --password=$2
oc project ${deployment.namespace}
oc apply -f ${deployment.appName}_Secret_v1.yaml

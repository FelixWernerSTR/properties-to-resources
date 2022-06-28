oc.exe oder oc executable muss im Pfad oder in PATH-Variable vorhanden sein!!!
oc login https://api.cntr.sv.loc:6443/ --username=N0009271 --password=...
oc project svi-ew-svis 
oc apply -f file.yaml 

Reihenfolge:
0. imagePullSecret muss pro Stage direkt auf Openshift ueber Web-Admin-Console oder OC-Skript angelegt werden
1. Secret/ConfigMap
2. Deployment
3. Service
4. Route (nur OpenShift)

ACHTUNG geht nur mit "Check Point Mobile" oder ueber VDE!

Ein Template fuer Ingress existiert. Ist aber nicht getestet. Das Deployment-Model muesste man dazu auch etwas ausbauen.
Fuer Kubernetes muss mann Ingress verwenden. 
Fuer Ingress Braucht man einen installierten und konfigurierten IngressController(z.B.: Nginx Ingress Controller). 
Mit Route auf OpenShift braucht man das nicht.

In einem Ingress kann man viele Routes abbilden.



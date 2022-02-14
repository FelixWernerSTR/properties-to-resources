oc.exe oder oc executable muss im Pfad oder in PATH-Variable vorhanden sein!!!
oc login https://api.cntr.sv.loc:6443/ --username=N0009271 --password=...
oc project myapp-dev
oc apply -f file.yaml 

Reihenfolge:
0. imagePullSecret muss pro Stage direkt auf Openshift über Web-Admin-Console oder Skript angelegt werden
1. Secret/ConfigMap
2. Deployment
3. Service
4. Route

rem testing yamls with docker-desktop "kubernetes running". see also: https://kubernetes.github.io/ingress-nginx/deploy/   https://kubernetes.io/docs/concepts/services-networking/ingress/
kubectl create namespace ${deployment.namespace}
kubectl apply -f ${deployment.appName}_ConfigMap_v1.yaml
kubectl apply -f ${deployment.appName}_Secret_v1.yaml
kubectl apply -f ${deployment.appName}_Deployment_v1.yaml
kubectl apply -f ${deployment.appName}_Service_v1.yaml
kubectl apply -f ${deployment.appName}_Ingress_v1.yaml
rem Note! to check your ui or web/rest-service from outside the kubernetes-cluster, you have to install an ingress-controller!
package de.fw.devops.utils.openshift.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO für Openshift/Kubernetes-Deployment/(Deafult)Route/Service
 * 
 * @author Felix Werner
 *
 */
@SuppressWarnings("javadoc")
public class Deployment {
  
  String stage;
  String appName;
  String name;
  String description;
  String namespace;
  String hostSuffix;
  String hostPrefix;
  String serviceAccountName;
  String imagePullSecretName;
  String image;
  String containerPort;
  String limitsCpu;
  String limitsMemory;
  String requestsCpu;
  String requestsMemory;
  String targetPort;
  String registryHost;
  String replicas;// Anzahl Pods/Docker-Container fürs Deployment
  String tls;// values:true or false
  String http = "false";// values:true or false, http erzwingen, kein https!
  String cookie;// values:true or false
  String routePath;
  String serviceType;// optional, möglichen Werte: LoadBalancer,ClusterIP,NodePort...
  String configMapRef;
  String secretRef;
  String configMapName;
  String targetPathSuffix;// optional, wenn nicht über Namespace/StageSysproperty-mapping aufgelöst werden kann
  String minReadySeconds;// optional
  String strategyType; // optional, möglichen Werte: RollingUpdate(default)/Recreate
  String rollingUpdateMaxUnavailable = "25%"; // wird gesetzt wenn strategyType=RollingUpdate. Man kann also auch diesen default Wert übersteuern
  String rollingUpdateMaxSurge = "25%"; // wird gesetzt wenn strategyType=RollingUpdate. Man kann also auch diesen default Wert übersteuern
  String readinessProbePath; // optional
  String ocBinaryPath; // optional: Pfad zum oc - OpenShift Command Line Interface, kann in sh/cmd Skripten gesetzt werden zum Beispiel:
                       // ${sys:basedir}/lib/oc/linux
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getNamespace() {
    return namespace;
  }
  
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }
  
  public String getServiceAccountName() {
    return serviceAccountName;
  }
  
  public void setServiceAccountName(String serviceAccountName) {
    this.serviceAccountName = serviceAccountName;
  }
  
  public String getImagePullSecretName() {
    return imagePullSecretName;
  }
  
  public void setImagePullSecretName(String imagePullSecretName) {
    this.imagePullSecretName = imagePullSecretName;
  }
  
  public String getImage() {
    return image;
  }
  
  public void setImage(String image) {
    this.image = image;
  }
  
  public String getContainerPort() {
    return containerPort;
  }
  
  public void setContainerPort(String containerPort) {
    this.containerPort = containerPort;
  }
  
  public String getLimitsCpu() {
    return limitsCpu;
  }
  
  public void setLimitsCpu(String limitsCpu) {
    this.limitsCpu = limitsCpu;
  }
  
  public String getLimitsMemory() {
    return limitsMemory;
  }
  
  public void setLimitsMemory(String limitsMemory) {
    this.limitsMemory = limitsMemory;
  }
  
  public String getRequestsCpu() {
    return requestsCpu;
  }
  
  public void setRequestsCpu(String requestsCpu) {
    this.requestsCpu = requestsCpu;
  }
  
  public String getRequestsMemory() {
    return requestsMemory;
  }
  
  public void setRequestsMemory(String requestsMemory) {
    this.requestsMemory = requestsMemory;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getTargetPort() {
    return targetPort;
  }
  
  public void setTargetPort(String targetPort) {
    this.targetPort = targetPort;
  }
  
  public String getHostSuffix() {
    return hostSuffix;
  }
  
  public void setHostSuffix(String hostSuffix) {
    this.hostSuffix = hostSuffix;
  }
  
  public String getStage() {
    return stage;
  }
  
  public void setStage(String stage) {
    this.stage = stage;
  }
  
  public String getAppName() {
    return appName;
  }
  
  public void setAppName(String appName) {
    this.appName = appName;
  }
  
  public String getRegistryHost() {
    return registryHost;
  }
  
  public void setRegistryHost(String registryHost) {
    this.registryHost = registryHost;
  }
  
  public String getReplicas() {
    return replicas;
  }
  
  public void setReplicas(String replicas) {
    this.replicas = replicas;
  }
  
  public String getTls() {
    return tls;
  }
  
  public void setTls(String tls) {
    this.tls = tls;
  }
  
  public String getCookie() {
    return cookie;
  }
  
  public void setCookie(String cookie) {
    this.cookie = cookie;
  }
  
  public String getRoutePath() {
    return routePath;
  }
  
  public void setRoutePath(String routePath) {
    this.routePath = routePath;
  }
  
  public String getHostPrefix() {
    return hostPrefix;
  }
  
  public void setHostPrefix(String hostPrefix) {
    this.hostPrefix = hostPrefix;
  }
  
  public String getServiceType() {
    return serviceType;
  }
  
  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }
  
  /**
   * @return the http
   */
  public String getHttp() {
    return http;
  }
  
  /**
   * @param http the http to set
   */
  public void setHttp(String http) {
    this.http = http;
  }
  
  /**
   * @return the configMapRef
   */
  public String getConfigMapRef() {
    return configMapRef;
  }
  
  /**
   * @param configMapRef the configMapRef to set
   */
  public void setConfigMapRef(String configMapRef) {
    this.configMapRef = configMapRef;
  }
  
  /**
   * @return the secretRef
   */
  public String getSecretRef() {
    return secretRef;
  }
  
  /**
   * @param secretRef the secretRef to set
   */
  public void setSecretRef(String secretRef) {
    this.secretRef = secretRef;
  }
  
  /**
   * @return the configMapName
   */
  public String getConfigMapName() {
    return configMapName;
  }
  
  /**
   * @param configMapName the configMapName to set
   */
  public void setConfigMapName(String configMapName) {
    this.configMapName = configMapName;
  }
  
  /**
   * @return the targetPathSuffix
   */
  public String getTargetPathSuffix() {
    return targetPathSuffix;
  }
  
  /**
   * @param targetPathSuffix the targetPathSuffix to set
   */
  public void setTargetPathSuffix(String targetPathSuffix) {
    this.targetPathSuffix = targetPathSuffix;
  }
  
  /**
   * @return the minReadySeconds
   */
  public String getMinReadySeconds() {
    return minReadySeconds;
  }
  
  /**
   * @param minReadySeconds the minReadySeconds to set
   */
  public void setMinReadySeconds(String minReadySeconds) {
    this.minReadySeconds = minReadySeconds;
  }
  
  /**
   * @return the strategyType
   */
  public String getStrategyType() {
    return strategyType;
  }
  
  /**
   * @param strategyType the strategyType to set
   */
  public void setStrategyType(String strategyType) {
    this.strategyType = strategyType;
  }
  
  /**
   * @return the rollingUpdateMaxUnavailable
   */
  public String getRollingUpdateMaxUnavailable() {
    return rollingUpdateMaxUnavailable;
  }
  
  /**
   * @param rollingUpdateMaxUnavailable the rollingUpdateMaxUnavailable to set
   */
  public void setRollingUpdateMaxUnavailable(String rollingUpdateMaxUnavailable) {
    this.rollingUpdateMaxUnavailable = rollingUpdateMaxUnavailable;
  }
  
  /**
   * @return the rollingUpdateMaxSurge
   */
  public String getRollingUpdateMaxSurge() {
    return rollingUpdateMaxSurge;
  }
  
  /**
   * @param rollingUpdateMaxSurge the rollingUpdateMaxSurge to set
   */
  public void setRollingUpdateMaxSurge(String rollingUpdateMaxSurge) {
    this.rollingUpdateMaxSurge = rollingUpdateMaxSurge;
  }
  
  /**
   * @return the readinessProbePath
   */
  public String getReadinessProbePath() {
    return readinessProbePath;
  }
  
  /**
   * @param readinessProbePath the readinessProbePath to set
   */
  public void setReadinessProbePath(String readinessProbePath) {
    this.readinessProbePath = readinessProbePath;
  }
  
  /**
   * @return the ocBinaryPath
   */
  public String getOcBinaryPath() {
    return ocBinaryPath;
  }
  
  /**
   * @param ocBinaryPath the ocBinaryPath to set
   */
  public void setOcBinaryPath(String ocBinaryPath) {
    this.ocBinaryPath = ocBinaryPath;
  }
  
  public static List<String> getRegex() {
    
    Class<Deployment> aClass = Deployment.class;
    Field[] fields = aClass.getDeclaredFields();
    
    List<String> fieldList = new ArrayList<>();
    
    for (Field field : fields) {
      fieldList.add(Deployment.class.getSimpleName().toLowerCase() + "[.]" + field.getName());
    }
    return fieldList;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Deployment [stage=");
    builder.append(stage);
    builder.append(", appName=");
    builder.append(appName);
    builder.append(", name=");
    builder.append(name);
    builder.append(", description=");
    builder.append(description);
    builder.append(", namespace=");
    builder.append(namespace);
    builder.append(", hostSuffix=");
    builder.append(hostSuffix);
    builder.append(", hostPrefix=");
    builder.append(hostPrefix);
    builder.append(", serviceAccountName=");
    builder.append(serviceAccountName);
    builder.append(", imagePullSecretName=");
    builder.append(imagePullSecretName);
    builder.append(", image=");
    builder.append(image);
    builder.append(", containerPort=");
    builder.append(containerPort);
    builder.append(", limitsCpu=");
    builder.append(limitsCpu);
    builder.append(", limitsMemory=");
    builder.append(limitsMemory);
    builder.append(", requestsCpu=");
    builder.append(requestsCpu);
    builder.append(", requestsMemory=");
    builder.append(requestsMemory);
    builder.append(", targetPort=");
    builder.append(targetPort);
    builder.append(", registryHost=");
    builder.append(registryHost);
    builder.append(", replicas=");
    builder.append(replicas);
    builder.append(", tls=");
    builder.append(tls);
    builder.append(", http=");
    builder.append(http);
    builder.append(", cookie=");
    builder.append(cookie);
    builder.append(", routePath=");
    builder.append(routePath);
    builder.append(", serviceType=");
    builder.append(serviceType);
    builder.append(", configMapRef=");
    builder.append(configMapRef);
    builder.append(", secretRef=");
    builder.append(secretRef);
    builder.append(", configMapName=");
    builder.append(configMapName);
    builder.append(", targetPathSuffix=");
    builder.append(targetPathSuffix);
    builder.append(", minReadySeconds=");
    builder.append(minReadySeconds);
    builder.append(", strategyType=");
    builder.append(strategyType);
    builder.append(", rollingUpdateMaxUnavailable=");
    builder.append(rollingUpdateMaxUnavailable);
    builder.append(", rollingUpdateMaxSurge=");
    builder.append(rollingUpdateMaxSurge);
    builder.append(", readinessProbePath=");
    builder.append(readinessProbePath);
    builder.append("]");
    return builder.toString();
  }
  
}

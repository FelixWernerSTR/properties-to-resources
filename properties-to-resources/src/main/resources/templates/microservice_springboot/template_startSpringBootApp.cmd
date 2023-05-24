set JAVA_HOME=C:/developerbase/java/jdk-11.0.13
set PATH=%JAVA_HOME%/bin;%PATH%
set PROPERTIES_ENCYPTOR_MASTERSECRET=y4nC4ncRkTaVtNvpDsID
set JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
%JAVA_HOME%/bin/java "-Dlogging.level.ROOT=DEBUG" -jar target/[=mavenproject.artifactId]-[=mavenproject.version].war
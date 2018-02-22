FROM java:8

EXPOSE 8080

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY target/cm-assists-0.0.2-SNAPSHOT.jar /services/cm-assists.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Shanghai", "-Dspring.profiles.active=prod", "-jar", "/services/cm-assists.jar"]
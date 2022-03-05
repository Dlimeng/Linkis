FROM harbor.shineweng.tech/linkis/linkis-base:1.0 as linkis

ADD assembly-combined-package/target/apache-linkis-1.0.3-incubating-bin.tar.gz /opt/linkis

FROM harbor.shineweng.tech/linkis/linkis-bd-base:1.0

ENV LINKIS_HOME=/opt/linkis

COPY --from=linkis /opt/linkis/linkis-package /opt/linkis

COPY docker/mysql-connector-java-5.1.49.jar /opt/linkis/lib/linkis-commons/public-module/
COPY docker/mysql-connector-java-5.1.49.jar /opt/linkis/lib/linkis-spring-cloud-services/linkis-mg-gateway/

WORKDIR /opt/linkis

ENTRYPOINT ["sh", "/opt/linkis/sbin/linkis-daemon-k8s.sh"]

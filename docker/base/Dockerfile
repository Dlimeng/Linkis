FROM centos:centos7

ADD jdk-8u311-linux-x64.tar.gz /usr/local
ADD scala-2.11.12.tgz /usr/local

ENV JAVA_HOME /usr/local/jdk1.8.0_311
ENV JRE_HOME ${JAVA_HOME}/jre
ENV CLASSPATH .:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV SCALA_HOME /usr/local/scala-2.11.12

ENV PATH $PATH:$JAVA_HOME/bin:$SCALA_HOME/bin


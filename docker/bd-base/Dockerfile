FROM harbor.shineweng.tech/linkis/linkis-base:1.0

ADD hadoop-2.7.2.tar.gz /usr/local
ENV HADOOP_HOME /usr/local/hadoop-2.7.2
ENV PATH $PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
ENV HADOOP_CONF_DIR $HADOOP_HOME/etc/hadoop
ENV LD_LIBRARY_PATH $LD_LIBRARY_PATH:$HADOOP_HOME/lib/native

ADD apache-hive-2.3.3-bin.tar.gz /usr/local
ENV HIVE_HOME /usr/local/apache-hive-2.3.3-bin
ENV PATH $PATH:$HIVE_HOME/bin

ADD spark-2.4.3-bin-hadoop2.7.tgz /usr/local
ENV SPARK_HOME /usr/local/spark-2.4.3-bin-hadoop2.7
ENV PATH $PATH:$SPARK_HOME/bin

ADD flink-1.12.2-bin-scala_2.11.tgz /usr/local
ENV FLINK_HOME /usr/local/flink-1.12.2
ENV PATH $PATH:$FLINK_HOME/bin

RUN yum -y install which \
    & ln -s /usr/local/hadoop-2.7.2 /usr/local/hadoop \
    & ln -s /usr/local/apache-hive-2.3.3-bin /usr/local/hive \
    & ln -s /usr/local/spark-2.4.3-bin-hadoop2.7 /usr/local/spark \
    & ln -s /usr/local/flink-1.12.2 /usr/local/flink


CMD ["/usr/sbin/init"]


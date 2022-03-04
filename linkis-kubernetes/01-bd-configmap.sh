#!/bin/sh
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`


[ -z "$CURRENT_DIR" ] && CURRENT_DIR=`cd "$PRGDIR" >/dev/null; pwd`

kubectl create configmap hadoop-config -n linkis --from-file=$CURRENT_DIR/hadoop-config
kubectl create configmap hive-config -n linkis --from-file=$CURRENT_DIR/hive-config
kubectl create configmap spark-config -n linkis --from-file=$CURRENT_DIR/spark-config

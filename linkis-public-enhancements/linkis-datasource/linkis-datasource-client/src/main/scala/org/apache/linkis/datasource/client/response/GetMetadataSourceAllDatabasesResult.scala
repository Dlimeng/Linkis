package org.apache.linkis.datasource.client.response

import org.apache.linkis.httpclient.dws.annotation.DWSHttpMessageResult
import org.apache.linkis.httpclient.dws.response.DWSResult

import scala.beans.BeanProperty

@DWSHttpMessageResult("/api/rest_j/v\\d+/datasource/dbs")
class GetMetadataSourceAllDatabasesResult extends DWSResult {
  @BeanProperty var dbs: java.util.List[java.util.Map[String, Any]] = _

  def allDbs: java.util.List[String] ={
    import scala.collection.JavaConverters._
    dbs.asScala.flatMap(m=>{
      m.asScala.map(_._2.toString)
    }).toList.asJava
  }
}

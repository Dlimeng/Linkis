package org.apache.linkis.datasource.client.response

import org.apache.linkis.httpclient.dws.annotation.DWSHttpMessageResult
import org.apache.linkis.httpclient.dws.response.DWSResult

import scala.beans.BeanProperty

@DWSHttpMessageResult("/api/rest_j/v\\d+/datasource/all/dbs")
class GetMetadataSourceAllDatabasesResult extends DWSResult {
  @BeanProperty var dbs: java.util.List[String] = _
}

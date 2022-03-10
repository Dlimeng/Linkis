package org.apache.linkis.datasource.client.response

import org.apache.linkis.httpclient.dws.annotation.DWSHttpMessageResult
import org.apache.linkis.httpclient.dws.response.DWSResult

import java.util
import scala.beans.BeanProperty

@DWSHttpMessageResult("/api/rest_j/v\\d+/datasource/all/size")
class GetMetadataSourceAllSizeResult extends DWSResult {
  @BeanProperty var sizeInfo: util.Map[String,Any] = _
}

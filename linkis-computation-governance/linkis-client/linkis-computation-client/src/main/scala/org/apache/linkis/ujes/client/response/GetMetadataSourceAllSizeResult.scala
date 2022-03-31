package org.apache.linkis.ujes.client.response

import org.apache.linkis.httpclient.dws.annotation.DWSHttpMessageResult
import org.apache.linkis.httpclient.dws.response.DWSResult
import org.apache.linkis.ujes.client.request.UserAction

import scala.beans.BeanProperty

@DWSHttpMessageResult("/api/rest_j/v\\d+/datasource/all/size")
class GetMetadataSourceAllSizeResult extends DWSResult with UserAction{
  @BeanProperty var sizeInfo: java.util.Map[String,Any] = _
}

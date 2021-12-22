package org.apache.linkis.datasource.client.request

import org.apache.linkis.datasource.client.config.DatasourceClientConfig.DATA_SOURCE_SERVICE_MODULE
import org.apache.linkis.datasource.client.exception.DataSourceClientBuilderException
import org.apache.linkis.httpclient.dws.DWSHttpClient
import org.apache.linkis.httpclient.request.PutAction


class DataSourceTestConnectAction private() extends PutAction with DataSourceAction {
  private var user: String = _

  private var dataSourceId:String= _

  private var version:String = _

  override def setUser(user: String): Unit = this.user = user

  override def getUser: String = this.user

  override def suffixURLs: Array[String] = Array(DATA_SOURCE_SERVICE_MODULE.getValue, dataSourceId, version, "op", "connect")

  override def getRequestPayload: String = DWSHttpClient.jacksonJson.writeValueAsString(getRequestPayloads)
}
object DataSourceTestConnectAction {
  def builder(): Builder = new Builder

  class Builder private[DataSourceTestConnectAction]() {
    private var user: String = _
    private var dataSourceId:String=_
    private var version:String=_

    def setUser(user:String):Builder={
      this.user = user
      this
    }

    def setDataSourceId(dataSourceId:String): Builder ={
      this.dataSourceId = dataSourceId
      this
    }

    def setVersion(version:String): Builder ={
      this.version = version
      this
    }

    def build(): DataSourceTestConnectAction = {
      if(dataSourceId == null) throw new DataSourceClientBuilderException("dataSourceId is needed!")
      if(version == null) throw new DataSourceClientBuilderException("version is needed!")
      if(user == null) throw new DataSourceClientBuilderException("user is needed!")

      val action = new DataSourceTestConnectAction()
      action.dataSourceId =dataSourceId
      action.version = version
      action.user = user

      action
    }
  }
}



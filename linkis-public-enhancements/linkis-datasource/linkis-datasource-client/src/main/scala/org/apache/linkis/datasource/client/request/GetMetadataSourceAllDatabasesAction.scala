package org.apache.linkis.datasource.client.request

import org.apache.linkis.datasource.client.config.DatasourceClientConfig
import org.apache.linkis.httpclient.request.GetAction

class GetMetadataSourceAllDatabasesAction  extends GetAction with DataSourceAction{
  override def suffixURLs: Array[String] = Array(DatasourceClientConfig.LINKIS_METADATA_SERVICE_MODULE.getValue,  "dbs")

  private var user:String = _

  override def setUser(user: String): Unit = this.user = user

  override def getUser: String = this.user
}
object GetMetadataSourceAllDatabasesAction{
  def builder(): Builder = new Builder

  class Builder private[GetMetadataSourceAllDatabasesAction]() {
    private var user: String = _
    def setUser(user: String): Builder = {
      this.user = user
      this
    }
    def build(): GetMetadataSourceAllDatabasesAction = {
      val action = new GetMetadataSourceAllDatabasesAction
      action.setUser(user)
      action
    }
  }
}

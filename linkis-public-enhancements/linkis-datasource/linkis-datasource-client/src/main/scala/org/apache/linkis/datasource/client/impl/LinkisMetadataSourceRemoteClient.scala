package org.apache.linkis.datasource.client.impl

import org.apache.linkis.datasource.client.request.{GetMetadataSourceAllDatabasesAction, GetMetadataSourceAllSizeAction}
import org.apache.linkis.datasource.client.response.{GetMetadataSourceAllDatabasesResult, GetMetadataSourceAllSizeResult}
import org.apache.linkis.datasource.client.{AbstractRemoteClient, MetadataSourceRemoteClient}
import org.apache.linkis.httpclient.dws.DWSHttpClient
import org.apache.linkis.httpclient.dws.config.DWSClientConfig

class LinkisMetadataSourceRemoteClient(clientConfig: DWSClientConfig) extends AbstractRemoteClient with MetadataSourceRemoteClient{
  override protected val dwsHttpClient: DWSHttpClient =  new DWSHttpClient(clientConfig, "MetadataSource-Client")

  override def getAllDBMetaDataSource(action: GetMetadataSourceAllDatabasesAction): GetMetadataSourceAllDatabasesResult = execute(action).asInstanceOf[GetMetadataSourceAllDatabasesResult]

  override def getAllSizeMetaDataSource(action: GetMetadataSourceAllSizeAction): GetMetadataSourceAllSizeResult = execute(action).asInstanceOf[GetMetadataSourceAllSizeResult]

}

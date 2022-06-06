package org.apache.linkis.storage.domain

import org.apache.linkis.common.utils.{Logging, Utils}
import org.apache.linkis.storage.domain.DataType.{isNull, warn}
import org.apache.linkis.storage.resultset.table.TableMetaData
import org.apache.parquet.schema.Types

import java.sql.{Date, Timestamp}

object ParquetType extends Logging{

    def convertMessageType(tableMetaData:TableMetaData): Unit ={
        tableMetaData.columns
    }
    //int32、int64、int96、float、double、boolean、binary、group
    def toFromCode(columns: Array[Column]): Unit ={
        Types.MessageTypeBuilder
        val builder = Types.buildMessage()
        columns.foreach(f=>{

        })
    }

    def toValue(dataType: DataType, value: String,builder:Types.MessageTypeBuilder): Any = Utils.tryCatch(dataType match {
        case NullType => null
        case StringType | CharType | VarcharType | StructType | ListType | ArrayType | MapType => value
        case BooleanType =>  if(isNull(value)) null else value.toBoolean
        case ShortIntType => if(isNull(value)) null else value.toShort
        case IntType =>if(isNull(value)) null else value.toInt
        case LongType | BigIntType => if(isNull(value)) null else value.toLong
        case FloatType => if(isNull(value)) null else value.toFloat
        case DoubleType  => if(isNull(value)) null else value.toDouble
        case DecimalType => if(isNull(value)) null else BigDecimal(value)
        case DateType => if(isNull(value)) null else Date.valueOf(value)
        case TimestampType => if(isNull(value)) null else Timestamp.valueOf(value).toString.stripSuffix(".0")
        case BinaryType => if(isNull(value)) null else value.getBytes()
        case _ => value
    }){
        t => warn(s"Failed to  $value switch  to dataType:",t)
            value
    }
}

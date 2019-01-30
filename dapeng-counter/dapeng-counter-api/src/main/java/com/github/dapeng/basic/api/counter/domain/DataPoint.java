package com.github.dapeng.basic.api.counter.domain;

        import java.util.Optional;
        import com.github.dapeng.org.apache.thrift.TException;
        import com.github.dapeng.org.apache.thrift.protocol.TCompactProtocol;
        import com.github.dapeng.util.TCommonTransport;

/**
         * Autogenerated by Dapeng-Code-Generator (2.1.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING

        *

 数据点

        **/
        public class DataPoint{
        
            /**
            *

 业务类型, 在时序数据库中也叫metric/measurement,
 相当于关系型数据库的数据表
 流量数据:dapeng_node_flow
 调用统计、耗时、成功率:dapeng_service_process

            **/
            public String bizTag ;
            public String getBizTag(){ return this.bizTag; }
            public void setBizTag(String bizTag){ this.bizTag = bizTag; }

            public String bizTag(){ return this.bizTag; }
            public DataPoint bizTag(String bizTag){ this.bizTag = bizTag; return this; }
          
            /**
            *

 field
 values 可以为一个，可以为多个Field
 value支持的类型floats，integers，strings，booleans
 value会用于展示

            **/
            public java.util.Map<String, Long> values = new java.util.HashMap<>();
            public java.util.Map<String, Long> getValues(){ return this.values; }
            public void setValues(java.util.Map<String, Long> values){ this.values = values; }

            public java.util.Map<String, Long> values(){ return this.values; }
            public DataPoint values(java.util.Map<String, Long> values){ this.values = values; return this; }
          
            /**
            *

 时间戳

            **/
            public long timestamp ;
            public long getTimestamp(){ return this.timestamp; }
            public void setTimestamp(long timestamp){ this.timestamp = timestamp; }

            public long timestamp(){ return this.timestamp; }
            public DataPoint timestamp(long timestamp){ this.timestamp = timestamp; return this; }
          
            /**
            *

 tag是可选的。不过写入数据时最好加上tag，因为它可以被索引。tag的类型只能是字符串。

            **/
            public java.util.Map<String, String> tags = new java.util.HashMap<>();
            public java.util.Map<String, String> getTags(){ return this.tags; }
            public void setTags(java.util.Map<String, String> tags){ this.tags = tags; }

            public java.util.Map<String, String> tags(){ return this.tags; }
            public DataPoint tags(java.util.Map<String, String> tags){ this.tags = tags; return this; }
          
            /**
            *

 influxdb的库:dapengState

            **/
            public String database ;
            public String getDatabase(){ return this.database; }
            public void setDatabase(String database){ this.database = database; }

            public String database(){ return this.database; }
            public DataPoint database(String database){ this.database = database; return this; }
          

        public static byte[] getBytesFromBean(DataPoint bean) throws TException {
          byte[] bytes = new byte[]{};
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Write);
          TCompactProtocol protocol = new TCompactProtocol(transport);

          new com.github.dapeng.basic.api.counter.domain.serializer.DataPointSerializer().write(bean, protocol);
          transport.flush();
          return transport.getByteBuf();
        }

        public static DataPoint getBeanFromBytes(byte[] bytes) throws TException {
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Read);
          TCompactProtocol protocol = new TCompactProtocol(transport);
          return new com.github.dapeng.basic.api.counter.domain.serializer.DataPointSerializer().read(protocol);
        }

        public String toString(){
          StringBuilder stringBuilder = new StringBuilder("{");
            stringBuilder.append("\"").append("bizTag").append("\":\"").append(this.bizTag).append("\",");
    stringBuilder.append("\"").append("values").append("\":").append(this.values).append(",");
    stringBuilder.append("\"").append("timestamp").append("\":").append(this.timestamp).append(",");
    stringBuilder.append("\"").append("tags").append("\":").append(this.tags).append(",");
    stringBuilder.append("\"").append("database").append("\":\"").append(this.database).append("\",");
    
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("}");

          return stringBuilder.toString();
        }
      }
      
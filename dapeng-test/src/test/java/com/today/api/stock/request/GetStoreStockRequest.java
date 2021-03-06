/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.today.api.stock.request;

        import java.util.Optional;
        import com.github.dapeng.org.apache.thrift.TException;
        import com.github.dapeng.org.apache.thrift.protocol.TCompactProtocol;
        import com.github.dapeng.util.TCommonTransport;

        /**
         * Autogenerated by Dapeng-Code-Generator (2.2.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING

        *
        **/
        public class GetStoreStockRequest{
        
            /**
            *

 门店财务 ID

            **/
            public String storeId ;
            public String getStoreId(){ return this.storeId; }
            public void setStoreId(String storeId){ this.storeId = storeId; }

            public String storeId(){ return this.storeId; }
            public GetStoreStockRequest storeId(String storeId){ this.storeId = storeId; return this; }
          
            /**
            *

 货号集合

            **/
            public java.util.List<String> skuNos = new java.util.ArrayList();
            public java.util.List<String> getSkuNos(){ return this.skuNos; }
            public void setSkuNos(java.util.List<String> skuNos){ this.skuNos = skuNos; }

            public java.util.List<String> skuNos(){ return this.skuNos; }
            public GetStoreStockRequest skuNos(java.util.List<String> skuNos){ this.skuNos = skuNos; return this; }
          

        public static byte[] getBytesFromBean(GetStoreStockRequest bean) throws TException {
          byte[] bytes = new byte[]{};
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Write);
          TCompactProtocol protocol = new TCompactProtocol(transport);

          new com.today.api.stock.request.serializer.GetStoreStockRequestSerializer().write(bean, protocol);
          transport.flush();
          return transport.getByteBuf();
        }

        public static GetStoreStockRequest getBeanFromBytes(byte[] bytes) throws TException {
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Read);
          TCompactProtocol protocol = new TCompactProtocol(transport);
          return new com.today.api.stock.request.serializer.GetStoreStockRequestSerializer().read(protocol);
        }

        public String toString(){
          StringBuilder stringBuilder = new StringBuilder("{");
            stringBuilder.append("\"").append("storeId").append("\":\"").append(this.storeId).append("\",");
    stringBuilder.append("\"").append("skuNos").append("\":").append(this.skuNos).append(",");
    
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("}");

          return stringBuilder.toString();
        }
      }
      
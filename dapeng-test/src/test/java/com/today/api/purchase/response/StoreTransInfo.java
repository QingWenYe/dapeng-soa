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
package com.today.api.purchase.response;

        import java.util.Optional;
        import com.github.dapeng.org.apache.thrift.TException;
        import com.github.dapeng.org.apache.thrift.protocol.TCompactProtocol;
        import com.github.dapeng.util.TCommonTransport;

        /**
         * Autogenerated by Dapeng-Code-Generator (2.2.0-SNAPSHOT)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING

        *
        **/
        public class StoreTransInfo{
        
            /**
            *

 转闭店前财务店号

            **/
            public String beforeStoreId ;
            public String getBeforeStoreId(){ return this.beforeStoreId; }
            public void setBeforeStoreId(String beforeStoreId){ this.beforeStoreId = beforeStoreId; }

            public String beforeStoreId(){ return this.beforeStoreId; }
            public StoreTransInfo beforeStoreId(String beforeStoreId){ this.beforeStoreId = beforeStoreId; return this; }
          
            /**
            *

 转闭店类型

            **/
            public com.today.api.stock.enums.InventoryResultInventoryTypeEnum inventoryResultInventoryTypeEnum ;
            public com.today.api.stock.enums.InventoryResultInventoryTypeEnum getInventoryResultInventoryTypeEnum(){ return this.inventoryResultInventoryTypeEnum; }
            public void setInventoryResultInventoryTypeEnum(com.today.api.stock.enums.InventoryResultInventoryTypeEnum inventoryResultInventoryTypeEnum){ this.inventoryResultInventoryTypeEnum = inventoryResultInventoryTypeEnum; }

            public com.today.api.stock.enums.InventoryResultInventoryTypeEnum inventoryResultInventoryTypeEnum(){ return this.inventoryResultInventoryTypeEnum; }
            public StoreTransInfo inventoryResultInventoryTypeEnum(com.today.api.stock.enums.InventoryResultInventoryTypeEnum inventoryResultInventoryTypeEnum){ this.inventoryResultInventoryTypeEnum = inventoryResultInventoryTypeEnum; return this; }
          
            /**
            *

 关闭店账表日

            **/
            public String transDate ;
            public String getTransDate(){ return this.transDate; }
            public void setTransDate(String transDate){ this.transDate = transDate; }

            public String transDate(){ return this.transDate; }
            public StoreTransInfo transDate(String transDate){ this.transDate = transDate; return this; }
          
            /**
            *

 转店后财务店号

            **/
            public Optional<String> afterStoreId = Optional.empty();
            public Optional<String> getAfterStoreId(){ return this.afterStoreId; }
            public void setAfterStoreId(Optional<String> afterStoreId){ this.afterStoreId = afterStoreId; }

            public Optional<String> afterStoreId(){ return this.afterStoreId; }
            public StoreTransInfo afterStoreId(Optional<String> afterStoreId){ this.afterStoreId = afterStoreId; return this; }
          

        public static byte[] getBytesFromBean(StoreTransInfo bean) throws TException {
          byte[] bytes = new byte[]{};
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Write);
          TCompactProtocol protocol = new TCompactProtocol(transport);

          new com.today.api.purchase.response.serializer.StoreTransInfoSerializer().write(bean, protocol);
          transport.flush();
          return transport.getByteBuf();
        }

        public static StoreTransInfo getBeanFromBytes(byte[] bytes) throws TException {
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Read);
          TCompactProtocol protocol = new TCompactProtocol(transport);
          return new com.today.api.purchase.response.serializer.StoreTransInfoSerializer().read(protocol);
        }

        public String toString(){
          StringBuilder stringBuilder = new StringBuilder("{");
            stringBuilder.append("\"").append("beforeStoreId").append("\":\"").append(this.beforeStoreId).append("\",");
    stringBuilder.append("\"").append("inventoryResultInventoryTypeEnum").append("\":").append(this.inventoryResultInventoryTypeEnum).append(",");
    stringBuilder.append("\"").append("transDate").append("\":\"").append(this.transDate).append("\",");
    stringBuilder.append("\"").append("afterStoreId").append("\":\"").append(this.afterStoreId.isPresent()?this.afterStoreId.get():null).append("\",");
    
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("}");

          return stringBuilder.toString();
        }
      }
      
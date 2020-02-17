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
package com.today.api.stock.response;

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
        public class AddStockByAppReturnResponseItem{
        
            /**
            *

 货号

            **/
            public String skuNo ;
            public String getSkuNo(){ return this.skuNo; }
            public void setSkuNo(String skuNo){ this.skuNo = skuNo; }

            public String skuNo(){ return this.skuNo; }
            public AddStockByAppReturnResponseItem skuNo(String skuNo){ this.skuNo = skuNo; return this; }
          
            /**
            *

 增加之后的实时库存数量, 如果门店不存在该 sku 库存则返回 0


            **/
            public int currentStockNum ;
            public int getCurrentStockNum(){ return this.currentStockNum; }
            public void setCurrentStockNum(int currentStockNum){ this.currentStockNum = currentStockNum; }

            public int currentStockNum(){ return this.currentStockNum; }
            public AddStockByAppReturnResponseItem currentStockNum(int currentStockNum){ this.currentStockNum = currentStockNum; return this; }
          
            /**
            *

 是否是安全库存 当前库存大于常量 SAFETY_STOCK_NUM = 3 个定义为安全库存

            **/
            public boolean hasSafetyStock ;
            public boolean getHasSafetyStock(){ return this.hasSafetyStock; }
            public void setHasSafetyStock(boolean hasSafetyStock){ this.hasSafetyStock = hasSafetyStock; }

            public boolean hasSafetyStock(){ return this.hasSafetyStock; }
            public AddStockByAppReturnResponseItem hasSafetyStock(boolean hasSafetyStock){ this.hasSafetyStock = hasSafetyStock; return this; }
          

        public static byte[] getBytesFromBean(AddStockByAppReturnResponseItem bean) throws TException {
          byte[] bytes = new byte[]{};
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Write);
          TCompactProtocol protocol = new TCompactProtocol(transport);

          new com.today.api.stock.response.serializer.AddStockByAppReturnResponseItemSerializer().write(bean, protocol);
          transport.flush();
          return transport.getByteBuf();
        }

        public static AddStockByAppReturnResponseItem getBeanFromBytes(byte[] bytes) throws TException {
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Read);
          TCompactProtocol protocol = new TCompactProtocol(transport);
          return new com.today.api.stock.response.serializer.AddStockByAppReturnResponseItemSerializer().read(protocol);
        }

        public String toString(){
          StringBuilder stringBuilder = new StringBuilder("{");
            stringBuilder.append("\"").append("skuNo").append("\":\"").append(this.skuNo).append("\",");
    stringBuilder.append("\"").append("currentStockNum").append("\":").append(this.currentStockNum).append(",");
    stringBuilder.append("\"").append("hasSafetyStock").append("\":").append(this.hasSafetyStock).append(",");
    
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("}");

          return stringBuilder.toString();
        }
      }
      
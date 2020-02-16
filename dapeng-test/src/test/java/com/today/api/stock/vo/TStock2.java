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
package com.today.api.stock.vo;

        import java.util.Optional;
        import com.github.dapeng.org.apache.thrift.TException;
        import com.github.dapeng.org.apache.thrift.protocol.TCompactProtocol;
        import com.github.dapeng.util.TCommonTransport;

        /**
         * Autogenerated by Dapeng-Code-Generator (2.1.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING

        *

  Stock2

        **/
        public class TStock2{
        
            /**
            *

 ID

            **/
            public long id ;
            public long getId(){ return this.id; }
            public void setId(long id){ this.id = id; }

            public long id(){ return this.id; }
            public TStock2 id(long id){ this.id = id; return this; }
          
            /**
            *

 库存主体编码

            **/
            public String ownerId ;
            public String getOwnerId(){ return this.ownerId; }
            public void setOwnerId(String ownerId){ this.ownerId = ownerId; }

            public String ownerId(){ return this.ownerId; }
            public TStock2 ownerId(String ownerId){ this.ownerId = ownerId; return this; }
          
            /**
            *

 库存主体类型,1:门店(store);2:仓库(warehouse)

            **/
            public com.today.api.stock.enums.StockOwnerTypeEnum ownerType ;
            public com.today.api.stock.enums.StockOwnerTypeEnum getOwnerType(){ return this.ownerType; }
            public void setOwnerType(com.today.api.stock.enums.StockOwnerTypeEnum ownerType){ this.ownerType = ownerType; }

            public com.today.api.stock.enums.StockOwnerTypeEnum ownerType(){ return this.ownerType; }
            public TStock2 ownerType(com.today.api.stock.enums.StockOwnerTypeEnum ownerType){ this.ownerType = ownerType; return this; }
          
            /**
            *

 货号

            **/
            public String skuNo ;
            public String getSkuNo(){ return this.skuNo; }
            public void setSkuNo(String skuNo){ this.skuNo = skuNo; }

            public String skuNo(){ return this.skuNo; }
            public TStock2 skuNo(String skuNo){ this.skuNo = skuNo; return this; }
          
            /**
            *

 库存数
 @datatype(name="bigdecimal")

            **/
            public java.math.BigDecimal stockNum ;
            public java.math.BigDecimal getStockNum(){ return this.stockNum; }
            public void setStockNum(java.math.BigDecimal stockNum){ this.stockNum = stockNum; }

            public java.math.BigDecimal stockNum(){ return this.stockNum; }
            public TStock2 stockNum(java.math.BigDecimal stockNum){ this.stockNum = stockNum; return this; }
          
            /**
            *

 逻辑删除,1:正常(normal);2:删除(deleted)

            **/
            public Optional<com.today.api.stock.enums.StockIsDeletedEnum> isDeleted = Optional.empty();
            public Optional<com.today.api.stock.enums.StockIsDeletedEnum> getIsDeleted(){ return this.isDeleted; }
            public void setIsDeleted(Optional<com.today.api.stock.enums.StockIsDeletedEnum> isDeleted){ this.isDeleted = isDeleted; }

            public Optional<com.today.api.stock.enums.StockIsDeletedEnum> isDeleted(){ return this.isDeleted; }
            public TStock2 isDeleted(Optional<com.today.api.stock.enums.StockIsDeletedEnum> isDeleted){ this.isDeleted = isDeleted; return this; }
          
            /**
            *

 创建时间

            **/
            public Optional<Long> createdAt = Optional.empty();
            public Optional<Long> getCreatedAt(){ return this.createdAt; }
            public void setCreatedAt(Optional<Long> createdAt){ this.createdAt = createdAt; }

            public Optional<Long> createdAt(){ return this.createdAt; }
            public TStock2 createdAt(Optional<Long> createdAt){ this.createdAt = createdAt; return this; }
          
            /**
            *

 特指后台创建人(公司员工 id)

            **/
            public int createdBy ;
            public int getCreatedBy(){ return this.createdBy; }
            public void setCreatedBy(int createdBy){ this.createdBy = createdBy; }

            public int createdBy(){ return this.createdBy; }
            public TStock2 createdBy(int createdBy){ this.createdBy = createdBy; return this; }
          
            /**
            *

 更新时间

            **/
            public long updatedAt ;
            public long getUpdatedAt(){ return this.updatedAt; }
            public void setUpdatedAt(long updatedAt){ this.updatedAt = updatedAt; }

            public long updatedAt(){ return this.updatedAt; }
            public TStock2 updatedAt(long updatedAt){ this.updatedAt = updatedAt; return this; }
          
            /**
            *

 特指后台更新人(公司员工 id)

            **/
            public int updatedBy ;
            public int getUpdatedBy(){ return this.updatedBy; }
            public void setUpdatedBy(int updatedBy){ this.updatedBy = updatedBy; }

            public int updatedBy(){ return this.updatedBy; }
            public TStock2 updatedBy(int updatedBy){ this.updatedBy = updatedBy; return this; }
          
            /**
            *

 备注

            **/
            public String remark ;
            public String getRemark(){ return this.remark; }
            public void setRemark(String remark){ this.remark = remark; }

            public String remark(){ return this.remark; }
            public TStock2 remark(String remark){ this.remark = remark; return this; }
          

        public static byte[] getBytesFromBean(TStock2 bean) throws TException {
          byte[] bytes = new byte[]{};
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Write);
          TCompactProtocol protocol = new TCompactProtocol(transport);

          new com.today.api.stock.vo.serializer.TStock2Serializer().write(bean, protocol);
          transport.flush();
          return transport.getByteBuf();
        }

        public static TStock2 getBeanFromBytes(byte[] bytes) throws TException {
          TCommonTransport transport = new TCommonTransport(bytes, TCommonTransport.Type.Read);
          TCompactProtocol protocol = new TCompactProtocol(transport);
          return new com.today.api.stock.vo.serializer.TStock2Serializer().read(protocol);
        }

        public String toString(){
          StringBuilder stringBuilder = new StringBuilder("{");
            stringBuilder.append("\"").append("id").append("\":").append(this.id).append(",");
    stringBuilder.append("\"").append("ownerId").append("\":\"").append(this.ownerId).append("\",");
    stringBuilder.append("\"").append("ownerType").append("\":").append(this.ownerType).append(",");
    stringBuilder.append("\"").append("skuNo").append("\":\"").append(this.skuNo).append("\",");
    stringBuilder.append("\"").append("stockNum").append("\":").append(this.stockNum).append(",");
    stringBuilder.append("\"").append("isDeleted").append("\":").append(this.isDeleted.isPresent()?this.isDeleted.get():null).append(",");
    stringBuilder.append("\"").append("createdAt").append("\":").append(this.createdAt.isPresent()?this.createdAt.get():null).append(",");
    stringBuilder.append("\"").append("createdBy").append("\":").append(this.createdBy).append(",");
    stringBuilder.append("\"").append("updatedAt").append("\":").append(this.updatedAt).append(",");
    stringBuilder.append("\"").append("updatedBy").append("\":").append(this.updatedBy).append(",");
    stringBuilder.append("\"").append("remark").append("\":\"").append(this.remark).append("\",");
    
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("}");

          return stringBuilder.toString();
        }
      }
      
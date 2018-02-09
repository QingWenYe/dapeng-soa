
      package com.isuwang.soa.order.scala.service

      import com.github.dapeng.core.{Processor, Service}
      import com.github.dapeng.core.SoaGlobalTransactional
      import scala.concurrent.Future

      /**
       * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

      * 
      **/
      @Service(name ="com.isuwang.soa.order.service.OrderService" , version = "1.0.0")
      @Processor(className = "com.isuwang.soa.order.scala.OrderServiceAsyncCodec$Processor")
      trait OrderServiceAsync extends com.github.dapeng.core.definition.AsyncService {
      
          /**
          * 
          **/
          
          @throws[com.github.dapeng.core.SoaException]
          def createOrder(
          order: com.isuwang.soa.order.scala.domain.Order ): Future[Unit]

        
          /**
          * 
          **/
          
          @throws[com.github.dapeng.core.SoaException]
          def getOrderById(
          orderId: Int ): Future[com.isuwang.soa.order.scala.domain.Order]

        
    }
    
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
package com.github.dapeng.message.serializer;

import com.github.dapeng.core.BeanSerializer;
import com.github.dapeng.message.config.MessageInfo;
import com.github.dapeng.org.apache.thrift.TException;
import com.github.dapeng.org.apache.thrift.protocol.TCompactProtocol;
import com.github.dapeng.util.TKafkaTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 描述: kafka 消息 编解码器
 *
 * @author maple.lei
 * @date 2018年02月13日 上午11:39
 */
public class KafkaMessageProcessor<T> {

    private Logger LOGGER = LoggerFactory.getLogger(KafkaMessageProcessor.class);

    private BeanSerializer<T> beanSerializer;
    private byte[] realMessage;

    public T dealMessage(byte[] message, ClassLoader classLoader) throws TException {

        String eventType = getEventType(message);
        LOGGER.info("fetch eventType: {}", eventType);
        beanSerializer = assemblyBeanSerializer(eventType, classLoader);
        MessageInfo<T> messageInfo = parseMessage(message);

        T event = messageInfo.getEvent();
        LOGGER.info("dealMessage:event {}", event.toString());
        return event;
    }

    /**
     * decode message
     *
     * @param msgBuffer
     * @param beanSerializer
     * @return
     * @throws TException
     */
    public T decodeMessage(ByteBuffer msgBuffer, BeanSerializer beanSerializer) throws TException {
        LOGGER.info("fetch event body: ");
        byte[] bytes = new byte[msgBuffer.remaining()];
        TKafkaTransport kafkaTransport = new TKafkaTransport(bytes, TKafkaTransport.Type.Read);
        TCompactProtocol protocol = new TCompactProtocol(kafkaTransport);

        T event = (T) beanSerializer.read(protocol);

        LOGGER.info("dealMessage:event {}", event.toString());
        return event;
    }


    /**
     * decode kafka message
     *
     * @return
     */
    private MessageInfo<T> parseMessage(byte[] bytes) throws TException {
        TKafkaTransport kafkaTransport = new TKafkaTransport(bytes, TKafkaTransport.Type.Read);
        TCompactProtocol protocol = new TCompactProtocol(kafkaTransport);
        String eventType = kafkaTransport.getEventType();
        T event = beanSerializer.read(protocol);
        return new MessageInfo<>(eventType, event);
    }

    /**
     * encoding kafka message
     *
     * @param event
     * @return
     * @throws TException
     */
    public byte[] buildMessageByte(T event) throws TException {
        String eventType = event.getClass().getName();
        beanSerializer = assemblyBeanSerializer(eventType);

        byte[] bytes = new byte[8192];
        TKafkaTransport kafkaTransport = new TKafkaTransport(bytes, TKafkaTransport.Type.Write);
        TCompactProtocol protocol = new TCompactProtocol(kafkaTransport);
        kafkaTransport.setEventType(eventType);
        beanSerializer.write(event, protocol);
        kafkaTransport.flush();
        bytes = kafkaTransport.getByteBuf();
        return bytes;
    }


    /**
     * 获取事件权限定名
     *
     * @param message
     * @return
     */
    public String getEventType(byte[] message) {
        int pos = 0;
        while (pos < message.length) {
            if (message[pos++] == (byte) 0) {
                break;
            }
        }
        byte[] subBytes = new byte[pos - 1];
        System.arraycopy(message, 0, subBytes, 0, pos - 1);

        realMessage = new byte[message.length - pos];
        System.arraycopy(message, pos, realMessage, 0, message.length - pos);
        return new String(subBytes);
    }

    public byte[] getEventBinary() {
        return realMessage;
    }


    /**
     * 根据event类名 构造event 编解码器对象
     *
     * @param eventType
     * @return
     */
    private BeanSerializer assemblyBeanSerializer(String eventType, ClassLoader classLoader) {
        String eventSerializerName = null;
        try {
            String eventPackage = eventType.substring(0, eventType.lastIndexOf("."));
            String eventName = eventType.substring(eventType.lastIndexOf(".") + 1);
            eventSerializerName = eventPackage + ".serializer." + eventName + "Serializer";

            Class<?> serializerClazz = classLoader.loadClass(eventSerializerName);
            BeanSerializer beanSerializer = (BeanSerializer) serializerClazz.newInstance();

            return beanSerializer;
        } catch (StringIndexOutOfBoundsException e) {
            LOGGER.error("组装权限定名出错!!");
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOGGER.error("找不到对应的消息解码器 {}", eventSerializerName);
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private BeanSerializer assemblyBeanSerializer(String eventType) {
        String eventSerializerName = null;
        try {

            String eventPackage = eventType.substring(0, eventType.lastIndexOf("."));
            String eventName = eventType.substring(eventType.lastIndexOf(".") + 1);
            eventSerializerName = eventPackage + ".serializer." + eventName + "Serializer";

            Class<?> serializerClazz = this.getClass().getClassLoader().loadClass(eventSerializerName);
            BeanSerializer beanSerializer = (BeanSerializer) serializerClazz.newInstance();

            return beanSerializer;
        } catch (StringIndexOutOfBoundsException e) {
            LOGGER.error("组装权限定名出错!!");
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOGGER.error("找不到对应的消息解码器 {}", eventSerializerName);
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}

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
package com.github.dapeng.core;

import java.util.concurrent.Future;

/**
 * @author craneding
 * @date 16/3/1
 */
public interface SoaConnectionPool {

    class ClientInfo {
        public final String serviceName;
        public final String version;

        public ClientInfo(String serviceName, String version) {
            this.serviceName = serviceName;
            this.version = version;
        }
    }

    ClientInfo registerClientInfo(String servcice, String version);

    <REQ, RESP> RESP send(
            String service,
            String version,
            String method,
            REQ request,
            BeanSerializer<REQ> requestSerializer,
            BeanSerializer<RESP> responseSerializer) throws SoaException;

    <REQ, RESP> Future<RESP> sendAsync(
            String service,
            String version,
            String method,
            REQ request,
            BeanSerializer<REQ> requestSerializer,
            BeanSerializer<RESP> responseSerializer) throws SoaException;

    /**
     * get runtime instance
     * @param serviceName
     * @param serviceIp
     * @param servicePort
     * @return
     */
    @Deprecated
    RuntimeInstance getRuntimeInstance(String serviceName, String serviceIp, int servicePort);
}

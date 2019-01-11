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
package com.github.dapeng.impl.filters.freq;

/**
 * 描述: DictionaryItem 字符串到id的映射区
 *
 * @author hz.lei
 * @date 2018年05月14日 上午10:52
 */
public class DictionaryItem {

    public final short length;
    public final int id;
    /**
     * DictionaryData[ 2 * utf8offset ] 处开始存储这个字符串。
     */
    public final short utf8offset;

    public DictionaryItem(short length, int id, short utf8offset) {
        this.length = length;
        this.id = id;
        this.utf8offset = utf8offset;
    }

    @Override
    public String toString() {
        return "DictionaryItem{" + "length=" + length + ", id=" + id +
                ", utf8offset=" + utf8offset + '}';
    }
}
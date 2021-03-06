/*
 * ******************************************************************************
 *  *
 *  * Pentaho Big Data
 *  *
 *  * Copyright (C) 2002-2018 by Hitachi Vantara : http://www.pentaho.com
 *  *
 *  *******************************************************************************
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *  *****************************************************************************
 */

package com.epam.facade.model;

import com.epam.health.tool.model.ClusterTypeEnum;
import com.epam.util.common.CheckingParamsUtil;

import java.util.function.Consumer;

public class ClusterSearchParam {
    private String node;
    private ClusterTypeEnum clusterType;
    private boolean secured;

    private ClusterSearchParam() {}

    public String getNode() {
        return node;
    }

    public ClusterTypeEnum getClusterType() {
        return clusterType;
    }

    public boolean isSecured() {
        return secured;
    }

    public static class ClusterSearchParamBuilder {
        private ClusterSearchParam clusterSearchParam;

        private ClusterSearchParamBuilder() {
            this.clusterSearchParam = new ClusterSearchParam();
        }

        public static ClusterSearchParamBuilder get() {
            return new ClusterSearchParamBuilder();
        }

        public ClusterSearchParamBuilder withNode( String node ) {
            setParamWithCheck( node, ( param ) -> this.clusterSearchParam.node = param );

            return this;
        }

        public ClusterSearchParamBuilder withClusterType( String clusterType ) {
            setParamWithCheck( clusterType, ( param ) -> this.clusterSearchParam.clusterType = ClusterTypeEnum.valueOf( param.replaceAll("\\d", "").toUpperCase() ) );

            return this;
        }

        public ClusterSearchParamBuilder withSecure( boolean secured ) {
            this.clusterSearchParam.secured = secured;

            return this;
        }

        public ClusterSearchParam build() {
            return this.clusterSearchParam;
        }

        private void setParamWithCheck(String param, Consumer<String> setParamConsumer) {
            if ( !CheckingParamsUtil.isParamsNullOrEmpty( param ) ) {
                setParamConsumer.accept( param );
            }
        }
    }
}

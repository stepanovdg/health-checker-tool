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

package com.epam.health.tool.facade.common.recap;

import com.epam.facade.model.accumulator.HealthCheckResultsAccumulator;
import com.epam.facade.model.validation.ClusterHealthValidationResult;
import com.epam.health.tool.facade.recap.IClusterHealthRecapFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClusterHealthRecapFacadeImpl implements IClusterHealthRecapFacade {
    @Autowired
    private List<IServiceHealthCheckRecapAction> serviceHealthCheckRecapActions;

    @Override
    public ClusterHealthValidationResult validateClusterHealth(HealthCheckResultsAccumulator healthCheckResultsAccumulator) {
        ClusterHealthValidationResult clusterHealthValidationResult = new ClusterHealthValidationResult( true );
        serviceHealthCheckRecapActions.forEach( serviceHealthCheckRecapAction -> serviceHealthCheckRecapAction
                .doRecapHealthCheck( healthCheckResultsAccumulator, clusterHealthValidationResult ) );

        return clusterHealthValidationResult;
    }
}

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

package com.epam.health.tool.facade.hdp.service.action.other;

import com.epam.health.tool.facade.resolver.ClusterSpecificComponent;
import com.epam.health.tool.facade.resolver.action.HealthCheckAction;
import com.epam.facade.model.HealthCheckActionType;
import com.epam.health.tool.facade.common.service.action.other.CommonOtherServicesHealthCheckAction;
import com.epam.health.tool.model.ClusterTypeEnum;
import org.springframework.stereotype.Component;

@Component
@HealthCheckAction( HealthCheckActionType.OTHER_SERVICES )
@ClusterSpecificComponent( ClusterTypeEnum.HDP )
public class HdpRestHealthCheckAction extends CommonOtherServicesHealthCheckAction {

}

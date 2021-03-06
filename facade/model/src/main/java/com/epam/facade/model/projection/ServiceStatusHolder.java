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

package com.epam.facade.model.projection;

import com.epam.health.tool.model.ServiceStatusEnum;
import com.epam.health.tool.model.ServiceTypeEnum;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by Vasilina_Terehova on 3/27/2018.
 */
public interface ServiceStatusHolder {
    @Value("#{target.clusterServiceEntity.serviceType}")
    ServiceTypeEnum getType();

    @Value("#{target.healthStatus}")
    ServiceStatusEnum getHealthSummary();

    @Value("#{target.clusterServiceEntity.serviceType}")
    ServiceTypeEnum getDisplayName();

    @Value("#{target.clusterServiceEntity.logPath}")
    String getLogDirectory();

    @Value("#{target.clusterServiceEntity.clusterNode}")
    String getClusterNode();

    @Value("#{target.jobResults}")
    List<JobResultProjection> getJobResults();

    void setType(ServiceTypeEnum serviceTypeEnum);

    void setHealthSummary(ServiceStatusEnum healthSummary);

    void setJobResults(List<JobResultProjection> jobResults);

    void setLogDirectory(String logDirectory);

    void setClusterNode(String clusterNode);
}

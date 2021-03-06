/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.guvnor.ala.wildfly.model;

import org.guvnor.ala.config.RuntimeConfig;
import org.guvnor.ala.runtime.RuntimeEndpoint;
import org.guvnor.ala.runtime.RuntimeInfo;
import org.guvnor.ala.runtime.RuntimeState;
import org.guvnor.ala.runtime.base.BaseRuntime;
import org.guvnor.ala.runtime.providers.ProviderId;

public class WildflyRuntime extends BaseRuntime {

    /*
     * No-args constructor for enabling marshalling to work, please do not remove. 
     */
    public WildflyRuntime() {
    }

    public WildflyRuntime( String id, RuntimeConfig config, ProviderId providerId,
            RuntimeEndpoint endpoint, RuntimeInfo info, RuntimeState state ) {
        super( id, config, providerId, endpoint, info, state );
    }

    @Override
    public String toString() {
        return "WildflyRuntime{" + getId() + " }";
    }
}

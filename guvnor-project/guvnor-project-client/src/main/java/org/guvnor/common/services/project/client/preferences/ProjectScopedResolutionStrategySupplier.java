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

package org.guvnor.common.services.project.client.preferences;

import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.guvnor.common.services.project.context.ProjectContextChangeEvent;
import org.guvnor.common.services.project.model.Project;
import org.guvnor.common.services.shared.preferences.WorkbenchPreferenceScopeResolutionStrategies;
import org.uberfire.preferences.shared.impl.PreferenceScopeResolutionStrategyInfo;

@ApplicationScoped
public class ProjectScopedResolutionStrategySupplier implements Supplier<PreferenceScopeResolutionStrategyInfo> {

    private WorkbenchPreferenceScopeResolutionStrategies scopeResolutionStrategies;

    private Project project;

    public ProjectScopedResolutionStrategySupplier() {
    }

    @Inject
    public ProjectScopedResolutionStrategySupplier( final WorkbenchPreferenceScopeResolutionStrategies scopeResolutionStrategies ) {
        this.scopeResolutionStrategies = scopeResolutionStrategies;
    }

    public void selectedProjectChanged( @Observes final ProjectContextChangeEvent event ) {
        this.project = event.getProject();
    }

    @Override
    public PreferenceScopeResolutionStrategyInfo get() {
        if ( project == null ) {
            return scopeResolutionStrategies.getUserInfoFor( null, null );
        }

        final String projectIdentifier = project.getEncodedIdentifier();
        return scopeResolutionStrategies.getUserInfoFor( "project", projectIdentifier );
    }
}

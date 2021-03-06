/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
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
package org.guvnor.common.services.project.events;

import org.guvnor.common.services.project.model.Project;
import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * An event representing creation of a new Project
 */
@Portable
public class RenameProjectEvent {

    private Project oldProject;
    private Project newProject;

    public RenameProjectEvent() {
    }

    public RenameProjectEvent( final Project oldProject,
                               final Project newProject ) {
        this.oldProject = oldProject;
        this.newProject = newProject;
    }

    public Project getOldProject() {
        return oldProject;
    }

    public Project getNewProject() {
        return newProject;
    }
}

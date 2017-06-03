/*
 * Copyright 2017 Patrik Karlsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nbgames.yaya.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.openide.util.Exceptions;
import se.trixon.almond.util.SystemHelper;

/**
 *
 * @author Patrik Karlsson
 */
public abstract class GameProvider {

    private final String mId;

    public GameProvider(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getDefinition() {
        InputStream inputStream = getClass().getResourceAsStream("/" + SystemHelper.getPackageAsPath(getClass()) + mId);

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, "utf-8");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return writer.toString();
    }
}

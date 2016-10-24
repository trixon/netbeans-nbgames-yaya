/* 
 * Copyright 2016 Patrik Karlsson.
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
package org.nbgames.yaya;

import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import org.nbgames.yaya.gamedef.GameDef;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        try {
            if (!Arrays.asList(Options.getPreferences().keys()).contains(Options.KEY_SHOW_INDICATORS)) {
                Options.INSTANCE.setShowIndicators(true);
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        GameDef.INSTANCE.init();
    }
}

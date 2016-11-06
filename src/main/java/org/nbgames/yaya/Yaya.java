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

import javax.swing.JPanel;
import org.nbgames.core.GameCategory;
import org.nbgames.core.GameController;
import org.nbgames.core.api.DiceGameProvider;
import org.nbgames.core.base.NewGamePanel;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@ServiceProviders(value = {
    @ServiceProvider(service = GameController.class)
    ,
    @ServiceProvider(service = DiceGameProvider.class)}
)
public class Yaya extends GameController implements DiceGameProvider {

    public static final String LOG_TITLE = "Yaya";

    private YayaPanel mGamePanel;

    public Yaya() {
    }

    @Override
    public GameCategory getCategory() {
        return GameCategory.DICE;
    }

    @Override
    public NewGamePanel getNewGamePanel() {
        return new YayaNewGamePanel();
    }

    @Override
    public JPanel getPanel() {
        if (mGamePanel == null) {
            mGamePanel = new YayaPanel();
            onRequestNewGameStart();
        }

        return mGamePanel;
    }

    @Override
    public String getHelp() {
        return "yaya help";
    }

    @Override
    public JPanel getOptionsPanel() {
        return new JPanel();
    }

    @Override
    public void onRequestNewGameStart() {
        System.out.println("onRequestNewGameStart " + getName());
        mGamePanel.newGame();
        String name = NbBundle.getMessage(getClass(), "CTL_NameType", mGamePanel.getGameTitle());
    }
}

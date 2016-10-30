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

import javax.swing.JButton;
import org.nbgames.core.DictNbg;
import org.nbgames.core.GameCategory;
import org.nbgames.core.GameController;
import org.nbgames.core.api.DiceGameProvider;
import org.nbgames.core.base.GamePanel;
import org.nbgames.core.game.NewGameController;
import org.nbgames.core.game.NewGameDialogManager;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.StatusDisplayer;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.openide.windows.WindowManager;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@ServiceProviders(value = {
    @ServiceProvider(service = GameController.class)
    ,
    @ServiceProvider(service = DiceGameProvider.class)}
)
public class YayaController extends GameController implements DiceGameProvider, NewGameController {

    public static final String LOG_TITLE = "Yaya";

    private YayaPanel mGamePanel;

    public YayaController() {
    }

    @Override
    public GamePanel getGamePanel() {
        if (mGamePanel == null) {
            mGamePanel = new YayaPanel();
            onRequestNewGameStart();
        }

        return mGamePanel;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

    @Override
    public GameCategory getCategory() {
        return GameCategory.DICE;
    }

    @Override
    public void onRequestNewGameCancel() {
    }

    @Override
    public void onRequestNewGameStart() {
        mGamePanel.newGame();
        updateStatusBar();
        String name = NbBundle.getMessage(getClass(), "CTL_NameType", mGamePanel.getGameTitle());
//        mGameTopComponent.setName(name);
    }

    @Override
    public void requestNewGame() {
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            NewGameDialogManager manager = new NewGameDialogManager(new YayaNewGamePanel(), YayaController.this);

            DialogDescriptor d = manager.getDialogDescriptor();
            d.setAdditionalOptions(new JButton[]{new JButton(DictNbg.SHUFFLE.toString())});

            DialogDisplayer.getDefault().notify(d);
        });
    }

    @Override
    public void updateStatusBar() {
        StatusDisplayer.getDefault().setStatusText(mGamePanel.getGameTitle(), StatusDisplayer.IMPORTANCE_ERROR_HIGHLIGHT);
    }
}

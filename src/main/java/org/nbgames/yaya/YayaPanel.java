/* 
 * Copyright 2018 Patrik Karlström.
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

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import org.nbgames.core.api.NbGames;
import org.nbgames.core.api.ui.GamePanel;
import org.nbgames.core.dice.DiceBoard;
import org.nbgames.core.dice.DiceBoard.RollEvent;
import org.nbgames.yaya.gamedef.GameTypeLoader;
import org.nbgames.yaya.gamedef.GameType;
import org.nbgames.yaya.scorecard.ScoreCard;
import org.nbgames.yaya.scorecard.ScoreCardObservable.ScoreCardEvent;

/**
 *
 * @author Patrik Karlström
 */
public class YayaPanel extends GamePanel implements Observer {

    private DiceBoard mDiceBoard;
    private boolean mRollable = true;
    private int mNumOfPlayers;
    private ScoreCard mScoreCard;
    private final Options mOptions = Options.getInstance();
    private GameType mGameType;

    /**
     * Creates new form YayaPanel
     */
    public YayaPanel() {
        initComponents();
        //GameDef.getInstance().init();
        initInitialLayout();
        setBackgroundImage(NbGames.getImage("images/wood_panel1.jpg"));
    }

    public String getGameTitle() {
        return mGameType.getTitle();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof RollEvent) {
            switch ((RollEvent) arg) {
                case PRE_ROLL:
                    mScoreCard.setEnabledUndo(false);
                    mRollable = mScoreCard.isRollable();
                    if (mRollable) {
                        mScoreCard.newRoll();
                        mDiceBoard.roll();
                    }

                    break;

                case POST_ROLL:
                    mScoreCard.parseDice(mDiceBoard.getValues());
                    break;
            }
        }

        if (arg instanceof ScoreCardEvent) {
            switch ((ScoreCardEvent) arg) {
                case GAME_OVER:
                    mDiceBoard.gameOver();
                    break;

                case REGISTER:
                    mDiceBoard.newTurn();
                    break;

                case UNDO:
                    mDiceBoard.undo();
                    break;
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 255, 153));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void newGame() {
        NbGames.outln(Yaya.LOG_TITLE, "newGame");
//        if (numOfPlayers != settings.getNumOfPlayers()) {
//            numOfPlayers = settings.getNumOfPlayers();
//            initRule(settings.getRule());
//        }
        initGame();
        mScoreCard.newGame();
        mDiceBoard.newTurn();
    }

    private void initDiceBoard() {
        mDiceBoard.addObserver(this);
        mDiceBoard.setDiceTofloor(1000);
        mDiceBoard.setMaxRollCount(mGameType.getNumOfRolls());
        add(mDiceBoard.getPanel(), BorderLayout.SOUTH);
    }

    private void initGame() {
        removeAll();
        mGameType = GameTypeLoader.getInstance().getType(mOptions.getGameTypeId());
        mNumOfPlayers = mOptions.getNumOfPlayers();

        mDiceBoard = new DiceBoard(mGameType.getNumOfDice());
        mScoreCard = new ScoreCard();
        initScoreCard();
        initDiceBoard();
    }

    private void initInitialLayout() {
        add(new DiceBoard(0).getPanel(), BorderLayout.SOUTH);
    }

    private void initScoreCard() {
        mScoreCard.getObservable().addObserver(this);
        add(mScoreCard.getCard(), BorderLayout.CENTER);
    }
}

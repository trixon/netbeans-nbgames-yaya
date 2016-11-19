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

import java.awt.Color;
import java.util.prefs.Preferences;
import org.nbgames.yaya.gamedef.GameType;
import org.openide.util.NbPreferences;
import se.trixon.almond.util.GraphicsHelper;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Options {

    public static final String KEY_GAME_TYPE_ID = "gameType";
    public static final String KEY_NUM_OF_PLAYERS = "numOfPlayers";
    public static final String KEY_SHOW_HI_SCORE_COLUMN = "showHiScoreColumn";
    public static final String KEY_SHOW_INDICATORS = "showIndicators";
    public static final String KEY_SHOW_MAX_COLUMN = "showMaxColumn";
    public static final String KEY_USE_SYMBOLS = "useSymbols";
    private static final String DEFAULT_COLOR_BACKGROUND = "#333333";
    private static final String DEFAULT_COLOR_HEADER = "#FFC800";
    private static final String DEFAULT_COLOR_INDICATOR_HI = "#BBEEBB";
    private static final String DEFAULT_COLOR_INDICATOR_LO = "#EEBBBB";
    private static final String DEFAULT_COLOR_ROW = "#FFFFFF";
    private static final String DEFAULT_COLOR_SCORECARD = "#666666";
    private static final String DEFAULT_COLOR_SUM = "#FFFF00";
    private static final String DEFAULT_GAME_TYPE_ID = "default";
    private static final String DEFAULT_GAME_VARIANT = "standard";
    private static final int DEFAULT_NUM_OF_PLAYERS = 2;
    private static final boolean DEFAULT_SHOW_HI_SCORE_COLUMN = false;
    private static final boolean DEFAULT_SHOW_INDICATORS = true;
    private static final boolean DEFAULT_SHOW_MAX_COLUMN = false;
    private static final boolean DEFAULT_USE_SYMBOLS = false;
    private static final Preferences mPreferences = NbPreferences.forModule(Options.class);
    private final Preferences mPreferencesColors = NbPreferences.forModule(getClass()).node("colors");

    public static Options getInstance() {
        return Holder.INSTANCE;
    }

    public static Preferences getPreferences() {
        return mPreferences;
    }

    private Options() {
        init();
    }

    public Color getColor(ColorItem colorItem) {
        return Color.decode(mPreferencesColors.get(colorItem.getKey(), colorItem.getDefaultColorAsString()));
    }

    public String getGameTypeId() {
        return mPreferences.get(KEY_GAME_TYPE_ID, DEFAULT_GAME_TYPE_ID);
    }

    public String getGameVariant(String type) {
        return mPreferences.get(GameType.VARIANT + type, DEFAULT_GAME_VARIANT);
    }

    public int getNumOfPlayers() {
        return mPreferences.getInt(KEY_NUM_OF_PLAYERS, DEFAULT_NUM_OF_PLAYERS);
    }

    public Preferences getPreferencesColors() {
        return mPreferencesColors;
    }

    public boolean isShowingHiScoreColumn() {
        return mPreferences.getBoolean(KEY_SHOW_HI_SCORE_COLUMN, DEFAULT_SHOW_HI_SCORE_COLUMN);
    }

    public boolean isShowingIndicators() {
        return mPreferences.getBoolean(KEY_SHOW_INDICATORS, DEFAULT_SHOW_INDICATORS);
    }

    public boolean isShowingMaxColumn() {
        return mPreferences.getBoolean(KEY_SHOW_MAX_COLUMN, DEFAULT_SHOW_MAX_COLUMN);
    }

    public boolean isUsingSymbols() {
        return mPreferences.getBoolean(KEY_USE_SYMBOLS, DEFAULT_USE_SYMBOLS);
    }

    public void setColor(ColorItem colorItem, Color color) {
        mPreferencesColors.put(colorItem.getKey(), GraphicsHelper.colorToString(color));
    }

    public void setGameTypeId(String typeId) {
        mPreferences.put(KEY_GAME_TYPE_ID, typeId);
    }

    public void setGameVariant(String type, String variant) {
        mPreferences.put(GameType.VARIANT + type, variant);
    }

    public void setNumOfPlayers(int players) {
        mPreferences.putInt(KEY_NUM_OF_PLAYERS, players);
    }

    public void setShowHiScoreColumn(boolean state) {
        mPreferences.putBoolean(KEY_SHOW_HI_SCORE_COLUMN, state);
    }

    public void setShowIndicators(boolean state) {
        mPreferences.putBoolean(KEY_SHOW_INDICATORS, state);
    }

    public void setShowMaxColumn(boolean state) {
        mPreferences.putBoolean(KEY_SHOW_MAX_COLUMN, state);
    }

    public void setUseSymbols(boolean state) {
        mPreferences.putBoolean(KEY_USE_SYMBOLS, state);
    }

    private void init() {
    }

    public enum ColorItem {

        BACKGROUND(DEFAULT_COLOR_BACKGROUND),
        HEADER(DEFAULT_COLOR_HEADER),
        INDICATOR_HI(DEFAULT_COLOR_INDICATOR_HI),
        INDICATOR_LO(DEFAULT_COLOR_INDICATOR_LO),
        ROW(DEFAULT_COLOR_ROW),
        SCORECARD(DEFAULT_COLOR_SCORECARD),
        SUM(DEFAULT_COLOR_SUM);

        private final String mDefaultColor;

        ColorItem(String defaultColor) {
            mDefaultColor = defaultColor;
        }

        public Color getDefaultColor() {
            return Color.decode(mDefaultColor);
        }

        public String getDefaultColorAsString() {
            return mDefaultColor;
        }

        public String getKey() {
            return name().toLowerCase();
        }
    }

    private static class Holder {

        private static final Options INSTANCE = new Options();
    }
}

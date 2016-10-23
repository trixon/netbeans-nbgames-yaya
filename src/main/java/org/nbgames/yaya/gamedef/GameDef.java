/*
 * Copyright 2015 Patrik Karlsson.
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
package org.nbgames.yaya.gamedef;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.nbgames.core.NbGames;
import org.nbgames.core.json.JsonHelper;
import org.nbgames.yaya.YayaController;
import org.nbgames.yaya.api.GameLoader;
import org.openide.util.Lookup;
import se.trixon.almond.util.FileHelper;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public enum GameDef {

    INSTANCE;
    private LinkedList<GameType> mGameTypes;

    public String dump() {
        StringBuilder stringBuilder = new StringBuilder();

        for (GameType gameType : mGameTypes) {
            stringBuilder.append(gameType.dump());
        }

        return stringBuilder.toString();
    }

    public String[] getIdArray() {
        String[] result = new String[mGameTypes.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = mGameTypes.get(i).getId();
        }

        return result;
    }

    public String getIdForIndex(int index) {
        return mGameTypes.get(index).getId();
    }

    public int getIndexForId(String id) {
        int index = -1;

        for (int i = 0; i < getIdArray().length; i++) {
            if (id.equalsIgnoreCase(getIdArray()[i])) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            index = getIndexForId("default");
        }

        return index;
    }

    public String getTitle(String id) {
        for (GameType gameType : mGameTypes) {
            if (gameType.getId().equalsIgnoreCase(id)) {
                return gameType.getTitle();
            }
        }
        // TODO Throw something?
        return "";
    }

    public String[] getTitleArray() {
        String[] result = new String[mGameTypes.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = mGameTypes.get(i).getTitle();
        }

        return result;
    }

    public GameType getType(String id) {
        for (GameType gameType : mGameTypes) {
            if (gameType.getId().equalsIgnoreCase(id)) {
                return gameType;
            }
        }
        // TODO Throw something?
        return getType("default");
    }

    public void init() {
        mGameTypes = new LinkedList<GameType>();
        Collection<? extends GameLoader> allGameLoaders = Lookup.getDefault().lookupAll(GameLoader.class);

        for (GameLoader gameLoader : allGameLoaders) {
            NbGames.outln(YayaController.LOG_TITLE, String.format("Found GameLoader in %s.", gameLoader.getId()));
            String jsonString = FileHelper.convertStreamToString(gameLoader.getInputStream());
            parse(jsonString);
        }

        Collections.sort(mGameTypes, GameType.NameComparator);
    }

    private void parse(String jsonString) {
        JSONObject gameObject = (JSONObject) JSONValue.parse(jsonString);
        GameType gameType = new GameType();
        gameType.setAuthor((String) gameObject.get("author"));
        gameType.setId((String) gameObject.get("id"));
        gameType.setNumOfDice(JsonHelper.getInt(gameObject, "numOfDice"));
        gameType.setNumOfRolls(JsonHelper.getInt(gameObject, "numOfRolls"));
        gameType.setResultRow(JsonHelper.getInt(gameObject, "resultRow"));
        gameType.setTitle(JsonHelper.parseLocalizedKey(gameObject, "title"));
        gameType.setDefaultVariant(JsonHelper.getInt(gameObject, "defaultVariant"));
        gameType.setVariants((String) gameObject.get("variants"));
        gameType.setVersionDate((String) gameObject.get("versionDate"));
        gameType.setVersionName((String) gameObject.get("versionName"));

        GameRows gameRows = new GameRows();
        JSONArray rowsArray = (JSONArray) gameObject.get("rows");

        for (int j = 0; j < rowsArray.size(); j++) {
            JSONObject rowObject = (JSONObject) rowsArray.get(j);
            GameRow gameRow = new GameRow();
            gameRow.setId((String) rowObject.get("id"));
            gameRow.setTitle(JsonHelper.parseLocalizedKey(rowObject, "title"));
            gameRow.setTitleSymbol(JsonHelper.optString(rowObject, "titleSym"));
            gameRow.setFormula(JsonHelper.optString(rowObject, "formula"));
            gameRow.setLim(JsonHelper.optInt(rowObject, "lim"));
            gameRow.setMax(JsonHelper.optInt(rowObject, "max"));
            gameRow.setSum(JsonHelper.optBoolean(rowObject, "isSum"));
            gameRow.setBonus(JsonHelper.optBoolean(rowObject, "isBonus"));
            gameRow.setRollCounter(JsonHelper.optBoolean(rowObject, "isRollCounter"));
            gameRow.setPlayable(JsonHelper.optBoolean(rowObject, "isPlayable"));

            if (rowObject.containsKey("sum")) {
                gameRow.setSumSet((String) rowObject.get("sum"));
            }

            gameRows.add(gameRow);
        }

        gameType.setGameRows(gameRows);
        mGameTypes.add(gameType);
    }
}

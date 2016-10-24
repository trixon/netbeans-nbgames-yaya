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
package org.nbgames.yaya.gamedef;

import java.util.TreeSet;
import se.trixon.almond.nbp.util.AUtil;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class GameRow {

    private boolean mBonus;
    private String mFormula;
    private String mId;
    private int mLim = 0;
    private int mMax = 0;
    private boolean mPlayable;
    private boolean mRollCounter;
    private boolean mSum;
    private TreeSet<Integer> mSumSet;
    private String mTitle;
    private String mTitleSymbol;

    public String dump() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=").append(getId()).append("\n");
        stringBuilder.append("title=").append(getTitle()).append("\n");
        stringBuilder.append("titleSymbol=").append(getTitleSymbol()).append("\n");
        stringBuilder.append("formula=").append(getFormula()).append("\n");
        stringBuilder.append("lim=").append(getLim()).append("\n");
        stringBuilder.append("max=").append(getMax()).append("\n");
        stringBuilder.append("isBonus=").append(isBonus()).append("\n");
        stringBuilder.append("isPlayable=").append(isPlayable()).append("\n");
        stringBuilder.append("isRollCounter=").append(isRollCounter()).append("\n");
        stringBuilder.append("isSum=").append(isSum()).append("\n");

        if (mSumSet != null) {
            stringBuilder.append("sumSet=").append(mSumSet.toString()).append("\n");
        } else {
            stringBuilder.append("sumSet=null").append("\n");
        }

        return stringBuilder.toString();
    }

    public String getFormula() {
        return mFormula;
    }

    public String getId() {
        return mId;
    }

    public int getLim() {
        return mLim;
    }

    public int getMax() {
        return mMax;
    }

    public TreeSet<Integer> getSumSet() {
        return mSumSet;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTitleSymbol() {
        return mTitleSymbol;
    }

    public boolean isBonus() {
        return mBonus;
    }

    public boolean isPlayable() {
        return mPlayable;
    }

    public boolean isRollCounter() {
        return mRollCounter;
    }

    public boolean isSum() {
        return mSum;
    }

    public void setBonus(boolean bonus) {
        mBonus = bonus;
    }

    public void setFormula(String formula) {
        mFormula = formula;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setLim(int lim) {
        mLim = lim;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public void setPlayable(boolean playable) {
        mPlayable = playable;
    }

    public void setRollCounter(boolean rollCounter) {
        mRollCounter = rollCounter;
    }

    public void setSum(boolean sum) {
        mSum = sum;
    }

    public void setSumSet(TreeSet<Integer> sumSet) {
        mSumSet = sumSet;
    }

    public void setSumSet(String sumSet) {
        setSumSet(AUtil.convertStringToIntSet(sumSet));
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setTitleSymbol(String titleSymbol) {
        mTitleSymbol = titleSymbol;
    }
}

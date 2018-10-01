package com.lwhtarena.company.rank.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 14:45 2018/8/4
 * @Modified By:
 * <h1>
 * <ol></ol>
 * </h1>
 */

public class RankUnitPlace {

    long lastID;
    long nextID;
    int placeNumber;

    public RankUnitPlace() {
    }

    public long getLastID() {
        return lastID;
    }

    public void setLastID(long lastID) {
        this.lastID = lastID;
    }

    public long getNextID() {
        return nextID;
    }

    public void setNextID(long nextID) {
        this.nextID = nextID;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    @Override
    public String toString() {
        return "RankUnitPlace{" +
                "lastID=" + lastID +
                ", nextID=" + nextID +
                ", placeNumber=" + placeNumber +
                '}';
    }
}

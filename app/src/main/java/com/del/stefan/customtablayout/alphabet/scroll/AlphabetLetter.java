package com.del.stefan.customtablayout.alphabet.scroll;

/**
 * Created by stefan on 3/30/17.
 */

public class AlphabetLetter {

    private int position;
    private String letter;
    private boolean isActive;

    public AlphabetLetter(int position, String letter, boolean isActive) {
        this.position = position;
        this.letter = letter;
        this.isActive = isActive;
    }

    public AlphabetLetter() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

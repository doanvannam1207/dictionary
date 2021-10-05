package com.dictionary.base;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private  List<Word> words = new ArrayList<>();

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}

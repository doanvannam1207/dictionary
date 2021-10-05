package com.dictionary.base;


import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private  List<Word> words = new ArrayList<Word>(); //tạo 1 danh sách từ cho đối tượng dictionary

    public List<Word> getWords() {
        return words;
    } // lấy danh sách từ

    public void setWords(List<Word> words) {
        this.words = words;
    } //cập nhật danh sách từ
}

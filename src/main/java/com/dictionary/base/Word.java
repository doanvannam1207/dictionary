package com.dictionary.base;

import java.io.Serializable;


//Kế thừa interface Serializable để có thể đọc và ghi đối tượng vào file
public class Word implements Serializable {
    private String word_target;  //từ tiếng Anh
    private String word_explain; //nghĩa
    private String type;  //loại từ
    private String pronounce;  //phiên âm

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }
}

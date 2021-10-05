package com.dictionary.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandLine {
    private static DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private Scanner sc = new Scanner(System.in);

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        dictionaryManagement.showAllWords();
    }

    public void dictionaryAdvanced() {
        dictionaryManagement.insertFromFile();
        dictionaryManagement.dictionaryExportToFile();
    }

    public void dictionarySearcher() {
        Dictionary dictionary = dictionaryManagement.getDictionary();
        List<Word> words = dictionary.getWords();
        System.out.println("Nhập từ cần tìm: ");
        String word_to_find = sc.nextLine();
        word_to_find.toLowerCase();
        String result = "";
        for (int i = 0; i < words.size(); i++) {
            String word_target = words.get(i).getWord_target();
            if (word_target.startsWith(word_to_find)) {
                result += word_target + ", ";
            }
        }
        System.out.println("Kết quá: ");
        System.out.println(result);
    }
}


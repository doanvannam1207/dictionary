package com.dictionary.JDBC;

import com.dictionary.base.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCManagement {

    private static Connection connection = JDBCConncetion.getJDBCConnection();
    public List<Word> getAllWord () {
        List<Word> words = new ArrayList<>();
        String sql = "SELECT * FROM WORD";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Word word = new Word();
                word.setWord_target(result.getString("word_target"));
                word.setType(result.getString("word_type"));
                word.setPronounce(result.getString("word_pronounce"));
                word.setWord_explain(result.getString("word_explain"));
                words.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public void addWord(Word word) {
        String sql = "INSERT INTO WORD (WORD_TARGET, WORD_TYPE, WORD_PRONOUNCE, WORD_EXPLAIN) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word.getWord_target());
            preparedStatement.setString(2, word.getType());
            preparedStatement.setString(3, word.getPronounce());
            preparedStatement.setString(4, word.getWord_explain());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

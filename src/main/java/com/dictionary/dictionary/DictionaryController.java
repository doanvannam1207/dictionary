package com.dictionary.dictionary;

import com.dictionary.base.DictionaryCommandLine;
import com.dictionary.base.DictionaryManagement;
import com.dictionary.base.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.speech.freetts.*;

public class DictionaryController implements Initializable {
    @FXML
    private TextField word_to_find;
    @FXML
    private ListView<String> words;
    @FXML
    private Label word_explain;
    @FXML
    private Label type;
    @FXML
    private Label pronounce;
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
    private ObservableList<String> listWord;

    public void loadData() {
        List<String> listWord = new ArrayList<>();
        List<Word> Words = dictionaryManagement.getWordFromMySQL();
        for (Word word: Words) {
            listWord.add(word.getWord_target());
        }
        words.getItems().addAll(listWord);
    }

    public void Findword (KeyEvent event) {
        listWord = FXCollections.observableArrayList();
        List<Word> Words = dictionaryManagement.getWordFromMySQL();
        String word_find = word_to_find.getText().toLowerCase();
        for (Word word: Words) {
            String temp = word.getWord_target().toLowerCase();
            if (temp.startsWith(word_find)) {
                int dem = 0;
                for (int i = 0; i < listWord.size(); i++) {
                    if (listWord.get(i).equals(temp)) {
                        dem += 1;
                    }
                }
                if ( dem == 0) {
                    listWord.add(temp);
                }
            }
            if (temp.equals(word_find)) {
                word_explain.setText(word.getWord_explain());
                type.setText(word.getType());
                pronounce.setText(word.getPronounce());
            } else {
                word_explain.setText("");
                type.setText("");
                pronounce.setText("");
            }
        }
        words.setItems(listWord);
    }

    public void search (ActionEvent event) {
        List<Word> Words = dictionaryManagement.getWordFromMySQL();
        String Word_to_find = word_to_find.getText().toLowerCase();
        for (int i = 0; i < Words.size(); i++) {
            String temp = Words.get(i).getWord_target().toLowerCase();
            if (temp.equals(Word_to_find)) {
                word_explain.setText(Words.get(i).getWord_explain());
                type.setText(Words.get(i).getType());
                pronounce.setText(Words.get(i).getPronounce());
            } else {
                word_explain.setText("Không có kết quả nào cho từ bạn tìm.");
            }
        }
    }

    public void speak(ActionEvent event) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
        final String voicename = "kevin16";
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(voicename);
        voice.allocate();
        voice.speak(word_to_find.getText());
    }

    @FXML
    public void selectItem(MouseEvent event) {
        List<Word> Words = dictionaryManagement.getWordFromMySQL();
        word_to_find.setText(words.getSelectionModel().getSelectedItem());
        String Word_to_find = word_to_find.getText().toLowerCase();
        for (int i = 0; i < Words.size(); i++) {
            String temp = Words.get(i).getWord_target().toLowerCase();
            if (temp.equals(Word_to_find)) {
                word_explain.setText(Words.get(i).getWord_explain());
                type.setText(Words.get(i).getType());
                pronounce.setText(Words.get(i).getPronounce());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pronounce.setBackground(new Background( new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        type.setBackground(new Background( new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        word_explain.setBackground(new Background( new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        loadData();
    }
}
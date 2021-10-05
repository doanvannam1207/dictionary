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
    private TextField word_to_find; //dùng để nhập từ tìm kiếm
    @FXML
    private ListView<String> words; //danh sách từ
    @FXML
    private Label word_explain; //nghĩa
    @FXML
    private Label type; //Loại từ
    @FXML
    private Label pronounce; //phiên âm
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
    private ObservableList<String> listWord; //dùng để ràng buộc listview

    //Lấy danh sách từ và add vào listview
    public void loadData() {
        List<String> listWord = new ArrayList<>();
        List<Word> Words = dictionaryManagement.getWordFromMySQL();
        for (Word word: Words) {
            listWord.add(word.getWord_target());
        }
        words.getItems().addAll(listWord);
    }

    //Gợi ý từ khi gõ từ tìm kiếm sử dụng event On key released của TextField
    public void Findword (KeyEvent event) {
        listWord = FXCollections.observableArrayList();
        List<Word> Words = dictionaryManagement.getWordFromMySQL();
        String word_find = word_to_find.getText().toLowerCase(); //lấy chuỗi được nhập vào từ textField và chuyển tất cả về chữ thường
        for (Word word: Words) { //chạy vòng for qua từng phần tử của danh sách từ đã lấy từ MySQL
            String temp = word.getWord_target().toLowerCase(); //lấy giá trị cúa từ tiếng Anh sau đó chuyển về chữ thường
            if (temp.startsWith(word_find)) { //từ tiếng Anh của word bắt đầu bằng chuỗi được nhập vào
                int dem = 0; // dùng để check xem word đã có trong listview hay chưa
                for (int i = 0; i < listWord.size(); i++) {
                    if (listWord.get(i).equals(temp)) {
                        dem += 1; // nếu đã có thì đếm tăng lên 1
                    }
                }
                if ( dem == 0) {
                    listWord.add(temp); // nếu chưa có thì thêm vào listview
                }
            }
            if (temp.equals(word_find)) { //nếu chuỗi nhập vào bằng với từ tiếng Anh của word thì hiển thị nội dung lên màn hình
                word_explain.setText(word.getWord_explain());
                type.setText(word.getType());
                pronounce.setText(word.getPronounce());
            } else { // nếu không thì các nội dung trở về ký tự rỗng
                word_explain.setText("");
                type.setText("");
                pronounce.setText("");
            }
        }
        words.setItems(listWord); // cập nhật lại listview
    }

    //tìm kiếm từ
    public void search (ActionEvent event) { //bắt event On Action cho Button
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

    //phát âm
    public void speak(ActionEvent event) { // bắt event On Action cho Button
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
        final String voicename = "kevin16";
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(voicename);
        voice.allocate();
        voice.speak(word_to_find.getText());
    }

    //khi chọn từ nào trong danh sách thì sẽ hiện nội dung ra
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
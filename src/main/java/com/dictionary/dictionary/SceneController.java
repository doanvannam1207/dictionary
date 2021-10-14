package com.dictionary.dictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SceneController {

    @FXML
    private Button dictionaryButton;

    @FXML
    private TextField myTextFeild;

    @FXML
    private TextField myTextTranslate;

    @FXML
    private Button speakButton;

    @FXML
    private Button translateButton;

    private Stage stage;
    private Scene scene;
    private  Parent root;

    @FXML
    public void speakController(ActionEvent event) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
        final String voicename = "kevin16";
        Voice voice;
        voice = VoiceManager.getInstance().getVoice(voicename);
        if (voice != null) {
            voice.allocate();
        }
        voice.speak(myTextFeild.getText());
    }


    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbyoNlHEnDRatf-gGMw-FZV4ySy37Bffb1wjDOiILp64VVzovih-6VCpMqhxubJsjIw9/exec"
                + "?q=" + URLEncoder.encode(text, "UTF-8") + "&target=" + langTo + "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    @FXML
    public void switchScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void translateController(ActionEvent event) throws IOException {
        String target = myTextFeild.getText();
        String desText = this.translate("en", "vi", target);
        myTextTranslate.setText(desText);
    }

}

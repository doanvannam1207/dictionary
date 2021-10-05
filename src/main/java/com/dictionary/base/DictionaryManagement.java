package com.dictionary.base;

import com.dictionary.JDBC.JDBCManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private final Scanner sc = new Scanner(System.in);
    private  static Dictionary dictionary = new Dictionary(); //tạo 1 đối tượng dictionary để thao tác với danh sách từ
    private static JDBCManagement jdbcManagement = new JDBCManagement(); // đối tượng liên thao tác với MySQL

    public static Dictionary getDictionary() {
        return dictionary;
    }

    public static void setDictionary(Dictionary dictionary) {
        DictionaryManagement.dictionary = dictionary;
    }

    // Thêm n từ mới
    public void insertFromCommandline() {
        System.out.println("Nhập số từ muốn thêm");
        int n = sc.nextInt();
        sc.nextLine();
        List<Word> words = dictionary.getWords();
        for ( int i = 0; i < n; i++  ) {
            System.out.println("Nhập từ tiếng Anh");
            String word_target = sc.nextLine();
            System.out.println("Nhập nghĩa của từ");
            String word_explain = sc.nextLine();
            Word word = new Word(); // tạo 1 đối tượng
            word.setWord_target(word_target);
            word.setWord_explain(word_explain);
            words.add(word); // thêm đối tượng vào danh sách các đối tượng đã tạo từ trước
        }
        dictionary.setWords(words); // cập nhật danh sách sau khi thêm
    }

    //in ra màn hình toàn bộ từ vựng
    public void showAllWords() {
        List<Word> words = dictionary.getWords(); // lấy danh sách từ
        System.out.println( "No\t|English\t\t|Tiếng Việt" );
        for ( int i = 0; i < words.size(); i++ ) {
            System.out.printf( "%d.\t|%s\t\t|%s\n", i + 1, words.get(i).getWord_target(), words.get(i).getWord_explain() );
        }
    }

    //Thêm từ vựng từ file có sẵn sử dụng FileReader, BufferReader
    public void insertFromFile() {
        List<Word> words = dictionary.getWords();
        //khi down về máy cần viết lại liên kết file
        File file = new File ("C:\\Users\\Admin\\OneDrive\\Desktop\\Nam\\Dictionary\\data\\dictionaries.txt");
        //try-catch dùng để xử lý ngoại lệ
        try {
            FileReader fileReader = new FileReader(file);
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String s = bufferedReader.readLine(); // đọc dòng đầu tiên
                while (s != null) {
                    Word word = new Word();
                    String Word = s.toLowerCase(); //chuyển toàn bộ ký tự về chữ thường
                    char temp[] = Word.toCharArray(); // chuyển chuỗi s về dạng mảng kí tự
                    /*tìm các ký tự đặc biệt đã quy định trong file từ để ngăn cách giữa
                    word_target, type, pronounce, word_explain để tách chuỗi
                    chuỗi quy định có dạng: word_target=type*pronounce%word_explain
                     */
                    for (int i = 0; i < s.length(); i++) {
                        int dem1 = 0, dem2 = 0, dem3 = 0;
                        if (Word.charAt(i) == '=') {
                            dem1 = i;
                            for (int j = dem1; j < s.length(); j++) {
                                if (Word.charAt(j) == '*') {
                                    dem2 = j;
                                    for (int k = dem2; k < s.length(); k++) {
                                        if (Word.charAt(k) == '%') {
                                            dem3 = k;
                                            //copy dem1 ký tự từ vị trí 0
                                            word.setWord_target(String.copyValueOf(temp, 0, dem1));
                                            //copy type từ sau ký tự '=' tới trước ký tự '*'
                                            word.setType(String.copyValueOf(temp, dem1 + 1,dem2 - dem1 - 1));
                                            //copy pronounce từ sau ký tự '*' tới trước ký tự '%'
                                            word.setPronounce(String.copyValueOf(temp, dem2 + 1, dem3 - dem2 -1));
                                            //copy phần còn lại
                                            word.setWord_explain(String.copyValueOf(temp, dem3 + 1, s.length() - dem3 -1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    words.add(word); //thêm từ vào danh sách
                    s = bufferedReader.readLine(); // đọc dòng tiếp theo
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dictionary.setWords(words); // cập nhật lại danh sách
    }

    //xuất danh sách word ra file
    /*
    ************************************************************************************************
    LƯU Ý: word là 1 đối tượng Word word = new Word(); nên class Word thừa kế interface Serializable
    để có thể thao tác với file
    Ở đây dùng FileOutputStream và ObjectOutputStream để ghi danh sách đối tượng ra file
    * **********************************************************************************************
     */
    public void dictionaryExportToFile() {
        List<Word> words = dictionary.getWords();
        File file = new File ("C:\\Users\\Admin\\OneDrive\\Desktop\\Nam\\Dictionary\\data\\data.txt");
        try { // try catch để xử lý lỗi ngoại lệ
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(words); // ghi đối tượng vào file
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //đọc dữ liệu từ file dùng FileInputStream và ObjectInputStream
    public List<Word> getWordFromData() {
        List<Word> words = new ArrayList<>();
        //khi down về máy cần viết lại liên kết file
        File file = new File ("C:\\Users\\Admin\\OneDrive\\Desktop\\Nam\\Dictionary\\data\\data.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            words = (List<Word>) objectInputStream.readObject(); // đọc file và load dữ liệu vào danh sách words
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    //tra từ trong bản text, đồ họa sẽ có hàm khác, nhưng vẫn phân biệt chữ hoa chữ thường, khi nào rảnh sẽ update
    public void dictionaryLookup () {
        List<Word> words = getWordFromData();
        System.out.println("Nhập từ cần tìm: ");
        String lookUp = sc.nextLine();
        lookUp.toLowerCase();
        for ( int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            if ( word.getWord_target().equals(lookUp) ) {
                System.out.println("English\t|Tiếng Việt");
                System.out.println(words.get(i).getWord_target() + "\t|" + words.get(i).getWord_explain());
            }
        }
    }

    //thêm từ vào danh sách trong file đã xuất ra từ trước
    public void dictionaryAddWord() {
        List<Word> wordFromData = getWordFromData(); // lấy danh sách từ từ trong file đã xuất ra
        System.out.println("Nhập từ mới: ");
        String word_target = sc.nextLine();
        System.out.println("Nhập nghĩa của từ: ");
        String word_explain = sc.nextLine();
        Word word = new Word (); // tạo từ mới
        word.setWord_target(word_target);
        word.setWord_explain(word_explain);
        wordFromData.add(word); // thêm từ vào danh sách
        dictionary.setWords(wordFromData); // update danh sách
        dictionaryExportToFile(); // xuất lại ra file
    }

    //sửa từ
    public void dictionaryUpdateWord() {
        List<Word> wordFromData = getWordFromData(); // lấy danh sách từ từ trong file đã xuất ra
        System.out.println("Nhập từ muốn sửa: ");
        String word_to_update = sc.nextLine();
        for (Word word: wordFromData) {
            if (word.getWord_target().equals(word_to_update)) {
                System.out.println("Sửa lại nghĩa: ");
                String word_explain = sc.nextLine();
                word.setWord_explain(word_explain);
            }
        }
        dictionary.setWords(wordFromData); // update danh sách
        dictionaryExportToFile(); // xuất lại ra file
    }

    //xóa từ
    public void dictionaryDeleteWord() {
        List<Word> wordFromData = getWordFromData(); // lấy danh sách từ từ trong file đã xuất ra
        System.out.println("Nhập từ muốn xóa: ");
        String word_target = sc.nextLine();
        for (int i = 0; i < wordFromData.size(); i++) {
            if (wordFromData.get(i).getWord_target().equals(word_target)) {
                wordFromData.remove(i);
            }
        }
        dictionary.setWords(wordFromData); // update danh sách
        dictionaryExportToFile(); // xuất lại ra file
    }

    //Xuất danh sách word ra Cơ sở dữ liệu MySQL
    /*
    LƯU Ý: để dùng được hàm này thì trên máy cần kết nối MySQL và tạo bảng trên 1 địa chỉ y hệt
     */
    public void dictionaryExportToMySQL() {
        List<Word> words = dictionary.getWords();
        for (Word word: words) {
            jdbcManagement.addWord(word);
        }
    }

    //Lấy danh sách từ trên MySQL
    public List<Word> getWordFromMySQL() {
        List<Word> words = jdbcManagement.getAllWord();
        return words;
    }
}

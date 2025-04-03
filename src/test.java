public class test {
    public static void main(String[] args) {
        String bookFilePath = "lib/fake_book.txt";
        String StopWordsFilePath = "lib/English_Stop_Words.txt";

        String bookAsString = new FileToString(bookFilePath).getFileContent();
        String stopWordsAsString = new FileToString(StopWordsFilePath).getFileContent();

        Tagger wordMap = new Tagger(bookAsString, stopWordsAsString);

        System.out.println(wordMap.wordCountMapToString());

    }
}

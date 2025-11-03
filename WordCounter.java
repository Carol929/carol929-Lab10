import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    private static final Pattern WORD = Pattern.compile("[A-Za-z0-9']+");

    public static void main(String[] args) {

    }
    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText{
        Matcher all = WORD.matcher(text);
        int total = 0;
        while (all.find()) total++;

        if (total < 5) {
            throw new TooSmallText("Only found " + total + " words.");
        }

        if (stopword == null) {
            return total;
        }

        Matcher m = WORD.matcher(text);
        int countUntil = 0;
        while (m.find()) {
            countUntil++;
            if (m.group().equals(stopword)) {
                return countUntil;
            }
        }

        throw new InvalidStopwordException("Couldn't find stopword: " + stopword);
    }

    public static StringBuffer processFile(String path) throws EmptyFileException {

    }

    public static StringBuffer processFile(String path) throws EmptyFileException {

    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    private static final Pattern WORD = Pattern.compile("[A-Za-z0-9']+");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("Choose 1 for file, 2 for text: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                sc.next(); 
            }
        }

        StringBuffer text = new StringBuffer();
        String stopword = (args.length >= 2) ? args[1] : null;

        if (choice == 1) {
            if (args.length >= 1) {
                try {
                    text = processFile(args[0]);
                } catch (EmptyFileException efe) {
                    System.out.println(efe.toString());
                    text = new StringBuffer("");
                }
            } else {
                text = new StringBuffer("");
            }
        } else { 
            System.out.print("Enter text: ");
            sc.nextLine(); 
            text.append(sc.nextLine());
        }

        try {
            int count = processText(text, stopword);
            System.out.println("Found " + count + " words.");
        } catch (InvalidStopwordException | TooSmallText e) {
            System.out.println(e.toString());
        }
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
        String currentPath = path;
        while (true) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(currentPath)));
                if (content.length() == 0) {
                    throw new EmptyFileException(currentPath + " was empty");
                }
                return new StringBuffer(content);
            } catch (EmptyFileException e) {
                throw e;
            } catch (java.io.IOException e) {
                System.out.print("Enter a valid file path: ");
                java.util.Scanner sc = new java.util.Scanner(System.in);
                if (sc.hasNext()) {
                    currentPath = sc.next();
                } else {
                    throw new RuntimeException("No replacement path provided");
                }
            }
        }
    }

}

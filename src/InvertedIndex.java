import java.io.*;
import java.util.*;

public class InvertedIndex {
    private static final Set<String> preposition = new HashSet<>(Arrays.asList("as", "of", "from", "at", "ago", "before", "by",
                    "during", "for", "the", "until", "a", "an", "to", "in", "on", "over", "though", "with", "or",
                    "that", "is", "i", "you", "he", "she", "us", "and", "be", "we", "it", "they", "me", "his", "her", "their", "till"));

    private static final Map<String, HashSet<String>> engine = new HashMap<>();
    private static Set<String> mustFiles = new HashSet<>();
    private static final Set<String> plusFiles = new HashSet<>();
    private static final Set<String> removedFiles = new HashSet<>();


    public static void addToDb(String filename, String word) {
        if (!NotWord(word)) {
            word = Get_root(word);
            if (!engine.containsKey(word)) {
                engine.put(word, new HashSet<>());
            }
            engine.get(word).add(filename);
        }
        return;
    }

    public static boolean NotWord(String word) {
        return preposition.contains(word.trim().toLowerCase()) || word.length() < 2;
    }

    public static String Get_root(String word) {
        Stemmer stemmer = new Stemmer();
        stemmer.add(word.toLowerCase().toCharArray(), word.length());
        stemmer.stem();
        return stemmer.toString();
    }

    public static void getDocuments(String[] all_words) {
        int num = 1;
        for (String w : all_words) {
            if (!NotWord(w)) {
                System.out.println(w + " :");
                for (String val : engine.get(Get_root(w))) {
                    System.out.println(num + ") " + val);
                    num++;
                }
                num = 1;
                System.out.println("************************************");
            }
        }
    }

    public static void print_spec_docs(List<String> all_words) {

        for (String word : all_words) {
            switch (word.charAt(0)) {
                case '+':
                    word = word.substring(1);
                    if (!NotWord(word)) {
                        getPlusFiles(word);
                    }
                    break;
                case '-':
                    word = word.substring(1);
                    if (!NotWord(word)) {
                        getRemovedFiles(word.substring(1));
                    }
                    break;
                default:
                    if (!NotWord(word)) {
                        getMustFiles(word);
                    }
                    break;
            }
        }
        intersectFiles();
        for (String w : mustFiles) {
            System.out.println(w);

        }
    }

    public static void getMustFiles(String word) {
        word = Get_root(word);

        HashSet<String> files = new HashSet<>();
        if (engine.containsKey(word)) {
            files = engine.get(word);
        }
        if (mustFiles.isEmpty()) mustFiles = files;
        else mustFiles.retainAll(files);
    }


    public static void getPlusFiles(String word) {
        word = Get_root(word);
        HashSet<String> files = new HashSet<>();
        if (engine.containsKey(word)) {
            files = engine.get(word);
        }
        plusFiles.addAll(files);
    }

    public static void getRemovedFiles(String word) {
        word = Get_root(word);
        HashSet<String> files = new HashSet<>();
        if (engine.containsKey(word)) {
            files = engine.get(word);
        }
        removedFiles.addAll(files);
    }

    public static void intersectFiles() {
        Set<String> finalFiles = mustFiles;
        finalFiles.retainAll(plusFiles);
        finalFiles.removeAll(removedFiles);
    }


}

package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;
    static final int length = 3;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        if (strings == null) {
            return 0;
        }
        int counter = 0;
        for (String str : strings) {
            for (String word : str.split(" ")) {
                if (word.length() > length && !contains(word)) {
                    trie.add(new Tuple(word, word.length()));
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            throw new IllegalArgumentException("Length of prefix is less than 2 symbols.");
        }
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2 || k < 1) {
            throw new IllegalArgumentException("Length of prefix is less than 2 symbols or K is less than 1");
        }

        int len;
        if (k == 2) {
            len = 3;
        }
        else{len = k;}
        Iterable<String> givenwords = trie.wordsWithPrefix(pref);
        ArrayList<String> finalresult = new ArrayList<>();
        for (String word : givenwords) {
            if (word.length() < (pref.length() + len)) {
                finalresult.add(word);
            }
        }
        return finalresult;
    }

    public int size() {
        return trie.size();
    }
}

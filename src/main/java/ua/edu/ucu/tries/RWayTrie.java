package ua.edu.ucu.tries;

import ua.edu.ucu.immutable.Queue;

import java.util.Collections;

public class RWayTrie implements Trie {

    private static int R = 256; // radix
    private Node root = new Node(); // root of trie
    private int size;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];

        public Node[] getNext() {
            return next;
        }
    }


    @Override
    public void add(Tuple t) {
        if (t.weight < 2)
            return;
        root = add(root, t.term, t.weight, 0);
        size++;
    }

    private Node add(Node x, String key, int val, int d) { // Change value associated with key if in subtrie rooted at x.
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        x.next[c] = add(x.next[c], key, val, d + 1);
        return x;
    }

    @Override
    public boolean contains(String word) {
        if (get(root, word, 0) != null) {
            return true;
        }
        return false;
    }

    private Node get(Node x, String key, int d) { // Return node associated with key in the subtrie rooted at x.
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        return get(x.next[c], key, d + 1);
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            root = delete(root, word, 0);
            return true;
        }
        return false;
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) {
                size--;
            }
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        if (x.val != null) return x;
        for (char c = 0; c < R; c++)
            if (x.next[c] != null) return x;
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override

    public Iterable<String> wordsWithPrefix(String s) {
        if (s.length() < 2){
            return Collections::emptyIterator;
        }
        Queue results = new Queue();
        collect(get(root, s, 0), new StringBuilder(s), results);
        return (Iterable<String>) results;
    }

    private void collect(Node x, StringBuilder prefix, Queue results) {
        if (x == null) {
            return;
        }
        if (x.val != null) {
            results.enqueue(prefix.toString());
        }
        for (char a = 0; a < R; a++) {
            prefix.append(a);
            collect(x.getNext()[a], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }



    @Override
    public int size() {
        return size;
    }

}

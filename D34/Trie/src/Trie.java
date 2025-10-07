import java.util.HashMap;
import java.util.Map;

// implementation for lower case english characters (a-z)
public class Trie {

    private static class TrieNode{

        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()){
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word){
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix){
        return findNode(prefix) != null;
    }

    public boolean delete(String word){
        return delete(root, word, 0);
    }

    private TrieNode findNode(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()){
            TrieNode node = current.children.get(ch);
            if (node == null){
                return null;
            }
            current = node;
        }
        return current;
    }

    private boolean delete(TrieNode current, String word, int index){
        if(index == word.length()){
            if(!current.isEndOfWord){
                return false; // word does not exist
            }
            current.isEndOfWord = false;
            // if node has no children, delete it
            return current.children.isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode childNode = current.children.get(ch);
        if(childNode == null){
            return false; // word does not exist
        }
        // recursively delete the node
        boolean shouldDeleteCurrentNode = delete(childNode, word, index + 1);
        if(shouldDeleteCurrentNode){
            current.children.remove(ch);
            return  current.children.isEmpty() && !current.isEndOfWord;
        }
        return false;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        System.out.println(" --- Testing Trie ---");

        System.out.println(" --- Inserting Trie apple ---");
        trie.insert("apple");
        System.out.println("search 'apple': "+trie.search("apple"));
        System.out.println("search 'app': "+trie.search("app"));
        System.out.println("StartsWith 'apple': "+trie.startsWith("apple"));
        System.out.println("StartsWith 'app': "+trie.startsWith("app"));

        System.out.println(" --- Inserting Trie app ---");
        trie.insert("app");
        System.out.println("search 'app': "+trie.search("app"));
        System.out.println("StartsWith 'app': "+trie.startsWith("app"));

        System.out.println(" --- Inserting Trie banana ---");
        trie.insert("banana");
        System.out.println("search 'banana': "+trie.search("banana"));
        System.out.println("StartsWith 'bana': "+trie.startsWith("bana"));

        System.out.println(" --- Deleting Trie apple ---");
        trie.delete("apple");
        System.out.println("search 'apple': "+trie.search("apple"));
        System.out.println("StartsWith 'apple': "+trie.startsWith("apple"));
        System.out.println("search 'app': "+trie.search("app"));
        System.out.println("StartsWith 'app': "+trie.startsWith("app"));

        System.out.println(" --- Deleting Trie app ---");
        trie.delete("app");
        System.out.println("search 'app': "+trie.search("app"));
        System.out.println("StartsWith 'app': "+trie.startsWith("app"));

        System.out.println("search 'banana': "+trie.search("banana"));

        System.out.println(" --- Deleting Trie banana ---");
        trie.delete("banana");
        System.out.println("search 'banana': "+trie.search("banana"));
        System.out.println("StartsWith 'bana': "+trie.startsWith("bana"));

    }

}

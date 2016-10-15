package DataStructure;

import java.util.HashMap;
import java.util.Map;

public class Trie {
	TrieNode root;
	public Trie() {
		root = new TrieNode();
	}
	public void add(String sequence) {
		int length = sequence.length();
		Character c;
		TrieNode ref=root;
		for(int i=0;i<length;i++) {
			c = sequence.charAt(i);
			if(ref.checkChild(c)) {
				ref = ref.getChild(c);
			}
			else {
				ref = ref.addChild(c);
			}
		}
		ref.addEndChild();
	}
	public boolean contains(String sequence) {
		sequence = sequence+"$";
		int length = sequence.length();
		Character c;
		TrieNode ref=root;
		boolean flag = true;
		for(int i=0;i<length;i++) {
			c = sequence.charAt(i);
			if(ref.checkChild(c)) {
				ref = ref.getChild(c);
			}
			else {
				flag = false;
				break;
			}
		}
		return flag;
	}
	public boolean containsPrefix(String sequence) {
		int length = sequence.length();
		Character c;
		TrieNode ref=root;
		boolean flag = true;
		for(int i=0;i<length;i++) {
			c = sequence.charAt(i);
			if(ref.checkChild(c)) {
				ref = ref.getChild(c);
			}
			else {
				flag = false;
				break;
			}
		}
		return flag;
	}
	public void addSuffix(String sequence) {
		int length = sequence.length();
		for(int i=0;i<length;i++) {
			add(sequence.substring(i));
		}
	}
}

class TrieNode {
	private Map<Character, TrieNode> children;
	boolean endOfWord;
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
	}
	public TrieNode (boolean isEnd) {
		endOfWord = isEnd;
	}
	public boolean checkChild(Character key) {
		return children.containsKey(key);
	}
	public TrieNode addChild(Character val) {
		TrieNode temp = new TrieNode();
		children.put(val, temp);
		return temp;
	}
	public TrieNode getChild(Character key) {
		return children.get(key);
	}
	public boolean isEnd() {
		return endOfWord;
	}
	public void addEndChild() {
		children.put('$', new TrieNode(true));
	}
}
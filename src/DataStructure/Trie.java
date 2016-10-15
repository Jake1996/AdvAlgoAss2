package DataStructure;

import java.util.HashMap;
import java.util.Map;

public class Trie {
	final char lex[] = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".toCharArray();
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
	public int noOfOccurances(String sequence) {
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
		if(flag) {
			return ref.getChild('$').getOccurances();
		}
		return 0;
	}
	
	public void addSuffix(String sequence) {
		int length = sequence.length();
		for(int i=0;i<length;i++) {
			add(sequence.substring(i));
		}
	}
	
	public void printIndex() {
		dfs(root,"");
	}
	private void dfs(TrieNode node,String current) {
		if(node.checkChild('$')) {
			System.out.println(current+", "+node.getChild('$').getOccurances());
		}
		for(char c : lex) {
			if(node.checkChild(c))
				dfs(node.getChild(c),current+c);
		}
	}
}

class TrieNode {
	private Map<Character, TrieNode> children;
	private Integer occurances;
	boolean endOfWord;
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
		
	}
	public TrieNode (boolean isEnd,int oc) {
		endOfWord = isEnd;
		occurances = oc;
	}
	public TrieNode setOccurances() {
		occurances++;
		return this;
	}
	public int getOccurances() {
		if(endOfWord)
			return occurances;
		else
			return 0;
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
		if(children.containsKey('$'))
			children.put('$', children.get('$').setOccurances());
		else
			children.put('$', new TrieNode(true,1));
		
	}
}
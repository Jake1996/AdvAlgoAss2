package DataStructure;

import java.util.HashMap;
import java.util.Map;

public class Trie {
	final char lex[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	TrieNode root;
	public Trie() {
		root = new TrieNode();
	}
	public void add(String sequence,String title) {
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
		ref.addEndChild(title);
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
	public String noOfOccurances(String sequence) {
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
		return "0";
	}
	
	public void printIndex() {
		dfs(root,"");
	}
	private void dfs(TrieNode node,String current) {
		if(node.checkChild('$')) {
			System.out.println(current+" - "+node.getChild('$').getOccurances());
		}
		for(char c : lex) {
			if(node.checkChild(c))
				dfs(node.getChild(c),current+c);
		}
	}
}

class TrieNode {
	private Map<Character, TrieNode> children;
	private HashMap<String,Integer> occurances;
	boolean endOfWord;
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
		
	}
	public TrieNode (boolean isEnd,String title) {
		endOfWord = isEnd;
		occurances= new HashMap<>();
		occurances.put(title, 1);
	}
	public TrieNode setOccurances(String title) {
		if(occurances.containsKey(title))
			occurances.put(title, occurances.get(title)+1);
		else
			occurances.put(title, 1);
		return this;
	}
	public String getOccurances() {
		if(endOfWord) {
			int total=0;
			String ans="";
			for(String key : occurances.keySet()) {
				total+=occurances.get(key);
				ans=ans+key+" : "+occurances.get(key)+", ";
			}
			return total+" :: "+ans;
		}
		else
			return "0";
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
	public void addEndChild(String title) {
		if(children.containsKey('$'))
			children.put('$', children.get('$').setOccurances(title));
		else
			children.put('$', new TrieNode(true,title));
		
	}
}
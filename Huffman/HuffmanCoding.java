import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

/*“I certify that this submission contains my own work, except as noted"
 * Michael Briggs
 * 20013906
 * 
 *
 */

public class HuffmanCoding {
   

    
    static TreeMap<Character, String> codes = new TreeMap<>();
    static StringBuilder text = new StringBuilder("");
    static int ASCII[] = new int[128];
    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static StringBuilder encoded = new StringBuilder("");
    static StringBuilder decoded = new StringBuilder("");
    
    public static void main(String[] args) throws IOException {
    	
    	
    	HuffmanCoding hc = new HuffmanCoding();
    	
    	//Files to read
    	ArrayList<String> file_set1 = new ArrayList<>();
    	file_set1.add("Short Text 1ASCII");
    	file_set1.add("Short Text 2ASCII");
    	file_set1.add("Short Text 3ASCII");
    	file_set1.add("Short Text 4ASCII");
    	file_set1.add("Short Text 5ASCII");
    	file_set1.add("Short Text 6ASCII");
    	file_set1.add("Short Text 7ASCII");
    	file_set1.add("Short Text 8ASCII");
    	file_set1.add("Short Text 9ASCII");
    	file_set1.add("Short Text 10ASCII");
    	
    	ArrayList<String> file_set2 = new ArrayList<>();
    	//file_set2.add("ChestertonASCII");
    	//file_set2.add("DickensASCII");
    	ArrayList<String> file_set3 = new ArrayList<>();
    	file_set3.add("File1");
    	StringBuilder str = new StringBuilder("");
    	
    	//---Create string from file list---\\
    	for(String file : file_set1) {
    		String path = new File("src/"+file+".txt").getAbsolutePath();
    		Scanner s = new Scanner(new File(path));
    		ArrayList<String> list = new ArrayList<String>();
        	while (s.hasNextLine()){
        	    list.add(s.nextLine());
        	}
        	s.close();
        	
        	for(String l : list) {
        		str = str.append(l);
        		
        	}
        	
    	}
    	//---Create second string from file list---\\
    	System.out.println(str);
    	StringBuilder str2 = new StringBuilder("");
    	for(String file : file_set2) {
    		String path = new File("src/"+file+".txt").getAbsolutePath();
    		Scanner s = new Scanner(new File(path));
    		ArrayList<String> list = new ArrayList<String>();
        	while (s.hasNextLine()){
        	    list.add(s.nextLine());
        	}
        	s.close();
        	
        	for(String l : list) {
        		str2 = str2.append(l);
        		
        	}
        	
    	}
    	//build tree,encode,decode etc.
    	
    	hc.handle_new(str);
    	
    	//---Output encoded to file---\\
    	String output_path = new File("src/output.txt").getAbsolutePath();
    	File output_file = new File(output_path);
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_file))) {
    	    writer.write(str.toString());
    	}
    	hc.handle_new(str2);
    	
        
    }

    private void handle_new(StringBuilder toEncode) {
        
        text = toEncode;
            ASCII = new int[128];
            
            codes.clear();
            encoded.setLength(0);
            decoded.setLength(0);
            nodes.clear();
            System.out.println("Text: " + text);
            calculate_intervals(nodes, true);
            build_tree(nodes);
            generate_codes(nodes.peek(), "");

            printCodes();
            System.out.println("-----------------");
            System.out.println("Encoding/Decoding");
            System.out.println("-----------------");
            encode();
            decode();
            
    }
    
    private void encode() {
    	encoded.setLength(0);
        for (int i = 0; i < text.length(); i++)
            encoded.append(codes.get(text.charAt(i)));
        System.out.println("Encoded Text: " + encoded);
    }

 
    private void decode() {
    	decoded.setLength(0);
        Node node = nodes.peek();
        for (int i = 0; i < encoded.length(); ) {
            Node tempNode = node;
            while (tempNode.left != null && tempNode.right != null && i < encoded.length()) {
                if (encoded.charAt(i) == '1')
                    tempNode = tempNode.right;
                else tempNode = tempNode.left;
                i++;
            }
            if (tempNode != null)
                if (tempNode.character.length() == 1)
                    decoded.append(tempNode.character);
                else
                    System.out.println("Input not Valid");

        }
        System.out.println("Decoded Text: " + decoded);
    }

    

    private void build_tree(PriorityQueue<Node> vector) {
        while (vector.size() > 1)
            vector.add(new Node(vector.poll(), vector.poll()));
    }
    
    private void generate_codes(Node node, String s) {
        if (node != null) {
        	
        	/*
        	 *       /\
        	 *      0  1
        	 */
        	
        	if (node.left != null)
                generate_codes(node.left, s + "0");
            if (node.right != null)
                generate_codes(node.right, s + "1");
            if (node.left == null && node.right == null)
                codes.put(node.character.charAt(0), s);
        }
    }

    private void printCodes() {
    	System.out.println("-----");
        System.out.println("Codes");
        System.out.println("-----");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    private void calculate_intervals(PriorityQueue<Node> vector, boolean printIntervals) {
        if (printIntervals) System.out.println("-- intervals --");

        for (int i = 0; i < text.length(); i++)
            ASCII[text.charAt(i)]++;

        for (int i = 0; i < ASCII.length; i++)
            if (ASCII[i] > 0) {
                vector.add(new Node(ASCII[i] / (text.length() * 1.0), ((char) i) + ""));
                if (printIntervals)
                    System.out.println((char)i/*"'" + ((char) i) + "' : " + ASCII[i]*/);
            }
    }
    class Node {
        Node left, right;
        double value;
        String character;

        public Node(double value, String character) {
            this.value = value;
            this.character = character;
            left = null;
            right = null;
        }

        public Node(Node left, Node right) {
            this.value = left.value + right.value;
            character = left.character + right.character;
            if (left.value < right.value) {
                this.right = right;
                this.left = left;
            } else {
                this.right = left;
                this.left = right;
            }
        }
    }
    
}


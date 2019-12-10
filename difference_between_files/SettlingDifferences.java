import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SettlingDifferences {
	
	private String filename1;
	private String filename2;
	private String filename3;
	private String filename4;

	void lcs(ArrayList<Integer> file1, ArrayList<Integer> file2, ArrayList<String> list1, ArrayList<String> list2) {
		int m = file1.size();
		int n = file2.size();
		
		System.out.println(m+" "+n);
		ArrayList<String> answer = new ArrayList();

		int matrix[][] = new int[m + 1][n + 1];
		int matches[] = new int[max(m,n)+1];

		int sequential = 0;
		
		/*
		 * Iterate through both files line by line
		 * ---------------------------------------
		 */
		
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {

				int temp1 = i - 1;
				int temp2 = j - 1;
				int seqTemp;
				if (i == 0 || j == 0)
					matrix[i][j] = 0;
				else if (file1.get(i - 1).equals(file2.get(j - 1))) {

					// Confirm they are equal with actual string values saved in separate list
					if (list1.get(i - 1).equals(list2.get(j - 1))) {
						seqTemp = temp1;
						matches[temp1] = temp2;
						// answer.add("Match: "+temp1+" --> "+temp2);
						// answer.add(temp1+": "+file1.get(i-1));
						// answer.add(temp2+": "+file2.get(j-1));
						matrix[i][j] = matrix[i - 1][j - 1] + 1;
					}
					// continue if not equal

				} else {
					// System.out.println(sequential);
					sequential = 0;
					// answer.add("Mismatch: "+temp1+" --> "+temp2);
					matrix[i][j] = max(matrix[i - 1][j], matrix[i][j - 1]);

				}
			}
		}
		/*
		 * Keeps track of start & end of match and mismatch sequences
		 */
		int start = 0;
		int end = 0;
		int start2 = 0;
		int end2 = 0;
		int count = 0;
		int tempEnd = 0;
		int tempEnd2 = 0;
		boolean first = false;
		int misMatches[] = new int[n];
		for (int i = 1; i < matches.length; i++) {
			
			//Mismatch found because no other line found in second file
			if (matches[i] == 0) {
				end = i - 1;
				end2 = matches[i - 1];
				//Print Out Matches
				if (count > 0) {
					System.out.print("Match:     "+filename1+" : <" + start + ".." + end+">");
					System.out.println("          "+filename2+" : <" + start2 + ".." + end2+">");
					tempEnd = end;
					tempEnd2 = end2;
				}

				start = i + 1;
				if((i+1)>=matches.length)
					start2 = matches[i];
				else
					start2 = matches[i + 1];
				count = 0;
				first = true;
			} else {
				if(first) {
					//Print out mismatches
					
					System.out.print("MisMatch:  "+filename1+" : <" + (tempEnd+1)+".."+(i-1)+">");
					System.out.println("          "+filename2+" : <" + (tempEnd2+1)+".."+(i)+">");
					first = false;
				}
				count++;
			}

		}	
		
	}

	// Returns max of 2 ints given
	int max(int a, int b) {
		return (a > b) ? a : b;
	}
	
	/*
	 * Sums file lines to integer representation
	 * ------------------------
	 */

	ArrayList<Integer> listToInt(ArrayList<String> ar) {
		ArrayList<Integer> retArray = new ArrayList<>();

		int count = 0;
		for (String s : ar) {
			
			char[] temp = s.toCharArray();
			int tempInt = 0;
			
			for (int i = 0; i < s.length(); i++) {
				//use ASCII code as character value
				tempInt = tempInt + temp[i];
			}
			
			retArray.add(tempInt);
			count++;
		}

		return retArray;

	}

	public static void main(String[] args) throws FileNotFoundException {
		SettlingDifferences sd = new SettlingDifferences();
		

		String path = new File("src/Three_Bears.v1.txt").getAbsolutePath();
		//String path = new File("src/Dijkstra.py").getAbsolutePath();
		sd.filename1 = path;
		
		Scanner s = new Scanner(new File(path));
		ArrayList<String> list = new ArrayList<String>();

		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}
		s.close();

		String path2 = new File("src/Three_Bears.v2.txt").getAbsolutePath();
		//String path2 = new File("src/Dijkstra_py3.py").getAbsolutePath();
		sd.filename2 = path2;
		
		Scanner sc2 = new Scanner(new File(path2));
		ArrayList<String> list2 = new ArrayList<String>();
		
		while (sc2.hasNextLine()) {
			list2.add(sc2.nextLine());
		}
		sc2.close();
		
		/*
		 * REMOVE ALL WHITE SPACE LINES
		 * ----------------------------
		 */

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("")) {
				list.remove(i);
				// System.out.println("removed");
			}
		}
		for (int i = 0; i < list2.size(); i++) {
			if (list2.get(i).equals("")) {
				list2.remove(i);
				// System.out.println("removed");
			}
		}
		
		//Convert files to int representation line by line
		
		ArrayList<Integer> intList = sd.listToInt(list);
		ArrayList<Integer> intList2 = sd.listToInt(list2);
		sd.lcs(intList, intList2, list, list2);

	}

}
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


/*“I certify that this submission contains my own work, except as noted"
 * Michael Briggs
 * 20013906
 * 
 *
 */
public class Subset {

	private int iterations;
	

	public static void main(String[] args) {
			
		Subset s = new Subset();
		
		ArrayList<Integer> eval = new ArrayList<>();
		
		/*eval.add(3);
		eval.add(5);
		eval.add(3);
		eval.add(9);
		
		eval.add(18);
		eval.add(4);
		eval.add(5);
		eval.add(6);
		eval.add(1);*/
		
		eval.add(400);
		eval.add(20);
		eval.add(30);
		eval.add(10);
		ArrayList<Integer> e_l = new ArrayList<>();
		Set res;
		Set res2;
		ArrayList<Set> ret;
		
		for(int n = 4; n <=15; n++) {
			for(int i = 1; i <= 20; i++) {
				ArrayList<Integer> temp = new ArrayList<>();
				ArrayList<Integer> targets = new ArrayList<>();
				for(int x = 0; x < n; x++) {
					
					temp.add((int) (Math.random()*100 +1));
					
				}
				for(int t = 0; t < 10; t++) {
					
					targets.add((int) (Math.random()*1000 +1));
					
				}
				int avg_iterations = 0;
				int avg_iterations2 = 0;
				int mark = 0;
				System.out.println("");
				System.out.println("********************************");
				System.out.println("");
				for(Integer tar : targets) {
					mark++;
					
					System.out.println("");
					System.out.println("Target: "+tar +"Test:"+mark);
					System.out.println("N: "+n +" I:"+i);
					System.out.println("");
					System.out.println("-------------------");
					System.out.println("TESTING HS");
					System.out.println("-------------------");
					
					
					System.out.println("Input: "+temp);
					
					res = s.HS_Subset_Sum(temp, tar);
					System.out.println("Total Iterations: "+ s.iterations);
					avg_iterations = avg_iterations + s.iterations;
					s.iterations = 0;
					
					System.out.println("");
					System.out.println("-------------------");
					System.out.println("TESTING BRUTE FORCE");
					System.out.println("-------------------");
					
					res2 = s.BFI_Subset_Sum(temp, tar);
					//System.out.println("Brute force set: "+res2+"Sum:"+res2.sum);
					System.out.println("Total Iterations: "+ s.iterations);
					
					//if(res != null)
					//System.out.println(res.a+ " "+res.sum);
					avg_iterations2 = avg_iterations2 + s.iterations;
					s.iterations = 0;
				}
				avg_iterations = avg_iterations/10;
				avg_iterations2 = avg_iterations2/10;
				System.out.println("Avg Iterations (HS): "+ avg_iterations);
				System.out.println("Avg Iterations (Brute Force): "+ avg_iterations2);
				System.out.println();
				
				
				//System.out.println(i+"  "+n+"   "+temp);
			}
			
		}
		
		//res = s.HS_Subset_Sum(eval, 450);
		//ret = s.modified_BFI_Subset_Sum(eval);
		//for(Set i : ret) {
			
		//System.out.println("Sum:   "+i.sum+ "   Set:   "+i.a);
		
		
		//}
		
		
		
		
		

	}
	
	public int Sum(ArrayList<Integer> S){
		
		int sum = 0;
		
	
		for(Integer i : S) {
			sum = sum + i;
		}
		
		return sum;
	}
	
	public ArrayList<Integer> leftHalf(ArrayList<Integer> a){
		ArrayList<Integer> temp = new ArrayList<>();
		
		for(int i = 0; i < (a.size())/2;i++) {
			temp.add(a.get(i));
		}
		
		return temp;
	}
	
public ArrayList<Integer> rightHalf(ArrayList<Integer> a){
	ArrayList<Integer> temp = new ArrayList<>();
	for(int i = a.size()/2; i < (a.size());i++) {
		temp.add(a.get(i));
	}
	
	return temp;
	}


	
	@SuppressWarnings("deprecation")
	public Set HS_Subset_Sum(ArrayList<Integer> S, int k){
		
		
		if(Sum(S) == k) {
			Set return_set = new Set(k, S);
		
			return return_set;
		}
		
		ArrayList<Integer> left_half = new ArrayList<>();
		ArrayList<Integer> right_half = new ArrayList<>();
		
		left_half = leftHalf(S);
		right_half = rightHalf(S);
		
		ArrayList<Set> left_sets = modified_BFI_Subset_Sum(left_half);
		ArrayList<Set> right_sets = modified_BFI_Subset_Sum(right_half);
		
		for(Set s : left_sets) {
			if(k == s.sum) {
				return s;
			}
			iterations++;
		}
		
		for(Set s : right_sets) {
			if(k == s.sum) {
				return s;
			}
			iterations++;
		}
		/*for(Set s : left_sets) {
			System.out.println("Sum:  "+s.sum+"  "+s.a);
		}
		for(Set s : right_sets) {
			System.out.println("-Sum:  "+s.sum+"  "+s.a);
		}*/
		
		iterations = iterations + (int) (left_sets.size()*Math.log(left_sets.size()));
		iterations = iterations + (int) (right_sets.size()*Math.log(right_sets.size()));
		Collections.sort(left_sets, (o1, o2) -> (new Integer(o1.sum)).compareTo(new Integer(o2.sum)));
		Collections.sort(right_sets, (o1, o2) -> (new Integer(o1.sum)).compareTo(new Integer(o2.sum)));
		/*for(Set s : left_sets) {
			System.out.println("--Sum:  "+s.sum+"  "+s.a);
		}
		for(Set s : right_sets) {
			System.out.println("---Sum:  "+s.sum+"  "+s.a);
		}*/
		
		//System.out.println(left_half);
		//System.out.println(right_half);
		
		Pair subset_pair = PairSum(left_sets,right_sets,k);
		
		if(subset_pair==null) {
			System.out.println("No subsets found");
		}
		else {
			System.out.println("Subsets found..... with Target:"+k);
			System.out.println(subset_pair.x.a +" -- and -- "+ subset_pair.y.a);
		}
		
			
		
		return null;
	}
	
	public Pair PairSum(ArrayList<Set> a1,ArrayList<Set>  a2,int k) {
		
		int start = 0;
		int end = a2.size()-1;
		
		while((start < a1.size()) && end >=0) {
			
			int temp = a1.get(start).sum + a2.get(end).sum;
			
			if(temp==k) {
				iterations++;
				iterations++;
				return new Pair(a1.get(start),a2.get(end));
			}
			else if(temp < k)
			{
				start++;
				iterations++;
			}
			else {
				end--;
				iterations++;
			}
		}
		
		return null;
	}
	
	public ArrayList<Set> modified_BFI_Subset_Sum(ArrayList<Integer> S){
		
		ArrayList<Integer> empty_list = new ArrayList<>();
		Set empty_set = new Set(0,empty_list);
		//Create empty set
		ArrayList<Set> subsets = new ArrayList<>();
		subsets.add(empty_set);
		
		int n = S.size();
		ArrayList<Set> all_subsets = new ArrayList<>();
		for(int i = 0; i <n;i++) {
			ArrayList<Set> new_subsets = new ArrayList<>();
			
			for(Set old_u : subsets) {
				iterations++;
				//New set object with old_u and current S element
				ArrayList<Integer> new_elements = new ArrayList<>(old_u.a);
				new_elements.add(S.get(i));
				//System.out.println(new_elements);
				//System.out.println(old_u.a);
				Set new_u = new Set(old_u.sum + S.get(i), new_elements);
				//System.out.println("old_u:   "+ old_u.sum +" "+old_u.a);
				//System.out.println("new_u:   "+ new_u.sum +" "+new_u.a);
				//System.out.println(new_u);
				new_subsets.add(old_u);
				new_subsets.add(new_u);
				
				all_subsets.add(old_u);
				all_subsets.add(new_u);
			}
			subsets = new_subsets;
			
		}
		
		return all_subsets;
		
		
		
		
	}
	
	public Set BFI_Subset_Sum(ArrayList<Integer> S, int k){
		ArrayList<Integer> empty_list = new ArrayList<>();
		Set empty_set = new Set(0,empty_list);
		
		ArrayList<Set> subsets = new ArrayList<>();
		subsets.add(empty_set);
		
		int n = S.size();
		
		for(int i = 0; i <n;i++) {
			ArrayList<Set> new_subsets = new ArrayList<>();
			
			for(Set old_u : subsets) {
				//Set temp = new Set(old_u.sum,old_u.a);
				//int S_i = S.get(i);
				//temp.a.add(S_i);
				//temp.sum+=S_i;
				
				
				ArrayList<Integer> new_elements = new ArrayList<>(old_u.a);
				new_elements.add(S.get(i));
				Set new_u = new Set(old_u.sum + S.get(i), new_elements);
				//Set new_u = new Set(temp.sum,temp.a);
				//System.out.println(temp.sum);
				//System.out.println(new_u.sum);
				if(new_u.sum == k) {
					System.out.println("Subset found...");
					System.out.println(new_u.a);
					
					
					return new_u;
				}
				else {
					new_subsets.add(old_u);
					new_subsets.add(new_u);
					
				}
				
				subsets= new_subsets;
				iterations++;
			}
			
		}
		System.out.println("No subsets found");
		
		return null;
		
	}
	
	class Set{
		ArrayList<Integer> a = new ArrayList<>();
		int sum;
		
		public Set(int sum, ArrayList<Integer> a) {
			this.a = a;
			this.sum = sum;
		}
	}
	
	class Pair{
		Set x;
		Set y;
		
		public Pair(Set set, Set set2) {
			this.x = set;
			this.y = set2;
		}
	}

}

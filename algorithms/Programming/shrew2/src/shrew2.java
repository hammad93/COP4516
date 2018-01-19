import java.util.*;
import java.io.*;

public class shrew2 {
	public Scanner sc;
	public PrintStream ps;
	
	public void doit() throws Exception{
		sc = new Scanner( System.in );
		ps = System.out;
		
		//create three lists, each for the types of shrews
		LinkedList<boolean[]> male = new LinkedList<boolean[]>(), female = new LinkedList<boolean[]>(), child = new LinkedList<boolean[]>();
		
		String current = new String();
		int length = 0;
		boolean[] genes;
		
		while( !current.matches("X") ){
			do {				
				//parse each shrew into corresponding class
				current = sc.next();
				if ( current.matches("M") ){
					current = sc.next();
					genes = toBoolean(current);
					length = current.length();
					male.add(genes);
				}
				else if ( current.matches("F") ){
					current = sc.next();
					genes = toBoolean(current);
					length = current.length();
					female.add(genes);
				}
				else if ( current.matches("C") ){
					current = sc.next();
					genes = toBoolean(current);
					length = current.length();
					child.add(genes);
				}
			} while (!current.matches("X"));
			
			//do calculations
			mutations(male, female, child, length);
			
			//setup for next iteration
			male.clear();
			female.clear();
			child.clear();
			current = sc.next();
		}
	}
	
	public void mutations(LinkedList<boolean[]> male, LinkedList<boolean[]> female, LinkedList<boolean[]> child, int length){
		
		for ( int l = 0, lowest = Integer.MAX_VALUE; l < child.size(); l++){
			//for every male, check every female and update the lowest number
			for ( int i = 0; i < male.size(); i++ ){
				for ( int j = 0, misMatch = 0; j < female.size(); j++, misMatch = 0 ){
					//look to see how many of the genes do NOT match
					for( int k = 0; k < length; k++){
						if (!match(child.get(l)[k], male.get(i)[k], female.get(j)[k])) misMatch++;
					}
					if ( misMatch < lowest ) lowest = misMatch;
				}
			}
			System.out.println("Child " + (l + 1) + " has a minimum of " + lowest +" mutations.");
		}
	}
	
	public boolean match(boolean child, boolean male, boolean female){
		//all cases where child has dominant
		if (child && !male && !female) return false;
		else if (child) return true;
		
		//all cases where child does not have dominant
		if (!child && !male && !female) return true; 
		else if (!child) return false;
		
		return true;
	}
	
	public boolean[] toBoolean(String s){
		boolean[] ans = new boolean[s.length()];
		
		for ( int i = 0; i < s.length(); i++){
			if ( s.charAt(i) == '0' ) ans[i] = false;
			else ans[i] = true;
		}
		
		return ans;
	}
	public static void main(String[] args) throws Exception {
		new shrew2().doit();
	}

}

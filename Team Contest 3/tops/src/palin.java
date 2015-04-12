import java.util.*;

public class palin {
	
	static int START;//start counter
	static int END;//end counter
	static int LENGTH;//size of double array
	/*
	 * this function checks for a palindrome
	 */
	public static boolean checkPalindrome(String s){
		
		for(int i=0;i<(s.length()/2);i++ ){
			
			if(s.charAt(i)!=s.charAt(s.length()-1-i)){
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * this function will complete a shift operation
	 */
	
	public static void shift(StringBuilder s){
		
		
		
	}
	
	public static void main(String[] args){
		
		
		//System.out.println(checkPalindrome("abbbbbbcdbbbbba"));
		Scanner d = new Scanner(System.in);
		StringBuilder s = new StringBuilder(d.next());
		START=0;
		END=s.length()-1;
		LENGTH=s.length();
		//simulating the shift operation
		
		/*
		 * CHECK IF FIRST IS A PALINDROME 
		 */
		boolean foundPALINDROME=false;//make sure that multiple cyclic palindromes dont trigger multiple prints
		if(checkPalindrome(s.toString())){
			System.out.println("yes");
		}
		else{
		for(int i=0;i<LENGTH;i++){
		
			s.append(s.charAt(START));
			START++;
			END++;
			
			//\System.out.println(s.substring(START, END+1)+" "+checkPalindrome(s.substring(START, END+1)));
			if(checkPalindrome(s.substring(START, END+1))){
				foundPALINDROME=true;;
			}
		}
		
		if(foundPALINDROME){
			System.out.println("yes");
			
		}
		else{
			System.out.println("no");
			
		}
		}
		
		
		}
		
	

}

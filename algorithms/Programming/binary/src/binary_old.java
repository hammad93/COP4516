import java.util.*;
import java.io.*;

public class binary_old {
	public Scanner sc;
    public PrintStream ps;
            
    public void doit() throws Exception
    {
    	sc = new Scanner( System.in );
        ps = System.out;
        
        //create two integer arrays for the two addends
        char[] add1 = new char[81];
        char[] add2 = new char[81];
        int[] ans = new int[81];
        
        //set number of integers to add
        int numAdd = sc.nextInt();
        //count var to keep track
        int count = 1;
        
        for (int i = 0; i < numAdd; i++, count++){
        	//parse in new ints
        	add1 = sc.next().toCharArray();
        	add2 = sc.next().toCharArray();
        	
        	//begin addition
        	int upper = (add1.length > add2.length ? add1.length : add2.length);
        	int lower = (add1.length < add2.length ? add1.length : add2.length);
        	int bit = 0, carry = 0;
        	for (int j = 0; j < upper; j++){
        		//if statements check if we run out of numbers to add
        		if (lower > j){ 
 	        		bit = Character.getNumericValue(add1[add1.length-j-1])
	        			+ Character.getNumericValue(add2[add2.length-j-1]) + carry;
 	        		if (carry > 0) carry = 0;
	        		if (bit > 1){
	        			carry = 1;
	        			bit = bit % 2;
	        		}
	        		//store bit into answer
	        		ans[80 - j] = bit;
        		}
        		
        		else{
        			bit = Character.getNumericValue((add1.length == upper ?
        											add1[add1.length-j-1] :
        											add2[add2.length-j-1])) + carry;
        			if (carry > 0) carry = 0;
        			if (bit > 1){
        				carry = 1;
        				bit = bit % 2;
        			}
        			//store bit into answer
        			ans[80 - j] = bit;
        		}
        		
        		//if we need to still have a carry and we're at the end
        		if (carry > 0 && j == upper -1 ) ans[80 - j - 1] = carry;
        	}
        	
        	//print out results
        	ps.printf("%d ", count);
        	int index = 0;
        	while (ans[index] == 0) index++; //find the index where ans starts
        	for (int j= index; j < ans.length; j++) ps.print(ans[j]);
        	ps.println();
        	
        	//reset values
        	ans = new int[81];
        	add1 = new char[81];
        	add2 = new char[81];
        }
    }
    
    public static void main(String[] args) throws Exception{
    	new binary_old().doit();
    }
}

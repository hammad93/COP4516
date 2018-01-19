import java.io.*;
import java.util.*;


public class binary_old2 {
	public Scanner sc;
	public PrintStream ps;
	
	public void doit() throws Exception{
		sc = new Scanner( new File("input") );
		ps = System.out;
		
		//read in the number of addition problems
		int numAdd = sc.nextInt();
		
		//perform and output calculations
		for (int i = 0; i < numAdd; i++){
			int[] add1 = toIntArr(sc.next()), add2 = toIntArr(sc.next());
			int[] sum = binaryAdd(add1, add2);
			//find out the array where the valid digits of the sum start
			int index;
			for (index = 0; index < sum.length; index++){
				if (sum[index] != 0) break;
			}
			
			//print out ans
			ps.print((i + 1) + " ");
			while ( index < sum.length ){
				ps.print(sum[index]);
				index++;
			}
			ps.println();
		}
	}
	
	public int[] binaryAdd(int[] add1, int[] add2){
		int length = (add1.length > add2.length ? add1.length : add2.length);
		int[] ans = new int[81];
		
		for (int i = 0, carry = 0, result = 0; i < length; i++){
			//both addends can participate in addition
			if ( i < add1.length && i < add2.length){
				result = add1[add1.length - i - 1] + add2[add2.length - i - 1] + carry;
			}
			//only add1 can participate
			else if ( i >= add2.length ){
				result = add1[add1.length - i - 1] + carry;
			}
			//only add2 can participate
			else if ( i >= add1.length){
				result = add1[add1.length - i - 1] + carry;
			}
			
			//record the result
			//if there is carry over into a new binary power
			if ( result > 1 && (i + 1) >= length ){
				carry = 1;
				result = result % 2;
				ans[ans.length - i - 1] = result;
				ans[ans.length - i - 2] = carry;
			}
			//no special case
			else {
				carry = ( result > 1 ? 1 : 0 );
				ans[ans.length - i - 1] = (result % 2);
			}
		}
		
		return ans;
	}
	
	public int[] toIntArr(String s){
		char[] temp = s.toCharArray();
		int[] result = new int[temp.length];
		
		for (int i = 0; i < temp.length; i++){
			result[i] = Character.getNumericValue(temp[i]);
		}
		
		return result;
	}

	public static void main(String[] args) throws Exception {
		new binary_old2().doit();
	}

}

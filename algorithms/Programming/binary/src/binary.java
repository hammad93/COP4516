import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class binary {
	public Scanner sc;
	public PrintStream ps;
	
	public void doit() throws Exception {
		sc = new Scanner( System.in );
		ps = System.out;
		
		//read in the number of addition problems
		int numAdd = sc.nextInt();
		
		//perform and output calculations
		for (int i = 0; i < numAdd; i++){
			ps.println((i + 1) + " " + binaryAdder(sc.next(), sc.next()));
		}
	}
	
	public String binaryAdder(String a, String b){
        BigInteger result = new BigInteger("0");
        
        result = result.add(new BigInteger(a, 2));
        result = result.add(new BigInteger(b, 2));

        return result.toString(2);
    }
	
	public static void main(String[] args) throws Exception {
		new binary().doit();
	}

}

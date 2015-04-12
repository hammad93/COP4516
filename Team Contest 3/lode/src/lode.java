import java.io.File;
import java.util.Scanner;


public class lode {
	static Scanner sc;
	public static void main(String[] args) throws Exception {
		sc = new Scanner(System.in);
		int cases = sc.nextInt();
		for (int i = 0; i < cases; i++){
			//find out log
			int weight = sc.nextInt();
			int log = (int)(Math.log(weight)/Math.log(3));
			int[] freq = new int[log + 1];
			int dimension = log;
			while (weight != 0){
				//if the dimension weight fits evenly, do it
				int capacity = (int) Math.pow(3, dimension);
				if ( capacity <= weight || dimension == 0){
					freq[dimension]++;
					weight -= capacity;
				}
				else dimension--;
			}
			
			//print out result
			for (int j = freq.length - 1; j >= 0; j--){
				System.out.print(freq[j] + " ");
			}
			System.out.println();
		}
		

	}

}

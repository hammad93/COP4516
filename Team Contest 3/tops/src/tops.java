

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class tops {
	static boolean decimal;
	static Scanner sc;
	public static void main(String[] args) throws Exception{
		sc = new Scanner(System.in);
		
		int cases = sc.nextInt();
		for (int i = 0; i < cases; i++){
			int people = sc.nextInt();
			//read in the people into a list
			int[] scores = new int[people];
			//read the scores into the array
			for (int j = 0; j < people; j++){
				//disregard the name
				sc.next();
				//read in the score
				scores[j] = sc.nextInt();
			}//all scores are read by this point
			//do calculations
			float med = median(scores);
			if (decimal){
				System.out.printf("Case #%d: %d %d %.1f\n", 
							(i + 1), max(scores), min(scores), med);
			}
			else{
				System.out.printf("Case #%d: %d %d %d\n", 
						(i + 1), max(scores), min(scores), (int)med);
			}
		}
	}
	public static int max(int[] scores){
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < scores.length; i++){
			if (scores[i] > max){
				//set new max
				max = scores[i];
			}
		}
		
		return max;
	}
	public static int min(int[] scores){
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < scores.length; i++){
			if (scores[i] < min){
				//set new min
				min = scores[i];
			}
		}
		
		return min;
	}
	
	public static float median(int[] scores){
		decimal = false;
		//sort our array using insertion sort
		Arrays.sort(scores);
		//find the median array index
		int index = scores.length / 2;
		
		//return the average of the two if its even
		if (scores.length % 2 == 0){
			int a = scores[index], b = scores[index - 1];
			float result = ((float)a + (float)b);
			if (result % 2 == 0) return result / 2;
			else{
				decimal = true;
				return result / 2;
			}
		}
		
		//return the index if it's odd
		if (scores.length % 2 == 1){
			return (float)scores[index];
		}
		
		else return 0;
	}
}

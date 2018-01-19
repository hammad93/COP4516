import java.util.*;
import java.io.*;

public class range {
	public Scanner sc;
	public PrintStream ps;
	
	public void doit() throws Exception {
		sc = new Scanner( System.in );
		ps = System.out;
		
		double totalFuel = 0, totalOdom = 0, average = 0, range = 0; //metrics
		double temp = 0, odom = -2, prevOdom = 0, fuel = 0, prevFuel = 0;
		while ( odom != -1.0 ){
			//case where we output for current range
			temp = sc.nextDouble();
			if (temp == 0.0){
				average = totalOdom / totalFuel;
				range = average * fuel;
				ps.println((int)Math.round(range));
				
				//reset and prepare for next range calculations
				fuel = sc.nextDouble();
				totalOdom = 0;
				totalFuel = 0;
				continue;
			}
			
			//odometer reading
			prevOdom = odom;
			odom = temp;
			
			//fuel reading
			prevFuel = fuel;
			fuel = sc.nextDouble();
			//we discard cases where the fuel increases
			if (fuel > prevFuel) continue;
			
			totalOdom += (odom - prevOdom);
			totalFuel += (prevFuel - fuel);
			
		}
	}

	public static void main(String[] args) throws Exception {
		new range().doit();

	}

	
}

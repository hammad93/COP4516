import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class chrl{
	int weight, capacity, total;
	public chrl(int w, int c){
		weight = w;
		capacity = c;
		total = c - w;
	}
}

public class cheer {
	static Scanner sc;
	public static void main(String[] args) throws Exception{
		sc = new Scanner(System.in);
		int cases = sc.nextInt();
		for (int i = 0; i < cases; i++){
			int numCheers = sc.nextInt();
			//create list of cheerleaders
			ArrayList<chrl> squad = new ArrayList<chrl>();
			for (int j = 0; j < numCheers; j++){
				chrl cheerleader = new chrl(sc.nextInt(), sc.nextInt());
				if (cheerleader.total >= 0) squad.add(cheerleader);
			}
			//go ahead and perform calculations
			ArrayList<chrl> sorted = sort(squad);//first sort by total weight one can carry
			ArrayList<chrl> stack = new ArrayList<chrl>();
			ArrayList<chrl> unused = new ArrayList<chrl>();
			//calculate highest squad size
			chrl current = sorted.remove(sorted.size() - 1);
			stack.add(current);
			int stackCap = current.total;
			while (!sorted.isEmpty()){
				//add on next possible cheerleader
				while (!sorted.isEmpty() && stackCap < sorted.get(0).weight) unused.add(sorted.remove(0));
				if (sorted.isEmpty())
					break;
				//set new stackCap
				current = sorted.remove(sorted.size() - 1);
				stack.add(current);
				stackCap = (stackCap - current.weight < current.total ? stackCap - current.weight : current.total);
			}
			//optimize our potential stack
			//remove inefficient cheerleaders from top to bottom
			for (int j = stack.size() - 1; !unused.isEmpty() && j > 0; j--){
				if (stack.get(j).total == stack.get(j - 1).total){
					//remove heavier one of the same aggregated total
					//only if there is another cheerleader to replace
					chrl inefficient = (stack.get(j).weight > stack.get(j - 1).weight ? stack.get(j) : stack.get(j - 1));
					//check to see if next unused cheerleader can be added
					if (canAdd(unused.get(0), inefficient, stack)){
						stack.remove(inefficient);
						stack.add(unused.remove(unused.size() - 1));
					}
					//see if the unused cheerleaders can be added in
					for (int k = 0; k < unused.size(); k++){
						if (canAdd(unused.get(k), null, stack)){
							stack.add(unused.remove(unused.size() - 1));
						}
					}
				}
			}
			System.out.println(stack.size());
		}
	}
	public static boolean canAdd(chrl potential, chrl replace, ArrayList<chrl> squad){
		ArrayList<chrl> stack = (ArrayList<chrl>) squad.clone();
		if (replace != null) stack.remove(replace);
		//calculate stack capacity
		chrl current = stack.get(0);
		int stackCap = current.total;
		for (int i = 1; i < stack.size(); i++){
			current = stack.get(i);
			stackCap = (stackCap - current.weight < current.total ? stackCap - current.weight : current.total);
		}
		return (potential.total > stackCap ? false : true);
	}
	public static int totalWeight(ArrayList<chrl> squad){
		int weight = 0;
		for (int i = 0; i < squad.size(); i++){
			weight += squad.get(i).weight;
		}
		return weight;
	}
	public static ArrayList<chrl> sort(ArrayList<chrl> squad){
		//sort by total weight a cheerleader can carry
		ArrayList<chrl> sorted = new ArrayList<chrl>();
		for (int i = 0; i < squad.size(); i++){
			sorted.add(0,  squad.get(i));
			for (int j = 0; j < sorted.size() - 1; j++){
				if (sorted.get(j).total < sorted.get(j + 1).total) break;
				else if (sorted.get(j).total == sorted.get(j + 1).total && sorted.get(j).capacity < sorted.get(j + 1).capacity) break;
				else{
					//swap
					chrl temp = sorted.get(j);
					sorted.remove(j);
					if (sorted.size() >= j + 1){
						sorted.add(j + 1, temp);
					}
					else{
						sorted.add(temp);
					}
				}
			}
		}
		
		return sorted;
	}

}

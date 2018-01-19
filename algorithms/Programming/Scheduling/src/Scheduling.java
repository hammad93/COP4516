/*HAMMAD USMANI
 * 
 * COP4600 OPERATING SYSTEMS*/

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Scheduling{
    /*CLASSES:
        1) process  		-contains the information about a process
        2) pcb      		-the process control block with the proper functionality*/
    static class process implements Comparable<process>{
        public String id;
        public int arrival;
        public int burst;
        
        int wait,turnaround = 0;
        
        public process(String _id, int _arrival, int _burst){
            this.id = _id;
            this.arrival = _arrival;
            this.burst = _burst;
        }

		@Override
		public int compareTo(process arg0) {
			return (this.arrival - arg0.arrival);
		}
    }
    
    static class pcb{
        int processCnt;
        int runfor;
        String use;
        int quantum;
        ArrayList<process> PCB = new ArrayList<process>(); //For indexing processes
        
        public pcb(int _ps, int _rf, String _use, int _q){
            this.processCnt = _ps;
            this.runfor = _rf;
            this.use = _use;
            this.quantum = _q;
        }
        
        public void add(process p){
            this.PCB.add(p);
        }
        public process get(int index){
            return this.PCB.get(index);
        }        
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("processes.in"));
        
        /*BEGIN READING IN PROCESSES
            -This will read line by line
            Structure:
            <ASPECT> <INT> <COMMENT>                            -process count is always the first int
            <ASPECT> <INT> <COMMENT>                            -runfor is always the first int
            <ASPECT> <STRING> <COMMENT>                         -use is always the second token
            <ASPECT> <INT> <COMMENT>                            -quantum is always the first int
            <PROCESS NAME> <INT> <ARRIVAL> <INT> <BURST> <INT>  -for each process up to processcount times,
            .                                                    processname is always the first int,
            . processcount times                                 the process arrival is always the second int,
            .                                                    the process burst is always the third int.
            <PROCESS NAME> <INT> <ARRIVAL> <INT> <BURST> <INT>
            <end>
            NOTE: to read, we will just use the nextInt() function and nextLine() for ignoring comments. this
                  assumes that no additional values need to be initialized on that line*/
        
        
        sc.next();//ignore first token
        int processCnt = sc.nextInt();
        sc.nextLine();//ignore comments
        sc.next();//ignore first token
        int runfor = sc.nextInt();
        sc.nextLine();//ignore comments
        sc.next();//ignore first token
        String use = sc.next();
        sc.nextLine();//ignore comments
        int quantum = -1;
        if (use.contains("rr")){
	        sc.next();//ignore first token
	        quantum = sc.nextInt();
        }
        sc.nextLine();//ignore comments
        pcb PCB = new pcb(processCnt, runfor, use, quantum);
        /*COLLECT PROCESS INFORMATION:
        loop processCnt times {
            -create process by reading from scanner
            -add process to process control block
        }*/
        for (int i = 0; i < processCnt; i++){
            /*id => process name in sequence
            arrival => 2nd int in current scanner line
            burst => 3rd int in the current scanner line
            */
        	sc.next();//ignore first token
        	sc.next();//ignore second token
            String id = sc.next();//third token is always process ID
            
            /*GET ARRIVAL AND BURST*/
            sc.next();//ignore arrival token
            int arrival = sc.nextInt();
            sc.next();//ignore burst token
            int burst = sc.nextInt();
            
            /* ADD PROCESS TO CURRENT PCB*/
            process p = new process(id, arrival, burst);
            PCB.add(p);
        }
        /*WRITE OUTPUT TO A FILE
            name: processes.out
            structure:
            <processCnt><"processes">
            <"Using"><NAME OF SCHEDULING ALGORITHM>
            <"Quantum"><quantum>*/
        
        PrintWriter writer = new PrintWriter("processes.out");
        writer.println(processCnt + " processes");
        switch(use){
            case "rr":
                writer.println("Using Round Robin");
                break;
            case "fcfs":
                writer.println("Using First Come First Serve");
                break;
            case "sjf":
                writer.println("Using Shortest Job First (Pre)");
                break;
            default:
                break;
        }
        if (quantum != -1) writer.println("Quantum " + quantum);
        writer.println();//for formatting
        /*OPERATE BASED ON THE SCHEDULING ALGORITHM
            -determined by the variable 'use'
            rr() => round robin scheduling alhorithm
            fcfs() => first come first serve scheduling algorithm
            sjf() => shortest job first scheduling algorithm
            NOTE: The processes.out file will be written with information about
            the scheduling algorithm at the time of the operation*/
        switch(use){
            case "rr":
                rr(writer, PCB);
                break;
            case "fcfs":
                fcfs(writer, PCB);
                break;
            case "sjf":
                sjf(writer, PCB);
                break;
            default:
                break;
        }
        /*FINISH UP AND FINALIZE*/
        writer.close();
        sc.close();
    }
    /*ROUND ROBIN SCHEDULING ALGORITHM
     * 	1) Order the process in the process control block by arrival time,
     * 	ascending
     * 	2) Construct an event notifier that changes though the variable time by
     * 	units of time
     * 	3) Operate on the PCB and traverse through time indefinitely until all 
     * 	processes are completed
     * 		a. For the Round Robin Algorithm, we do the following:
     * 			1. As we traverse linearly, ascending, by units of 1 through time
     * 			we allow the next process in the queue to operate for quantum units of time
     * 			2. Quantum units are defined in processes.in, our input file
     * 			3. We continue queuing and dequeuing until either:
     * 				a. All processes are completed
     * 				b. We have exhausted our time units 
     * 	4) As we are conducting operations, we output the results of the event
     * 	notifier*/
    public static void rr(PrintWriter writer, pcb PCB){
    	/*EVENT NOTIFIER:
    	 * NOTE: Each scheduling algorithm will have its own event notifier
    	 * METHOD: The Round Robin Event Notifier will operate by the following:
    	 * 		1) Construct a queue by using the PCB list of processes by the following:
    	 * 			a. enqueue by list number
    	 * 		2) Construct a loop that terminates on final time unit, increments by one
    	 * 		time unit and utilizes the time quantum as the process timeout
    	 * 		3) As we loop, we do the following:
    	 * 			a. check to see if the next process has arrived, if so notify
    	 * 			b. if none selected, select the next process in queue
    	 * 				1. notify the selection to output
    	 * 			c. decrement the current quantum and the burst of the current process by one
    	 * 			d. if quantum is at zero after decrement,
    	 * 				1. select next process
    	 * 				2. reset quantum to process timeout value */
    	/*SORT BY ARRIVAL TIME*/
    	Collections.sort(PCB.PCB);
    	/*BEGIN CONSTRUCTING QUEUE*/
    	Queue<process> queue = new LinkedList<process>();
    	LinkedList<process> done = new LinkedList<process>();
    	/*START OPERATIONS BY USING AN EVENT NOTIFIER*/
    	int quantum = PCB.quantum;
    	process current = null;
    	for (int i = 0; i < PCB.runfor; i++){
    		//check if the current process has any processing time remaining
    		if (current != null && current.burst == 0){
    			writer.println("Time " + i + ": " + current.id + " finished");
    			done.add(current);
    			current = null;
    		}
    		
    		//See if any processes have arrived and notify
    		while (!PCB.PCB.isEmpty() && PCB.PCB.get(0).arrival == i){
    			//output first
    			writer.println("Time " + i + ": " + PCB.PCB.get(0).id + " arrived");
    			//add to queue
    			queue.add(PCB.PCB.remove(0));
    		}
    		//select the next process if none has been selected
    		if (!queue.isEmpty() && current == null){
    			
    			current = queue.poll();
    			//notify to output
        		if (current != null && current.burst == 0){
        			writer.println("Time " + i + ": " + current.id + " finished");
        			done.add(current);
        			current = null;
        		}
        		else{
        			writer.println("Time " + i + ": " + current.id + " selected (burst " + current.burst + ")");
        		}
    		}
			
    		//Simulate operation by decrement both quantum and current process burst time
    		quantum--;
    		if (current != null) {
    			/*GO THROUGH THE QUEUE AND TELL THEM THEY HAD TO WAIT*/
    			Iterator<process> _itr = queue.iterator();
    			for (int j = 0; j < queue.size(); j++) _itr.next().wait++;
    			
    			current.burst--;
    		}
    		
    		//if we have exhausted the quantum, do the proper procedure
    		if (quantum == 0){
    			//add the current process back to the queue and make current null if it still needs time
    			if (current != null) {
    	    		queue.add(current);
    			}
    			
    			current = null;
    			quantum = PCB.quantum; //reset quantum
    		}
    		
    		//notify idle if all requirements are met
    		if (PCB.PCB.isEmpty() && queue.isEmpty()){
    			writer.println("Time " + i + ": " + "IDLE");
    		}
    		
    		//add the turnaround and wait time to all elements in the queue
    		Iterator<process> itr = queue.iterator();
    		while (itr.hasNext()){
    			process temp = itr.next();
    			temp.turnaround++;
    		}
    		if (current != null){	
    			current.turnaround++;
    		}
    	}
    	if (!queue.isEmpty()){
    		writer.println("Did not finish by time " + PCB.runfor);
    	}
    	else{
    		writer.println("Finished at time " + PCB.runfor);
    	}
    	writer.println();
    	//print out wait and turnaround
    	for (int i = 0; i < done.size(); i++){
    		writer.println(done.get(i).id + " wait " + done.get(i).wait + " turnaround " + done.get(i).turnaround);
    	}
    }
    /*FIRST COME FIRST SERVE SCHEDULING ALGORITHM
        1) Order the processes in the process control block by arrival time,
        ascending
        2) Construct an event notifier that changes through the variable time by
        units of Time
        3) Operate on the Process Control Block chain and traverse through time
        indefinitely until all proccesses are completed
        4) As we are conducting operations, we output the results of the event
        notifier*/
    public static void fcfs(PrintWriter writer, pcb PCB){
        /*BEGIN ORDERING THE PCB
            by: Process -> arrival
            using: sort ascending, low to high*/
    	Collections.sort(PCB.PCB);
    	/*BEGIN CONSTRUCTING QUEUE
    	 * add all the process in the PCB to the queue*/
    	Queue<process> queue = new LinkedList<process>();
    	for (int i = 0; i < PCB.PCB.size(); i++){
    		queue.add(PCB.PCB.get(i));
    	}

    	/*METHOD:
    	 * Allow current process to execute and notify, unless the following flags:
    	 * 1. current is null, meaning no process is being operated on
    	 * 2. get the next element in the queue
    	 * 3. end of time unit
    	 * */
    	process current = null;
    	for (int i = 0; i <= PCB.runfor; i++){
    		//check if any processes have arrived
    		for (int j = 0; j < PCB.PCB.size(); j++){
    			if (PCB.PCB.get(j).arrival == i){
    				writer.println("Time " + i + ": " + PCB.PCB.get(j).id + " arrived");
    			}
    		}
    		if (current == null){
    			//set it to the next thing in the queue
    			if (queue.peek().arrival >= i) current = queue.poll();
    			writer.println("Time " + i + ": " + current.id + " selected (burst " + current.burst + ")");
    		}
    		//if we're out of burst time
    		if (current.burst == 0){
    			writer.println("Time " + i + ": " + current.id + " finished");
    			if (queue.isEmpty()) break;
    			current = queue.poll();
    			writer.println("Time " + i + ": " + current.id + " selected (burst " + current.burst + ")");
    		}
    		current.burst--;
    		current.turnaround++;
    		
    		//go through the queue and add to wait time
    		Iterator<process> itr = queue.iterator();
    		while(itr.hasNext()) {
    			process temp = itr.next();
    			if (temp.arrival <= i){
        			temp.turnaround++;
        			temp.wait++;
    			}
    		}
    	}
    	if (!queue.isEmpty()){
    		writer.println("Did not finish by time " + PCB.runfor);
    	}
    	else{
    		writer.println("Finished at time " + PCB.runfor);
    	}
    	writer.println();
    	for (int i = 0; i < PCB.PCB.size(); i++){
    		writer.println(PCB.PCB.get(i).id + " wait " + PCB.PCB.get(i).wait + " turnaround " + PCB.PCB.get(i).turnaround);
    	}
    }
    /*SHORTEST JOB FIRST SCHEDULING ALGORITHM
     * needs to be premptive*/
    public static void sjf(PrintWriter writer, pcb PCB){
    	/*SORT BASED ON BURST TIME*/
    	Collections.sort(PCB.PCB, new Comparator<process>(){
    		@Override
    		public int compare(process a, process b){
    			return (a.arrival - b.arrival);
    		}
    	});
    	ArrayList<process> queue = new ArrayList<process>();
    	for (int i = 0; i < PCB.PCB.size(); i++){
    		queue.add(PCB.PCB.get(i));
    	}
    	/*METHOD:
    	 * Allow current process to execute and notify, unless the following flags:
    	 * 1. current is null, meaning no process is being operated on
    	 * 2. get the next element in the queue
    	 * 3. end of time unit
    	 * */
    	process current = null;
    	for (int i = 0; i <= PCB.runfor; i++){
    		//check if any processes have arrived
    		for (int j = 0; j < PCB.PCB.size(); j++){
    			if (PCB.PCB.get(j).arrival == i){
    				writer.println("Time " + i + ": " + PCB.PCB.get(j).id + " arrived");
        			/*PREMPTIVE
        			 * everytime a new process arrives we have to:
        			 * 1. put the current back into queue
        			 * 2. sort the queue by burst time
        			 * 3. top of queue is next*/
        			if (current != null && !queue.isEmpty()){
        				queue.sort(new Comparator<process>(){
        					@Override
        					public int compare(process a, process b){
        						return (a.burst - b.burst);
        					}
        				});
        				if (queue.get(0).burst < current.burst){
        					queue.add(current);
        					current = null;
        				}
        			}
    			}
    			
    		}
     		if (current == null && !queue.isEmpty()){
    			//set it to the next thing in the queue
    			if (queue.get(0).arrival <= i){
    				current = queue.get(0);
    				queue.remove(0);
        			writer.println("Time " + i + ": " + current.id + " selected (burst " + current.burst + ")");

    			}
    		}
    		//if we're out of burst time
    		if (current != null && current.burst == 0){
    			writer.println("Time " + i + ": " + current.id + " finished");
    			if (!queue.isEmpty()){
    				current = queue.get(0);
        			queue.remove(0);
        			writer.println("Time " + i + ": " + current.id + " selected (burst " + current.burst + ")");
    			}
       		}

    		if (current != null && current.burst == 0 && queue.isEmpty()){
    			writer.println("Time " + i + ": IDLE");
    		}
    		else if (current == null && i != PCB.runfor){
    			writer.println("Time " + i + ": IDLE");
    		}
    		
    		if (current != null){
    			if (current.burst == 0){
    				current = null;
    			}
    			else{
            		current.burst--;
            		current.turnaround++;
    			}
    		}
    		
    		
    		//go through the queue and add to wait time
    		Iterator<process> itr = queue.iterator();
    		while(itr.hasNext()) {
    			process temp = itr.next();
    			if (temp.arrival <= i){
        			temp.turnaround++;
        			temp.wait++;
    			}
    		}
    	}

    	if (!queue.isEmpty()){
    		writer.println("Did not finish by time " + PCB.runfor);
    	}
    	else{
    		writer.println("Finished at time " + PCB.runfor);
    	}
    	writer.println();
    	Collections.sort(PCB.PCB, new Comparator<process>(){
    		@Override
    		public int compare(process a, process b){
    			return Integer.compare(Integer.valueOf(a.id.charAt(1)), Integer.valueOf(b.id.charAt(1)));
    		}
    	});
    	for (int i = 0; i < PCB.PCB.size(); i++){
    		writer.println(PCB.PCB.get(i).id + " wait " + PCB.PCB.get(i).wait + " turnaround " + PCB.PCB.get(i).turnaround);
    	}
        
    }
}
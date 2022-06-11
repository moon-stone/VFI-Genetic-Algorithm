package makespanTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
	public int population_size, stoping_cond, pair1, pair2;
	public double mutation_rate, crossover_rate;
	public int no_of_subtasks,  no_of_machines, min_fv = 999999999, counter;
	public Scanner in_,  inheft_;
	public PrintStream out_;
	public int[] basic_ss, subtask_to_processor, processor_to_island;
	public int[][] est, rel, island_to_processor, predecessor_mat;
	public double[][] voltage_pair1, voltage_pair2, slot_voltage;
	public double[] busy_energy, idle_energy;
	public int[] degree, start_time, end_time, processor_avail_time, processor_slots, processor_last_task_end_time;
	LinkedList<Integer>[] available_slot;
	public Main(String file_name) throws IOException{
		in_ = new Scanner(new FileInputStream(file_name));
		
		///------------------taking input from file--------------------
		
		//finding no of processor
		while(true){
			if(in_.next().compareTo("No_of_Processor") == 0){
				no_of_machines = in_.nextInt();
				break;
			}
		}
		System.out.println("no. of machines " +no_of_machines);
		
		//finding no of subtasks
		while(true){
			if(in_.next().compareTo("t_nst") == 0){
				no_of_subtasks = in_.nextInt();
				break;
			}
		}
		System.out.println("no. of subtask " +no_of_subtasks);
		
		
		est = new int[no_of_subtasks][no_of_machines];
		rel = new int[no_of_subtasks][no_of_subtasks];
		island_to_processor = new int[2][no_of_machines];
		predecessor_mat = new int [no_of_subtasks][no_of_subtasks];
		start_time = new int[no_of_subtasks];
		end_time = new int[no_of_subtasks];
		processor_avail_time = new int[no_of_machines];
		subtask_to_processor = new int[no_of_subtasks];
		processor_to_island = new int[no_of_machines];
		processor_last_task_end_time = new int[no_of_machines];
		busy_energy = new double[no_of_machines];
		busy_energy = new double[no_of_machines];
		idle_energy = new double[no_of_machines];
		processor_slots = new int[no_of_machines];
		slot_voltage = new double[2][500000];
		available_slot = new LinkedList[no_of_machines];
		for (int i=0; i < no_of_machines; i++) {
		    available_slot[i] = new LinkedList<Integer>();
		    processor_slots[i] = 1;
		    available_slot[i].add(0);
		    available_slot[i].add(20);
		}
	//	for(int i = 0; i < no_of_machines; i++) {
	//		System.out.print(available_slot[i].get(0));
	//	}
		
		//estimated cost matrix
		for (int i = 0; i < no_of_subtasks; i++) {
			in_.next();
			for (int j = 0; j < no_of_machines; j++) {
				est[i][j] = in_.nextInt();
				System.out.print(est[i][j] + " ");
			}
			System.out.println();
		}
		int x, y, w;
		while (true) {
			if (in_.next().compareTo("task_end") == 0) {
				in_.nextLine();
				break;
			}
			x = in_.nextInt();
			y = in_.nextInt();
			w = in_.nextInt();
			rel[x][y] = w;
		}
		
		//communication cost matrix
		System.out.println("Communication cost matrix" + " ");
		for (int i = 0; i < no_of_subtasks; i++) {
			for (int j = 0; j < no_of_subtasks; j++) {
				System.out.print(rel[i][j] + " ");
				if(rel[i][j] != 0) {
					predecessor_mat[j][i] = 1;
				}
			}
			System.out.println();
		}
		
		//finding number of voltage pairs in pair1 for island0

		while(true){
			if(in_.next().compareTo("Pair1") == 0){
				pair1 = in_.nextInt();
				break;
			}
		}
		System.out.println("Volatage pairs for Island0 are "+ pair1);
		voltage_pair1 = new double[pair1][3];
		for(int i = 0; i < pair1; i++) {
			in_.next();
			
			voltage_pair1[i][0] = in_.nextDouble();
			voltage_pair1[i][1] = in_.nextDouble();
			voltage_pair1[i][2] = in_.nextDouble();
			System.out.print(voltage_pair1[i][0] + " ");
			System.out.print(voltage_pair1[i][1] + " ");
			System.out.print(voltage_pair1[i][2] + " ");
			System.out.println();		
		}
		
		//finding number of voltage pairs in pair2 for island1
		while(true){
			if(in_.next().compareTo("Pair2") == 0){
				pair2 = in_.nextInt();
				break;
			}
		}
		voltage_pair2 = new double[pair2][3];
		System.out.println("Voltage pairs for island1 are "+ pair2);
		for(int i = 0; i < pair2; i++) {
			in_.next();
			voltage_pair2[i][0] = in_.nextDouble();
			voltage_pair2[i][1] = in_.nextDouble();
			voltage_pair2[i][2] = in_.nextDouble();
			System.out.print(voltage_pair2[i][0] + " ");
			System.out.print(voltage_pair2[i][1] + " ");
			System.out.print(voltage_pair2[i][2] + " ");
			System.out.println();		
		}
		
		
	//	System.out.println(".....................................................................");
	    
		//processor to island(we are making only two island) mapping
	//	Map<Integer, Integer> processor_to_island = new HashMap<Integer, Integer>();
		for(int i = 0; i < no_of_machines; i++) {
			processor_to_island[i] = i%2;
			island_to_processor[i%2][i] = 1;
		}
		
		System.out.println("Machine to island mapping");
		
		for(int i = 0; i < no_of_machines; i++) {
			System.out.println("processor " + i + " mapped " + 
		    processor_to_island[i] + " island");
		}
		
		System.out.println("Island to processor mapping");
		for(int i = 0; i < 2; i++) {
			System.out.print("Island " + i + " have these machines = ");
			for(int j = 0; j < no_of_machines; j++) {
				if(island_to_processor[i][j] == 1) {
					System.out.print(j + " ");
				}
			}
			System.out.println();
		}
		
		//changing estimated cost to for two island...making odd and even columns equal
		System.out.println("Making execution cost of processors in island equal");
		for(int i = 0; i < no_of_machines; i++) {
			for(int j = 0; j < no_of_subtasks; j++) {
				est[j][i] = est[j][processor_to_island[i]];
			}
		}
		
		//printing changed estimated cost
		for (int i = 0; i < no_of_subtasks; i++) {
			for (int j = 0; j < no_of_machines; j++) {
				System.out.print(est[i][j] + " ");
			}
			System.out.println();
		}
		
		Random rand = new Random();
		Map<Integer, Integer> sub_task_to_island = new HashMap<Integer, Integer>();
		
		//randomly alloting islands to the subtasks
		System.out.println("Randomly allocating subtasks to islands");
		for(int i = 0; i < no_of_subtasks; i++) {
			int rand_no = rand.nextInt(2);
			sub_task_to_island.put(i, rand_no);
		}
		 for (Map.Entry<Integer, Integer> set :
             sub_task_to_island.entrySet()) {
 
            // Printing all allocation of subtask to island
            System.out.println(set.getKey() + " = "
                               + set.getValue());
         }
		 
		 
		 int [] visited = new int[no_of_subtasks];
		 degree = new int[no_of_subtasks];
		 int [] temp_degree = new int[no_of_subtasks];
		 for(int i = 0; i < no_of_subtasks; i++) {
			 int count = 0;
			 for(int j = 0; j < no_of_subtasks; j++) {
				 if(rel[j][i] != 0) {
					 count++;
				 }
			 }
			 degree[i] = count;
		 }
		 for(int i = 0; i < no_of_subtasks; i++) {
			 temp_degree[i] = degree[i];
		 }
		 
		 //checking indegree of each vertex
		 System.out.println("Indegree of each subtask");
		 for(int i = 0; i < no_of_subtasks; i++) {
			 System.out.println(i + " " + degree[i]);
		 }
		 
		 System.out.println("Predecessor of each subtask in DAG");
		 for(int i = 0; i < no_of_subtasks; i++) {
			 System.out.print("Subtasks " + i + " predecessor = ");
			 for(int j = 0; j < no_of_subtasks; j++) {
				 if(predecessor_mat[i][j] == 1) {
					 System.out.print(j + " ");
				 }
			 }
			 System.out.println();
		 }
		 
		 basic_ss = new int[no_of_subtasks];
		 int ind = 0;
		 //generating initial valid schedule string through topological sort
		 for(int i = 0; i < no_of_subtasks; i++) {
			 for(int j = 0; j < no_of_subtasks; j++) {
				 if(temp_degree[j] == 0 && visited[j] == 0) {
					 basic_ss[ind++] = j;
					 visited[j] = 1;
					 for(int k = 0; k < no_of_subtasks; k++) {
						 if(rel[j][k] != 0) {
							 temp_degree[k]--;
						 }
					 }
				 }
			 }
			 
		 }
		 
		 System.out.println("Initial valid schedule string");
		 for(int i = 0; i < no_of_subtasks; i++) {
			 System.out.print(basic_ss[i] + " ");
			 visited[i] = 0;
		 }
		 
		 //checking given string is valid schedule string or not
		 System.out.println(check_valid_order(basic_ss) + " " + "ss");
		  
		 //taking indegree variable to use indegree frequently
		 int[] in_degree = new int[no_of_subtasks];
		 for(int i = 0; i < no_of_subtasks; i++) {
			 in_degree[i] = degree[i];
		 }
		
		 //calculating start time and finish time of each task by using
		 //execution cost and communication cost 
		 int entry_task = 0;
		 for(int i = 0; i < no_of_subtasks; i++) {
			 if(degree[i] == 0) {
				 entry_task = i;
				 break;
			 }
		 }
		 
		 
		 Queue<Integer> q = new LinkedList<>();
		 q.add(entry_task);
		 visited[entry_task] = 1;
		 while(!q.isEmpty()) {
			 int node = q.peek();
			 q.remove();
			 //decreasing indegree of all vertices dependent on node
			 for(int i = 0; i < no_of_subtasks; i++) {
				 if(rel[node][i] != 0) {
					 in_degree[i]--;
				 }
			 }
			 for(int i = 0; i < no_of_subtasks; i++) {
				 if(in_degree[i] == 0 && visited[i] == 0) {
					 q.add(i);
					 visited[i] = 1;
				 }
			 }
	         
			 
			 int temp_island = sub_task_to_island.get(node);
			// System.out.println(node + " " + temp_island);
			 int flag = 0;
			 int expected_allocation_time = 0;
			 int temp_processor = 0;
			 for(int i = 0; i < no_of_subtasks; i++) {
				 //we are going to find a processor where node's predecessor
				 //allocated so that communication time can be ignored for one
				 //predecessor atleast.
				 if(predecessor_mat[node][i] == 1) {
					 temp_processor = subtask_to_processor[i];
					 if(processor_to_island[temp_processor] == temp_island) {
						 flag = 1;
						 expected_allocation_time = expected_min_allocation_time(node, i);
						 subtask_to_processor[node] = temp_processor;
						 if(temp_island == 0) {
							 subtask_execution(temp_processor, node, temp_island, pair1, expected_allocation_time);
						 }
						 else {
							 subtask_execution(temp_processor, node, temp_island, pair2, expected_allocation_time);
						 }
					     break;
					 }
					 
				 }
			 }
			 if(flag == 0) {
				 expected_allocation_time = expected_min_allocation_time(node, node);
				 int mini = Integer.MAX_VALUE, k = 0, temp = 0;
				 for(int i = 0; i < no_of_machines; i++) {
					 if(processor_to_island[i] == temp_island ) {
						 temp = i;
						 int len = available_slot[i].size();
						 if(len != 0 && available_slot[i].get(len-1) < mini) {
							 k = 1;
							 temp_processor = i;
							 mini = available_slot[i].get(len-1);
						 }
					 }
				 }
			     if(k == 0) {
			    	 temp_processor = temp;
			     }
				 subtask_to_processor[node] = temp_processor;
		
				 if(temp_island == 0) {
					 subtask_execution(temp_processor, node, temp_island, pair1, expected_allocation_time);
				 }
				 else {
					 subtask_execution(temp_processor, node, temp_island, pair2, expected_allocation_time);
				 }
			 
			 }
			 
			 
		 }
		 System.out.println();
		 for(int i = 0; i < no_of_subtasks; i++) {
			 
			 System.out.println(i + "th subtask start and end time with processor no. = " + start_time[i] + " " + 
		             end_time[i] + " " + subtask_to_processor[i]);
		 }
		 
		 for(int i = 0; i < no_of_machines; i++) {
			 System.out.println(i + "th processor busy energy = " + busy_energy[i]);
		 }
		 
		 //idle energy computation
		 double idle_energy_island0, idle_energy_island1;
		 idle_energy_island0 = voltage_pair1[pair1-1][2];
		 idle_energy_island1 = voltage_pair2[pair2-1][2];
		 for(int i = 0; i < no_of_machines; i++) {
			 int idle_time = 0;
			 for(int j = 1; j < available_slot[i].size(); j = j+2) {
				 if(processor_last_task_end_time[i] > available_slot[i].get(j)) {
					 idle_time = idle_time + (available_slot[i].get(j) - available_slot[i].get(j-1));
				 }
				 else
					 break;
			 }
			 if(processor_to_island[i] == 0) {
				 idle_energy[i] = idle_time * idle_energy_island0;
			 }
			 else {
				 idle_energy[i] = idle_time * idle_energy_island1;
			 }
			 System.out.println("total idle time and last busy time for processor" + i + " is " +idle_time + " " + processor_last_task_end_time[i]);
		 }
		 
		 for(int i = 0; i < no_of_machines; i++) {
			 System.out.println(i + "th processor idle energy = " + idle_energy[i]);
		 }
		
	}
	
	public boolean check_valid_order(int []temp_ss) {
		
		int[] in_degree = new int[no_of_subtasks];
		int[] visited = new int[no_of_subtasks];
		for(int i = 0; i < no_of_subtasks; i++) {
			in_degree[i] = degree[i];
		}
		
		for(int i = 0; i < no_of_subtasks; i++) {
			int k = temp_ss[i];
			
			visited[k] = 1;
			for(int j = 0; j < no_of_subtasks; j++) {
				if(rel[k][j] != 0 && visited[j] == 0) {
					in_degree[j]--;
				}
			}
		}
	//	for(int i = 0; i < no_of_subtasks; i++) {
	//		System.out.print(in_degree[i] + " ");
	//	}
		
		for(int i = 0; i < no_of_subtasks; i++) {
			if(in_degree[i] != 0) {
			//	System.out.println(i + " " + in_degree[i]);
				return false;
			}
		}
		return true;
	}
	
	//expected allocation time at which all the details from its predecessors
	//are available.
	public int expected_min_allocation_time(int node, int predecessor_subtask) {
		int max_time = 0;
		for(int i = 0; i < no_of_subtasks; i++) {
			if(predecessor_mat[node][i] == 1 && i != predecessor_subtask) {
				max_time = Math.max(max_time , end_time[i]+rel[i][node]);
			}
			//if task allocated to same processor as of precedence task
			else if(predecessor_mat[node][i] == 1 && i == predecessor_subtask) {
				max_time = Math.max(max_time, end_time[i]);
			}
			
			
		}
		return max_time;
	}
	
	//pair variable holds no of voltage pairs available for that island.
	public void subtask_execution(int processor, int subtask, int island, int pair, int expected_allocation_time) {
		double execution_cost = est[subtask][processor];
	//	int next = expected_allocation_time;
		Random rand = new Random();
		int last_slot = 20, flag = 0;
	//	int check = 0;
		System.out.println();
		System.out.print(subtask + " ");
		while(execution_cost > 0) {
			
		//	System.out.print(execution_cost + " ");
		//	check++;
		//	if(check >= 50)
		//		break;
			int rand_no = rand.nextInt(pair-1), i;
			double offset, temp_voltage, temp_energy;
			if(island == 0) {
				temp_voltage = voltage_pair1[rand_no][1];
				temp_energy = voltage_pair1[rand_no][2];
			}
			else {
				temp_voltage = voltage_pair2[rand_no][1];
				temp_energy = voltage_pair2[rand_no][2];
			}
			offset = 1 + (1 - (temp_voltage/100));
			execution_cost = execution_cost * offset;
		//	System.out.print(rand_no + " " + offset + " " +execution_cost + " ");
			for(i = 0; i < available_slot[processor].size(); i = i+2) {
				last_slot = available_slot[processor].get(i+1);
				if(expected_allocation_time < last_slot) {
					int slot = (available_slot[processor].get(i)/20);
					if(slot_voltage[island][slot] == 0) {
						slot_voltage[island][slot] = temp_voltage;
					}
					else {
						temp_voltage = slot_voltage[island][slot];
						execution_cost = execution_cost/offset;
						offset = 1 + (1 - (temp_voltage/100));
						execution_cost = execution_cost * offset;
					}
					System.out.print(island + " " + processor +" " + slot + " " + temp_voltage + " ");
				//	if(expected_allocation_time < available_slot[processor].get(i))
					//	expected_allocation_time = available_slot[processor].get(i);
					
					if((expected_allocation_time) == available_slot[processor].get(i)) {
						if(execution_cost > (last_slot - (expected_allocation_time))) {
							if(flag == 0) {
								flag = 1;
								start_time[subtask] = (expected_allocation_time);
							//	System.out.print("A " + subtask + " ");
							}
							execution_cost = execution_cost - (last_slot - (expected_allocation_time));
							expected_allocation_time = last_slot;
							busy_energy[processor] = busy_energy[processor] + (last_slot - (expected_allocation_time)) * temp_energy;
							available_slot[processor].remove(i+1);
							available_slot[processor].remove(i);
							
						}
						else {
							end_time[subtask] = (int)Math.ceil((expected_allocation_time) + execution_cost);
							if(end_time[subtask] > processor_last_task_end_time[processor]) {
								processor_last_task_end_time[processor] = end_time[subtask];
							}
							busy_energy[processor] = busy_energy[processor] + (execution_cost) * temp_energy;
							execution_cost = 0;
							if((int)Math.ceil(execution_cost) == (last_slot - (expected_allocation_time))) {
								available_slot[processor].remove(i+1);
								available_slot[processor].remove(i);
							}
							else
							    available_slot[processor].set(i, end_time[subtask]);
						}
					}
					
					else if(expected_allocation_time > available_slot[processor].get(i)) {
						int t = expected_allocation_time;
						if(execution_cost > (last_slot - (expected_allocation_time))) {
							if(flag == 0) {
								flag = 1;
								start_time[subtask] = (expected_allocation_time);
						//		System.out.print("B " + subtask + " ");
							}
							execution_cost = execution_cost - (last_slot - (expected_allocation_time));
		                    busy_energy[processor] = busy_energy[processor] + (last_slot - (expected_allocation_time)) * temp_energy;
							expected_allocation_time = last_slot;
							
						}
						else {
							end_time[subtask] = (int)Math.ceil((expected_allocation_time) + execution_cost);
							if(end_time[subtask] > processor_last_task_end_time[processor]) {
								processor_last_task_end_time[processor] = end_time[subtask];
							}
							busy_energy[processor] = busy_energy[processor] + execution_cost * temp_energy;
							execution_cost = 0;
						}
							
						available_slot[processor].set(i+1, t);					
					}
					else if((expected_allocation_time) < available_slot[processor].get(i)) {
						if(execution_cost > (last_slot - available_slot[processor].get(i))) {
							if(flag == 0) {
								flag = 1;
								start_time[subtask] = available_slot[processor].get(i);
						//		System.out.println(" C " + subtask + " ");
							}
							execution_cost = execution_cost - (last_slot - available_slot[processor].get(i));
							busy_energy[processor] = busy_energy[processor] + (last_slot - available_slot[processor].get(i)) * temp_energy;
							expected_allocation_time = last_slot;
							available_slot[processor].remove(i+1);
							available_slot[processor].remove(i);
						}
						else {
							busy_energy[processor] = busy_energy[processor] + execution_cost * temp_energy;
							end_time[subtask] = (int)Math.ceil(available_slot[processor].get(i) + execution_cost);
							if(end_time[subtask] > processor_last_task_end_time[processor]) {
								processor_last_task_end_time[processor] = end_time[subtask];
							}
							execution_cost = 0;
							available_slot[processor].set(i, available_slot[processor].get(i) + (int)Math.ceil(execution_cost ));
							
						}
					}
					break;
				}
			}
			execution_cost = execution_cost / offset;
		//	System.out.print(execution_cost + " ");
			if(i == available_slot[processor].size() || available_slot[processor].size() == 0) {
				int slot = processor_slots[processor];
				available_slot[processor].add(slot*20);
				available_slot[processor].add(slot*20+20);
			    processor_slots[processor] = slot+1;
			}
			
		}
	}
}

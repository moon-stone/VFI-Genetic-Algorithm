package vfiTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Driver {
	public static void main(String args[]) throws IOException
	{
		
		Scanner in = new Scanner(new FileInputStream("param.txt"));
		String source_folder = in.nextLine().split("=")[1];/// Main-Source-Folder
		System.out.println(source_folder);
		
		String outer_folder_name = source_folder + "\\INPUT";/// Input-Folder-Where-All-Input-Folder-Exists
		//System.out.println("outerfolder: "+outer_folder_name);
		File outer_folder = new File(outer_folder_name);
		
		for (File inner_folder : outer_folder.listFiles()) {
			if (inner_folder.isDirectory() == false) {
				continue;
			}
			File folder = new File(outer_folder.getAbsolutePath() + "\\" + inner_folder.getName());/// Choosing-Folder-To-Take-Input
			//System.out.println("outerfolder: "+folder.getName());
			for (File file : folder.listFiles()) {
				//traversing each file in each folder of Input
				
				//System.out.println("outerfolder: "+file.getName());
				
				if (file.getName().contains(".graph~") == false && file.getName().contains(".graphsch") == false
						&& file.getName().contains(".graph") == true) {
					//System.out.println("outerfolder: "+file.getName());
					//System.out.println(folder.getAbsolutePath() + "\\" + file.getName().split(".graph")[0] + ".graphsch");
					if ((new File(
							folder.getAbsolutePath() + "\\" + file.getName().split(".graph")[0] + ".graphsch"))
									.exists()) {
						//System.out.println(file.getName());
						
						String file1 = folder.getAbsolutePath() + "\\" + file.getName();
						System.out.println(file1);
						
						
						//new makespanTest.Main(file1);
						
						int slot_size = 30;
						makespanTest.Main m = new makespanTest.Main(file1, slot_size);
						System.out.println("final makespan, total energy and island comm = "+m.final_makespan + ", " + m.total_energy + ", "+ m.island_communication);
					}
					
				}
			
			}
			
			
		}
		
	}

}

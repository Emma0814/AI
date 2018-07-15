/**
* Created on 20 April, 2018
* @author: Emma Jing
*/

import java.util.*;
import java.io.*;
public class ThreeDigits {

	static String algorithm;
	static int start;
	static int goal;
	static boolean find = false;
	static int order = 1;
	static ArrayList<Integer> forbidden = new ArrayList<>();
	static LinkedList<Digit> expanded = new LinkedList<>();
	static Stack<Integer> solution = new Stack<Integer>();
	static Queue<Digit> fringe;

	public static int calculateHeuristic(int digit, int path){
		int p1 = digit / 100;
		int p2 = digit / 10 - p1 * 10;
		int p3 = digit - p1 * 100 - p2 * 10;
		int g1 = goal / 100;
		int g2 = goal / 10 - g1 * 10;
		int g3 = goal - g1 * 100 - g2 * 10;
		if(algorithm.equals("A"))
			return Math.abs(p1 - g1) +  Math.abs(p2 - g2) +  Math.abs(p3 - g3) + path;
		return Math.abs(p1 - g1) +  Math.abs(p2 - g2) +  Math.abs(p3 - g3);
	}

	public static boolean isExpanded(Queue<Digit> array, Digit self){
		for(Digit d : array){
			if(d.getSelf() == self.getSelf() && d.getPosition() == self.getPosition())
				return true;
		}
		return false;
	}

	public static void getSolution(){
		Digit last = expanded.get(expanded.size() - 1);
		solution.push(last.getSelf());
		while(last.getParent() != null){
			solution.push(last.getParent().getSelf());
			last = last.getParent();
		}
	}

	public static LinkedList<Digit> getChildren(Digit selfD){
		LinkedList<Digit> children = new LinkedList<>();
		int p1 = selfD.getSelf() / 100;
		int p2 = selfD.getSelf() / 10 - p1 * 10;
		int p3 = selfD.getSelf() - p1 * 100 - p2 * 10;
		if(selfD.getPosition() != 1){
			if(p1 > 0){
				if(!forbidden.contains(selfD.getSelf() - 100)){
					if(algorithm.equals("B") || algorithm.equals("D") || algorithm.equals("I"))
						children.add(new Digit(selfD, selfD.getSelf() - 100, 1));
					else if(algorithm.equals("A")){
						order++;
						children.add(new Digit(selfD, selfD.getSelf() - 100, 1, calculateHeuristic(selfD.getSelf() - 100, selfD.getPath()), selfD.getPath() + 1, order));
					}else{
						order++;
						children.add(new Digit(selfD, selfD.getSelf() - 100, 1, calculateHeuristic(selfD.getSelf() - 100, 0), order));
					}
				}
			}
			if(p1 < 9){
				if(!forbidden.contains(selfD.getSelf() + 100)){
					if(algorithm.equals("B") || algorithm.equals("D") || algorithm.equals("I"))
						children.add(new Digit(selfD, selfD.getSelf() + 100, 1));
					else if(algorithm.equals("A")){
						order++;
						children.add(new Digit(selfD, selfD.getSelf() + 100, 1, calculateHeuristic(selfD.getSelf() + 100, selfD.getPath()), selfD.getPath() + 1, order));
					}else{
						order++;
						children.add(new Digit(selfD, selfD.getSelf() + 100, 1, calculateHeuristic(selfD.getSelf() + 100, 0), order));
					}
				}
			}
		}
		if(selfD.getPosition() != 2){
			if(p2 > 0){
				if(!forbidden.contains(selfD.getSelf() - 10)){
					if(algorithm.equals("B") || algorithm.equals("D") || algorithm.equals("I"))
						children.add(new Digit(selfD, selfD.getSelf() - 10, 2));
					else if(algorithm.equals("A")){
						order++;
						children.add(new Digit(selfD, selfD.getSelf() - 10, 2, calculateHeuristic(selfD.getSelf() - 10, selfD.getPath()), selfD.getPath() + 1, order));
					}else{
						order++;
						children.add(new Digit(selfD, selfD.getSelf() - 10, 2, calculateHeuristic(selfD.getSelf() - 10, 0), order));
					}
				}
			}
			if(p2 < 9){
				if(!forbidden.contains(selfD.getSelf() + 10)){
					if(algorithm.equals("B") || algorithm.equals("D") || algorithm.equals("I"))
						children.add(new Digit(selfD, selfD.getSelf() + 10, 2));
					else if(algorithm.equals("A")){
						order++;
						children.add(new Digit(selfD, selfD.getSelf() + 10, 2, calculateHeuristic(selfD.getSelf() + 10, selfD.getPath()), selfD.getPath() + 1, order));
					}else{
						order++;
						children.add(new Digit(selfD, selfD.getSelf() + 10, 2, calculateHeuristic(selfD.getSelf() + 10, 0), order));
					}
				}
			}
		}
		if(selfD.getPosition() != 3){
			if(p3 > 0){
				if(!forbidden.contains(selfD.getSelf() - 1)){
					if(algorithm.equals("B") || algorithm.equals("D") || algorithm.equals("I"))
						children.add(new Digit(selfD, selfD.getSelf() - 1, 3));
					else if(algorithm.equals("A")){
						order++;
						children.add(new Digit(selfD, selfD.getSelf() - 1, 3, calculateHeuristic(selfD.getSelf() - 1, selfD.getPath()), selfD.getPath() + 1, order));
					}else{
						order++;
						children.add(new Digit(selfD, selfD.getSelf() - 1, 3, calculateHeuristic(selfD.getSelf() - 1, 0), order));
					}
				}
			}
			if(p3 < 9){
				if(!forbidden.contains(selfD.getSelf() + 1)){
					if(algorithm.equals("B") || algorithm.equals("D") || algorithm.equals("I"))
						children.add(new Digit(selfD, selfD.getSelf() + 1, 3));
					else if(algorithm.equals("A")){
						order++;
						children.add(new Digit(selfD, selfD.getSelf() + 1, 3, calculateHeuristic(selfD.getSelf() + 1, selfD.getPath()), selfD.getPath() + 1, order));
					}else{
						order++;
						children.add(new Digit(selfD, selfD.getSelf() + 1, 3, calculateHeuristic(selfD.getSelf() + 1, 0), order));
					}
				}
			}
		}
		return children;
	}

	public static void ids(){
		fringe = new LinkedList<Digit>();
		Digit root = new Digit(null, start, 0);
		if(forbidden.contains(root.getSelf()))
			return;
		for(int i = 0; i < Integer.MAX_VALUE; i++){
			find = dls(root, i);
			fringe.clear();
			if(expanded.size() == 1000)
				return;
			if(find){
				getSolution();
				return;
			}
		}
	}

	public static boolean dls(Digit root, int depth){
		if(expanded.size() == 1000)
			return false;
		if(isExpanded(fringe, root))
			return false;
		fringe.add(root);
		expanded.add(root);
		if(root.getSelf() == goal)
			return true;
		if(depth > 0){
			for(Digit d : getChildren(root)){
				if(dls(d, depth - 1))
					return true;
			}
		}
		return false;
	}

	public static void bfs(){
		Digit father;
		if(algorithm.equals("G") || algorithm.equals("A") || algorithm.equals("H")){
			fringe = new PriorityQueue<Digit>(new Comparator<Digit>(){
		        @Override
		        public int compare(Digit digit1, Digit digit2){
		            if (digit1.getHValue() > digit2.getHValue())
		                return 1;
		            else if (digit1.getHValue() < digit2.getHValue())
		                return -1;
		            else{
		            	if(digit1.getOrder() > digit2.getOrder())
		            		return -1;
		            	if(digit1.getOrder() < digit2.getOrder())
		            		return 1;
		            	return 0;
		            }
		        }
		    });
		}else
			fringe = new LinkedList<>();
		if(algorithm.equals("G") || algorithm.equals("H"))
			father = new Digit(null, start, 0, calculateHeuristic(start, 0), 1);
		else if(algorithm.equals("A"))
			father = new Digit(null, start, 0, calculateHeuristic(start, 0), 0, 1);
		else
			father = new Digit(null, start, 0);
		if(forbidden.contains(father.getSelf()))
			return;
		expanded.add(father);
		if(start == goal){
			find = true;
			solution.push(start);
			return;
		}
		for(Digit d : getChildren(father))
			fringe.add(d);
		while(expanded.size() < 1000 && fringe.size() != 0){
			Digit self= fringe.peek();
			if(algorithm.equals("H") && self.getHValue() >= expanded.get(expanded.size() - 1).getHValue()){
				break;
			}
			if(!isExpanded(expanded, self))
				expanded.add(self);
			else{
				fringe.poll();
				continue;
			}
			if(self.getSelf() == goal){
				find = true;
				break;
			}
			fringe.poll();
			if(algorithm.equals("B"))
				fringe.addAll(getChildren(self));
			if(algorithm.equals("H"))
				fringe.clear();
			if(algorithm.equals("D")){
				LinkedList<Digit> temp = getChildren(self);
				temp.addAll(fringe);
				fringe = temp;
			}
			if(algorithm.equals("G") || algorithm.equals("A") || algorithm.equals("H")){
				for(Digit d : getChildren(self))
					fringe.add(d);
			}
		}
		if(find)
			getSolution();
	}

	public static void main(String[] args) {
		try {
			algorithm = args[0];
			//algorithm = "D";
			File file = new File(args[1]);
			//File file = new File("sample.txt");
			Scanner sc = new Scanner(file);
			start = Integer.parseInt(sc.nextLine());
			goal = Integer.parseInt(sc.nextLine());
			if(sc.hasNextLine()){
				String line = sc.nextLine();
				if(!line.equals("")){
					String[] num = line.trim().split(",");
					for(int i = 0; i < num.length; i++)
						forbidden.add(Integer.parseInt(num[i]));
				}
			}
			switch(algorithm){
			case "B" :
			case "D" :
			case "G" :
			case "H" :
			case "A" :
				bfs();
				break;
			case "I" :
				ids();
				break;
			}
			String format = "000";
			java.text.DecimalFormat f = new java.text.DecimalFormat(format);
			if(find){
				int size = solution.size();
				for(int i = 0; i < size - 1; i++){
					System.out.print(f.format(solution.pop()));
					System.out.print(",");
				}
				System.out.println(f.format(solution.pop()));
			}else
				System.out.println("No solution found.");
			for(int i = 0; i < expanded.size() - 1; i++){
				System.out.print(f.format(expanded.get(i).getSelf()));
				System.out.print(",");
			}
			if(!expanded.isEmpty())
				System.out.print(f.format(expanded.get(expanded.size() - 1).getSelf()));
		} catch (FileNotFoundException e) {
			return;
		}
	}
}

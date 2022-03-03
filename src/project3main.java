import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class project3main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(LocalTime.now());
		Scanner input = new Scanner(new File(args[0]));
		PrintStream output = new PrintStream(new File(args[1]));
		
		// taking data
		int timeLimit = input.nextInt();
		int numOfCities = input.nextInt();
		input.next();
		int leylaCity = Integer.parseInt(input.next().substring(1));
		
		// continue to take data and construct the first graph
		City[] cities = new City[leylaCity + 1];
		PriorityQueue<City> sortedCities = new PriorityQueue<City>();
		for(int i = 1; i < leylaCity; i++) {
			cities[i] = new City(i);
			input.next();
			String line = "";
			if(input.hasNextLine()) {
				line = input.nextLine();
			}
			//String line = input.nextLine().substring(1);
			if(!line.isBlank()) {
				line = line.substring(1);
				String[] list = line.split(" ", 0);
				for(int j = 0; j < list.length; j+=2) {
					int parse = Integer.parseInt(list[j].substring(1));
						cities[i].addNeighbour(new Neighbour(parse, Integer.parseInt(list[j + 1])));
					
				}
			}
			sortedCities.add(cities[i]);
		}
		cities[1].minLength = 0;
		cities[leylaCity] = new City(leylaCity);
		sortedCities.add(cities[leylaCity]);
		
		// control variables
		boolean hasArrived = false;
		
		// dijkstra's algorithm
		while(!sortedCities.isEmpty()) {
			City current = sortedCities.poll();
			if(current.minLength == Integer.MAX_VALUE) {
				current.minLength = 0;
			}
			current.isVisited = true;
			Iterator<Neighbour> iter = current.neighbours.iterator();
			while(iter.hasNext()) {
				Neighbour nei = iter.next();
				City neiCity = cities[nei.id];
				if(!neiCity.isVisited && (current.minLength + nei.weight) < neiCity.minLength) {
					neiCity.minLength = current.minLength + nei.weight;
					neiCity.parentIndex = current.id;
					sortedCities.remove(neiCity);
					sortedCities.add(neiCity);
				}
			}
		}
		
		int parentIndex = leylaCity;
		Stack<Integer> list1 = new Stack<Integer>();
		while(parentIndex != -1) {
			list1.add(parentIndex);
			parentIndex = cities[parentIndex].parentIndex;
		}
		String firstLine = "";
		while(!list1.isEmpty()) {
			int n = list1.pop();
			firstLine += ("c"+n+" ");
			if(n == 1) {
				hasArrived = true;
			}
		}
		
		// print a part of output
		if(!hasArrived) {
			output.println(-1);
			output.print(-1);
		} else {
			output.println(firstLine.substring(0, firstLine.length()-1));
			if(cities[leylaCity].minLength > timeLimit) {
				output.print(-1);
			} else {
				
				// continue to take data and construct the second graph
//				// first method
//				int[][] matrix = new int[numOfCities - leylaCity + 1][numOfCities - leylaCity + 1];
//				for(int i = 0; i <= numOfCities - leylaCity; i++) {
//					//input.next();
//					input.next();
//					String line = "";
//					if(input.hasNextLine()) {
//						//System.out.println(input.nextLine());
//						//String a = input.n
//						line = input.nextLine();
//					}
//					if(!line.isBlank()) {
//						line = line.substring(1);
//						String[] list = line.split(" ", 0);
//						for(int j = 0; j < list.length; j+=2) {
//							int neighbourId = Integer.parseInt(list[j].substring(1));
//							int neighbourWeight = Integer.parseInt(list[j + 1]);
//							matrix[i][neighbourId] = neighbourWeight;
//							if(matrix[neighbourId][i] == 0) {
//								matrix[neighbourId][i] = neighbourWeight;
//							} else {
//								if(matrix[neighbourId][i] < neighbourWeight) {
//									matrix[i][neighbourId] = matrix[neighbourId][i];
//								} else {
//									matrix[neighbourId][i] = neighbourWeight;
//								}
//							}
//							//moons[i].addNeighbour(new Neighbour(neighbourId, neighbourWeight));
//						}
//					}
//				}
//				
//				//
//				City[] moons = new City[numOfCities - leylaCity + 1];
//				for(int i = 0; i < moons.length; i++) {
//					moons[i] = new City(i);
//					for(int k = 1; k < moons.length; k++) {
//						if(matrix[i][k] != 0) {
//							moons[i].neighbours.add(new Neighbour(k, matrix[i][k]));
//						}
//					}
//				}
				
				// second method
				City[] moons = new City[numOfCities - leylaCity + 1];
				for(int i = 0; i <= numOfCities - leylaCity; i++) {
					moons[i] = new City(i);
					input.next();
					String line = "";
					if(input.hasNextLine()) {
						line = input.nextLine();
					}
					//String line = input.nextLine().substring(1);
					if(!line.isBlank()) {
						line = line.substring(1);
						String[] list = line.split(" ", 0);
						for(int j = 0; j < list.length; j+=2) {
							if(list[j].charAt(0) != 'c') {
								int neighbourId = Integer.parseInt(list[j].substring(1));
									int neighbourWeight = Integer.parseInt(list[j + 1]);
									moons[i].addNeighbour(new Neighbour(neighbourId, neighbourWeight));
								
							}
						}
					}
				}
				moons[0].minLength = 0;
				input.close();
				for(int i = 0; i < moons.length; i++) {
					City current = moons[i];
					Iterator<Neighbour> iter = current.neighbours.iterator();
					while(iter.hasNext()) {
						Neighbour nei = iter.next();
						City neiCity = moons[nei.id];
						if(neiCity.getWeight(i) == -1) {
							neiCity.addNeighbour(new Neighbour(i, nei.weight));
						} else {
							if(neiCity.getWeight(i) < nei.weight) {
								nei.weight = neiCity.getWeight(i);
							} else {
								neiCity.changeWeight(i, nei.weight);
							}
						}
					}
				}
				
				// prim's algorithm
				PriorityQueue<City> sortedMoons = new PriorityQueue<City>();
				sortedMoons.add(moons[0]);
				
				while(!sortedMoons.isEmpty()) {
					City current = sortedMoons.poll();
					if(!current.isVisited) {
						current.isVisited = true;
						Iterator<Neighbour> iter = current.neighbours.iterator();
						while(iter.hasNext()) {
							Neighbour nei = iter.next();
							City neiCity = moons[nei.id];
							if(!neiCity.isVisited && nei.weight < neiCity.minLength) {
								neiCity.minLength = nei.weight;
								neiCity.parentIndex = current.id;
								sortedMoons.remove(neiCity);
								sortedMoons.add(neiCity);
							}
						}
					}
				}
				boolean hasMooned = true;
				int cost = 0;
				for(int i = 0; i < moons.length; i++) {
					if(moons[i].minLength == Integer.MAX_VALUE) {
						hasMooned = false;
						break;
					}
					cost += moons[i].minLength;
				}
				if(!hasMooned) {
					output.print(-2);
				} else {
					output.print(2*cost);
				}
			}
		}
		System.out.println(cities[leylaCity].minLength);
		
		System.out.println(LocalTime.now());
	}

}
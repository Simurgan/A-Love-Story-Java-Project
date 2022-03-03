import java.util.ArrayList;
import java.util.Iterator;

public class City implements Comparable<City>{
	int id;
	ArrayList<Neighbour> neighbours;
	int minLength;
	int parentIndex;
	boolean isVisited;
	
	public City(int id) {
		this.id = id;
		this.neighbours = new ArrayList<Neighbour>();
		this.minLength = Integer.MAX_VALUE;
		this.parentIndex = -1;
		this.isVisited = false;
	}
	
	public void addNeighbour(Neighbour a) {
		this.neighbours.add(a);
	}
	
	public int getWeight(int neiId) {
		Iterator<Neighbour> iter = this.neighbours.iterator();
		while(iter.hasNext()) {
			Neighbour a = iter.next();
			if(a.id == neiId) {
				return a.weight;
			}
		}
		return -1;
	}
	
	public void changeWeight(int neiId, int weight) {
		Iterator<Neighbour> iter = this.neighbours.iterator();
		while(iter.hasNext()) {
			Neighbour a = iter.next();
			if(a.id == neiId) {
				a.weight = weight;
			}
		}
	}

	@Override
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		return this.minLength - o.minLength;
	}
	
}

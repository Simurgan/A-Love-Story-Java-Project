
public class Neighbour implements Comparable<Neighbour>{
	int id;
	int weight;
	
	public Neighbour(int id, int weight) {
		this.id = id;
		this.weight = weight;
	}

	@Override
	public int compareTo(Neighbour o) {
		// TODO Auto-generated method stub
		return this.weight - o.weight;
	}
	
}

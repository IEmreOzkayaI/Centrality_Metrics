package concretes;

public class Entry<K, V> {

	private K name;
	private V number;
	
	public Entry(K name , V number) {
		this.name = name;
		this.number=number;
	}

	public V getNumber() {
		return number;
	}

	public void setNumber(V number) {
		this.number = number;
	}
	public K getName() {
		return name;
	}
	
}

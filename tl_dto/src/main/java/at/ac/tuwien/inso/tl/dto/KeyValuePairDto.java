package at.ac.tuwien.inso.tl.dto;


public class KeyValuePairDto<K, V>
{
	private K key;
	private V value;		
	
	public KeyValuePairDto() {
	}
	public KeyValuePairDto(K key, V value) {
		this.key = key;
		this.value = value;
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}	
	
	@Override
	public String toString() {
		return "KeyValuePairDto [key=" + key + ", value=" + value + "]";
	}
}

package at.ac.tuwien.swag.util;

import java.util.HashMap;
import java.util.Map;

public class MapMaker<K,V> extends HashMap<K, V> {
	private static final long serialVersionUID = 7181096740431144308L;

	public static <K,V> MapMaker<K, V> map( K k, V v ) {
		return  MapMaker.<K,V>map().add( k, v );
	}
	public static <K,V> MapMaker<K, V> map() {
		return new MapMaker<K, V>();
	}

	public MapMaker<K, V> add( K k, V v ) {
		put( k, v );
		
		return this;
	}
	
	public Map<K,V> build() {
		return new HashMap<K,V>( this );
	}
}

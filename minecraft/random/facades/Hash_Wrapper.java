package random.facades;

import net.minecraftforge.common.DimensionManager;

public class Hash_Wrapper<K, V> extends Hashtable<K, V>
{
	ArrayList<Hashtable<K,V>> unis = new ArrayList<Hashtable<K,V>>();
	public Hash_Wrapper(Hashtable<K, V> ht)
	{
		super(ht);
	}
	
	public void clear()
	{
		super.clear();
		for(Hashtable<K,V> ht : unis)
			ht.clear();
	}
	
	public boolean containsKey(K key)
	{
		
	}
	
	public boolean containsValue(K key)
	{
		
	}
	
	public Set<Map.Entry<K,V>> entrySet()
	{
		
	}
	
	public boolean equals(Object obj)
	{
		
	}
	
	public V get(K key)
	{
		
	}
	
	public boolean isEmpty()
	{
		if(!super.isEmpty())
			return false;
		for(Hashtable<K,V> ht : unis)
			if(!ht.isEmpty())
				return false;
		return true;
	}
	
	public Set<K> keySet()
	{
		
	}
	
	public V put(K key, V value)
	{
		
	}
	
	public void putAll(Map<K,V> m)
	{
		
	}
	
	public V remove(K key)
	{
		
	}
	
	public int size()
	{
		
	}
	
	public Collection<V> values()
	{
		
	}
}
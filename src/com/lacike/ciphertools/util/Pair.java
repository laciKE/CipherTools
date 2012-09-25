package com.lacike.ciphertools.util;

/**
 * Provides generic Pair<F,S> type with comparision.
 */
public class Pair<F extends Comparable<F>, S extends Comparable<S> > implements Comparable<Pair<F, S>>{

	public F first;
	public S second;

	public Pair(F first, S second){
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Compares two pairs by first field, if equals then by second field.
	 */
	@Override
	public int compareTo(Pair<F, S> another) {
		if(first.compareTo(another.first) == 0) {
			return second.compareTo(second);
		} else {
			return first.compareTo(another.first);
		}
	}
	
}

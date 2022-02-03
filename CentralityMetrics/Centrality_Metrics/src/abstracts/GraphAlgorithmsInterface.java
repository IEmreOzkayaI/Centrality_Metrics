package abstracts;

import java.util.List;
import java.util.Stack;

import concretes.Entry;

public interface GraphAlgorithmsInterface<T> {
	public Stack<T> getShortestPath(T begin,T end , Stack<T> path,List<Entry<String,Integer>> mostImp);
}

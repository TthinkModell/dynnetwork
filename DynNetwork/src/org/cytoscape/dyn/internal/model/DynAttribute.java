package org.cytoscape.dyn.internal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents all dynamical attributes, i.e. a list of intervals containing the value of type
 * T and the time interval.
 * @author sabina
 *
 * @param <T>
 */
public abstract class DynAttribute<T> {
	
	private double minTime = Double.POSITIVE_INFINITY;
	private double maxTime = Double.NEGATIVE_INFINITY;

	//TODO: intervals should be hold in a tree for fast searching
	protected List<DynInterval<T>> intervalList;
	
	protected String type;
	
	public DynAttribute(){
		intervalList = new ArrayList<DynInterval<T>>();
	}
	
	public DynAttribute(DynInterval<T> interval){
		intervalList.add(interval);
	}
		
	public void addInterval(DynInterval<T> interval) {
		intervalList.add(interval);
	}

    public T getFirst(DynInterval<T> interval) {
        for (DynInterval<T> i : intervalList)
        {
        	if (i.compareTo(interval)==1)
        	{
        		return i.getValue();
        	}
        }
		return null;
    }
 
    public boolean getIsInRange(DynInterval<T> interval) {
        for (DynInterval<T> i : intervalList)
        {
        	if (i.compareTo(interval)==1)
        	{
        		return true;
        	}
        }
		return false;
    }
    
	public double getMinTime() {
		return minTime;
	}

	public double getMaxTime() {
		return maxTime;
	}

	abstract public Class<?> getType();

}

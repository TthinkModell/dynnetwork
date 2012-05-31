package org.cytoscape.dyn.internal.model.attributes;

import org.cytoscape.dyn.internal.model.tree.DynInterval;

/**
 * {@inheritDoc}
 */
public class DynDoubleAttribute extends DynAttribute<Double>
{

	public DynDoubleAttribute()
	{
		super();
	}
	
	public DynDoubleAttribute(DynInterval<Double> interval)
	{
		super(interval);
	}

	@Override
	public Class<Double> getType()
	{
		return Double.class;
	}

}



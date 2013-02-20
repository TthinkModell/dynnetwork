/*
 * DynNetwork plugin for Cytoscape 3.0 (http://www.cytoscape.org/).
 * Copyright (C) 2012 Sabina Sara Pfister
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.cytoscape.dyn.internal.io.read.xgmml.handler;

import org.cytoscape.dyn.internal.io.event.Sink;
import org.cytoscape.dyn.internal.io.event.Source;
import org.cytoscape.dyn.internal.layout.DynLayoutFactory;
import org.cytoscape.dyn.internal.model.DynNetwork;
import org.cytoscape.dyn.internal.model.DynNetworkFactory;
import org.cytoscape.dyn.internal.view.model.DynNetworkViewFactory;
import org.cytoscape.group.CyGroup;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

/**
 * <code> AbstractXGMMLSource </code> is an abstract class to generates 
 * graph events from file parsing.
 * 
 * @author Sabina Sara Pfister
 *
 * @param <T>
 */
public abstract class AbstractXGMMLSource<T> implements Source<T>
{
	// Note: i don't implement generation of events, since xgmml reading is almost sequential 
	// and direct calling of sink methods is much faster.

	protected DynNetworkFactory<T> networkSink;
	protected DynNetworkViewFactory<T> viewSink;
	protected DynLayoutFactory<T> layoutSink;

	protected DynNetwork<T> addGraph(
			String id, String label, String start, String end, String directed)
	{
		return networkSink.addedGraph(id, label, start, end, directed);
	}
	
	protected CyNode addNode(DynNetwork<T> currentNetwork, CyGroup group, 
			String id, String label, String start, String end)
	{
		return networkSink.addedNode(currentNetwork, group, id, label, start, end);
	}
	
	protected CyEdge addEdge(DynNetwork<T> currentNetwork, 
			String id, String label, String source, String target, String start, String end)
	{
		return networkSink.addedEdge(currentNetwork, id, label, source, target, start, end);
	}
	
	protected CyGroup addGroup(DynNetwork<T> currentNetwork, CyNode currentNode)
	{
		return networkSink.addedGroup(currentNetwork, currentNode);
	}
	
	protected void addGraphAttribute(DynNetwork<T> currentNetwork, 
			String name, String value, String type, String start, String end)
	{
		networkSink.addedGraphAttribute(currentNetwork, name, value, type, start, end);
	}
	
	protected void addNodeAttribute(DynNetwork<T> network, CyNode currentNode, 
			String name, String value, String type, String start, String end)
	{
		networkSink.addedNodeAttribute(network, currentNode, name, value, type, start, end);
	}
	
	protected void addEdgeAttribute(DynNetwork<T> network, CyEdge currentEdge, 
			String name, String value, String type, String start, String end)
	{
		networkSink.addedEdgeAttribute(network, currentEdge, name, value, type, start, end);
	}
	
	protected void addGraphGraphics(DynNetwork<T> network, 
			String fill, String start, String end)
	{
		networkSink.addedGraphGraphics(network, fill, start, end);
	}
	
	protected void addNodeGraphics(DynNetwork<T> network, CyNode currentNode, 
			String type, String height, String width, String size, String fill, String linew, String outline, String start, String end)
	{
		networkSink.addedNodeGraphics(network, currentNode, type, height, width, size, fill, linew, outline, start, end);
	}
	
	protected void addEdgeGraphics(DynNetwork<T> network, CyEdge currentEdge, 
			String width, String fill, String start, String end)
	{
		networkSink.addedEdgeGraphics(network, currentEdge, width, fill, start, end);
	}
	
	protected void addNodeDynamics(DynNetwork<T> network, CyNode currentNode, 
			String x, String y, String start, String end)
	{
		layoutSink.addedNodeDynamics(network, currentNode, x, y, start, end);
	}
	
	protected void deleteGraph(DynNetwork<T> netwrok)
	{
		networkSink.deletedGraph(netwrok);
	}

	protected void deleteNode(DynNetwork<T> currentNetwork, CyNode node)
	{
		networkSink.deletedNode(currentNetwork, node);
	}
	
	protected void deleteEdge(DynNetwork<T> currentNetwork, CyEdge edge)
	{
		networkSink.deletedEdge(currentNetwork, edge);
	}
	
	protected void deleteGraphAttribute(DynNetwork<T> currentNetwork, CyNetwork netwrok, String label)
	{
//		sink.deletedGraphAttribute(currentNetwork, network, label);
	}
	
	protected void deleteNodeAttribute(DynNetwork<T> currentNetwork, CyNode node, String label)
	{
//		sink.deletedNodeAttribute(currentNetwork, node, label);
	}
	
	protected void deleteEdgeAttribute(DynNetwork<T> currentNetwork, CyEdge edge, String label)
	{
//		sink.deletedEdgeAttribute(currentNetwork, edge, label);
	}
	
	protected void finalize(DynNetwork<T> currentNetwork)
	{
		networkSink.finalizeNetwork(currentNetwork);
	}

	@Override
	public void addSink(Sink<T> sink) 
	{
		if (sink instanceof DynNetworkViewFactory<?>)
			this.networkSink = (DynNetworkFactory<T>) sink;
		else if (sink instanceof DynNetworkViewFactory<?>)
			this.viewSink = (DynNetworkViewFactory<T>) sink;
		else if (sink instanceof DynLayoutFactory<?>)
			this.layoutSink = (DynLayoutFactory<T>) sink;
	}
	
	@Override
	public void removeSink(Sink<T> sink) 
	{
		if (this.networkSink == sink)
			this.networkSink = null;
		else if (this.viewSink == sink)
			this.viewSink = null;
		else if (this.layoutSink==null)
			this.layoutSink = null;
	}
	
}

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

package org.cytoscape.dyn.internal.view.model;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.dyn.internal.model.DynNetwork;
import org.cytoscape.dyn.internal.model.tree.DynInterval;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;

/**
 * <code> DynNetworkViewImpl </code> is the interface for the visualization of 
 * dynamic networks {@link DynNetworkView}.
 * 
 * @author Sabina Sara Pfister
 *
 * @param <T>
 */
public final class DynNetworkViewImpl<T> implements DynNetworkView<T>
{
	private final DynNetwork<T> dynNetwork;
	private final CyNetworkView view;
	private final VisualMappingManager cyStyleManager;
	
	private int visibleNodes;
	private int visibleEdges;
	
	private List<DynInterval<T>> currentNodes;
	private List<DynInterval<T>> currentEdges;
	private List<DynInterval<T>> currentGraphsAttr;
	private List<DynInterval<T>> currentNodesAttr;
	private List<DynInterval<T>> currentEdgesAttr;
	
	private double currentTime;

	public DynNetworkViewImpl(
			DynNetwork<T> dynNetwork,
			final CyNetworkViewManager networkViewManager,
			final CyNetworkViewFactory cyNetworkViewFactory,
			final VisualMappingManager cyStyleManager,
			final VisualStyleFactory visualStyleFactory)
	{
		this.currentTime = 0;
		
		this.dynNetwork = dynNetwork;
		this.cyStyleManager = cyStyleManager;
		
		this.visibleNodes = 0;
		this.visibleEdges = 0;
		
		this.currentNodes = new ArrayList<DynInterval<T>>();
		this.currentEdges = new ArrayList<DynInterval<T>>();
		this.currentGraphsAttr = new ArrayList<DynInterval<T>>();
		this.currentNodesAttr = new ArrayList<DynInterval<T>>();
		this.currentEdgesAttr = new ArrayList<DynInterval<T>>();
		
		this.view = cyNetworkViewFactory.createNetworkView(dynNetwork.getNetwork());
		networkViewManager.addNetworkView(view);
		
//		VisualStyle vs = visualStyleFactory.createVisualStyle(cyStyleManager.getDefaultVisualStyle());
//		vs.setTitle(dynNetwork.getNetworkLabel());
//		
//		double nodesOverEdges = (1.0*view.getNodeViews().size())/(1.0*view.getEdgeViews().size());
//		double nodesize = Math.max(1,60.0*Math.sqrt(nodesOverEdges));
//		int fontSize = Math.max(1,(int) (2.0*nodesize/3.0));
//		double edgewidth = Math.max(0.1,80.0*nodesOverEdges/10.0);
//
//		vs.setDefaultValue(BasicVisualLexicon.NODE_SIZE, new Double(nodesize));
//		vs.setDefaultValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, new Integer(fontSize));
//		vs.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
//		vs.setDefaultValue(BasicVisualLexicon.NODE_BORDER_WIDTH, new Double(0));
//		vs.setDefaultValue(BasicVisualLexicon.EDGE_WIDTH, new Double(edgewidth));
//		cyStyleManager.addVisualStyle(vs);
//		cyStyleManager.setCurrentVisualStyle(vs);
//		vs.apply(view);
		
		cyStyleManager.getDefaultVisualStyle().apply(view);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int readVisualProperty(CyNode node, VisualProperty<Integer> vp) 
	{
		return view.getNodeView(node).getVisualProperty(vp).intValue();	
	}
	
	@Override
	public double readVisualProperty(CyNode node, VisualProperty<Double> vp) 
	{
		return view.getNodeView(node).getVisualProperty(vp).doubleValue();	
	}
	
	@Override
	public void writeVisualProperty(CyNode node, VisualProperty<Integer> vp, int value) 
	{
		view.getNodeView(node).setVisualProperty(vp,value);
	}
	
	@Override
	public void writeVisualProperty(CyNode node, VisualProperty<Double> vp, double value) 
	{
		view.getNodeView(node).setVisualProperty(vp,value);
	}
	
	@Override
	public void writeLockedVisualProperty(CyNode node, VisualProperty<Integer> vp, int value) 
	{
		view.getNodeView(node).setLockedValue(vp, value);
	}

	@Override
	public void writeLockedVisualProperty(CyNode node, VisualProperty<Double> vp, double value) 
	{
		view.getNodeView(node).setLockedValue(vp, value);
	}
	
	@Override
	public int readVisualProperty(CyEdge edge, VisualProperty<Integer> vp) 
	{
		return view.getEdgeView(edge).getVisualProperty(vp).intValue();
	}
	
	@Override
	public double readVisualProperty(CyEdge edge, VisualProperty<Double> vp) 
	{
		return view.getEdgeView(edge).getVisualProperty(vp).doubleValue();
	}

	@Override
	public void writeVisualProperty(CyEdge edge, VisualProperty<Integer> vp, int value) 
	{
		view.getEdgeView(edge).setVisualProperty(vp,value);
	}
	
	@Override
	public void writeVisualProperty(CyEdge edge, VisualProperty<Double> vp, double value) 
	{
		view.getEdgeView(edge).setVisualProperty(vp,value);
	}
	
	@Override
	public void writeLockedVisualProperty(CyEdge edge, VisualProperty<Integer> vp, int value) 
	{
		view.getEdgeView(edge).setLockedValue(vp,value);
	}
	
	@Override
	public void writeLockedVisualProperty(CyEdge edge, VisualProperty<Double> vp, double value) 
	{
		view.getEdgeView(edge).setLockedValue(vp,value);
	}
	
	@Override
	public List<DynInterval<T>> searchChangedNodes(DynInterval<T> interval)
	{
		List<DynInterval<T>> tempList = dynNetwork.searchNodes(interval);
		List<DynInterval<T>> changedList = nonOverlap(currentNodes, tempList);
		this.visibleNodes = tempList.size();
		currentNodes = tempList;
		return changedList;
	}

	@Override
	public List<DynInterval<T>> searchChangedEdges(DynInterval<T> interval)
	{
		List<DynInterval<T>> tempList = dynNetwork.searchEdges(interval);
		List<DynInterval<T>> changedList = nonOverlap(currentEdges, tempList);
		this.visibleEdges = tempList.size();
		currentEdges = tempList;
		return changedList;
	}
	
	@Override
	public List<DynInterval<T>> searchChangedGraphsAttr(DynInterval<T> interval)
	{
		List<DynInterval<T>> tempList = dynNetwork.searchGraphsAttr(interval);
		List<DynInterval<T>> changedList = nonOverlap(currentGraphsAttr, tempList);
		currentGraphsAttr = tempList;
		return changedList;
	}

	@Override
	public List<DynInterval<T>> searchChangedNodesAttr(DynInterval<T> interval)
	{
		List<DynInterval<T>> tempList = dynNetwork.searchNodesAttr(interval);
		List<DynInterval<T>> changedList = nonOverlap(currentNodesAttr, tempList);
		currentNodesAttr = tempList;
		return changedList;
	}

	@Override
	public List<DynInterval<T>> searchChangedEdgesAttr(DynInterval<T> interval)
	{
		List<DynInterval<T>> tempList = dynNetwork.searchEdgesAttr(interval);
		List<DynInterval<T>> changedList = nonOverlap(currentEdgesAttr, tempList);
		currentEdgesAttr = tempList;
		return changedList;
	}
	
	@Override
	public int getVisibleNodes()
	{
		return this.visibleNodes;
	}

	@Override
	public int getVisibleEdges()
	{
		return this.visibleEdges;
	}

	@Override
	public void updateView() 
	{
		view.updateView();
	}

	@Override
	public DynNetwork<T> getNetwork() 
	{
		return this.dynNetwork;
	}
	
	@Override
	public CyNetworkView getNetworkView() 
	{
		return this.view;
	}

	@Override
	public double getCurrentTime() 
	{
		return currentTime;
	}

	@Override
	public void setCurrentTime(double currentTime) 
	{
		this.currentTime = currentTime;
	}	
	
	@Override
	public VisualStyle getCurrentVisualStyle() 
	{
		return cyStyleManager.getCurrentVisualStyle();
	}
	
	private List<DynInterval<T>> nonOverlap(List<DynInterval<T>> list1, List<DynInterval<T>> list2) 
	{
		List<DynInterval<T>> diff = new ArrayList<DynInterval<T>>();
		for (DynInterval<T> i : list1)
			if (!list2.contains(i))
			{
				diff.add(i);
				i.setOn(false);
			}
		for (DynInterval<T> i : list2)
			if (!list1.contains(i))
			{
				diff.add(i);
				i.setOn(true);
			}
		return diff;
	}
	
	@Override
	public void initTransparency(int visibility) 
	{
		for (final View<CyNode> nodeView : this.getNetworkView().getNodeViews())
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, visibility);
			nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, visibility);
			
			// TODO: clean. this is a quick fix for a bug in node_label_transparency (core Cytoscape code)
			if (visibility==0)
				nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_TRANSPARENCY, visibility);
		}
		
		for (final View<CyEdge> edgeView : this.getNetworkView().getEdgeViews())
		{
			edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY, visibility);
			edgeView.setLockedValue(BasicVisualLexicon.EDGE_LABEL_TRANSPARENCY, visibility);
		}
	}

}

package org.cytoscape.dyn.internal.view.model;

import org.cytoscape.dyn.internal.model.DynNetwork;
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

/**
 * <code> DynNetworkViewImpl </code> is the interface for the visualization of 
 * dynamic networks {@link DynNetworkView}.
 * 
 * @author sabina
 *
 * @param <T>
 */
public final class DynNetworkViewImpl<T> implements DynNetworkView<T>
{
	private final DynNetwork<T> dynNetwork;
	private final CyNetworkView view;
	private final VisualStyle style;
	final VisualMappingManager vmm;
	
	private double currentTime;

	public DynNetworkViewImpl(
			DynNetwork<T> dynNetwork,
			final CyNetworkViewManager networkViewManager,
			final CyNetworkViewFactory cyNetworkViewFactory,
			final VisualMappingManager vmm)
	{
		this.currentTime = 0;
		this.dynNetwork = dynNetwork;
		this.style = vmm.getDefaultVisualStyle();
		this.dynNetwork.removeMetaNodes();
		this.vmm = vmm;
		
		this.view = cyNetworkViewFactory.createNetworkView(dynNetwork.getNetwork());
		networkViewManager.addNetworkView(view);
		vmm.setVisualStyle(style, view);
		style.apply(view);
		
		// TODO: FIXME
		initNodes();
		initEdges();
		view.updateView();
	}

	@Override
	public CyNetworkView getNetworkView() 
	{
		return this.view;
	}

	@Override
	public int readVisualProperty(CyNode node, VisualProperty<Integer> vp) 
	{
		return view.getNodeView(node).getVisualProperty(vp).intValue();
		
	}

	@Override
	public void writeVisualProperty(CyNode node, VisualProperty<Integer> vp, int value) 
	{
		view.getNodeView(node).setVisualProperty(vp,value);
	}

	@Override
	public int readVisualProperty(CyEdge edge, VisualProperty<Integer> vp) 
	{
		return view.getEdgeView(edge).getVisualProperty(vp).intValue();
	}

	@Override
	public void writeVisualProperty(CyEdge edge, VisualProperty<Integer> vp, int value) 
	{
		view.getEdgeView(edge).setVisualProperty(vp,value);
	}

	@Override
	public void updateView() 
	{
		view.updateView();
	}
	
//	@Override
//	public void viewNestedImage() 
//	{
//		view.setLockedValue(BasicVisualLexicon.NODE_NESTED_NETWORK_IMAGE_VISIBLE,true);
//		VisualStyle viewStyle = vmm.getVisualStyle(view);
//		viewStyle.apply(view);
//	}

	@Override
	public DynNetwork<T> getNetwork() 
	{
		return this.dynNetwork;
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
	
	private void initNodes()
	{
		for (final View<CyNode> nodeView : view.getNodeViews())
		{
			nodeView.setVisualProperty(BasicVisualLexicon.NODE_TRANSPARENCY, 0);
//			nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 0);
			nodeView.setVisualProperty(BasicVisualLexicon.NODE_LABEL_TRANSPARENCY, 0);
		}
	}

	private void initEdges()
	{
		for (final View<CyEdge> edgeView : view.getEdgeViews())
			edgeView.setVisualProperty(BasicVisualLexicon.EDGE_TRANSPARENCY, 0);
	}
}

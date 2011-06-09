package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class ModalSquarePanel extends Panel {

	private static final long serialVersionUID = 7551603131679340398L;
	
	public ModalSquarePanel(String id) {
		super(id);
		
		add(new Label("dateTimeField", "HOLY"));
	}
	
	abstract void onCancel(AjaxRequestTarget target);
	abstract void onSelect(AjaxRequestTarget target, String selection);

}

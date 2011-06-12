package at.ac.tuwien.swag.webapp.in.map;



import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.swag.webapp.SwagWebSession;

public abstract class EmptySquareModalPanel extends Panel {
	private static final long serialVersionUID = 3896873376610498862L;

	public EmptySquareModalPanel(String id) {
        super(id);

        // Create the form, to use later for the buttons
        Form<?> form = new Form<Object>("form");
        add(form);

        // 
        form.add(new AjaxButton("settleMe") {
			private static final long serialVersionUID = -4995261824564883849L;

			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
            	SwagWebSession session = (SwagWebSession) getSession(); 
                long squareId = session.getSelectedSquareId();
            	
            	onSettle(target, squareId);
            }

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				// TODO Auto-generated method stub
				
			}
        });
    }
	abstract void onSettle(AjaxRequestTarget target, long squareId);
}

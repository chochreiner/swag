package at.ac.tuwien.swag.webapp.in.map;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.base.BaseUtils;

public abstract class ForeignSquareModalPanel extends Panel {
	private static final long serialVersionUID = 3896873376610498862L;

	 @Inject
	 private MapUserDAO mapUserDao;
	 
	 @Inject
	 private BaseUtils baseutils;

	private FeedbackPanel feedbackPanel;
	
	public ForeignSquareModalPanel(String id) {
        super(id);

        // Create the form, to use later for the buttons
        Form<Object> form = new Form<Object>("attackForm");
        add(form);

        // 
        form.add(new AjaxButton("attack") {
			private static final long serialVersionUID = 5232221086822182182L;

			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				SwagWebSession session = (SwagWebSession) getSession(); 
                long squareId = session.getSelectedSquareId();
				onAttack(target,squareId );
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				// TODO Auto-generated method stub
				
			}
        });
    }
	
	
	abstract void onAttack(AjaxRequestTarget target, long squareId);
	abstract void onCancel(AjaxRequestTarget target);
}

package at.ac.tuwien.swag.webapp.in.map;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.dao.BuildingDAO;
import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.base.BaseUtils;

public abstract class EmptySquareModalPanel extends Panel {
	private static final long serialVersionUID = 3896873376610498862L;

	    @Inject
	    private SquareDAO squareDao;

	    @Inject
	    private MapUserDAO mapUserDao;
	 
	    @Inject
	    private BaseUtils baseutils;
	    
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
            	/*
                Square square	= squareDao.findById(squareId);
                MapUser mapUser = getMapuser(session.getUsername(), session.getMapname(), square);
                mapUser.getSquares().add(square);
                
                squareDao.beginTransaction();
            		squareDao.update(square);
            		mapUserDao.update(baseutils.reduceRessources(mapUser, 2500));
            	squareDao.commitTransaction();
                */
            	onSettle(target, squareId);
            }

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				// TODO Auto-generated method stub
				
			}
        });
    }
	
	private MapUser getMapuser(String username, String mapname, Square square) {
        String query =
            "SELECT m FROM MapUser m LEFT JOIN FETCH m.squares WHERE m.user.username = :username AND m.map.name = :mapname";

        SwagWebSession session = (SwagWebSession) getSession();

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", session.getUsername());
        values.put("mapname", session.getMapname());

        List<MapUser> buffer = mapUserDao.findByQuery(query, values);

        if (!buffer.isEmpty()) {
        	if (!buffer.get(0).getSquares().contains(square)) {
                setResponsePage(MapPage.class);
            }
        	
            return buffer.get(0);
        } else {
            setResponsePage(MapPage.class);
            
            return null;
        }
    }
	
	abstract void onSettle(AjaxRequestTarget target, long squareId);
}

package at.ac.tuwien.swag.webapp.out;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.inject.Inject;

public class StartPage extends OutPage {
	private static final long serialVersionUID = 1000701427791102913L;

	public StartPage(PageParameters parameters) {
        super(parameters);

        Query q = em.createQuery( "SELECT m FROM Map m" );
        
        this.add( 
        	new Label( "message", 
        		"If you see this the database (probably) works <br/>" + 
        		"Maps: " + q.getResultList().toString()     + "<br/>"
        	).setEscapeModelStrings( false )
        );
    }

    @PersistenceContext
    @Inject
    private EntityManager em;
	
}

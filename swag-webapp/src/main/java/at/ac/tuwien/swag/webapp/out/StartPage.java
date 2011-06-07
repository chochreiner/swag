package at.ac.tuwien.swag.webapp.out;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.inject.Inject;

public class StartPage extends OutPage {
	private static final long serialVersionUID = 1000701427791102913L;

	public StartPage(PageParameters parameters) {
        super(parameters);

        String str = "If you see this the database (probably) works <br/>";
        
        List<?> list = em.createQuery( "SELECT m FROM Map m" ).getResultList();
        
        str += "Maps: " + list.size() + "<br/>";
        
        for ( Object o : em.createQuery( "SELECT m FROM Map m" ).getResultList() ) {
        	str += o + "<br/>";
        }

        this.add( new Label( "message", str ).setEscapeModelStrings( false ) );
    }

    @PersistenceContext
    @Inject
    private EntityManager em;
	
}

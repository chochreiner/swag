package at.ac.tuwien.swag.webapp.in.form;

import org.apache.wicket.markup.html.form.Form;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.webapp.SwagWebSession;

public final class DeleteUserForm extends Form<Void> {
	private static final long serialVersionUID = 7175270445878902006L;

	@Inject
	private UserDAO users;
	
	public DeleteUserForm( String id ) {
		super( id );
	}
	
	@Override
	public void onSubmit() {
		SwagWebSession session = (SwagWebSession) getSession();
		
		User user = users.findByUsername( session.getUsername() );
		
		users.beginTransaction();
			users.delete( user );
		users.commitTransaction();
			
		// log out after we're done
		setResponsePage( getApplication().getHomePage() );
		((SwagWebSession)getSession()).signOut();
		
		info( "Goodbye" );
	}

}

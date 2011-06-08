package at.ac.tuwien.swag.webapp.in.admin;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.SQLHelper;
import at.ac.tuwien.swag.webapp.in.InPage;

@AuthorizeInstantiation({Roles.ADMIN})
public class DropDBPage extends InPage {
	private static final long serialVersionUID = 1L;

	@Inject
	private SQLHelper helper;
	
	public DropDBPage( PageParameters parameters ) {
		super( parameters );

		add( new AjaxLazyLoadPanel( "output" ) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getLazyLoadComponent( String markupId ) {
				helper.dropDatabase();
				
				return new Label( markupId, "Congratulations, you destroyed the entire DB." );
			}
		});		
	}

}

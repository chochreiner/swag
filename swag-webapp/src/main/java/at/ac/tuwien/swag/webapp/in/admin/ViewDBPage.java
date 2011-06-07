package at.ac.tuwien.swag.webapp.in.admin;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import com.google.inject.Inject;

import at.ac.tuwien.swag.webapp.in.InPage;

@AuthorizeInstantiation({Roles.ADMIN})
public class ViewDBPage extends InPage {
	private static final long serialVersionUID = -8566989222495867333L;

	private final RequiredTextField<String> sql;
	private final Label                     results;
	
	@Inject
	private EntityManager em;
	
	public ViewDBPage( PageParameters parameters ) {
		super( parameters );
		
		final FeedbackPanel feedback = new FeedbackPanel( "feedback" );
		feedback.setOutputMarkupId( true );
		
		Form<String> sqlForm = new Form<String>( "sqlForm" );
		
		sql = new RequiredTextField<String>( "sql", new Model<String>() );
		sqlForm.add( sql );
		
		sqlForm.add( new AjaxButton("query", sqlForm ) {
			private static final long serialVersionUID = 5942735452382715333L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					List<?> objs = em.createQuery( sql.getModel().getObject() ).getResultList();
					
					String str = "Results: " + objs.size() + "<br/><br/>";
					
					for ( Object o : objs ) {
						str += o + "<br/>";
					}
					
					results.setDefaultModelObject( str );					
				} catch ( Exception e ) {
					String str = "An error occured: <br/>";
					
					for ( Throwable t = e; t != null; t = t.getCause() ) {
						str += t + "<br/>";
						
						for ( StackTraceElement ste : e.getStackTrace() ) {
							str += "\t" + ste + "<br/>";
						}						
						
						str += "<br/>";
						
						if ( t == t.getCause() ) break;
					}
					
					results.setDefaultModelObject( str );
				}
				
				// repaint the feedback panel so that it is hidden
				target.add( feedback, results );
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				// repaint the feedback panel so errors are shown
				target.add( feedback, results );
			}
		});
		sqlForm.add( new AjaxButton( "update", sqlForm ) {
			private static final long serialVersionUID = 5942735452382715333L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					int rowsAffected = em.createQuery( sql.getModel().getObject() ).executeUpdate();
					
					String str = rowsAffected + " row(s) affected <br/>";
					
					results.setDefaultModelObject( str );					
				} catch ( Exception e ) {
					String str = "An error occured: " + e.getMessage() + "<br/>";
					
					for ( StackTraceElement ste : e.getStackTrace() ) {
						str += "\t" + ste + "<br/>";
					}
					
					results.setDefaultModelObject( str );
				}
				
				// repaint the feedback panel so that it is hidden
				target.add( feedback, results );
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				// repaint the feedback panel so errors are shown
				target.add( feedback, results );
			}
		});
		
		results = new Label( "results", new Model<String>() );
		results.setEscapeModelStrings( false );
		results.setOutputMarkupId( true );
		
		this.add( feedback, sqlForm, results );
	}	
}

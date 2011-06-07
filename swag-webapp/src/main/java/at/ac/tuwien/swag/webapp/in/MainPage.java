package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@AuthorizeInstantiation({"ADMIN", "USER"})
public class MainPage extends InPage {
	private static final long serialVersionUID = -6454931055063290217L;

	public MainPage(PageParameters parameters) {
		super(parameters);
	}
}

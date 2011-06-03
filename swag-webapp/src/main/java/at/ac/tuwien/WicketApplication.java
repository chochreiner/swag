package at.ac.tuwien;

import org.apache.wicket.protocol.http.WebApplication;

import at.ac.tuwien.out.OutPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start
 * class.
 * 
 * @see at.ac.tuwien.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<OutPage> getHomePage() {
        return OutPage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        // add your configuration here
    }
}

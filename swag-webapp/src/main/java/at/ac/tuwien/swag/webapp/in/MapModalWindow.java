package at.ac.tuwien.swag.webapp.in;



import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;


public abstract class MapModalWindow extends ModalWindow {
	private static final long serialVersionUID = -7126506235299190200L;

	public MapModalWindow(String id) {
        super(id);

        // Set sizes of this ModalWindow. You can also do this from the HomePage
        // but its not a bad idea to set some good default values.
        setInitialWidth(800);
        setInitialHeight(600);

        setTitle("TEST MODAL");

        // Set the content panel, implementing the abstract methods
        setContent(new SelectContentPanel(this.getContentId()){
			private static final long serialVersionUID = -1675999722693245461L;

			void onCancel(AjaxRequestTarget target) {
                MapModalWindow.this.onCancel(target);
            }

            void onSelect(AjaxRequestTarget target, String selection) {
                MapModalWindow.this.onSelect(target, selection);
            }
        });
    }

    abstract void onCancel(AjaxRequestTarget target);

    abstract void onSelect(AjaxRequestTarget target, String selection);

}

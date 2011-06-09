package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public abstract class ModalSquareWindow extends ModalWindow {

	private static final long serialVersionUID = 2602717039382320874L;

	public ModalSquareWindow(String id) {
		super(id);
		
        setInitialWidth(450);
        setInitialHeight(300);

        setTitle("Select something");

        // Set the content panel, implementing the abstract methods
        setContent(new ModalSquarePanel(this.getContentId()){
			private static final long serialVersionUID = 7901709027539590941L;

			void onCancel(AjaxRequestTarget target) {
            	ModalSquareWindow.this.onCancel(target);
            }

            void onSelect(AjaxRequestTarget target, String selection) {
            	ModalSquareWindow.this.onSelect(target, selection);
            }
        });
	}
	 abstract void onCancel(AjaxRequestTarget target);
	 
     abstract void onSelect(AjaxRequestTarget target, String selection);
}

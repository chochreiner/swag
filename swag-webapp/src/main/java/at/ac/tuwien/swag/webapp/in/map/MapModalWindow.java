package at.ac.tuwien.swag.webapp.in.map;



import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

import at.ac.tuwien.swag.webapp.in.base.BasePanel;



public abstract class MapModalWindow extends ModalWindow {
	private static final long serialVersionUID = -7126506235299190200L;

	private long squareId;
	
	public MapModalWindow(String id) {
        super(id);
        squareId =new Long(111);
        // Set sizes of this ModalWindow. You can also do this from the HomePage
        // but its not a bad idea to set some good default values.
        setInitialWidth(800);
        setInitialHeight(600);

        setTitle("TEST MODAL");
        
        // Set the content panel, implementing the abstract methods
        setContent(new SelectContentPanel(this.getContentId()){
			private static final long serialVersionUID = -1675999722693245461L;

			void onCancel(AjaxRequestTarget target) {
				
				setTitle("BLAAA");
                MapModalWindow.this.onCancel(target);
            }

            void onSelect(AjaxRequestTarget target, String selection) {
                
            	setTitle("BLAAA");
            	MapModalWindow.this.onSelect(target, selection);
            }
        });
    }
	
	public void loadBasePanel(long squareId) {
		setContent(new BasePanel(this.getContentId(), squareId){

			@Override
			public void onCancel(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSelect(AjaxRequestTarget target, String selection) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSubmitButton(AjaxRequestTarget target, long squareId) {
				setTitle("BLAAA");
			}
		});
	}

    public long getSquareId() {
		return squareId;
	}



	public void setSquareId(long squareId) {
		this.squareId = squareId;
	}



	abstract void onCancel(AjaxRequestTarget target);

    abstract void onSelect(AjaxRequestTarget target, String selection);
}

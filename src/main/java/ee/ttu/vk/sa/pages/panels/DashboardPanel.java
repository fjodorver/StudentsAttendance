package ee.ttu.vk.sa.pages.panels;

import ee.ttu.vk.sa.domain.Attendance;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.ListModel;

/**
 * Created by strukov on 3/2/16.
 */
public class DashboardPanel extends Panel {

    public DashboardPanel(String id, ListModel<Attendance> listModel) {
        super(id);
    }
}

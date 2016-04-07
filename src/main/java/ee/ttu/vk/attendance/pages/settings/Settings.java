package ee.ttu.vk.attendance.pages.settings;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel;
import ee.ttu.vk.attendance.pages.AbstractPage;
import ee.ttu.vk.attendance.service.TeacherService;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by vadimstrukov on 2/27/16.
 */
public class Settings extends AbstractPage {

    @SpringBean
    private TeacherService teacherService;
    private AjaxBootstrapTabbedPanel<AbstractTab> tabbedPanel;

    public Settings(){
        List<AbstractTab> tabs = Lists.newArrayList();
        tabs.add(new AbstractTab(new ResourceModel("navbar.menu.settings.user")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new UserSettingsPanel(s, target -> target.add(navbar));
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("navbar.menu.settings.local")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new LocalizationSettings(s);
            }
        });
        add(tabbedPanel = new AjaxBootstrapTabbedPanel<>("tabs", tabs));
    }
}

package ee.ttu.vk.attendance.pages.listeners;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import java.io.IOException;
import java.io.Serializable;

@FunctionalInterface
public interface AjaxAction<T> extends Serializable {
    void onSubmit(AjaxRequestTarget target, IModel<T> model) throws IOException;
}

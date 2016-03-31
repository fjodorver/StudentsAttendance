package ee.ttu.vk.attendance.pages.listeners;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

@FunctionalInterface
public interface AjaxOnClick extends Serializable{
    void onClick(AjaxRequestTarget target);
}

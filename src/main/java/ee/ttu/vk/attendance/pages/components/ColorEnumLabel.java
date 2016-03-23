package ee.ttu.vk.attendance.pages.components;

import com.google.common.collect.Maps;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.LabelBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.LabelType;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import java.util.Map;
import java.util.Optional;

/**
 * Created by fjodor on 15.02.16.
 */
public class ColorEnumLabel<T extends Enum<T>> extends EnumLabel<T> {
    private Map<T, LabelType> labelTypeMap;

    public ColorEnumLabel(String id, IModel<T> model) {
        super(id, model);
        labelTypeMap = Maps.newHashMap();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(new LabelBehavior(new AbstractReadOnlyModel<LabelType>() {
            @Override
            public LabelType getObject() {
                return Optional.ofNullable(labelTypeMap.get(getModel().getObject())).orElse(LabelType.Default);
            }
        }));
    }

    public ColorEnumLabel<T> addEnumLabel(T t, LabelType labelType) {
        labelTypeMap.put(t, labelType);
        return this;
    }
}

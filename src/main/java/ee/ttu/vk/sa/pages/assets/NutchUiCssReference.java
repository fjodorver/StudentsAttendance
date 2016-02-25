package ee.ttu.vk.sa.pages.assets;

import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Created by vadimstrukov on 2/25/16.
 */
public class NutchUiCssReference extends CssResourceReference {
    private static final long serialVersionUID = 1L;

    private static final NutchUiCssReference INSTANCE = new NutchUiCssReference();

    public static NutchUiCssReference instance() {
        return INSTANCE;
    }

    private NutchUiCssReference() {
        super(NutchUiCssReference.class, "nutch-style.css");
    }
}

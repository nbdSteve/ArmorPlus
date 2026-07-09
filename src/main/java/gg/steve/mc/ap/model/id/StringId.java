package gg.steve.mc.ap.model.id;

import org.apache.commons.lang3.Validate;

public abstract class StringId extends TypedString {
    protected StringId(String id) {
        super(id);
        Validate.notEmpty(id);
    }
}

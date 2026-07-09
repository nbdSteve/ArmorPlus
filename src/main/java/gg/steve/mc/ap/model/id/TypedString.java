package gg.steve.mc.ap.model.id;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TypedString {
    private final String id;

    protected TypedString(String id) {
        Validate.notNull(id);
        this.id = id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        TypedString other = (TypedString) obj;
        return new EqualsBuilder().append(id, other.id).isEquals();
    }

    @Override
    public String toString() {
        return id;
    }

    public static List<String> toStrings(Collection<? extends TypedString> typedStrings) {
        return typedStrings.stream().map(TypedString::toString).collect(Collectors.toList());
    }
}

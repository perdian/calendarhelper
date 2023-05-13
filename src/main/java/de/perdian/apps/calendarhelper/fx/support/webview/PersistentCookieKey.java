package de.perdian.apps.calendarhelper.fx.support.webview;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.net.HttpCookie;

class PersistentCookieKey implements Serializable {

    static final long serialVersionUID = 1L;

    private String domain = null;
    private String name = null;

    PersistentCookieKey(HttpCookie cookie) {
        this.setDomain(cookie.getDomain());
        this.setName(cookie.getName());
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof PersistentCookieKey thatKey) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getDomain(), thatKey.getDomain());
            equalsBuilder.append(this.getName(), thatKey.getName());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getDomain());
        hashCodeBuilder.append(this.getName());
        return hashCodeBuilder.toHashCode();
    }

    String getDomain() {
        return domain;
    }
    private void setDomain(String domain) {
        this.domain = domain;
    }

    String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }

}

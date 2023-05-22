package de.perdian.apps.calendarhelper.support.fx.webview;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.HttpCookie;

public class PersistentCookie implements Externalizable {

    static final long serialVersionUID = 1L;

    private HttpCookie cookie = null;

    public PersistentCookie() {
    }

    public PersistentCookie(HttpCookie cookie) {
        this.setCookie(cookie);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getCookie().getName());
        out.writeObject(this.getCookie().getValue());
        out.writeObject(this.getCookie().getComment());
        out.writeObject(this.getCookie().getCommentURL());
        out.writeBoolean(this.getCookie().getDiscard());
        out.writeObject(this.getCookie().getDomain());
        out.writeLong(this.getCookie().getMaxAge());
        out.writeObject(this.getCookie().getPath());
        out.writeObject(this.getCookie().getPortlist());
        out.writeBoolean(this.getCookie().getSecure());
        out.writeInt(this.getCookie().getVersion());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        HttpCookie cookie = new HttpCookie((String) in.readObject(), (String) in.readObject());
        cookie.setComment((String) in.readObject());
        cookie.setCommentURL((String) in.readObject());
        cookie.setDiscard(in.readBoolean());
        cookie.setDomain((String) in.readObject());
        cookie.setMaxAge(in.readLong());
        cookie.setPath((String) in.readObject());
        cookie.setPortlist((String) in.readObject());
        cookie.setSecure(in.readBoolean());
        cookie.setVersion(in.readInt());
        this.setCookie(cookie);
    }

    HttpCookie getCookie() {
        return this.cookie;
    }
    private void setCookie(HttpCookie cookie) {
        this.cookie = cookie;
    }

}

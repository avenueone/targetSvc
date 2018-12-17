package org.avenue1.target.domain.enumeration;

/**
 * The TargetTypeEnum enumeration.
 */
public enum TargetTypeEnum {
    STORE("a1-tgt-str","Store"), STOREGROUP("a1-tgt-str","Store"),
    EMAILGROUP("a1-tgt-emailg","Email-Group"), EMAIL("a1-tgt-email","EMail"),
    URL("a1-tgt-url","URL"), SERVICE("a1-tgt-service","Service"),
    URI("a1-tgt-uri","URI"), JSON("a1-tgt-json","JSON");

    private String icon;
    private String displayName;

    TargetTypeEnum(String icon, String displayName) {
        this.icon = icon;
        this.displayName = displayName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }}

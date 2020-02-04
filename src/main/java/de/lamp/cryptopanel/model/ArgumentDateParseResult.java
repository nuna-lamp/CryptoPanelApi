package de.lamp.cryptopanel.model;

public class ArgumentDateParseResult {
    public String info;
    public Boolean success;

    public ArgumentDateParseResult() {
        this.info = "";
        this.success=true;
    }

    public ArgumentDateParseResult(String info, Boolean success) {
        this.info = info;
        this.success=success;
    }
}

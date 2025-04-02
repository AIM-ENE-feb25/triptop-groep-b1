package org.prototype.demo.API;



public class FallbackStrategy implements APIServiceActionStrategy {
    @Override
    public String getData() {
        return "Insert fallback data";
    }

}

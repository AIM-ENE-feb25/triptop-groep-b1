package org.prototype.demo.API;

import org.springframework.stereotype.Service;

@Service
public class FallbackStrategy implements APIServiceActionStrategy {
    @Override
    public String getData() {
        return "Insert fallback data";
    }

}

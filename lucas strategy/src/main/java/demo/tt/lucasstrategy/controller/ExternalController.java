package demo.tt.lucasstrategy.controller;

import demo.tt.lucasstrategy.API.BookingAPICall;
import demo.tt.lucasstrategy.API.BookingErrorHandler;
import demo.tt.lucasstrategy.API.StrategyContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExternalController {
    StrategyContext strategyContext;
    ExternalController(StrategyContext strategyContext){
        this.strategyContext = strategyContext;
    }
    @GetMapping("getData")
    public String getData(){
        strategyContext.setStrategy(new BookingAPICall());
        try {
            return strategyContext.getData();
        }catch(Exception e){
            strategyContext.setStrategy(new BookingErrorHandler());
            return strategyContext.getData();
        }
    }
}

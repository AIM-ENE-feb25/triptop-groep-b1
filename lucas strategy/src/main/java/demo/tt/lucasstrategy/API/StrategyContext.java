package demo.tt.lucasstrategy.API;

import org.springframework.stereotype.Service;

@Service
public class StrategyContext {
    APIStrategy strategy;
    public void setStrategy(APIStrategy strategy){
        this.strategy = strategy;
    }
    public String getData(){
        return strategy.getData();
    }
}

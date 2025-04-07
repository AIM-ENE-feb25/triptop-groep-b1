package demo.tt.lucasstrategy.API;

public class BookingErrorHandler implements APIStrategy{
    @Override
    public String getData() {
        return "An error has occured";
    }
}

package examples;

interface Messenger {
    String getMessage();
}

interface BookingService{
    void setMessenger(Messenger messenger);
    void processBooking();
}

class DefaultBookingService implements BookingService {

    private Messenger messenger;

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public void processBooking() {
        // use the injected Messenger object...
    }
}
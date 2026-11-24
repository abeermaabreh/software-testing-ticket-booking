package edu.course.booking;

public class BookingManager {

    private final IPaymentGateway paymentGateway;
    private final IEventRepository eventRepository;
    private final INotificationService notificationService;

    public BookingManager(
            IPaymentGateway paymentGateway,
            IEventRepository eventRepository,
            INotificationService notificationService) {

        this.paymentGateway = paymentGateway;
        this.eventRepository = eventRepository;
        this.notificationService = notificationService;
    }

    public String book(BookingRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
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
        if (request.getTicketQuantity() < 1 ||
        request.getTicketQuantity() > 5) {
    throw new IllegalArgumentException("Invalid ticket quantity");
}
       eventRepository.isSoldOut(request.getEventId());

int totalAmountInCents = calculateTotalAmount(request);

String transactionId =
        paymentGateway.processPayment(totalAmountInCents);

eventRepository.saveBooking(request);

notificationService.sendConfirmation(request, transactionId);

return transactionId;
    }
 private int calculateTotalAmount(BookingRequest request) {
    return request.getTicketQuantity() *
            request.getPricePerTicketInCents();
   }
}
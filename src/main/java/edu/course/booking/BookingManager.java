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
     validateTicketQuantity(request); 
     if (request.getCustomerId() == null ||
        request.getCustomerId().isBlank()) {
    throw new IllegalArgumentException("Invalid customer ID");
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
   private void validateTicketQuantity(BookingRequest request) {
    if (request.getTicketQuantity() < 1 ||
            request.getTicketQuantity() > 5) {
        throw new IllegalArgumentException("Invalid ticket quantity");
    }
}
}
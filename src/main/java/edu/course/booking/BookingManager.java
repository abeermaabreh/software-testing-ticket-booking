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
        if (request == null) {
    throw new IllegalArgumentException("Booking request must not be null");
}
     validateTicketQuantity(request); 
     validateTicketPrice(request);
     if (request.getCustomerId() == null ||
        request.getCustomerId().isBlank()) 
        {
    throw new IllegalArgumentException("Invalid customer ID");
}
if (request.getEventId() == null ||
        request.getEventId().isBlank()) {
    throw new IllegalArgumentException("Invalid event ID");
}
       if (eventRepository.isSoldOut(request.getEventId())) {
    throw new IllegalArgumentException("Event is sold out");
}

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

private void validateTicketPrice(BookingRequest request) {
    if (request.getPricePerTicketInCents() <= 0) {
        throw new IllegalArgumentException("Invalid ticket price");
    }
}
}



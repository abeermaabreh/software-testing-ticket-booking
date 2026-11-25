
package edu.course.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingManagerTest {

    @Test
    void successfulBooking() {

        // Arrange
        IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
        IEventRepository eventRepository = mock(IEventRepository.class);
        INotificationService notificationService =
                mock(INotificationService.class);

        BookingManager manager = new BookingManager(
                paymentGateway,
                eventRepository,
                notificationService
        );

        BookingRequest request = new BookingRequest(
                "C101", "E20", 3, 2500
        );

        String expectedTransactionId = "TX-C101-E20-001";

        when(eventRepository.isSoldOut("E20")).thenReturn(false);
        when(paymentGateway.processPayment(7500))
                .thenReturn(expectedTransactionId);

        // Act
        String result = manager.book(request);

        // Assert
        assertEquals(expectedTransactionId, result);

        verify(eventRepository, times(1)).isSoldOut("E20");

        verify(paymentGateway, times(1)).processPayment(7500);

        verify(eventRepository, times(1)).saveBooking(request);

        verify(notificationService, times(1))
                .sendConfirmation(request, expectedTransactionId);
    } 
       @Test
    void invalidTicketQuantity() {

        // Arrange
        IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
        IEventRepository eventRepository = mock(IEventRepository.class);
        INotificationService notificationService =
                mock(INotificationService.class);

        BookingManager manager = new BookingManager(
                paymentGateway,
                eventRepository,
                notificationService
        );

        BookingRequest request = new BookingRequest(
                "C101", "E20", 6, 2500
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> manager.book(request));

        verifyNoInteractions(
                paymentGateway,
                eventRepository,
                notificationService
        );
    }
    @Test
void zeroTicketQuantity() {

    // Arrange
    IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
    IEventRepository eventRepository = mock(IEventRepository.class);
    INotificationService notificationService =
            mock(INotificationService.class);

    BookingManager manager = new BookingManager(
            paymentGateway,
            eventRepository,
            notificationService
    );

    BookingRequest request = new BookingRequest(
            "C101", "E20", 0, 2500
    );

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> manager.book(request));

    verifyNoInteractions(
            paymentGateway,
            eventRepository,
            notificationService
    );
}
@Test
void invalidCustomerId() {

    // Arrange
    IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
    IEventRepository eventRepository = mock(IEventRepository.class);
    INotificationService notificationService =
            mock(INotificationService.class);

    BookingManager manager = new BookingManager(
            paymentGateway,
            eventRepository,
            notificationService
    );

    BookingRequest request = new BookingRequest(
            "", "E20", 3, 2500
    );

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> manager.book(request));

    verifyNoInteractions(
            paymentGateway,
            eventRepository,
            notificationService
    );
}
@Test
void invalidEventId() {

    // Arrange
    IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
    IEventRepository eventRepository = mock(IEventRepository.class);
    INotificationService notificationService =
            mock(INotificationService.class);

    BookingManager manager = new BookingManager(
            paymentGateway,
            eventRepository,
            notificationService
    );

    BookingRequest request = new BookingRequest(
            "C101", "", 3, 2500
    );

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> manager.book(request));

    verifyNoInteractions(
            paymentGateway,
            eventRepository,
            notificationService
    );
}
@Test
void invalidTicketPrice() {

    // Arrange
    IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
    IEventRepository eventRepository = mock(IEventRepository.class);
    INotificationService notificationService =
            mock(INotificationService.class);

    BookingManager manager = new BookingManager(
            paymentGateway,
            eventRepository,
            notificationService
    );

    BookingRequest request = new BookingRequest(
            "C101", "E20", 3, 0
    );

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> manager.book(request));

    verifyNoInteractions(
            paymentGateway,
            eventRepository,
            notificationService
    );
}
@Test
void soldOutEvent() {

    // Arrange
    IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
    IEventRepository eventRepository = mock(IEventRepository.class);
    INotificationService notificationService =
            mock(INotificationService.class);

    BookingManager manager = new BookingManager(
            paymentGateway,
            eventRepository,
            notificationService
    );

    BookingRequest request = new BookingRequest(
            "C205", "E35", 2, 1800
    );

    when(eventRepository.isSoldOut("E35")).thenReturn(true);

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> manager.book(request));

    verify(eventRepository, times(1)).isSoldOut("E35");
    verify(eventRepository, never()).saveBooking(any());

    verifyNoInteractions(paymentGateway, notificationService);
}
@Test
void failedPayment() {

    // Arrange
    IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
    IEventRepository eventRepository = mock(IEventRepository.class);
    INotificationService notificationService =
            mock(INotificationService.class);

    BookingManager manager = new BookingManager(
            paymentGateway,
            eventRepository,
            notificationService
    );

    BookingRequest request = new BookingRequest(
            "C310", "E42", 2, 2200
    );

    when(eventRepository.isSoldOut("E42")).thenReturn(false);

    when(paymentGateway.processPayment(4400))
            .thenThrow(new PaymentFailedException("Payment failed"));

    // Act & Assert
    assertThrows(PaymentFailedException.class,
            () -> manager.book(request));

    verify(eventRepository, times(1)).isSoldOut("E42");

    verify(paymentGateway, times(1)).processPayment(4400);

    verify(eventRepository, never()).saveBooking(any());

    verifyNoInteractions(notificationService);
}

@Test
    void nullBookingRequest() {

        
        IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
        IEventRepository eventRepository = mock(IEventRepository.class);
        INotificationService notificationService =
                mock(INotificationService.class);

        BookingManager manager = new BookingManager(
                paymentGateway,
                eventRepository,
                notificationService
        );

        
        assertThrows(IllegalArgumentException.class,
                () -> manager.book(null));

        verifyNoInteractions(
                paymentGateway,
                eventRepository,
                notificationService
        );
    }

    @Test
    void negativeTicketQuantity() {
        IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
        IEventRepository eventRepository = mock(IEventRepository.class);
        INotificationService notificationService =
                mock(INotificationService.class);

        BookingManager manager = new BookingManager(
                paymentGateway,
                eventRepository,
                notificationService
        );

        BookingRequest request = new BookingRequest(
                "C101", "E20", -1, 2500
        );

        assertThrows(IllegalArgumentException.class,
                () -> manager.book(request));

        verifyNoInteractions(
                paymentGateway,
                eventRepository,
                notificationService
        );
    }

    @Test
    void whitespaceCustomerId() {
        IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
        IEventRepository eventRepository = mock(IEventRepository.class);
        INotificationService notificationService =
                mock(INotificationService.class);

        BookingManager manager = new BookingManager(
                paymentGateway,
                eventRepository,
                notificationService
        );

        BookingRequest request = new BookingRequest(
                "   ", "E20", 3, 2500
        );

        assertThrows(IllegalArgumentException.class,
                () -> manager.book(request));

        verifyNoInteractions(
                paymentGateway,
                eventRepository,
                notificationService
        );
    }
}

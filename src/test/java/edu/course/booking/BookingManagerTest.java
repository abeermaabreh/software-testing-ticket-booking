
package edu.course.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;

class BookingManagerTest {
    private IPaymentGateway paymentGateway;
    private IEventRepository eventRepository;
    private INotificationService notificationService;
    private BookingManager manager;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(IPaymentGateway.class);
        eventRepository = mock(IEventRepository.class);
        notificationService = mock(INotificationService.class);
        manager = new BookingManager(
                paymentGateway,
                eventRepository,
                notificationService
        );
    }

    @Test
    void successfulBooking() {

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

    @Test
    void minimumValidTicketQuantity() {

        BookingRequest request = new BookingRequest(
                "C101", "E20", 1, 2500
        );

        when(eventRepository.isSoldOut("E20")).thenReturn(false);
        when(paymentGateway.processPayment(2500)).thenReturn("TX-MIN-001");

        String result = manager.book(request);

        assertEquals("TX-MIN-001", result);
        verify(paymentGateway).processPayment(2500);
        verify(eventRepository).saveBooking(request);
        verify(notificationService).sendConfirmation(request, "TX-MIN-001");
    }

    @Test
    void maximumValidTicketQuantity() {

        BookingRequest request = new BookingRequest(
                "C101", "E20", 5, 2500
        );

        when(eventRepository.isSoldOut("E20")).thenReturn(false);
        when(paymentGateway.processPayment(12500)).thenReturn("TX-MAX-005");

        String result = manager.book(request);

        assertEquals("TX-MAX-005", result);
        verify(paymentGateway).processPayment(12500);
        verify(eventRepository).saveBooking(request);
        verify(notificationService).sendConfirmation(request, "TX-MAX-005");
    }

    @Test
    void nullPaymentGatewayInConstructor() {

        assertThrows(IllegalArgumentException.class,
                () -> new BookingManager(
                        null,
                        eventRepository,
                        notificationService
                ));
    }

    @Test
    void nullEventRepositoryInConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new BookingManager(
                        paymentGateway,
                        null,
                        notificationService
                ));
    }

    @Test
    void nullNotificationServiceInConstructor() {

        assertThrows(IllegalArgumentException.class,
                () -> new BookingManager(
                        paymentGateway,
                        eventRepository,
                        null
                ));
    }
}


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
}

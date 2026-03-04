package com.sindhgov.pbp.repository;

import com.sindhgov.pbp.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingRepositoryJdbcTest {

    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    @BeforeEach
    void setUp() throws Exception {
        System.setProperty("db.url", H2_URL);
        System.setProperty("db.user", "sa");
        System.setProperty("db.password", "");

        try (Connection con = DriverManager.getConnection(H2_URL, "sa", "");
             Statement st = con.createStatement()) {
            st.execute("DROP TABLE IF EXISTS bookings");
            st.execute("""
                    CREATE TABLE bookings (
                        id BIGINT PRIMARY KEY,
                        customer_id BIGINT,
                        schedule_id BIGINT,
                        seats VARCHAR(255),
                        status VARCHAR(50)
                    )""");
            st.execute("INSERT INTO bookings VALUES (1, 10, 20, '1A,1B', 'CONFIRMED')");
        }
    }

    @Test
    void findById_returnsBookingWhenExists() {
        BookingRepositoryJdbc repo = new BookingRepositoryJdbc();
        Optional<Booking> result = repo.findById(1L);

        assertTrue(result.isPresent());
        Booking b = result.get();
        assertEquals(1L, b.id);
        assertEquals(10L, b.customerId);
        assertEquals(20L, b.scheduleId);
        assertEquals("1A,1B", b.seatsCsv);
        assertEquals("CONFIRMED", b.status);
    }

    @Test
    void findById_returnsEmptyWhenNotFound() {
        BookingRepositoryJdbc repo = new BookingRepositoryJdbc();
        Optional<Booking> result = repo.findById(999L);

        assertFalse(result.isPresent());
    }
}

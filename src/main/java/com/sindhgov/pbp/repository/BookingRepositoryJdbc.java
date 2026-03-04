package com.sindhgov.pbp.repository;

import com.sindhgov.pbp.db.Db;
import com.sindhgov.pbp.model.Booking;

import java.util.Optional;

public class BookingRepositoryJdbc {

    public Optional<Booking> findById(long id) {
        String sql = "SELECT id, customer_id, schedule_id, seats, status FROM bookings WHERE id=?";
        try (var con = Db.getConnection();
             var ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking b = new Booking();
                    b.id = rs.getLong("id");
                    b.customerId = rs.getLong("customer_id");
                    b.scheduleId = rs.getLong("schedule_id");
                    b.seatsCsv = rs.getString("seats");
                    b.status = rs.getString("status");
                    return Optional.of(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

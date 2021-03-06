/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entity.HotelFacilityBooking;
import entity.RoomBooking;
import error.NoResultException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BookingSession implements BookingSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RoomBooking> getAllRoomBooking() throws NoResultException {
        Query q;
        q = em.createQuery("SELECT rb FROM RoomBooking rb ");
        return q.getResultList();
    }

    @Override
    public List<RoomBooking> getAllRoomBookingByStatus(String status, String hotelCodeName) throws NoResultException {
        Query q;
        if (hotelCodeName == null) {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.status) = :status ");
            q.setParameter("status", status.toLowerCase());
        } else {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.status) = :status AND "
                    + "LOWER(rb.bookedRoom.hotel.hotelCodeName) = :hotelCodeName");
            q.setParameter("status", status.toLowerCase());
            q.setParameter("hotelCodeName", hotelCodeName.toLowerCase());
        }

        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            return null;
        }
    }
    @Override
    public List<RoomBooking> getAllRoomBookingByHotel(String hotelCodeName) throws NoResultException {
        Query q;
      
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.bookedRoom.hotel.hotelCodeName) = :hotelCodeName");
            q.setParameter("hotelCodeName", hotelCodeName.toLowerCase());
        
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            return null;
        }
    }

    @Override
    public List<RoomBooking> getAllRoomBookingByDate(Date todayDate) throws NoResultException {
        Query q;
        q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                + "rb.bookInDate <= :todayDate "
                + "AND rb.bookOutDate >= :todayDate ");
        q.setParameter("todayDate", todayDate);
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public List<RoomBooking> getAllRoomBookingByCheckoutDate(Date checkoutDate, String hotelCodeName) throws NoResultException {
        Query q;
        //Wont filter by hotel if hotelcodename is not specified
        if (hotelCodeName == null) {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "rb.bookInDate <= :checkoutDate ");
            q.setParameter("todayDate", checkoutDate);
        } else {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "rb.bookInDate <= :checkoutDate AND "
                    + "LOWER(rb.bookedRoom.hotel.hotelCodeName) = :hotelCodeName");
            q.setParameter("checkoutDate", checkoutDate);
            q.setParameter("hotelCodeName", hotelCodeName.toLowerCase());
        }
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public RoomBooking getRoomBookingByID(Long roomBookingID) throws NoResultException {
        RoomBooking rb = em.find(RoomBooking.class, roomBookingID);
        if (rb != null) {
            return rb;
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public List<RoomBooking> getRoomBookingByPassportNum(String passportNum, String hotelCodeName) throws NoResultException {
        Query q;
        if (hotelCodeName == null) {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.passportNum) = :passportNum");
            q.setParameter("passportNum", passportNum.toLowerCase());
        } else {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.passportNum) = :passportNum AND "
                    + "LOWER(rb.bookedRoom.hotel.hotelCodeName) = :hotelCodeName");
            q.setParameter("passportNum", passportNum.toLowerCase());
            q.setParameter("hotelCodeName", hotelCodeName.toLowerCase());

        }
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public List<RoomBooking> getRoomBookingByPassportNumCheckInToday(String passportNum, Date todayDate, String hotelCodeName) throws NoResultException {
        Query q;
        if (hotelCodeName == null) {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.passportNum) = :passportNum AND "
                    + "rb.bookInDate = :todayDate");
            q.setParameter("passportNum", passportNum.toLowerCase());
            q.setParameter("todayDate", todayDate);
        } else {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.passportNum) = :passportNum AND "
                    + "LOWER(rb.bookedRoom.hotel.hotelCodeName) = :hotelCodeName AND "
                    + "rb.bookInDate = :todayDate");
            q.setParameter("passportNum", passportNum.toLowerCase());
            q.setParameter("hotelCodeName", hotelCodeName.toLowerCase());
            q.setParameter("todayDate", todayDate);

        }
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public List<RoomBooking> getRoomBookingByRoomNumber(String roomNumber, String status, String hotelCodeName) throws NoResultException {
        Query q;
        if (hotelCodeName == null) {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.bookedRoom.roomNumber) = :roomNumber AND "
                    + "LOWER(rb.status) = :status");
            q.setParameter("roomNumber", roomNumber.toLowerCase());
            q.setParameter("status", status.toLowerCase());
        } else {
            q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                    + "LOWER(rb.bookedRoom.roomNumber) = :roomNumber AND "
                    + "LOWER(rb.status) = :status AND "
                    + "LOWER(rb.bookedRoom.hotel.hotelCodeName) = :hotelCodeName");
            q.setParameter("roomNumber", roomNumber.toLowerCase());
            q.setParameter("status", status.toLowerCase());
            q.setParameter("hotelCodeName", hotelCodeName.toLowerCase());

        }
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public List<RoomBooking> getRoomBookingByEmail(String email, Date bookInDate, Date bookOutDate, String status) throws NoResultException {
        Query q;
        if (!email.equals("")) {
            if (bookInDate != null && bookOutDate != null) {
                //Perform search with email, bookInDate, bookOutDate, status parameter
                if (status != null) {
                    q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                            + "LOWER(rb.bookedBy.email) = :email "
                            + "AND rb.bookInDate <= :bookInDate "
                            + "AND rb.bookOutDate >= :bookOutDate "
                            + "AND LOWER(rb.status) = :status");
                    q.setParameter("email", email.toLowerCase());
                    q.setParameter("bookInDate", bookInDate);
                    q.setParameter("bookOutDate", bookOutDate);
                    q.setParameter("status", status.toLowerCase());
                } //Perform search with email, bookInDate, bookOutDate parameter
                else {
                    q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                            + "LOWER(rb.bookedBy.email) = :email "
                            + "AND rb.bookInDate <= :bookInDate "
                            + "AND rb.bookOutDate >= :bookOutDate ");
                    q.setParameter("email", email.toLowerCase());
                    q.setParameter("bookInDate", bookInDate);
                    q.setParameter("bookOutDate", bookOutDate);
                }
            } else {
                //Perform search with email and status parameter
                if (status != null) {
                    q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                            + "LOWER(rb.bookedBy.email) = :email "
                            + "AND LOWER(rb.status) = :status");
                    q.setParameter("email", email.toLowerCase());
                    q.setParameter("status", status.toLowerCase());
                } //Perform search with email parameter
                else {
                    q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                            + "LOWER(rb.bookedBy.email) = :email");
                    q.setParameter("email", email.toLowerCase());
                }
            }
            if (!q.getResultList().isEmpty()) {
                return q.getResultList();
            } else {
                throw new NoResultException("Room Booking not found.");
            }
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public List<RoomBooking> getRoomBookingByDate(Date bookInDate, Date bookOutDate, String status) throws NoResultException {
        Query q;
        if (bookInDate != null && bookOutDate != null) {
            //Perform search with bookInDate, bookOutDate, status parameter
            if (status != null) {
                q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                        + "rb.bookInDate <= :bookInDate "
                        + "AND rb.bookOutDate >= :bookOutDate "
                        + "AND LOWER(rb.status) = :status");
                q.setParameter("bookInDate", bookInDate);
                q.setParameter("bookOutDate", bookOutDate);
                q.setParameter("status", status.toLowerCase());
            } //Perform search with bookInDate, bookOutDate parameter
            else {
                q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                        + "rb.bookInDate <= :bookInDate "
                        + "AND rb.bookOutDate >= :bookOutDate");
                q.setParameter("bookInDate", bookInDate);
                q.setParameter("bookOutDate", bookOutDate);
            }
        } else {
            //Perform search with status parameter
            if (status != null) {
                q = em.createQuery("SELECT rb FROM RoomBooking rb WHERE "
                        + "LOWER(rb.status) = :status");
                q.setParameter("status", status.toLowerCase());
            } //No parameters found.
            else {
                throw new NoResultException("Room Booking not found.");
            }
        }
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            throw new NoResultException("Room Booking not found.");
        }
    }

    @Override
    public void updateRoomBooking(RoomBooking roomBooking) throws NoResultException {
        RoomBooking oldRoomBooking = em.find(RoomBooking.class, roomBooking.getRoomBookingID());
        if (oldRoomBooking != null) {
            oldRoomBooking.setBookInDate(roomBooking.getBookInDate());
            oldRoomBooking.setBookOutDate(roomBooking.getBookOutDate());
            oldRoomBooking.setStatus(roomBooking.getStatus());
            oldRoomBooking.setHasTransport(roomBooking.getHasTransport());
            oldRoomBooking.setPrice(roomBooking.getPrice());
            oldRoomBooking.setBookOutDate(roomBooking.getBookOutDate());
            oldRoomBooking.setBookedRoom(roomBooking.getBookedRoom());
            oldRoomBooking.setBookedBy(roomBooking.getBookedBy());
            oldRoomBooking.setTransportTime(roomBooking.getTransportTime());
            oldRoomBooking.setPickUpLocation(roomBooking.getPickUpLocation());
        } else {
            throw new NoResultException("Room Booking not found");
        }
    }

    @Override
    public void deleteRoomBooking(Long roomBookingID) throws NoResultException {
        RoomBooking roomBooking = em.find(RoomBooking.class, roomBookingID);

        if (roomBooking != null) {
            em.remove(roomBooking);
        } else {
            throw new NoResultException("Room Booking not found");
        }
    }

    @Override
    public void createRoomBooking(RoomBooking roomBooking) {
        em.persist(roomBooking);
    }

    @Override
    public List<HotelFacilityBooking> getAllHotelFacilityBooking() throws NoResultException {
        Query q;
        q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb ");
        return q.getResultList();
    }

    @Override
    public HotelFacilityBooking getHotelFacilityBookingByID(Long hotelFacilityID) throws NoResultException {
        HotelFacilityBooking hfb = em.find(HotelFacilityBooking.class, hotelFacilityID);
        if (hfb != null) {
            return hfb;
        } else {
            throw new NoResultException("Hotel Facility Booking not found.");
        }
    }

    @Override
    public List<HotelFacilityBooking> getHotelFacilityBookingByEmail(String email, Date startDate, Date endDate, String status) throws NoResultException {
        Query q;
        if (!email.equals("")) {
            if (startDate != null && endDate != null) {
                //Perform search with email, startDate, endDate, status parameter
                if (status != null) {
                    q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb WHERE "
                            + "LOWER(hfb.bookedBy.email) = :email "
                            + "AND hfb.startDate <= :startDate "
                            + "AND hfb.endDate >= :endDate "
                            + "AND LOWER(hfb.status) = :status");
                    q.setParameter("email", email.toLowerCase());
                    q.setParameter("startDate", startDate);
                    q.setParameter("endDate", endDate);
                    q.setParameter("status", status.toLowerCase());
                } //Perform search with email, startDate, endDate parameter
                else {
                    q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb WHERE "
                            + "LOWER(hfb.bookedBy.email) = :email "
                            + "AND hfb.startDate <= :startDate "
                            + "AND hfb.endDate >= :endDate ");
                    q.setParameter("email", email.toLowerCase());
                    q.setParameter("startDate", startDate);
                    q.setParameter("endDate", endDate);
                }
            } else {
                //Perform search with email and status parameter
                if (status != null) {
                    q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb WHERE "
                            + "LOWER(hfb.bookedBy.email) = :email "
                            + "AND LOWER(hfb.status) = :status");
                    q.setParameter("email", email.toLowerCase());
                    q.setParameter("status", status.toLowerCase());
                } //Perform search with email parameter
                else {
                    q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb WHERE "
                            + "LOWER(hfb.bookedBy.email) = :email");
                    q.setParameter("email", email.toLowerCase());
                }
            }
            if (!q.getResultList().isEmpty()) {
                return q.getResultList();
            } else {
                throw new NoResultException("Hotel Facility Booking not found.");
            }
        } else {
            throw new NoResultException("Hotel Facility Booking not found.");
        }
    }

    @Override
    public List<HotelFacilityBooking> getHotelFacilityBookingByDate(Date startDate, Date endDate, String status) throws NoResultException {
        Query q;
        if (startDate != null && endDate != null) {
            //Perform search with startDate, endDate, status parameter
            if (status != null) {
                q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb WHERE "
                        + "hfb.startDate <= :startDate "
                        + "AND hfb.endDate >= :endDate "
                        + "AND LOWER(hfb.status) = :status");
                q.setParameter("startDate", startDate);
                q.setParameter("endDate", endDate);
                q.setParameter("status", status.toLowerCase());
            } //Perform search with startDate, endDate parameter
            else {
                q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb WHERE "
                        + "hfb.startDate <= :startDate "
                        + "AND hfb.endDate >= :endDate");
                q.setParameter("startDate", startDate);
                q.setParameter("endDate", endDate);
            }
        } else {
            //Perform search with status parameter
            if (status != null) {
                q = em.createQuery("SELECT hfb FROM HotelFacilityBooking hfb WHERE "
                        + "LOWER(hfb.status) = :status");
                q.setParameter("status", status.toLowerCase());
            } //No parameters found.
            else {
                throw new NoResultException("Hotel Facility Booking not found.");
            }
        }
        if (!q.getResultList().isEmpty()) {
            return q.getResultList();
        } else {
            throw new NoResultException("Hotel Facility Booking not found.");
        }
    }

    @Override
    public void updateHotelFacilityBooking(HotelFacilityBooking hotelFacilityBooking) throws NoResultException {
        HotelFacilityBooking oldHotelFacilityBooking = em.find(HotelFacilityBooking.class, hotelFacilityBooking.getHotelFacilityBookingID());
        if (oldHotelFacilityBooking != null) {
            oldHotelFacilityBooking.setStartDate(hotelFacilityBooking.getStartDate());
            oldHotelFacilityBooking.setEndDate(hotelFacilityBooking.getEndDate());
            oldHotelFacilityBooking.setStatus(hotelFacilityBooking.getStatus());
            oldHotelFacilityBooking.setPrice(hotelFacilityBooking.getPrice());
            oldHotelFacilityBooking.setBookedHotelFacility(hotelFacilityBooking.getBookedHotelFacility());
            oldHotelFacilityBooking.setBookedBy(hotelFacilityBooking.getBookedBy());
        } else {
            throw new NoResultException("Hotel Facility Booking not found");
        }
    }

    @Override
    public void deleteHotelFacilityBooking(Long hotelFacilityBookingID) throws NoResultException {
        HotelFacilityBooking hotelFacilityBooking = em.find(HotelFacilityBooking.class, hotelFacilityBookingID);

        if (hotelFacilityBooking != null) {
            em.remove(hotelFacilityBooking);
        } else {
            throw new NoResultException("Hotel Facility Booking not found");
        }
    }

    @Override
    public void createHotelFacilityBooking(HotelFacilityBooking hotelFacilityBooking) {
        em.persist(hotelFacilityBooking);
    }

    @Override
    public RoomBooking getLastRoomBooking() throws NoResultException {
        Query q = em.createQuery("SELECT r FROM RoomBooking r ORDER BY r.roomBookingID DESC");
        return (RoomBooking) q.getResultList().get(0);
    }

}

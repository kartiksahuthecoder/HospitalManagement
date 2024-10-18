package dao;
import java.sql.*;
import entity.Appointment;
import java.util.ArrayList;
import java.util.List;
import util.DBConnUtil;
public class HospitalServiceImpl implements IHospitalService {

    private List<Appointment> appointments = new ArrayList<>();
    

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId() == appointmentId) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE patientId = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointmentId"));
                appointment.setPatientId(rs.getInt("patientId"));
                appointment.setDoctorId(rs.getInt("doctorId"));
                appointment.setAppointmentDate(rs.getDate("appointmentDate"));
                appointment.setDescription(rs.getString("description"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }


    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getDoctorId() == doctorId) {
                result.add(a);
            }
        }
        return result;
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        String sql = "INSERT INTO Appointment (patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            stmt.setString(4, appointment.getDescription());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

    @Override
    public boolean updateAppointment(Appointment appointment) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId() == appointment.getAppointmentId()) {
                a.setDescription(appointment.getDescription());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        String sql = "DELETE FROM Appointment WHERE appointmentId = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointmentId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

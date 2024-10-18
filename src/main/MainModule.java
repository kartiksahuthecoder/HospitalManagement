package main;

import dao.HospitalServiceImpl;
import entity.Appointment;
import java.util.Date;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        HospitalServiceImpl service = new HospitalServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View Appointments by Patient ID");
            System.out.println("3. View Appointments by Doctor ID");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter appointment ID, patient ID, doctor ID, and description:");
                    int appointmentId = scanner.nextInt();
                    int patientId = scanner.nextInt();
                    int doctorId = scanner.nextInt();
                    String description = scanner.next();
                    Appointment appointment = new Appointment(appointmentId, patientId, doctorId, new Date(), description);
                    service.scheduleAppointment(appointment);
                    System.out.println("Appointment scheduled!");
                    break;
                case 2:
                    System.out.println("Enter patient ID:");
                    patientId = scanner.nextInt();
                    System.out.println(service.getAppointmentsForPatient(patientId));
                    break;
                case 3:
                    System.out.println("Enter doctor ID:");
                    doctorId = scanner.nextInt();
                    System.out.println(service.getAppointmentsForDoctor(doctorId));
                    break;
                case 4:
                    System.out.println("Enter appointment ID to cancel:");
                    appointmentId = scanner.nextInt();
                    if (service.cancelAppointment(appointmentId)) {
                        System.out.println("Appointment canceled!");
                    } else {
                        System.out.println("Appointment not found!");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}

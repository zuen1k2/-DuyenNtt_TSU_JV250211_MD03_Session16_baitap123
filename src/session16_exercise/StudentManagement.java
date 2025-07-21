package session16_exercise;

import database.Database;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class StudentManagement {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1.Hiển thị danh sách sinh viên");
            System.out.println("2.Thêm mới sinh viên");
            System.out.println("3.Sửa sinh viên");
            System.out.println("4.Xóa sinh viên");
            System.out.println("5.Tìm kiếm sinh viên");
            System.out.println("6.Thoát.");
            System.out.println("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    displayAllStudents();
                    break;
                case 2:
                  addNewStudent();
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Vui lòng chọn từ 1-6.");
            }

        }
    }
    public static void displayAllStudents() {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL get_all_students()}")) {

            ResultSet rs = cs.executeQuery();
            System.out.printf("%-5s %-25s %-15s %-25s\n", "ID", "Họ tên", "Ngày sinh", "Email");

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("full_name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String email = rs.getString("email");

                System.out.printf("ID: %d | Name: %s | DOB: %s | Email: %s\n", id, name, dateOfBirth, email);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addNewStudent() {
        String fullName;
        String dateOfBirth;
        String email;

        while (true) {
            System.out.print("Nhập họ tên: ");
            fullName = scanner.nextLine();
            if (!fullName.isEmpty()) break;
            System.out.println("Họ tên không được để trống.");
        }

        while (true) {
            System.out.print("Nhập ngày sinh (YYYY-MM-DD): ");
            dateOfBirth = scanner.nextLine();
            if (dateOfBirth.matches("\\d{4}-\\d{2}-\\d{2}")) break;
            System.out.println("Sai định dạng ngày.");
        }

        while (true) {
            System.out.print("Nhập email: ");
            email = scanner.nextLine();
            if (!email.isEmpty()) break;
            System.out.println("Email không được để trống.");
        }

        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL add_student(?, ?, ?)}")) {

            cs.setString(1, fullName);
            cs.setDate(2, Date.valueOf(dateOfBirth));
            cs.setString(3, email);

            cs.executeUpdate();
            System.out.println("Thêm sinh viên thành công.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
        }
    }
    public static void updateStudent(Scanner scanner) {
        try (Connection connection = Database.getConnection()) {
            int id = 0;
            String fullName = "";
            String email = "";
            LocalDate dob = null;


            while (true) {
                try {
                    System.out.print("Nhập ID sinh viên cần cập nhật: ");
                    id = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ID phải là số nguyên. Vui lòng nhập lại.");
                }
            }


            while (true) {
                System.out.print("Nhập họ tên mới: ");
                fullName = scanner.nextLine();
                if (!fullName.isBlank()) break;
                System.out.println("Họ tên không được để trống.");
            }


            while (true) {
                System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
                String dateStr = scanner.nextLine();
                try {
                    dob = LocalDate.parse(dateStr);
                    break;
                } catch (Exception e) {
                    System.out.println("Sai định dạng ngày. Vui lòng nhập theo yyyy-MM-dd.");
                }
            }


            while (true) {
                System.out.print("Nhập email mới: ");
                email = scanner.nextLine();
                if (!email.isBlank() && email.contains("@")) break;
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            }


            CallableStatement cs = connection.prepareCall("{CALL update_student(?, ?, ?, ?)}");
            cs.setInt(1, id);
            cs.setString(2, fullName);
            cs.setDate(3, Date.valueOf(dob));
            cs.setString(4, email);

            int rows = cs.executeUpdate();
            if (rows > 0) {
                System.out.println("Cập nhật sinh viên thành công.");
            } else {
                System.out.println("Không tìm thấy sinh viên với ID đã nhập.");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật: " + e.getMessage());
        }
    }

    public static void deleteStudent(Scanner scanner) {
        try (Connection connection = Database.getConnection()) {
            int id = 0;

            while (true) {
                try {
                    System.out.print("Nhập ID sinh viên cần xóa: ");
                    id = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ID phải là số nguyên. Vui lòng nhập lại.");
                }
            }

            CallableStatement cs = connection.prepareCall("{CALL delete_student(?)}");
            cs.setInt(1, id);
            int rows = cs.executeUpdate();

            if (rows > 0) {
                System.out.println("Đã xóa sinh viên thành công.");
            } else {
                System.out.println("Không tìm thấy sinh viên với ID này.");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }


}
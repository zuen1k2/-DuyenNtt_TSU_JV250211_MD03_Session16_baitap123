CREATE DATABASE ss16;
USE ss16;

CREATE TABLE Students (
                          student_id int auto_increment primary key ,
                          full_name varchar(100) not null ,
                          date_of_birth date not null ,
                          email varchar(100) not null unique
);

DELIMITER //
CREATE PROCEDURE
    get_all_students()
BEGIN
SELECT * FROM Students;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE
    add_student( IN in_full_name varchar(100),
                 IN  in_date_of_birth date,
                 IN  in_email varchar(100))
BEGIN
INSERT INTO Students(full_name,date_of_birth,email)
VALUES (in_full_name,in_date_of_birth,in_email);

end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE
    update_student(
    IN in_id int,
    IN in_full_name varchar(100),
    IN  in_date_of_birth date,
    IN  in_email varchar(100))
BEGIN
UPDATE Students
SET full_name = in_full_name, date_of_birth = in_date_of_birth, email = in_email
WHERE student_id = in_id;

end //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE
    find_student_by_id( IN in_id int)
BEGIN
SELECT * FROM Students
WHERE student_id = in_id;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE
    delete_student( IN in_id int)
BEGIN
DELETE FROM Students
WHERE student_id = in_id;
end //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE search_student(IN in_name VARCHAR(100))
BEGIN
SELECT * FROM Students
WHERE full_name LIKE CONCAT('%', in_name, '%');
END //
DELIMITER ;


CALL get_all_students();
CALL add_student('Nguyễn Văn A','2004-08-09','nva@gmail.com');
CALL add_student ('Trần Văn C ','1998-10-10','tvc@gmail.com');
CALL update_student(1,'Nguyễn Văn B','1999-09-09','nvb@gmail.com');
CALL find_student_by_id(1);
CALL delete_student(1);




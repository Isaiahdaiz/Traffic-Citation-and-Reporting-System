-- Sample insert data
INSERT INTO Users (Username, Password, Type, Status)
VALUES 
    ('admin', 'password', 'local', 'active'),
    ('john', 'password', 'local', 'active'),
    ('jane', 'password', 'local', 'inactive'),
    ('jim', 'password', 'provincial', 'active'),
    ('jack', 'password', 'provincial', 'inactive');

INSERT INTO Vehicles (VIN, LicensePlateNumber, Make, Model, RegistrationStatus, StolenStatus, WarrantStatus, Notes) VALUES 
('1GNSKAKC9FR288113', 'ABC123', 'Chevrolet', 'Tahoe', true, false, false, 'Red SUV'), 
('1FMJU2A57CEF33227', 'XYZ789', 'Ford', 'Expedition', true, false, false, 'White SUV'), 
('1N4AA6AP3HC387645', 'DEF456', 'Nissan', 'Maxima', true, false, false, 'Silver sedan'), 
('2T2HK31U48C065630', 'GHI789', 'Lexus', 'RX', true, false, false, 'Blue SUV'), 
('3FA6P0D92JR202122', 'JKL012', 'Ford', 'Fusion', true, false, false, 'Black sedan');

INSERT INTO Drivers (DLNumber, FirstName, LastName, DateOfBirth, LicenseStatus, WarrantStatus, DrivingRecord, Notes) VALUES 
('B12345678901234', 'John', 'Doe', '1980-01-01', 'Valid', false, 'Clean', 'No notes'), 
('B23456789012345', 'Jane', 'Doe', '1990-01-01', 'Valid', false, 'Clean', 'No notes'), 
('B34567890123456', 'Bob', 'Smith', '1985-01-01', 'Valid', false, 'Clean', 'No notes'), 
('B45678901234567', 'Alice', 'Johnson', '1975-01-01', 'Valid', false, 'Clean', 'No notes'), 
('B56789012345678', 'Tom', 'Williams', '1995-01-01', 'Valid', false, 'Clean', 'No notes');

INSERT INTO Citations (Officer, Type, DLNumber, VIN, Date, FineAmount, PaymentStatus, TrafficSchool, Notes) VALUES
('John Smith', 'Speeding', 'B12345678901234', '1GNSKAKC9FR288113', '2022-01-15 09:30:00', 150.00, 'Paid', 'N/A', 'Driver was going 75 mph in a 55 mph zone.'),
('Jane Doe', 'Seat Belt Violation', 'B23456789012345', '1FMJU2A57CEF33227', '2022-02-03 11:15:00', 50.00, 'Unpaid', 'N/A', 'Driver was not wearing a seat belt.'),
('Mike Johnson', 'Running Red Light', 'B34567890123456', '1N4AA6AP3HC387645', '2022-03-22 18:45:00', 200.00, 'Unpaid', 'Attended', 'Driver ran a red light and almost caused an accident.'),
('Sarah Lee', 'Expired Registration', 'B12345678901234', '2T2HK31U48C065630', '2022-04-11 14:00:00', 75.00, 'Paid', 'N/A', 'Vehicle had expired registration tags.'),
('David Kim', 'No Proof of Insurance', 'B23456789012345', '3FA6P0D92JR202122', '2022-05-19 10:30:00', 100.00, 'Unpaid', 'N/A', 'Driver was unable to provide proof of insurance.');
insert into tbl_student (stuNum, name, password)
values ('01010101', 'xiaoming', 'test0001'),
       ('01010102', 'xiaowang', 'test0002'),
       ('01010103', 'xiaohong', 'test0003'),
       ('01010104', 'xiaogang', 'test0004'),
       ('01010105', 'xiaohuang', 'test0005');

insert into tbl_studyroom (buildingNum, classroomNum, seat_Id_Set, if_Open, open_Time, close_Time)
values ('01', '001', '01,02,03,04,05', true, '7', '22'),
       ('01', '002', '01,03,05,07,09', false, '0', '0'),
       ('01', '003', '10,11,12,13,14', true, '0', '0'),
       ('02', '001', '01,02,03,04,05', false, '0', '0'),
       ('02', '002', '01,03,05,07,09', false, '0', '0'),
       ('02', '003', '10,11,12,13,14', false, '0', '0');

insert into tbl_seat (buildingNum, classRoomNum, seat_Id, if_Open, open_Time, close_Time, if_Near_Window, if_Near_Power)
values ('01', '001', '01', true, '0800', '2300', false, true),
       ('01', '001', '02', false, '0830', '2300', false, false),
       ('01', '001', '03', true, '0800', '2200', false, false),
       ('01', '001', '04', false, '0800', '2230', false, false),
       ('01', '001', '05', true, '0900', '2200', false, false),
       ('01', '002', '01', false, '0820', '2300', false, false),
       ('01', '002', '03', false, '0830', '2300', false, false),
       ('01', '002', '05', false, '0900', '2200', false, false),
       ('01', '002', '07', false, '0800', '2320', false, false),
       ('01', '002', '09', false, '0800', '2200', false, false),
       ('01', '003', '10', false, '0800', '2300', false, false),
       ('01', '003', '11', false, '0700', '2250', false, false),
       ('01', '003', '12', false, '0730', '2300', false, false),
       ('01', '003', '13', false, '0730', '2300', false, false),
       ('01', '003', '14', false, '0800', '2300', false, false),
       ('02', '001', '01', false, '0800', '2300', false, false),
       ('02', '001', '02', false, '0800', '2200', false, false),
       ('02', '001', '03', false, '0830', '2200', false, false),
       ('02', '001', '04', false, '0800', '2200', false, false),
       ('02', '001', '05', false, '0830', '2200', false, false),
       ('02', '002', '01', false, '0800', '2200', false, false),
       ('02', '002', '03', false, '0800', '2200', false, false),
       ('02', '002', '05', false, '0800', '2300', false, false),
       ('02', '002', '07', false, '0900', '2300', false, false),
       ('02', '002', '09', false, '0830', '2200', false, false),
       ('02', '003', '10', false, '0820', '2330', false, false),
       ('02', '003', '11', false, '0830', '2200', false, false),
       ('02', '003', '12', false, '0900', '2300', false, false),
       ('02', '003', '13', false, '0730', '2230', false, false),
       ('02', '003', '14', false, '0800', '2200', false, false);

insert into tbl_booking_xxx (buildingNum, classroomNum, seat_Id, begin_Time, end_Time, if_Online, stuId)
values ('01', '001', '01', '0830', '2200', false, '01010101'),
       ('01', '001', '02', '0800', '2300', true,  '01010101'),
       ('01', '002', '05', '0830', '2230', false, '01010102'),
       ('02', '001', '01', '0800', '2300', true,  '01010101'),
       ('02', '002', '01', '0820', '2220', false, '01010103'),
       ('02', '002', '01', '0800', '2300', false, '01010101');

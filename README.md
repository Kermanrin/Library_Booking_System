# Library_Booking_System

##A library booking system with front-end(Vue) and back-end(Java/spring boot) programme. 

##Complete Function include:

###To Students:
1) log in, by storing stu's ID/name/password/other attributes in H2 database, we can support sign in function.
2) Search classroom/seats, in own system, the library is a tree-struct with three layers: buildings-classrooms-seats, To help students booking seats easier, we give two searching interfaces.
3) book seat,once a student makes sure the seats is available and wants to book that seat, he can use this function to book a seat in correction period.
4) query history, in fact this function is for both Students(query oneself) and Administrators(total privilege), we design a database to store all students' booking records.
5) Sign in， once a student arrive that booking seat, he can use his stuId to sign in and start learning. At very begining, we achieve a random fuction to give a random code to simulate the QRcode on seats(offline sign in), however we think that is a little useless.
6) personalized recommendation, in this function, system will give somes best seats choices throught the student's booking records, the recommendation factors includes: seat if_Open/if_Near_Window/of_Near_Power, which are attributes of seats.
7) modify password

###To Administrators:
1) add/delete a classroom: init or destruct a classroom.
2) modify classroom: once an administrator make a classroom, he need to than give some primary parameters like seats_Id_Set/if_Open//open_Time/close_Time to make a classroom table complete.
3) query/modify seats attributes: we need administrators are able to manager all situation in this system. seats clearly are not availabe all the time(as same as the classroom), so we provide these interfaces to make sure this.

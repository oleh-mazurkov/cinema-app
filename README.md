# cinema-app

**What this application is for and what it can do**
Primitive demonstration program of service of cinema services and its clients

**Technologies used**
Program based on the use of:
- Hibernate 
- Spring framework

**How to run a project**
Before lunching the programm you need to install on you comp:
MySQL Workbench (DB for datas working) and Apache Tomcat (to lunch the programm)

To lunch the project you need to copy program **cinema-app-1.0-SNAPSHOT.war** from this server
and place it in /webapps Tomcat-programm`s on your comp.
Lunch **/bin/startup.bat** Tomcat-programm/
In brouser you can see authentication data to start of the program.
But at first you need to inject previus data in your DB for first authentication.
So you need to lunch in brouser: **http://localhost:8080/inject**
You can see "Done!" in brousers window (or "HTTP Status 500 – Internal Server Error" - dont pay your attantion on it)).
Next step - authentication: lunch in brouser **http://localhost:8080/login** 
and input "alice@i.ua" as login, "12345678" as password (this person have role "ADMIN")
You can see "Hello alice@i.ua!" in brousers window.

**Now you can work with DB**
Here you can work with 2 services:

1. Cinema services
   The program allows:
   1.1 Add to the database cinemas (with a description of the hall and the number of seats) and get a list of all halls
   1.2 Add movies to the database (with movie title and description) and get a list of all movies
   1.3 Add, edit and delete sessions (including: Cinema, Movie, show date and time),
   and get a list of sessions by movie title and show date
2. Customer services
   The program allows:
   2.1 Register a client (includes: email and password) as USER - external cinema client
   with automatic creation of the client's card
   `ADMINs (which will be able to use, enter and edit data on cinema services)
   entered into InjectController programs and, when creating a database, are entered with the command /inject`
   2.2 Authenticate the client when logging in by his email and password
   2.3 Ticket formation (includes: session and client)
   2.4 Forming a customer card (includes: tickets and customer)
   2.5 Formation of the order with display of the current date of the order, tickets and the client

Function check:
1 Running the program via Tomcat (an empty database "ticket_app" is created in MySQL)
2 Filling the database with test data through a browser command: **http://localhost:8080/inject**
3 Login via **http://localhost:8080/login** "alice@i.ua" password "12345678" (role "ADMIN")
4 New user registration **http://localhost:8080/register**
  e.g. (via Postman) **{"email":"chucki@i.ua","password":"12345678","repeatPassword":"12345678"}**
  This automatically creates a SoppingCart of the client with its id
4 Examples of possible database operations in the browser (eg Chrome) and in the Postman API platform,
depending on the user's status
- for "ADMIN":
**http://localhost:8080/users/** - returns data for all clients
**http://localhost:8080/users/by-email** +e.g. **?email=bob@i.ua** - returns data on the client's email
- for "ADMIN" and "USER":
**http://localhost:8080/cinema-halls/** - returns data for all cinemas
**http://localhost:8080/movies/** - returns data for all movies
**http://localhost:8080/orders** - returns the login customer order data
**http://localhost:8080/shopping-carts/by-user/** - returns data
  Shoping Cart of the login client
**http://localhost:8080/movie-sessions/available** +e.g. **?movieId=1&date=14.04.2022** -
  receives available sessions by movie id and login date
- POST-requests to Postman (for data entry)
**http://localhost:8080/cinema-halls** +eg.**{"capacity":"100","description":"Yellow Hall"}**
**http://localhost:8080/movies** +eg.**{"title":"The bell","description":"Horror"}**
**http://localhost:8080/movie-sessions**   
  +eg.**{"showTime":"2022-04-22T10:30:00","cinemaHallId":"1","movieId":"2"}** - adds session to database
- PUT request to Postman (for data correction)
**http://localhost:8080/movie-sessions/1**
  +eg.**{"showTime":"2022-05-22T10:30:00","cinemaHallId":"1","movieId":"2"}**
- DELETE-request to Postman 
  **http://localhost:8080/movie-sessions/1** - Soft-deleting -sets id_deleted TRUE
  for a session with id = 1 
   
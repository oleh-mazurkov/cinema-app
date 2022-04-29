package mate.academy.spring.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mate.academy.spring.model.CinemaHall;
import mate.academy.spring.model.Movie;
import mate.academy.spring.model.MovieSession;
import mate.academy.spring.model.Role;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.CinemaHallService;
import mate.academy.spring.service.MovieService;
import mate.academy.spring.service.MovieSessionService;
import mate.academy.spring.service.OrderService;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final UserService userService;
    private final RoleService roleService;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;
    private final MovieSessionService movieSessionService;
    private final AuthenticationService authenticationService;
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;

    public InjectController(UserService userService,
                            RoleService roleService,
                            MovieService movieService,
                            CinemaHallService cinemaHallService,
                            MovieSessionService movieSessionService,
                            AuthenticationService authenticationService,
                            ShoppingCartService shoppingCartService,
                            OrderService orderService) {
        this.userService = userService;
        this.roleService = roleService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
        this.movieSessionService = movieSessionService;
        this.authenticationService = authenticationService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    @GetMapping
    public String injectData() {

        roleService.add(new Role(Role.RoleName.ADMIN));
        authenticationService.registerAdmin("alice@i.ua","12345678");
        authenticationService.registerAdmin("jack@i.ua","12345678");
        roleService.add(new Role(Role.RoleName.USER));
        authenticationService.registerUser("bob@i.ua","12345678");
        authenticationService.registerUser("mishel@i.ua","12345678");

        CinemaHall greenCinemaHall = new CinemaHall();
        greenCinemaHall.setDescription("Green hall for 49 seats, "
                + "which give you an unforgettable journey into the 3D world of cinema");
        greenCinemaHall.setCapacity(225);
        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setDescription("Red hall - 64 channels and special geometry"
                + " of the hall guarantee the accuracy of sound "
                + "positioning and the effect of presence");
        redCinemaHall.setCapacity(285);

        cinemaHallService.add(greenCinemaHall);
        cinemaHallService.add(redCinemaHall);

        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie terminatorII = new Movie();
        terminatorII.setTitle("Terminator II");
        terminatorII.setDescription("The Terminator is a 1984 American science "
                + "fiction action film directed by James Cameron");

        movieService.add(fastAndFurious);
        movieService.add(terminatorII);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(greenCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-04-15 12:30", formatter);
        tomorrowMovieSession.setShowTime(dateTime1);

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(redCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        LocalDateTime dateTime2 = LocalDateTime.parse("2022-04-14 10:30", formatter);
        yesterdayMovieSession.setShowTime(dateTime2);

        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        List<MovieSession> realMovieSession = movieSessionService
                .findAvailableSessions(1L, LocalDate.from(dateTime1));

        shoppingCartService.addSession(yesterdayMovieSession, userService.get(1L));
        shoppingCartService.addSession(tomorrowMovieSession, userService.get(1L));
        shoppingCartService.addSession(yesterdayMovieSession, userService.get(2L));

        orderService.completeOrder(shoppingCartService.getByUser(userService.get(1L)));
        orderService.completeOrder(shoppingCartService.getByUser(userService.get(2L)));

        return "Done!";
    }
}

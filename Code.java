 import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.io.*; // NEW: Required for persistence (Serialization)

// ========================
// Centralized color constants
// ========================
class Color {
    public static final String RESET  = "\033[0m";
    public static final String RED    = "\033[0;31m";
    public static final String GREEN  = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE   = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN   = "\033[0;36m";
    public static final String GOLD   = "\033[38;5;220m"; // golden color
    public static final String BOLD   = "\033[1m";
    public static final String WHITE  = "\033[0;37m"; // Added white color constant
    public static final String MAGENTA = "\033[0;35m"; // Added magenta for consistent headers
}

// ========================
// Admin movie holder
// ========================
class AdminMovie implements java.io.Serializable { // Added Serializable for consistency
    String name;
    String rating;
    String duration;
    String genre;
    String certification;
    String releaseDate;
    String director;
    String musicDirector;
    ArrayList<String> cast;

    AdminMovie(String name, String rating, String duration, String genre,
               String certification, String releaseDate, String director,
               String musicDirector, ArrayList<String> cast) {

        this.name = name;
        this.rating = rating;
        this.duration = duration;
        this.genre = genre;
        this.certification = certification;
        this.releaseDate = releaseDate;
        this.director = director;
        this.musicDirector = musicDirector;
        this.cast = cast;
    }
}

// ========================
// Admin panel + movie list
// ========================
class MovieSystem {
    static ArrayList<AdminMovie> movies = new ArrayList<>();


    static {
        // UPDATED: Release Dates as requested (dd-MM-yyyy format)
        movies.add(new AdminMovie(
                "VARANASI",
                "10/10",
                "2h 45m",
                "ACTION/DRAMA",
                "UA16+",
                "20-12-2025", // UPDATED
                "SS.RAJAMOULI",
                "MM.KEERAVANI",
                new ArrayList<>(Arrays.asList("MAHESH BABU", "PRIDVI RAJ SUKUMARAN"))
        ));

        movies.add(new AdminMovie(
                "PUSHPA - PART 3",
                "10/10",
                "3h 05m",
                "ACTION/THRILLER",
                "UA16+",
                "21-12-2025", // UPDATED
                "SUKUMAR",
                "DSP",
                new ArrayList<>(Arrays.asList("Allu Arjun", "Rashmika Mandanna", "Sunil"))
        ));

        movies.add(new AdminMovie(
                "SALAAR - PART 2",
                "10/10",
                "2h 55m",
                "ACTION",
                "UA16+",
                "22-12-2025", // UPDATED
                "PRASHANTH NEEL",
                "RAVI BASRUR",
                new ArrayList<>(Arrays.asList("Prabhas", "Prithviraj Sukumaran", "Shruti Haasan"))
        ));

        movies.add(new AdminMovie(
                "DEVARA - PART 2",
                "10/10",
                "2h 50m",
                "ACTION/DRAMA",
                "UA16+",
                "23-12-2025", // UPDATED
                "KORATALA SIVA",
                "ANIRUDH",
                new ArrayList<>(Arrays.asList("NTR Jr", "Janhvi Kapoor"))
        ));

        movies.add(new AdminMovie(
                "OG - PART 2",
                "10/10",
                "2h 55m",
                "ACTION/THRILLER",
                "UA16+",
                "24-12-2025", // UPDATED
                "SUJEETH",
                "SS THAMAN",
                new ArrayList<>(Arrays.asList("Pawan Kalyan", "Imraan Hashmi", "Arjun Das"))
        ));
    }



    // UPDATED: Structured display for recommended movies
    static void displayMovies() {
        System.out.println(Color.MAGENTA + "\n                        RECOMMENDED MOVIES" + Color.RESET);
        System.out.println(Color.MAGENTA + "                        ------------------" + Color.RESET);
        // Header Row
        System.out.println(Color.CYAN + "+---------------------------------------------------+" + Color.RESET);
        System.out.println(Color.CYAN + "| No. | Movie Title                      | Rating  |" + Color.RESET);
        System.out.println(Color.CYAN + "+---------------------------------------------------+" + Color.RESET);
        int index = 1;
        for (AdminMovie m : movies) {
            // Alignment: Index (3 chars), Name (32 chars), Rating (7 chars)
            String output = String.format("| %-3d | %-32s | %-7s |", index, m.name, m.rating);
            System.out.println(Color.WHITE + output + Color.RESET);
            index++;
        }
        // Footer and Exit Row
        System.out.println(Color.CYAN + "+---------------------------------------------------+" + Color.RESET);
        // Exit is option 'index' which is 1 + movies.size()
        String exitOutput = String.format("| %-3d | %-41s |", index, "HOME/LOGOUT"); // Changed text from EXIT to HOME/LOGOUT
        System.out.println(Color.YELLOW + exitOutput + Color.RESET);
        System.out.println(Color.CYAN + "+---------------------------------------------------+" + Color.RESET);
    }

    static void adminLogin() {
        int attempts = 3;

        while (attempts > 0) {
            System.out.print(Color.CYAN + "\nENTER ADMIN USERNAME: " + Color.RESET);
            String user = Main.sc.nextLine();

            System.out.print(Color.CYAN + "ENTER ADMIN PASSWORD: " + Color.RESET);
            String pass = Main.sc.nextLine();

            if (user.equals("admin") && pass.equals("1234")) {
                System.out.println(Color.GREEN + "\nLOGIN SUCCESSFUL!" + Color.RESET);
                adminMenu();
                return; // exit after successful login
            } else {
                attempts--;
                if (attempts > 0) {
                    System.out.println(Color.RED + "\nINVALID CREDENTIALS! Attempts left: " + attempts + Color.RESET);
                }
            }
        }

        System.out.println(Color.RED + "\nTOO MANY FAILED ATTEMPTS! ACCESS BLOCKED.\n" + Color.RESET);
    }


    static void adminMenu() {
        while (true) {
            System.out.println("\n====== ADMIN PANEL ======");
            System.out.println(Color.CYAN+"1. VIEW MOVIES"+Color.RESET);
            System.out.println(Color.CYAN+"2. ADD MOVIE"+Color.RESET);
            System.out.println(Color.CYAN+"3. DELETE MOVIE"+Color.RESET);
            System.out.println(Color.CYAN+"4. LOGOUT"+Color.RESET);
            System.out.print(Color.YELLOW+"ENTER OPTION: "+Color.RESET);

            int opt;
            try {
                // Use nextLine() and parse to avoid leaving newline characters
                opt = Integer.parseInt(Main.sc.nextLine());
            } catch (Exception e) {
                System.out.println(Color.RED+"INVALID OPTION!"+Color.RESET);
                continue;
            }

            switch (opt) {
                case 1: displayMovies(); break;
                case 2: addMovie(); break;
                case 3: deleteMovie(); break;
                case 4: System.out.println(Color.GREEN+" LOGOUT SUCCESSFUL!"+Color.RESET); return;
                default: System.out.println(Color.RED+"INVALID OPTION!"+Color.RESET);
            }
        }
    }

    static void addMovie() {

        System.out.print(Color.YELLOW + "ENTER MOVIE NAME: " + Color.RESET);
        String name = Main.sc.nextLine();

        System.out.print(Color.YELLOW + "ENTER MOVIE RATING (e.g. 9/10): " + Color.RESET);
        String rating = Main.sc.nextLine();

        System.out.print(Color.YELLOW + "ENTER MOVIE DURATION (e.g. 2h 55m): " + Color.RESET);
        String duration = Main.sc.nextLine();

        System.out.print(Color.YELLOW + "ENTER GENRE (e.g. ACTION/THRILLER): " + Color.RESET);
        String genre = Main.sc.nextLine();

        System.out.print(Color.YELLOW + "ENTER MOVIE CERTIFICATION (e.g. UA, U/A, 18+): " + Color.RESET);
        String certification = Main.sc.nextLine();

        System.out.print(Color.YELLOW + "ENTER RELEASE DATE (e.g., 20-12-2025): " + Color.RESET);
        String releaseDate = Main.sc.nextLine();

        System.out.print(Color.YELLOW + "ENTER DIRECTOR NAME: " + Color.RESET);
        String director = Main.sc.nextLine();

        System.out.print(Color.YELLOW + "ENTER MUSIC DIRECTOR NAME: " + Color.RESET);
        String musicDirector = Main.sc.nextLine();

        // CAST LIST
        ArrayList<String> cast = new ArrayList<>();
        while (true) {
            System.out.print(Color.YELLOW + "ENTER CAST NAME (type 'done' to finish): " + Color.RESET);
            String actor = Main.sc.nextLine();

            if (actor.equalsIgnoreCase("done")) break;
            cast.add(actor);
        }

        movies.add(new AdminMovie(name, rating, duration, genre, certification,
                releaseDate, director, musicDirector, cast));

        System.out.println(Color.GREEN + "MOVIE ADDED SUCCESSFULLY!" + Color.RESET);
    }


    static void deleteMovie() {
        displayMovies();
        System.out.print(Color.YELLOW+"ENTER MOVIE NUMBER TO DELETE: "+Color.RESET);
        int num;
        try {
            // Input handling fixed
            num = Integer.parseInt(Main.sc.nextLine());
        } catch (Exception e) {
            System.out.println(Color.RED+"INVALID MOVIE NUMBER!"+Color.RESET);
            return;
        }

        if (num >= 1 && num <= movies.size()) {
            AdminMovie removed = movies.remove(num - 1);
            System.out.println(Color.RED+" MOVIE DELETED! (" + removed.name + ")"+Color.RESET);
        } else {
            System.out.println(Color.RED+"INVALID MOVIE NUMBER!"+Color.RESET);
        }
    }
}

// ========================
// User Login
// ========================


class Account {
    private String username;
    private String password;
    private String phoneNumber;

    Account(String user, String pass, String phone) {
        this.username = user;
        this.password = pass;
        this.phoneNumber = phone;
    }

    String getUser() { return username; }
    String getPass() { return password; }
    String getNum()  { return phoneNumber; }
    void setPass(String password) { this.password = password; }
}
 class Login {

    // STORED ACCOUNTS
    static ArrayList<Account> accounts = new ArrayList<>();

    // GLOBAL SCANNER (uses Main.sc in your project)
    static Scanner sc = Main.sc;

    // WRONG ATTEMPT COUNTER (shared by all login methods)
    static int[] wrongAttempts = {0};

    // COLORS
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String RESET = "\033[0m";

    // MAIN LOGIN MENU (CALL FROM Main.java)
    public static boolean startLogin() {

        // Default account
        if (accounts.isEmpty()) {
            accounts.add(new Account("ram", "Ramak123", "9391573109"));
        }

        while (true) {
            System.out.println();
            System.out.println(Header());

            System.out.print(CYAN + "Enter your choice: " + RESET);

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println(RED + "Invalid choice. Try again." + RESET);
                continue;
            }

            System.out.println();

            switch (choice) {
                case 1: if (userPassLogin()) return true;  break;
                case 2: if (phoneLogin()) return true;     break;
                case 3: register();                        break;
                case 4:
                    System.out.println(GREEN + "Exiting Login..." + RESET);
                    return false;

                default:
                    System.out.println(RED + "Invalid Choice." + RESET);
            }

            if (wrongAttempts[0] == 2) {
                System.out.println(RED + "WARNING: Only ONE attempt remaining!" + RESET);
            }

            if (wrongAttempts[0] >= 3) {
                System.out.println(RED + "Too many wrong attempts. Try later." + RESET);
                return false;
            }
        }
    }

    // HEADER MENU
    public static String Header() {
       // inside Header()
return YELLOW +
"+------------------------------------------------------------+\n" +
"|                                                            |\n" +
"| 1. Login using Username and Password                       |\n" +
"| 2. Login using Phone Number                                |\n" +
"| 3. Registration                                            |\n" +
"| 4. Exit                                                    |\n" +
"|                                                            |\n" +
"+------------------------------------------------------------+\n" +
RESET;


    }

    // REGISTER
    public static void register() {
        System.out.println(YELLOW_BOLD + "---------------------- REGISTER ----------------------" + RESET);

        System.out.print(CYAN + "Enter new username: " + RESET);
        String user = sc.nextLine();

        if (findAccount(user) != null) {
            System.out.println(RED + "Username already exists! Please Login instead." + RESET);
            return;
        }

        System.out.println(Color.YELLOW +
        "Password must have:\n" +
        "- Min 8 characters\n" +
        "- At least 1 Uppercase\n" +
        "- At least 1 Number\n" +
        Color.RESET);


        String pass;
        while (true) {
            System.out.print(CYAN + "Enter Password: " + RESET);
            pass = sc.nextLine();

            if (isValidPassword(pass)) break;
            System.out.println(RED + "Invalid password. Follow the rules above." + RESET);
        }

        String phone;
        while (true) {
            System.out.print(CYAN + "Enter Phone Number: " + RESET);
            phone = sc.nextLine();

            if (!phone.matches("[6-9]\\d{9}")) {
                System.out.println(RED + "Phone must be 10 digits and start with 6-9!" + RESET);
                continue;
            }

            if (findAccountByPhone(phone) != null) {
                System.out.println(RED + "Phone already registered!" + RESET);
                continue;
            }

            break;
        }

        accounts.add(new Account(user, pass, phone));
        System.out.println(GREEN + "Registration Successful!" + RESET);
    }

    // LOGIN SUCCESS
    public static void loginSuccess() {
        System.out.println(GREEN + "Login Successful!!!" + RESET);
        System.out.println(PURPLE + "Loading..." + RESET);
    }

    // USERNAME & PASSWORD LOGIN
    public static boolean userPassLogin() {
        while (true) {
            System.out.print(CYAN + "Enter username: " + RESET);
            String user = sc.nextLine();

            System.out.print(CYAN + "Enter password: " + RESET);
            String pass = sc.nextLine();

            Account acc = findAccount(user);

            if (acc != null && acc.getPass().equals(pass)) {
    Login.setCurrentUser(acc.getUser(), acc.getNum());  // store logged-in user
    loginSuccess();
    return true;
}


            System.out.println(RED + "\nIncorrect Username or Password." + RESET);
            wrongAttempts[0]++;
            if (wrongAttempts[0] >= 3) return false;

            System.out.println(YELLOW + "1. Try Again\n2. Forgot Password\n3. Return to Menu" + RESET);
            System.out.print(CYAN + "Enter choice: " + RESET);

            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1: continue;
                case 2:
                    if (acc == null) {
                        System.out.println(RED + "Cannot reset password. Username incorrect." + RESET);
                        continue;
                    }
                    if (forgotPassword(acc)) {
                        System.out.println(YELLOW + "Login with NEW password." + RESET);
                    }
                    continue;

                case 3: return false;

                default:
                    System.out.println(RED + "Invalid choice." + RESET);
            }
        }
    }

    // FORGOT PASSWORD
    public static boolean forgotPassword(Account acc) {
        System.out.print(CYAN + "Re-enter Username: " + RESET);
        String user = sc.nextLine();

        if (!user.equals(acc.getUser())) {
            System.out.println(RED + "Wrong Username!" + RESET);
            return false;
        }

        System.out.println(GREEN + "Username Verified." + RESET);

        String newPass;
        while (true) {
            System.out.println(Color.YELLOW +
        "Password must contain:\n" +
        "- 8+ characters\n" +
        "- 1 Uppercase\n" +
        "- 1 Number\n" +
        Color.RESET);


            System.out.print(CYAN + "New Password: " + RESET);
            newPass = sc.nextLine();

            if (isValidPassword(newPass)) break;

            System.out.println(RED + "Invalid Password." + RESET);
        }

        acc.setPass(newPass);
        System.out.println(GREEN + "Password Reset Successful!" + RESET);
        return true;
    }

    // PHONE LOGIN
    public static boolean phoneLogin() {
        System.out.print(CYAN + "Enter Phone Number: " + RESET);
        String num = sc.nextLine();

        if (!num.matches("[6-9]\\d{9}")) {
            System.out.println(RED + "Invalid phone number!" + RESET);
            return false;
        }

        Account acc = findAccountByPhone(num);
        if (acc == null) {
            System.out.println(RED + "No account found for this phone!" + RESET);
            return false;
        }

        int otp = otpGen();
        System.out.println(GREEN + "OTP sent: " + PURPLE + otp + RESET);

        while (true) {
            System.out.print(CYAN + "Enter OTP: " + RESET);
            int entered;

            try {
                entered = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println(RED + "Invalid OTP format." + RESET);
                wrongAttempts[0]++;
                return false;
            }

            if (entered == otp) {
    System.out.println(GREEN + "OTP Verified!" + RESET);
    Login.setCurrentUser(acc.getUser(), acc.getNum());  // store logged-in user
    loginSuccess();
    return true;
}


            System.out.println(RED + "Incorrect OTP." + RESET);
            wrongAttempts[0]++;
            if (wrongAttempts[0] >= 3) return false;

            System.out.println(YELLOW + "1. Try Again\n2. Resend OTP\n3. Return to Menu" + RESET);
            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1: continue;
                case 2:
                    otp = otpGen();
                    System.out.println(YELLOW + "New OTP: " + PURPLE + otp + RESET);
                    continue;
                case 3: return false;
                default: System.out.println(RED + "Invalid Choice." + RESET);
            }
        }
    }

    // OTP GENERATOR
    public static int otpGen() {
        return new Random().nextInt(9000) + 1000;
    }

    // FIND ACCOUNT BY USERNAME
    static Account findAccount(String user) {
        for (Account a : accounts)
            if (a.getUser().equals(user)) return a;
        return null;
    }

    // FIND ACCOUNT BY PHONE
    static Account findAccountByPhone(String phone) {
        for (Account a : accounts)
            if (a.getNum().equals(phone)) return a;
        return null;
    }

    // PASSWORD RULE CHECK
    static boolean isValidPassword(String p) {
        return p.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
// ============================================================
// NEW: STORE CURRENT LOGGED-IN USER DETAILS
// ============================================================
private static String currentUser = null;
private static String currentPhone = null;

public static void setCurrentUser(String user, String phone) {
    currentUser = user;
    currentPhone = phone;
}

public static String getCurrentUser() {
    return currentUser;
}

public static String getCurrentPhone() {
    return currentPhone;
}

}


// ========================
// Payment system
// ========================

class PaymentAppUPI {

    static final String RESET  = Color.RESET;
    static final String BLUE   = Color.BLUE;
    static final String PURPLE = Color.PURPLE;
    static final String CYAN   = Color.CYAN;
    static final String GREEN  = Color.GREEN;
    static final String RED    = Color.RED;
    static final String YELLOW = Color.YELLOW;

    public static boolean performPayment(double amount) throws InterruptedException {
        Scanner sc = Main.sc;

        while (true) {

            System.out.println("\n--------- Select Payment Method ---------\n");

            button(1, "Google Pay", BLUE);
            button(2, "PhonePe", PURPLE);
            button(3, "Paytm", CYAN);
            button(4, "Exit", YELLOW);

            System.out.print("\nEnter your choice: ");
            String input = sc.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println(RED + "Invalid Choice!" + RESET);
                continue;
            }

            int choice = Integer.parseInt(input);

            if (choice < 1 || choice > 4) {
                System.out.println(RED + "Invalid Choice!" + RESET);
                continue;
            }

            if (choice == 4) {
                System.out.println(YELLOW + "Payment Cancelled! Exiting..." + RESET);
                return false;
            }

            if (choice >= 1 && choice <= 3) {
                return pay(sc, choice, amount);
            }
        }
    }

    private static void button(int no, String name, String color) {
        System.out.println(color + "[ " + no + " ]  " + name + RESET);
    }

    // MODIFIED: Added HOME option during PIN entry
    private static boolean pay(Scanner sc, int choice, double amount) throws InterruptedException {

        String color = (choice == 1) ? BLUE : (choice == 2) ? PURPLE : CYAN;

        System.out.print("\nPayment Amount: Rs.");
        System.out.println(amount);

        int attempts = 3;
        boolean success = false;

        while (attempts > 0) {
            // UPDATED: Added prompt for Home option
            System.out.println(YELLOW + "Enter UPI PIN (or type 'HOME'): " + RESET);
            System.out.print("Enter PIN/Choice: ");
            String input = sc.nextLine().trim();

            // Handle 'HOME' option
            if (input.equalsIgnoreCase("HOME")) {
                System.out.println(YELLOW + "" + RESET);
                return false;
            }

            // Handle UPI PIN
            if (input.equals("1234")) {
                success = true;
                break;
            } else {
                attempts--;
                if (attempts > 0)
                    System.out.println(RED + "Wrong PIN! Attempts left: " + attempts + RESET);
            }
        }

        if (!success) {
            System.out.println(RED + "Transaction Failed! Maximum attempts reached.\n" + RESET);
            return false;
        }

        // LOADING EFFECT
        System.out.print(GREEN + "\nProcessing");
        for (int i = 0; i < 12; i++) {
            Thread.sleep(250);
            System.out.print(".");
        }
        System.out.println(RESET);

        System.out.println(color + "Payment Successful! Paid Rs." + amount + RESET + "\n");

        return true;
    }
}


// ========================
// Movie booking
// ========================

class Movie implements java.io.Serializable { // NEW: Added Serializable
    // Use Main's static scanner
    static Scanner sc = Main.sc;

    // local ANSI COLORS
    static final String RESET = Color.RESET;
    static final String YELLOW = Color.YELLOW;
    static final String CYAN = Color.CYAN;
    static final String GREEN = Color.GREEN;
    static final String RED = Color.RED;
    static final String MAGENTA = Color.PURPLE;
    static final String BLUE = Color.BLUE;
    static final String BOLD = Color.BOLD;
    static final String GOLD = Color.GOLD;

    // seats[movieIndex][theatre][show][seatNumber]
    // NOTE: first dimension expanded to support many movies (up to 100)
    // The seats array state is loaded/saved in Main using the instance field booking.seats.
    boolean[][][][] seats = new boolean[101][6][5][51];

    // Total seat capacity for each category
    final int NORMAL_TOTAL = 20;
    final int PREMIUM_TOTAL = 15;
    final int VIP_TOTAL = 15;
    final int TOTAL_SEATS = NORMAL_TOTAL + PREMIUM_TOTAL + VIP_TOTAL; // 50

    // Max seats allowed per transaction
    final int MAX_SEATS_PER_BOOKING = 6;

    // Helper method to determine total available seats for a specific type/show
    int getAvailableSeatsByType(int movie, int theatre, int show, int seatType) {
        int minSeat = 1, maxSeat = 50, total = 0, booked = 0;
        if (seatType == 1) {
            minSeat = 1;
            maxSeat = 20;
            total = NORMAL_TOTAL;
        } else if (seatType == 2) {
            minSeat = 21;
            maxSeat = 35;
            total = PREMIUM_TOTAL;
        } else if (seatType == 3) {
            minSeat = 36;
            maxSeat = 50;
            total = VIP_TOTAL;
        } else return 0;

        // guard: ensure movie index is within bounds
        if (movie < 1 || movie > MovieSystem.movies.size()) return 0;

        for (int i = minSeat; i <= maxSeat; i++) {
            if (seats[movie][theatre][show][i]) {
                booked++;
            }
        }
        return total - booked;
    }

    int getPricePerType(int seatType) {
        if (seatType == 1) return 150;
        if (seatType == 2) return 250;
        if (seatType == 3) return 350;
        return 0;
    }

    // Displays the small, category-specific layout (No Legend)
    void displayCategoryLayout(int movie, int theatre, int show, int seatType) {

        int minSeat = 1, maxSeat = 50;
        String typeName, color;

        if (seatType == 1) {
            minSeat = 1;
            maxSeat = 20;
            typeName = "NORMAL";
            color = GREEN;
        } else if (seatType == 2) {
            minSeat = 21;
            maxSeat = 35;
            typeName = "PREMIUM";
            color = GOLD;
        } else {
            minSeat = 36;
            maxSeat = 50;
            typeName = "VIP";
            color = BLUE;
        }

        int available = getAvailableSeatsByType(movie, theatre, show, seatType);

        System.out.println(BOLD + "\n--- " + typeName + " SEAT LAYOUT (Seats " + minSeat + "-" + maxSeat + ") ---" + RESET);
        System.out.println(CYAN + "AVAILABLE SEATS: " + available + RESET);

        if (available == 0) {
            System.out.println(RED + "ALL SEATS BOOKED IN THIS CATEGORY." + RESET);
            return;
        }

        System.out.println("----------------------------------------");

        // Print seats in the category (descending for compact view)
        int count = 0;
        for (int i = maxSeat; i >= minSeat; i--) {
            if (seats[movie][theatre][show][i]) {
                // Booked: print seat number as [XX]
                System.out.print(RED + BOLD + "[XX]" + RESET + " ");
            } else {
                // Available: print seat number
                System.out.print(color + BOLD + String.format("[%02d]", i) + RESET + " ");
            }

            count++;
            // Print 5 seats per line for a neat, compact view
            if (count % 5 == 0) {
                System.out.println();
            }
        }
        if (count % 5 != 0) System.out.println(); // Newline if last row wasn't full
        System.out.println("----------------------------------------");
    }

    // Helper method to determine total available seats for a specific show (used in Main)
    int getAvailableSeats(int movie, int theatre, int show) {
        int count = 0;
        if (movie < 1 || movie > MovieSystem.movies.size() || theatre < 1 || theatre > 5 || show < 1 || show > 4) return 0;
        for (int i = 1; i <= TOTAL_SEATS; i++) {
            if (!seats[movie][theatre][show][i]) {
                count++;
            }
        }
        return count;
    }

    void display_movies() {
        MovieSystem.displayMovies();
    }

    // Select movie now uses MovieSystem.movies dynamically and prints its full details
    String select_movie(int a) {
        int movieCount = MovieSystem.movies.size();
        if (a < 1 || a > movieCount) return "invalid";

        AdminMovie m = MovieSystem.movies.get(a - 1);

        System.out.println("\n---------------------------------");
        System.out.println(YELLOW + "          " + m.name + RESET);
        System.out.println("          --------");
        System.out.println("DURATION            :- " + (m.duration == null ? "N/A" : m.duration));
        System.out.println("GENRE               :- " + (m.genre == null ? "N/A" : m.genre));
        System.out.println("MOVIE CERTIFICATION :- " + (m.certification == null ? "N/A" : m.certification));
        System.out.println("RELEASE DATE        :- " + (m.releaseDate == null ? "N/A" : m.releaseDate));
        System.out.println("---------------------------------");
        System.out.println("DIRECTOR       :- " + (m.director == null ? "N/A" : m.director));
        System.out.println("MUSIC DIRECTOR :- " + (m.musicDirector == null ? "N/A" : m.musicDirector));
        System.out.println("---------------------------------");
        System.out.println("CAST");
        System.out.println("----");
        if (m.cast == null || m.cast.isEmpty()) {
            System.out.println(" (No cast data)");
        } else {
            for (String actor : m.cast) {
                System.out.println("ACTOR   :- " + actor);
            }
        }
        System.out.println("---------------------------------");

        return m.name;
    }
    String getMovieReleaseDate(int index) {
        if (index < 1 || index > MovieSystem.movies.size()) return "N/A";
        return MovieSystem.movies.get(index - 1).releaseDate;
    }

    void theatres() {
        System.out.println(MAGENTA + "                AVAILABLE THEATRES" + RESET);
        System.out.println(MAGENTA + "                ___________________" + RESET);
        System.out.println();
        System.out.println("1 : " + CYAN + "PRASADS IMAX" + RESET);
        System.out.println("2 : " + CYAN + "SANDHYA 70 MM (RTC X ROADS)" + RESET);
        System.out.println("3 : " + CYAN + "SUDHARSHAN 35 MM (4k DOLBY ATMOS)" + RESET);
        System.out.println("4 : " + CYAN + "VISHWANATH 70MM KUKATPALLY" + RESET);
        System.out.println("5 : " + CYAN + "AMB CINEMAS HYDERABAD" + RESET);
        System.out.println("0 : HOME");
    }

    String[] theatreNames = {
            "", "PRASADS IMAX", "SANDHYA 70 MM (RTC X ROADS)",
            "SUDHARSHAN 35 MM (4k DOLBY ATMOS)",
            "VISHWANATH 70MM KUKATPALLY", "AMB CINEMAS HYDERABAD"
    };

    String select_theatre(int a) {
        if (a < 1 || a > 5) return "invalid";
        String name = theatreNames[a];
        System.out.println(CYAN + "\n                   " + name + RESET);
        showTimings();
        return name;
    }

    void showTimings() {
        System.out.println();
        System.out.println(MAGENTA + "                         AVAILABLE SHOWS" + RESET);
        System.out.println(MAGENTA + "                         ---------------" + RESET);
        System.out.println("    --------------- --------------- --------------- ---------------");
        System.out.println("    | 1:- 10:20AM | | 2:- 01:00PM | | 3:- 04:25PM | | 4:- 07:20PM |");
        System.out.println("    --------------- --------------- --------------- ---------------");
        System.out.println("0 : HOME");
        System.out.println("SELECT THE SHOW");
    }

    String select_show(int a) {
        if (a == 1) return "10:20AM";
        if (a == 2) return "01:00PM";
        if (a == 3) return "04:25PM";
        if (a == 4) return "07:20PM";
        return "invalid type";
    }

    // Seat Selection Logic - Returns List of Selected Seats on Success
    List<Integer> select_seat(int movie, int theatre, int show, int seatType) {
        if (seatType < 1 || seatType > 3) return null;

        // validate movie index against dynamic list
        if (movie < 1 || movie > MovieSystem.movies.size()) {
            System.out.println(RED + "INVALID MOVIE SELECTED" + RESET);
            return null;
        }

        int minSeat = 1, maxSeat = 50;
        String typeName;

        if (seatType == 1) {
            minSeat = 1;
            maxSeat = 20;
            typeName = "NORMAL";
        } else if (seatType == 2) {
            minSeat = 21;
            maxSeat = 35;
            typeName = "PREMIUM";
        } else {
            minSeat = 36;
            maxSeat = 50;
            typeName = "VIP";
        }

        int totalAvailableHere = getAvailableSeatsByType(movie, theatre, show, seatType);

        System.out.println(YELLOW + "\n--- " + typeName + " SEAT SELECTION ---" + RESET);

        // 1. Display the small category layout
        displayCategoryLayout(movie, theatre, show, seatType);

        if (totalAvailableHere <= 0) {
            return null;
        }

        int maxSelectable = Math.min(totalAvailableHere, MAX_SEATS_PER_BOOKING);

        int numSeats;
        while (true) {
            // MODIFIED: Added specific prompts for number of seats selection
            System.out.println(YELLOW + "ENTER HOW MANY SEATS (Max " + maxSelectable + " seats):" + RESET);
            System.out.println(CYAN + "0 : HOME (Go Back to Movie Selection)" + RESET);
            System.out.print(YELLOW + "ENTER CHOICE (1-" + maxSelectable + " or 0): " + RESET);

            try {
                // Use nextLine() and parse
                numSeats = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println(RED + "INVALID INPUT, ENTER AGAIN" + RESET);
                continue;
            }

            if (numSeats == 0) return null; // User chose to go back (Home)

            if (numSeats >= 1 && numSeats <= MAX_SEATS_PER_BOOKING) {
                if (numSeats > totalAvailableHere) {
                    System.out.println(RED + "ONLY " + totalAvailableHere + " SEAT(S) AVAILABLE, ENTER AGAIN" + RESET);
                    continue;
                }
                break;
            }
            System.out.println(RED + "INVALID INPUT (Must be between 1 and " + MAX_SEATS_PER_BOOKING + "), ENTER AGAIN" + RESET);
        }

        List<Integer> selectedSeats = new ArrayList<>();

        System.out.println("\nCHOOSE OPTION");
        System.out.println(CYAN + "SELECT SEAT NUMBERS (" + minSeat + " to " + maxSeat + ") ONE BY ONE" + RESET);
        System.out.println(CYAN + "ENTER 0 TO GO BACK" + RESET);
        System.out.println("-------------------------------------------------------------------");

        for (int i = 1; i <= numSeats; i++) {
            System.out.print("Seat " + i + ": ");
            int seatNo;
            try {
                seatNo = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println(RED + "INVALID INPUT, ENTER AGAIN" + RESET);
                i--;
                continue;
            }
            if (seatNo == 0) return null; // User chose to go back

            if (seatNo < minSeat || seatNo > maxSeat) {
                System.out.println(RED + "INVALID SEAT NUMBER FOR THIS SEAT TYPE, ENTER AGAIN" + RESET);
                i--;
                continue;
            }

            // Check if already booked in the array for this specific show
            if (seats[movie][theatre][show][seatNo]) {
                System.out.println(RED + "THIS SEAT IS ALREADY BOOKED — CHOOSE ANOTHER SEAT" + RESET);
                i--;
                continue;
            }

            // Check if already selected in this current transaction
            if (selectedSeats.contains(seatNo)) {
                System.out.println(RED + "THIS SEAT IS ALREADY SELECTED — CHOOSE ANOTHER SEAT" + RESET);
                i--;
                continue;
            }

            selectedSeats.add(seatNo);
            System.out.println(GREEN + "Seat " + seatNo + " selected." + RESET);
        }

        return selectedSeats;
    }

    // Prints the full theatre layout (No Legend text)
    void printTheatreLayout(int movie, int theatre, int show, String theatreName) {

        // 1. --- THEATRE NAME HEADER ---
        System.out.println(Color.PURPLE + "\n" + Color.BOLD + "=========================================================" + Color.RESET);
        System.out.println(Color.PURPLE + Color.BOLD + String.format("%-57s", "  " + theatreName.toUpperCase()) + Color.RESET);
        System.out.println(Color.PURPLE + Color.BOLD + "=========================================================" + Color.RESET);

        // 2. --- ENTRANCE (BACK OF THE THEATRE) ---
        System.out.println(Color.CYAN + "\n" + Color.BOLD + "                       ENTRANCE" + Color.RESET);
        System.out.println(Color.CYAN + "---------------------------------------------------------" + Color.RESET);

        // 3. --- SEAT LAYOUT (10 rows of 5 seats) ---
        int currentSeat = 50;

        for (int r = 10; r >= 1; r--) {

            String categoryLabel = "";

            // Assign seat category label for clarity (These labels are kept for spatial awareness)
            if (r == 10) categoryLabel = Color.BOLD + Color.BLUE + "VIP (36-50)  " + Color.RESET;
            else if (r == 7) categoryLabel = Color.BOLD + Color.GOLD + "PREMIUM (21-35)" + Color.RESET;
            else if (r == 4) categoryLabel = Color.BOLD + Color.GREEN + "NORMAL (1-20) " + Color.RESET;
            else categoryLabel = "               ";

            // Print category label (left aligned)
            System.out.printf("%s ", categoryLabel);

            // Print seats in the row (5 seats per row)
            for (int seatInRow = 1; seatInRow <= 5; seatInRow++) {

                String display;
                String finalColor;

                if (seats[movie][theatre][show][currentSeat]) {
                    // Booked Seat
                    display = String.format("%-2s", "XX");
                    finalColor = Color.RED + Color.BOLD;
                } else {
                    // Available Seat
                    display = String.format("%02d", currentSeat);

                    if (currentSeat >= 36) finalColor = Color.BLUE + Color.BOLD; // VIP: 36-50
                    else if (currentSeat >= 21) finalColor = Color.GOLD + Color.BOLD; // Premium: 21-35
                    else finalColor = Color.GREEN + Color.BOLD; // Normal: 1-20
                }

                System.out.print(finalColor + "[" + display + "]" + Color.RESET + "  ");
                currentSeat--;
            }

            System.out.println(); // Newline after each row

        }

        // 4. --- SCREEN/STAGE (FRONT) ---
        System.out.println(Color.CYAN + "---------------------------------------------------------" + Color.RESET);
        System.out.println(Color.CYAN + Color.BOLD + "                       SCREEN THIS WAY" + Color.RESET);
        System.out.println(Color.CYAN + "---------------------------------------------------------" + Color.RESET);
    }
}


// ========================
// Ticket Class (for History)
// ========================

class Ticket implements java.io.Serializable { // NEW: Added Serializable
    String bookingId;
    String movieName;
    String theatre;
    String showTime;
    String releaseDate;
    String seatsList;
    String seatType;
    int numTickets;
    int totalPrice;
    String user;
    String phone;
    String date; // This now only holds the date (dd-MM-yyyy)

    public Ticket(String bookingId, String movieName, String theatre, String showTime, String releaseDate,
                  String seatsList, String seatType, int numTickets, int totalPrice, String user, String phone, String date) {
        this.bookingId = bookingId;
        this.movieName = movieName;
        this.theatre = theatre;
        this.showTime = showTime;
        this.releaseDate = releaseDate;
        this.seatsList = seatsList;
        this.seatType = seatType;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
        this.user = user;
        this.phone = phone;
        this.date = date;
    }

    // Small ticket print method for history
    public void printSmallTicket() {
        final String RESET = Color.RESET;
        final String CYAN = Color.CYAN;
        final String GREEN = Color.GREEN;
        final String BOLD = Color.BOLD;
        final String GOLD = Color.GOLD;

        System.out.println(BOLD + GOLD + "\n========================================" + RESET);
        System.out.println(BOLD + GOLD + "|         TICKET CONFIRMED             |" + RESET);
        System.out.println(BOLD + GOLD + "========================================" + RESET);
        System.out.println(CYAN + String.format("| %-14s : %-19s|", "Booking ID", bookingId) + RESET);
        // Shorten long strings for small format
        String shortMovie = movieName.substring(0, Math.min(19, movieName.length()));
        String shortTheatre = theatre.substring(0, Math.min(19, theatre.length()));
        String shortSeats = seatsList.substring(0, Math.min(19, seatsList.length()));

        System.out.println(GREEN + String.format("| %-14s : %-19s|", "Movie", shortMovie) + RESET);
        System.out.println(GREEN + String.format("| %-14s : %-19s|", "Theatre", shortTheatre) + RESET);
        System.out.println(CYAN + String.format("| %-14s : %-19s|", "Booking Date", date) + RESET); // MODIFIED: Date only
        System.out.println(CYAN + String.format("| %-14s : %-19s|", "Show Time", showTime) + RESET); // MODIFIED: Time separate
        System.out.println(CYAN + String.format("| %-14s : %-19s|", "Rel. Date", releaseDate) + RESET);
        System.out.println(GREEN + String.format("| %-14s : %-19s|", "Seats/Type", numTickets + " (" + seatType + ")") + RESET);
        System.out.println(GREEN + String.format("| %-14s : %-19s|", "Seat Nos.", shortSeats) + RESET);
        System.out.println(GOLD + String.format("| %-14s : Rs. %-15s|", "Total Price", totalPrice) + RESET);
        System.out.println(BOLD + GOLD + "========================================" + RESET);
    }
}


// ========================
// Main orchestrator
// =======================


class Main {
    public static final Scanner sc = new Scanner(System.in);
	public static void printPreConfirmationDetails(
        String movieName,
        String theatre,
        String show,
        String releaseDate,
        String seatsList,
        String seatTypeName,
        int seatCount,
        int totalPrice) {

    System.out.println("\n========== PRE-CONFIRMATION DETAILS ==========");
    System.out.println("Movie Name       : " + movieName);
    System.out.println("Theatre          : " + theatre);
    System.out.println("Show Time        : " + show);
    System.out.println("Release Date     : " + releaseDate);
    System.out.println("Seat Type        : " + seatTypeName);
    System.out.println("Selected Seats   : " + seatsList);
    System.out.println("Seats Count      : " + seatCount);
    System.out.println("Total Price      : Rs." + totalPrice);
    System.out.println("================================================");
}


    // NEW: Persistence constants and static Movie instance
    private static final String HISTORY_FILE = "history.ser";
    private static final String SEATS_FILE = "seats.ser";
    private static Movie booking = new Movie(); // Made static and initialized here

    // History list to store confirmed tickets (remains static)
    static List<Ticket> history = new ArrayList<>();

    // Define date formatter for consistency (dd-MM-yyyy format)
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // NEW: Helper method to load data from files
    @SuppressWarnings("unchecked")
    private static void loadData() {
        // Load Seats Array
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SEATS_FILE))) {
            booking.seats = (boolean[][][][]) ois.readObject();
            System.out.println(Color.GREEN + "" + Color.RESET);
        } catch (FileNotFoundException e) {
            System.out.println(Color.YELLOW + "" + Color.RESET);
        } catch (Exception e) {
            System.out.println(Color.RED + "Error loading seat data: " + e.getMessage() + Color.RESET);
        }

        // Load History List
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HISTORY_FILE))) {
            history = (List<Ticket>) ois.readObject();
            System.out.println(Color.GREEN + "" + Color.RESET);
        } catch (FileNotFoundException e) {
            System.out.println(Color.YELLOW + "" + Color.RESET);
        } catch (Exception e) {
            System.out.println(Color.RED + "Error loading history data: " + e.getMessage() + Color.RESET);
        }
    }

    // NEW: Helper method to save data to files
    private static void saveData() {
        // Save Seats Array
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SEATS_FILE))) {
            oos.writeObject(booking.seats);
        } catch (Exception e) {
            System.out.println(Color.RED + "Error saving seat data: " + e.getMessage() + Color.RESET);
        }

        // Save History List
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE))) {
            oos.writeObject(history);
        } catch (Exception e) {
            System.out.println(Color.RED + "Error saving history data: " + e.getMessage() + Color.RESET);
        }
        System.out.println(Color.GREEN + "\nData persistence complete." + Color.RESET);
    }


    // Helper method to generate a simple unique Booking ID
    private static String generateBookingId() {
        // Simple 8-character alphanumeric ID for console application
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return "BMS" + sb.toString();
    }

    // New method to print pre-payment confirmation details
   private static void printFinalTicket(String movieName, String theatre, String show, String releaseDate,
                                     String seatsList, String seatType, int numTickets, int totalPrice) {

    String bookingId = generateBookingId();

    // NEW LOGIN SYSTEM
    String user = Login.getCurrentUser();
    String phone = Login.getCurrentPhone();

    String date = LocalDate.now().format(DATE_FORMATTER);

    // Store in history
    history.add(new Ticket(bookingId, movieName, theatre, show, releaseDate, seatsList,
            seatType, numTickets, totalPrice, user, phone, date));

    final String RESET = Color.RESET;
    final String CYAN = Color.CYAN;
    final String GREEN = Color.GREEN;
    final String YELLOW = Color.YELLOW;
    final String BOLD = Color.BOLD;
    final String GOLD = Color.GOLD;

    System.out.println(BOLD + GOLD + "\n==================================================================" + RESET);
    System.out.println(BOLD + GOLD + "|                        TICKET CONFIRMED                        |" + RESET);
    System.out.println(BOLD + GOLD + "==================================================================" + RESET);

    System.out.println(CYAN + String.format("| %-20s : %-39s |", "BOOKING ID", bookingId) + RESET);
    System.out.println(CYAN + String.format("| %-20s : %-39s |", "DATE OF BOOKING", date) + RESET);

    System.out.println(BOLD + GOLD + "|----------------------------------------------------------------|" + RESET);
    System.out.println(GREEN + String.format("| %-20s : %-39s |", "MOVIE", movieName) + RESET);
    System.out.println(GREEN + String.format("| %-20s : %-39s |", "THEATRE", theatre) + RESET);
    System.out.println(GREEN + String.format("| %-20s : %-39s |", "SHOW TIME", show) + RESET);
    System.out.println(CYAN + String.format("| %-20s : %-39s |", "RELEASE DATE", releaseDate) + RESET);
    System.out.println(GREEN + String.format("| %-20s : %-39s |", "SEAT TYPE", seatType) + RESET);
    System.out.println(GREEN + String.format("| %-20s : %-39s |", "NO. OF TICKETS", numTickets) + RESET);
    System.out.println(GREEN + String.format("| %-20s : %-39s |",
            "SEAT NUMBERS",
            seatsList.substring(0, Math.min(39, seatsList.length()))
    ) + RESET);

    System.out.println(BOLD + GOLD + "|----------------------------------------------------------------|" + RESET);
    System.out.println(YELLOW + String.format("| %-20s : Rs. %-35s |", "TOTAL PRICE", totalPrice) + RESET);
    System.out.println(BOLD + GOLD + "|----------------------------------------------------------------|" + RESET);

    // USER DETAILS
    System.out.println(CYAN + String.format("| %-20s : %-39s |", "BOOKED BY", user) + RESET);
    System.out.println(CYAN + String.format("| %-20s : %-39s |", "CONTACT NO", phone) + RESET);

    System.out.println(BOLD + GOLD + "==================================================================" + RESET);
    System.out.println(BOLD + GOLD + "|       SHOW THIS BOOKING ID AT THE BOX OFFICE. ENJOY!           |" + RESET);
    System.out.println(BOLD + GOLD + "==================================================================" + RESET);
}
public static void displayHistory() {
    System.out.println(Color.PURPLE + "\n=========================================================" + Color.RESET);
    System.out.println(Color.PURPLE + Color.BOLD + "|                   BOOKING HISTORY                   |" + Color.RESET);
    System.out.println(Color.PURPLE + "=========================================================" + Color.RESET);

    String user = Login.getCurrentUser();

    // USER must be logged in
    if (user == null) {
        System.out.println(Color.RED + "ERROR: Please LOG IN first to view history." + Color.RESET);
        return;
    }

    boolean found = false;

    for (Ticket ticket : history) {
        if (ticket.user.equals(user)) {
            ticket.printSmallTicket();
            found = true;
        }
    }

    if (!found) {
        System.out.println(Color.YELLOW + "\nNO BOOKING HISTORY FOUND FOR " + user.toUpperCase() + "." + Color.RESET);
    }

    System.out.println(Color.PURPLE + "=========================================================" + Color.RESET);
}



    public static void main(String[] args) {
        // Movie booking = new Movie(); // OLD: Removed, now 'booking' is a static field.

        // NEW: Load persistence data on startup
        loadData();

        mainMenu:
        while (true) {
            System.out.println("\n==== WELCOME TO BOOK-MY-SHOW ====");
            System.out.println("1. USER");
            System.out.println("2. ADMIN");
            System.out.println("3. EXIT"); // HISTORY removed, EXIT renumbered to 3
            System.out.print("Select: ");

            String opt = sc.nextLine().trim();

            
                switch (opt) {

            case "1": // USER
                boolean loggedIn = Login.startLogin();
                if (!loggedIn) continue;

                //  ADDED — LOGIN LOADING EFFECT
                System.out.print(Color.YELLOW + "Logging you in");
                for (int i = 0; i < 3; i++) {
                    try { Thread.sleep(500); } catch (Exception e) {}
                    System.out.print(".");
                }
                System.out.println("\nLoading your dashboard...\n" + Color.RESET);
                try { Thread.sleep(700); } catch (Exception e) {}
                    userMenuLoop: // New loop for post-login user options
                    while (true) {
                        System.out.println(Color.PURPLE + "\n--- USER MENU ---" + Color.RESET);
                        System.out.println("1. BOOK MOVIE");
                        System.out.println("2. VIEW HISTORY"); // History is now here
                        System.out.println("3. LOGOUT");
                        System.out.print(Color.YELLOW + "SELECT OPTION: " + Color.RESET);

                        String userOpt = sc.nextLine().trim();

                        switch (userOpt) {
                            case "1": // BOOK MOVIE - existing booking flow
                                userBooking: // Label for the movie selection loop
                                while (true) {
                                    // --- Movie selection ---
                                    booking.display_movies();
                                    int maxMovieOption = MovieSystem.movies.size() + 1;
                                    int a;
                                    while (true) {
                                        System.out.print("SELECT MOVIE (1-" + maxMovieOption + "): ");
                                        try { a = Integer.parseInt(sc.nextLine()); }
                                        catch (Exception e) { System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET); continue; }
                                        if (a >= 1 && a <= maxMovieOption) break;
                                        System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET);
                                    }
                                    if (a == maxMovieOption) break userBooking; // Back to userMenuLoop

                                    String movieName = booking.select_movie(a);
                                    String releaseDate = booking.getMovieReleaseDate(a); // Fetch release date
                                    if (movieName.equals("invalid")) {
                                        System.out.println(Color.RED + "Invalid movie selection. Returning to movie list." + Color.RESET);
                                        continue;
                                    }

                                    // HOME / BOOK
                                    int b;
                                    while (true) {
                                        System.out.println("1 : HOME\n2 : BOOK TICKET");
                                        try { b = Integer.parseInt(sc.nextLine()); }
                                        catch (Exception e) { System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET); continue; }
                                        if (b == 1 || b == 2) break;
                                        System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET);
                                    }
                                    if (b == 1) continue;

                                    // --- Theatre selection ---
                                    booking.theatres();
                                    int d;
                                    while (true) {
                                        System.out.println("SELECT THE THEATRE (1-5), 0 TO GO BACK");
                                        try { d = Integer.parseInt(sc.nextLine()); }
                                        catch (Exception e) { System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET); continue; }
                                        if (d >= 0 && d <= 5) break;
                                        System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET);
                                    }
                                    if (d == 0) continue;
                                    String theatre = booking.select_theatre(d);

                                    // --- Show selection ---
                                    int c;
                                    while (true) {
                                        System.out.println("SELECT SHOW (1-4)");
                                        try { c = Integer.parseInt(sc.nextLine()); }
                                        catch (Exception e) { System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET); continue; }
                                        if (c >= 1 && c <= 4) break;
                                        System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET);
                                    }
                                    String show = booking.select_show(c);

                                    booking.printTheatreLayout(a, d, c, theatre);
                                    int availableOverall = booking.getAvailableSeats(a, d, c);
                                    System.out.println(Color.YELLOW + "TOTAL AVAILABLE SEATS: " + availableOverall + Color.RESET);
                                    if (availableOverall == 0) { System.out.println(Color.RED + "Show fully booked." + Color.RESET); continue; }

                                    // --- Seat type selection ---
                                    int normalAvail = booking.getAvailableSeatsByType(a,d,c,1);
                                    int premiumAvail = booking.getAvailableSeatsByType(a,d,c,2);
                                    int vipAvail = booking.getAvailableSeatsByType(a,d,c,3);

                                    System.out.println("\nTICKET PRICES:");
                                    System.out.println("1. " + (normalAvail>0?Color.GREEN:Color.RED) + "NORMAL (1-20) Rs.150 - Available: "+normalAvail+Color.RESET);
                                    System.out.println("2. " + (premiumAvail>0?Color.GOLD:Color.RED) + "PREMIUM (21-35) Rs.250 - Available: "+premiumAvail+Color.RESET);
                                    System.out.println("3. " + (vipAvail>0?Color.BLUE:Color.RED) + "VIP (36-50) Rs.350 - Available: "+vipAvail+Color.RESET);

                                    // MODIFIED: Added structured input for seat type
                                    int seatType;
                                    while (true) {
                                        System.out.println(Color.CYAN + "0 : HOME (Go Back to Movie Selection)" + Color.RESET);
                                        System.out.print(Color.YELLOW + "ENTER CHOICE (1-3 or 0): " + Color.RESET);
                                        try { seatType = Integer.parseInt(sc.nextLine()); }
                                        catch (Exception e) { System.out.println(Color.RED + "INVALID INPUT" + Color.RESET); continue; }

                                        if (seatType == 0) continue userBooking; // Go back to movie selection

                                        if (seatType>=1 && seatType<=3) {
                                            int availableSeats = booking.getAvailableSeatsByType(a,d,c,seatType);
                                            if (availableSeats <= 0) {
                                                System.out.println(Color.RED + "NO SEATS AVAILABLE in this category. Select another." + Color.RESET);
                                                continue;
                                            }
                                            break;
                                        }
                                        System.out.println(Color.RED + "INVALID INPUT" + Color.RESET);
                                    }

                                    String seatTypeName = seatType==1?"NORMAL":seatType==2?"PREMIUM":"VIP";
                                    List<Integer> selectedSeats = booking.select_seat(a,d,c,seatType);
                                    if (selectedSeats == null || selectedSeats.isEmpty()) continue; // Go back if user chose 0

                                    int totalPrice = booking.getPricePerType(seatType) * selectedSeats.size();
                                    String seatsList = selectedSeats.toString().replace("[","").replace("]","");

                                    // --- Pre-Payment Confirmation ---
                                    Main.printPreConfirmationDetails(movieName, theatre, show, releaseDate, seatsList, seatTypeName, selectedSeats.size(), totalPrice);

                                    // --- Confirmation & Payment ---
                                    paymentLoop:
                                    while (true) {
                                        System.out.println(Color.YELLOW + "\nDO YOU WANT TO CONTINUE THIS BOOKING OR CANCEL IT?" + Color.RESET);
                                        System.out.println(Color.CYAN + "1 : CONTINUE FOR PAYMENT" + Color.RESET);
                                        System.out.println(Color.CYAN + "2 : CANCEL" + Color.RESET);
                                        System.out.print(Color.YELLOW + "ENTER CHOICE: " + Color.RESET);
                                        String confirm = sc.nextLine();

                                        if (confirm.equals("1")) {
                                            boolean paid = false;
                                            try { paid = PaymentAppUPI.performPayment(totalPrice); }
                                            catch (InterruptedException ie) { System.out.println("Payment interrupted."); }

                                            if (paid) {
                                                for (int seatNo : selectedSeats) booking.seats[a][d][c][seatNo] = true;

                                                // Store and Print the structured ticket
                                                Main.printFinalTicket(movieName, theatre, show, releaseDate, seatsList, seatTypeName, selectedSeats.size(), totalPrice);

                                                // NEW: Save all persistent data after successful booking
                                                saveData();

                                                // Post-Booking Options
                                                String postBookingDecision;
                                                while (true) {
                                                    System.out.println(Color.YELLOW + "\nBOOKING COMPLETE. WHAT NEXT?" + Color.RESET);
                                                    System.out.println(Color.CYAN + "1 : BOOK AGAIN (Recommended Movies)" + Color.RESET);
                                                    System.out.println(Color.CYAN + "2 : LOGOUT (User Menu)" + Color.RESET); // Updated text
                                                    System.out.print(Color.YELLOW + "ENTER CHOICE: " + Color.RESET);
                                                    postBookingDecision = sc.nextLine();

                                                    if (postBookingDecision.equals("1")) {
                                                        break paymentLoop; // Exit payment loop, continue userBooking (Book Again)
                                                    } else if (postBookingDecision.equals("2")) {
                                                        continue userMenuLoop; // Exit user session (Main Menu)
                                                    } else {
                                                        System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET);
                                                    }
                                                }
                                            } else {
                                                System.out.println(Color.RED + "Payment failed/cancelled. Seats released." + Color.RESET);
                                                break paymentLoop; // Back to movie list (userBooking continue)
                                            }

                                        } else if (confirm.equals("2")) {
                                            System.out.println(Color.YELLOW + "BOOKING CANCELLED! Seats released." + Color.RESET);
                                            break paymentLoop; // Back to movie list (userBooking continue)
                                        } else {
                                            System.out.println(Color.RED + "INVALID INPUT, ENTER AGAIN" + Color.RESET);
                                        }
                                    }
                                } // END userBooking loop
                                break;

                            case "2": // VIEW HISTORY
                                displayHistory();
                                break;

                            case "3": // LOGOUT
                                System.out.println(Color.GREEN + "LOGOUT SUCCESSFUL!" + Color.RESET);
                                break userMenuLoop; // Go back to the mainMenu loop
                            default:
                                System.out.println(Color.RED + "INVALID OPTION!" + Color.RESET);
                        }
                    } // END userMenuLoop
                    break;

                case "2":
                    MovieSystem.adminLogin();
                    break;

                case "3": // EXIT
                    // NEW: Save all persistent data before exit
                    saveData();
                    System.out.println("THANK YOU!");
                    break mainMenu;

                default:
                    System.out.println(Color.RED + "INVALID CHOICE! " + Color.RESET);
                    break;
            } // END switch
        } // END mainMenu loop
    } // END main()
} // END Main class
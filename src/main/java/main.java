import java.sql.*;

public class main {
    public static void main(String[] args) {
        UserCommand userCommand = new UserCommand();
        userCommand.signUp("aouldcott0", "06416856140", "Wanstall", "Ouldcott", "zC2@3q~Av", "Accountant I");
        userCommand.signUp("sanooavi", "09105039074", "sana", "mousavi", "13901390", "studying");
        userCommand.signUp("saraMahmoudi", "09234679832", "sara", "mahmoudi", "12345", null);
        userCommand.signUp("amcilenna3", "04823776619", "MacAllester", "McIlenna", "gX0|4uuU)", "Accounting Assistant III");
        userCommand.signUp("bklementz8", "02761287515", "Gambie", "Klementz", "zG0)!Nnj", "General Manager");
        userCommand.signUp("cidney9", "01389281164", "MacGarrity", "Idney", "oD7)B+{k~1O{", "Tax Accountant");
        userCommand.signUp("clestor7", "06339000507", "Neame", "Lestor", "vY5)<nQ<wH\"0J\"d", null);
        userCommand.signUp("kmariaud6", "09679962737", "McLucas", "Mariaud", "jQ0?2Trj?L|m%", null);
        userCommand.login("sanoooavi", "1380");
        userCommand.login("sanooavi", "1380");
        userCommand.change_bio("sanooavi", "salamsalam");

        try {
            System.out.println("\n******************************Users Table*******************************");
            userCommand.printTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }


}

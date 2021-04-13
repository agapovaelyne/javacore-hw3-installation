import java.io.File;

public class Main {
    public static void main(String[] args) {

        String gamesPath = "/Users/elina.agapova/NTLG/Games/";
        Game newGame = new Game("Don't Starve Together");
        System.out.println(newGame.install(gamesPath));        ;
    }
}

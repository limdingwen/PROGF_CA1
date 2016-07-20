import java.util.Scanner;

public class Quiz {
    String name;
    List<Question> questions;
    
    public Quiz(Scanner quiz) {
        name = quiz.nextLine();
    }
}
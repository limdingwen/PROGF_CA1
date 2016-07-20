import java.util.Scanner;

public class Question {
    enum Type {
        MCQ, OPEN
    }
    
    enum Matching {
        EXACT, CONTAINS
    }
    
    enum CaseMatching {
        EXACT, ANYTHING
    }
    
    String name;
    Type type;
    Matching matching;
    CaseMatching caseMatching;
    List<String> possibilities;
    List<String> answers;
    
    public Question(Scanner question) throws CorruptedQuestionException {
        name = question.nextLine();
        
        String typeStr = question.nextLine();
        switch (typeStr) {
            case "MCQ":
                type = Type.MCQ;
                break;
            case "OPEN":
                type = Type.OPEN;
                break;
            default:
                throw new CorruptedQuestionException("Invalid type " + typeStr);
        }
        
        switch (type) {
            case Type.MCQ:
                possibilities = ArrayList<String>();
                
                while (question.hasNextLine()) {
                    String possibility = question.nextLine();
                    if (possibility.equals("ANS")) break;
                    possibilities.add(possibility);
                }
                
                break;
        }
        
        answers = ArrayList<String>();
        
        while (question.hasNextLine()) {
            answers.add(question.nextLine());
        }
    }
}
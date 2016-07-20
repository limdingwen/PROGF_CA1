package ignis.quiz;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Quiz {
    public String name;
    public String instructions;
    public Question[] questions;
    
    public Quiz(Scanner scanner) throws InvalidQuizException {
        try {
            name = scanner.nextLine();
            instructions = scanner.nextLine().replace("\\n", "\n");
            
            // Questions
            
            List<Question> questionsList = new ArrayList<Question>();
            
            while (scanner.hasNextLine()) {
                Question question = new Question();
                
                String typeStr = scanner.nextLine();
                switch (typeStr) {
                    case "MCQ":
                        question.type = Question.Type.MCQ;
                        break;
                    case "OPEN":
                        question.type = Question.Type.OPEN;
                        break;
                    default:
                        throw new InvalidQuizException(typeStr + " is not a question type (MCQ or OPEN).");
                }
                
                question.question = scanner.nextLine().replace("\\n", "\n");
                
                switch (question.type) {
                    case MCQ:
                        List<String> possibilitiesList = new ArrayList<String>();
                        for (int i = 0; i < 4; i++) {
                            possibilitiesList.add(scanner.nextLine());
                        }
                        question.possibilities = possibilitiesList.toArray(new String[possibilitiesList.size()]);
                        
                        question.mcqAnswer = Integer.parseInt(scanner.nextLine());
                        
                        break;
                    case OPEN:
                        question.openAnswer = scanner.nextLine();
                        
                        break;
                    default:
                        throw new InvalidQuizException("We messed up; we used a question type that didn't exist?!");
                }
                
                questionsList.add(question);
            }
            
            questions = questionsList.toArray(new Question[questionsList.size()]);
        }
        catch (NumberFormatException e) {
            throw new InvalidQuizException("That's not a number, but it needs to be! " + e.getMessage());
        }
        catch (NoSuchElementException e) {
            throw new InvalidQuizException("Hey, there should be more! " + e.getMessage());
        }
    }
}
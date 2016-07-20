package ignis.quiz;

public class Question {
    enum Type { MCQ, OPEN }
    
    String question;
    Type type;
    String[] possibilities;
    String openAnswer;
    int mcqAnswer;
}
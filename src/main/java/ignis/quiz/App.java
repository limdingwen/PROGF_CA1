package ignis.quiz;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

public class App {
    static Scanner console;
    
    public static void main(String[] args) {
        console = new Scanner(System.in);
        
        // Get file and parse quiz
        
        Quiz quiz;
        try {
            File quizFile = promptFile();
            quiz = new Quiz(new Scanner(quizFile));
        }
        catch (FileNotFoundException e) {
            showMessage("That's not a correct file path! " + e.getMessage());
            return;
        }
        catch (InvalidQuizException e) {
            showMessage("That quiz file is a spy! " + e.getMessage());
            return;
        }
        
        // Show loaded details
        
        showMessage(quiz.name + " (" + quiz.questions.length + " questions)" +
            "\n" + 
            "\n" + 
            "INSTRUCTIONS\n" + 
            "Answer all questions.\n" +
            "For MCQ questions, choose the answer from 1 to 4.\n" + 
            "Open-ended answers must be exact.\n" +
            "Open-ended answers are case insensitive.\n" +
            "You can take this quiz multiple times.\n" +
            "\n" + 
            "QUIZ\n" + quiz.instructions);
        
        // Start quiz
        
        boolean again = false;
        
        do {
            int score = 0;
            
            for (Question question : quiz.questions) {
                switch (question.type) {
                    case MCQ:
                        int mcqAns;
                        
                        // MCQ Answers will not be out of range since we're using a drop down menu. Uncomment if using
                        // a more open-ended way (e.g. System.in) to enable 1-to-4-validation of MCQ answer.
                        
                        //while (true) {
                            mcqAns = promptMCQ(question.question, question.possibilities);
                            
                        //    if (1 <= mcqAns && mcqAns <= 4) break;
                        //    else showMessage("MCQ answer must be between 1 to 4.");
                        //}
                            
                        if (mcqAns == question.mcqAnswer) score++;
                        else showMessage("Incorrect. The correct answer is " + question.mcqAnswer + ".");
                        
                        break;
                    case OPEN:
                        String openAns;
                        
                        while (true) {
                            openAns = promptOpen(question.question);
                            
                            if (openAns.equals("")) showMessage("Answers must not be blank.");
                            else break;
                        }
                        
                        if (question.openAnswer.toLowerCase().equals(openAns.toLowerCase())) score++;
                        else showMessage("Incorrect. The correct answer is \"" + question.openAnswer + "\".");
                        
                        break;
                    default:
                        showMessage("Unknown question type.");
                        return;
                }
            }
            
            again = promptChoice("You got " + score + " out of " + quiz.questions.length + " questions correct (" + round((double) score / quiz.questions.length * 100, 2) + "%). Do you want to take the quiz again?");
        } while (again);
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    // Customizable GUI functions
    
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    public static File promptFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Quizzes", "quiz");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) return chooser.getSelectedFile();
        else return null;
    }
    
    public static int promptMCQ(String question, String[] possibilities) {
        return JOptionPane.showOpenDialog(null, question, null, JOptionPane.PLAIN_MESSAGE, null, possibilities, null);
    }
    
    public static String promptOpen(String question) {
        return JOptionPane.showInputDialog(question);
    }
    
    public static boolean promptChoice(String question) {
        return JOptionPane.showConfirmDialog(null, question, null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }
}
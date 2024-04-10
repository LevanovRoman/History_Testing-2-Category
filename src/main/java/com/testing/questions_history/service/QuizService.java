package com.testing.questions_history.service;

import com.testing.questions_history.model.Question;
import com.testing.questions_history.model.QuestionWrapper;
import com.testing.questions_history.model.Quiz;
import com.testing.questions_history.repository.QuestionRepository;
import com.testing.questions_history.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;

    private final QuizRepository quizRepository;

    public Quiz createQuiz(String quizTitle, String category) {
        List<Question> questionList =  questionRepository.findRandomQuestionsByCategory(category, 3);
        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setQuestions(questionList);
        quizRepository.save(quiz);

        return quiz;

    }

    public List<QuestionWrapper> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestion_title(), q.getOption1(),
                    q.getOption2(),  q.getOption3(),  q.getOption4());
            questionsForUser.add(qw);
        }

        return questionsForUser;
    }

    public int calculateResult(Integer id, List<String> resultList) {

        Quiz quiz = quizRepository.findById(id).get(); // лучше Optional
        List<Question> questions = quiz.getQuestions();

        int right = 0;
        int i = 0;
        System.out.println("SERVICE");
        for (int j = 0; j < 3; j++) {
            System.out.println(resultList.get(i));
            System.out.println(questions.get(i).getRight_answer());
            if (resultList.get(i).equals(questions.get(i).getRight_answer()))
                right++;
            i++;
        }
        return right;
    }

}

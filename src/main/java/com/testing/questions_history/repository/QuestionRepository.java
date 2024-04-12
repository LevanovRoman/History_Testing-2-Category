package com.testing.questions_history.repository;

import com.testing.questions_history.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Long countById(Integer id);

    @Query(value = "SELECT * FROM question q WHERE q.category_id=(SELECT id FROM category c WHERE c.category_title =:category) ORDER BY RANDOM() LIMIT :numQ",
            nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);

    @Query(value = "SELECT * FROM question q WHERE q.category_id=(SELECT id FROM category c WHERE c.category_title =:category) ORDER BY RANDOM()",
            nativeQuery = true)
    List<Question> findQuestionsByCategory(String category);


//    @Query(value = "SELECT q.id as questionid, q.difficulty_level, q.option1, q.option2, q.option3, q.option4, q.question_title, q.right_answer, c.category_title \n" +
//            "FROM question q join category c on q.category_id = c.id where c.id = 1",
//            nativeQuery = true)
//    List<Question> findQuestionsByCategory(int id);

//    @Query(value = "SELECT id, question_title, difficulty_level,  category.category_title  FROM question q",
//            nativeQuery = true)
//    List<Question> findAll();
}

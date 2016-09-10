/**
 * Created by chinm_000 on 9/9/2016.
 */
import { Injectable } from '@angular/core';

import { Question } from './question';
import { QUESTIONS } from './mock-questions';

@Injectable()
export class QuestionService {

    private questions : Question[];

    getNumberOfQuestions(): number {
        this.checkNullOrEmptyQuestions();
        return this.questions.length;
    }

    getQuestion(index: number): Question {
        //var randomIndex = Math.floor(Math.random() * (QUESTIONS.length - 1));
        //console.log('randomIndex:' + randomIndex);
        this.checkNullOrEmptyQuestions();
        return QUESTIONS[index];
    }

    resetQuestionsForCategory(categoryId: number): void {
        Promise.resolve(QUESTIONS).then(questions => this.questions = questions);
    }

    private checkNullOrEmptyQuestions(): void {
        if (this.questions === null || this.questions.length === 0) {
            throw new Error("No Questions Found");
        }
    }

}
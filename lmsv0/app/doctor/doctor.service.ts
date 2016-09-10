/**
 * Created by chinm_000 on 9/9/2016.
 */
import { Injectable } from '@angular/core';

import { Question } from './question';
import { QUESTIONS } from './mock-questions';

@Injectable()
export class QuestionService {
    getNextQuestion(): Promise<Question> {
        var randomIndex = Math.floor(Math.random() * (QUESTIONS.length - 1));
        console.log('randomIndex:' + randomIndex);
        return Promise.resolve(QUESTIONS[randomIndex]);
    }
}
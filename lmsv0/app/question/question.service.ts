/**
 * Created by chinm_000 on 9/9/2016.
 */
import {Injectable, Output, EventEmitter} from '@angular/core';

import { Question } from './question';
import { QUESTIONS } from './mock-questions';

@Injectable()
export class QuestionService {
    @Output() questionsAnsweredEvent = new EventEmitter();
    private questions : Question[];

    resetQuestionsForCategory(categoryId: string): Promise<Question[]> {
        return Promise.resolve(QUESTIONS.filter(function(element: any, index: number, array : any[]) {
            let question: Question = <Question>element;
            return question.categoryId === categoryId;
        }));
    }

    questionsAnswered() {
        this.questionsAnsweredEvent.emit({value: true});
    }
}
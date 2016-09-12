/**
 * Created by chinm_000 on 9/9/2016.
 */
import { Component, Input, OnInit } from '@angular/core';
import { QuestionService } from "./question.service";
import { Question } from "./question";

import {ActivatedRoute, Params} from "@angular/router";

@Component({
    selector: "question-list",
    template: `
        <div *ngIf="questions != null">
            <div *ngFor="let question of questions; let i = index;">
                <question [question]="question" 
                    *ngIf="i === questionIndex" (answerProvided)="nextQuestion($event)"></question>            
            </div> 
        </div>
    `
})

export class QuestionListComponent implements OnInit {

    questions: Question[];
    questionIndex: number = 0;
    debug: boolean = true;

    constructor(private route: ActivatedRoute, private questionService: QuestionService) {}


    nextQuestion(event: any) : void {
        this.questionIndex++;
        if (this.questions.length == this.questionIndex) {
            this.questionService.questionsAnswered();
        }
    }

    ngOnInit() : void {
        this.route.params.forEach((params: Params) => {
            console.log("In route for each in question list");
            let categoryId = params['id'];
            this.resetQuestionsForCategory(categoryId);
            console.log("Question Retrieved: " + JSON.stringify(this.questions))
        });
    }

    private resetQuestionsForCategory(categoryId: string) : void {
        this.questions = null;
        this.questionService.resetQuestionsForCategory(categoryId)
            .then(questions => this.questions = questions);
    }
}

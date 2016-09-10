/**
 * Created by chinm_000 on 9/9/2016.
 */
import { Component, Input, OnInit } from '@angular/core';
import { QuestionService } from "./question.service";
import { Question } from "./question";
import { DQuestion } from "./dquestion.component";

import { ActivatedRoute, Params } from "@angular/router";

@Component({
    selector: "question-list",
    template: `
        <question-list *ngIf="questions != null">
            <div *ngFor="let question of questions">
                <question *ngIf="index === questionIndex" (answerProvided)="nextQuestion($event)"/>            
            </div> 
        </question-list>
    `
})

export class QuestionList implements OnInit {
    @Input()
    questions: Question[];

    questionIndex: number = 0;

    debug: boolean = true;

    nextQuestion(event) : void {
        if (this.questions.length == this.questionIndex) {
            // route to thank you component.
        } else {
            this.questionIndex++;
        }
    }
    ngOnInit() : void {

    }
}

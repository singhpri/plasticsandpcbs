/**
 * Created by chinm_000 on 9/9/2016.
 */
import { Component, Input, OnInit } from '@angular/core';
import { QuestionService } from "./question.service";
import { Question } from "./question";

@Component({
    selector: "question",
    template: `
        <div *ngIf="question != null">
            <h1>{{ question.title }}</h1>
                <li *ngFor="let item of question.answers">
                    <input [(ngModel)]="answer" name="{{'question:' + question.id}}" value="{{item}}" type="radio">{{item}}                    
                </li>
                <span *ngIf="debug">{{ answer }}</span>
        </div>        
    `
})

export class DQuestion implements OnInit {
    //@Input()
    //questionId: string;
    question: Question;
    answer: string;
    debug: boolean = true;

    constructor(private questionService: QuestionService) {
        this.questionService = questionService;
    }

    ngOnInit() : void {
        this.questionService.getNextQuestion()
            .then(result => {
                this.question = result;
                console.log("Question Retrieved: " + JSON.stringify(this.question))
            });
    }
}

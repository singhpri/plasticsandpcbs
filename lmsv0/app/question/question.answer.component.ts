/**
 * Created by chinm_000 on 9/9/2016.
 */
import { Component, Input, OnInit, EventEmitter, Output} from '@angular/core';
import { Question } from "./question";

@Component({
    selector: "question",
    template: `
        <div *ngIf="question != null">
            <h1>{{ question.title }}</h1>
                <li *ngFor="let item of question.answers">
                    <input [(ngModel)]="answer" name="{{'question:' + question.id}}" (select)="answerSelected($event)" value="{{item}}" type="radio">{{item}}                    
                </li>
                <span *ngIf="debug">{{ answer }}</span>
        </div>        
    `
})

export class QuestionComponent {

    @Input()
    question: Question;
    answer: string;
    debug: boolean = true;

    @Output
    answerProvided = new EventEmitter();

    answerSelected(event) : void {
        this.answerProvided.emit({value: event.value});
    }
}

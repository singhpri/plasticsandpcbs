import {Component, OnInit, AfterContentInit} from '@angular/core';

import { Hero } from '../hero';
import { HeroService } from '../hero.service';
import {Router} from "@angular/router";

@Component({
  selector: 'thankyou',
  template: '<h1>Thank you!</h1>',
})

export class ThankYouComponent implements OnInit, AfterContentInit {

    constructor(private router: Router) {};

    ngOnInit(): void {
    }

    ngAfterContentInit(): void {
        //setTimeout(() => {this.router.navigate([''])}, 6000);
    }
}
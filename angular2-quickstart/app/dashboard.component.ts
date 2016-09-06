import { Component, OnInit } from '@angular/core';
import { Hero } from './hero';
import { HeroService } from './hero.service';
import {Router} from "@angular/router";

@Component({
    selector : "dashboard",
    templateUrl : "app/dashboard.component.html"
})

export class DashboardComponent implements OnInit {
    
    heroes : Hero[] = [];
    
    constructor(
        private heroService: HeroService,
        private router: Router) { } // Empty constructor for Injecting heroService.
    
    ngOnInit(): void {
        this.heroService.getHeroes()
            .then(heroes => this.heroes = heroes.slice(1, 5));
    }
    
    gotoDetail(hero : Hero) : void {
        let link = ['/detail', hero.id]; // for readability
        this.router.navigate(link);
    }
}
import { Component } from '@angular/core';

export class Person {
    name: string;
    mobile: number;
}

@Component({
    selector: 'my-app',
    template:

    '<div>' +
    '<label> Your name: </label>' +
    '<div><input [(ngModel)] ="person.name" placeholder="name"></div>' +
    '</div>'+
        '<div>' +
    '<label> Mobile Phone: </label>' +
        '<div><input [(ngModel)] ="person.mobile" placeholder="mobile"></div>'+
    '</div>',

    styles: [`
    label {
      text-align: center;
      color:#A9A9A9;
      font-size: 40px;
      font-family: "arial", sans-serif;
    }
    input {
    width: 400px;
    height: 50px;
    padding: 12px, 20px;
    margin: 8px 0;
    box-sizing: border-box;
    border: 1px solid gray;
    background-color: light gray;
    
    }
    `]

})


export class AppComponent {
    name = 'Your Name';
    mobile = 'Mobile Phone';
    person: Person = {
        name: '',
        mobile: 0
    };

}


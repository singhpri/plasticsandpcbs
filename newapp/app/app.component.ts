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
    '<input value="{{person.name}}" placeholder="name">' +
    '</div>'+
        '<div>' +
    '<label> Mobile Phone: </label>' +
        '<input value="{{person.mobile}}" placeholder="mobile">' +
    '</div>'

})

export class AppComponent {
    name = 'Your Name';
    mobile = 'Mobile Phone';
    person: Person = {
        name: 'Your Name',
        mobile: 0
    };

}


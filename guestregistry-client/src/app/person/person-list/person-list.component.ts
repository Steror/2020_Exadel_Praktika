import { Component, OnInit } from '@angular/core';
import { PersonService } from '../person.service';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

  persons: Array<any>;

  constructor(private personService: PersonService) { }

  ngOnInit() {
    this.loadLocations();
  }

  remove(id) {
    this.personService.remove(id).subscribe(result => {
      this.loadLocations();
    });
  }

  loadLocations() {
    this.personService.getAll().subscribe(data => {
      console.log(data);
      this.persons = data;
    });
  }
}

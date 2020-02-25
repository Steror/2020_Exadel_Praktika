import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../event.service';
import { LocationService } from '../../location/location.service';
import { NgForm } from '@angular/forms';
import { PersonService } from '../../person/person.service';

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrls: ['./event-edit.component.css']
})
export class EventEditComponent implements OnInit {
  event: any = {};
  sub: Subscription;
  locations: Array<any>;
  persons: Array<any>;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private eventService: EventService,
              private locationService: LocationService,
              private personService: PersonService ) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.eventService.get(id).subscribe((event: any) => {
          if (event) {
            this.event = event;
          }
        });
      }
      this.locationService.getAll().subscribe((locations: Array<any>) => {
        if (locations) {
          this.locations = locations;
        }
      });
      this.personService.getAll().subscribe((persons: Array<any>) => {
        if (persons) {
          this.persons = persons;
          // this.removeDuplicatePersons();
        }
      });
    });
  }

  save(form: NgForm) {
    this.eventService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  gotoList() {
    this.router.navigate(['/event-list']);
  }

  // removeDuplicatePersons() {  // Removes all persons that match attendees assigned to event
  //   if (this.event.attendees === undefined || this.event.attendees.length() === 0) {
  //      this.persons = this.persons.filter( function(person) {
  //        return !this.event.attendees.indexOf(person);
  //      });
  //   }
  // }
  //
  // addPerson(person: any) {
  //   this.event.attendees.push(person);
  // }
}

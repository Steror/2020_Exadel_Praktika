import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../event.service';
import { LocationService } from '../../location/location.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrls: ['./event-edit.component.css']
})
export class EventEditComponent implements OnInit {
  event: any = {};
  sub: Subscription;
  locations: Array<any>;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private eventService: EventService,
              private locationService: LocationService) { }

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
    });
  }

  save(form: NgForm) {
    console.log(form);
    this.eventService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  gotoList() {
    this.router.navigate(['/event-list']);
  }
}

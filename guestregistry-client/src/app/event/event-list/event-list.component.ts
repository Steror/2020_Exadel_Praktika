import { Component, OnInit } from '@angular/core';
import { EventService } from '../event.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {
  events: Array<any>;

  constructor(private eventService: EventService) { }

  ngOnInit() {
    this.loadEvents();
  }

  remove(id) {
    this.eventService.remove(id).subscribe(result => {
      this.loadEvents();
    });
  }

  loadEvents() {
    this.eventService.getAll().subscribe(data => {
      this.events = data;
    });
  }
}

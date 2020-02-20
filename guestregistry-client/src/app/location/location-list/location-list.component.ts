import { Component, OnInit } from '@angular/core';
import { LocationService } from '../location.service';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {
  locations: Array<any>;

  constructor(private locationService: LocationService) { }

  ngOnInit() {
    this.locationService.getAll().subscribe(data => {
      this.locations = data;
    });
  }

  remove(id) {
    this.locationService.remove(id).subscribe();
  }
}
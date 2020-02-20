import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationService } from '../location.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-location-edit',
  templateUrl: './location-edit.component.html',
  styleUrls: ['./location-edit.component.css']
})
export class LocationEditComponent implements OnInit {
  location: any = {};
  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private locationService: LocationService) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.locationService.get(id).subscribe((location: any) => {
          if (location) {
            this.location = location;
          }
        });
      }
    });
  }

  save(form: NgForm) {
    this.locationService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  gotoList() {
    this.router.navigate(['/location-list']);
  }
}

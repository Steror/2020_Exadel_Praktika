import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NgForm } from '@angular/forms';
import { PersonService } from '../person.service';

@Component({
  selector: 'app-person-edit',
  templateUrl: './person-edit.component.html',
  styleUrls: ['./person-edit.component.css']
})

export class PersonEditComponent implements OnInit {

  person: any = {};
  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private personService: PersonService) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.personService.get(id).subscribe((location: any) => {
          if (location) {
            this.person = location;
          }
        });
      }
    });
  }

  save(form: NgForm) {
    this.personService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  gotoList() {
    this.router.navigate(['/person-list']);
  }
}

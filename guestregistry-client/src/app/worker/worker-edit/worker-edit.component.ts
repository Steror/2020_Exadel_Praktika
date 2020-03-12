import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NgForm } from '@angular/forms';
import { WorkerService } from '../worker.service';
import {CardService} from "../../card/card.service";
import {Card} from "../../card/card";

@Component({
  selector: 'app-worker-edit',
  templateUrl: './worker-edit.component.html',
  styleUrls: ['./worker-edit.component.css']
})
export class WorkerEditComponent implements OnInit {

  worker: any = {};
  cards: Array<any>;
  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private workerService: WorkerService,
              private cardService: CardService) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let response = this.cardService.getCards();
      response.subscribe((data:Array<any>) => this.cards = data);

      const id = params.id;
      if (id) {
        this.workerService.get(id).subscribe((worker: any) => {
          if (worker) {
            this.worker = worker;
          }
        });
      }
    });
  }

  save(form: NgForm) {
    this.workerService.save(form).subscribe(result => {
      // this.gotoList();
    }, error => console.error(error));
  }

  public updateWorker() {
    this.workerService.save(this.worker).subscribe(); //() =>
      // this.gotoList()
    // );
  }


  gotoList() {
    this.router.navigate(['/worker-list']);
  }
}

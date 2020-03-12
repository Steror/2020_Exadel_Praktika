import { Component, OnInit } from '@angular/core';
import { WorkerService } from '../worker.service';

@Component({
  selector: 'app-worker-list',
  templateUrl: './worker-list.component.html',
  styleUrls: ['./worker-list.component.css']
})
export class WorkerListComponent implements OnInit {
  workers: Array<any>;

  constructor(private workerService: WorkerService) { }

  ngOnInit() {
    this.loadLocations();
  }

  remove(id) {
    this.workerService.remove(id).subscribe(result => {
      this.loadLocations();
    });
  }

  loadLocations() {
    this.workerService.getAll().subscribe(data => {
      this.workers = data;
    });
  }
}

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
    this.loadWorkers();
  }

  remove(id) {
    this.workerService.remove(id).subscribe(result => {
      this.loadWorkers();
    });
  }

  loadWorkers() {
    this.workerService.getAll().subscribe(data => {
      this.workers = data;
    });
  }
}

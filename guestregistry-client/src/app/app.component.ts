import { Component } from '@angular/core';
import { AppService } from './app.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'guestregistry-client';
  constructor(public app: AppService, private http: HttpClient, private router: Router) {
    // this.app.authenticate(undefined, undefined);
  }
  logout() {
      sessionStorage.removeItem('token');
      this.router.navigateByUrl('/login');
      this.app.authenticated = false;
    // this.http.post('logout', {}).pipe( finalize(() => {
    // })).subscribe();
  }
}

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalStorageService } from '../local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private localSS: LocalStorageService,
    private router:Router) { }
  
  canActivate() {
    if (this.localSS.getItem('user')) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
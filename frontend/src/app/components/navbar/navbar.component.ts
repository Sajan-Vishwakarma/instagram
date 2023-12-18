import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../models/User';
import { ImageService } from '../../services/image.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { SharedImageUserService } from '../../services/shared-image-user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  user !: User;
  profileImageURL !: string;
  userDataFetched = false;

  constructor(private localSS: LocalStorageService, private router: Router, 
    private imageService:ImageService, private siuService:SharedImageUserService) {
      this.siuService.userSubject$.subscribe(
        (user) => this.user = user,
        (err) => console.log("navbar error while fetching user")
      )
      this.siuService.profileImageSubject$.subscribe(
        (img_url) => this.profileImageURL = img_url,
        (err) => console.log("navbar error while fetching image")
      )
  }
  
  ngOnInit(): void {
    this.user = this.localSS.getItem('user');
  }

  logout() {
    this.localSS.removeItem('user');
    this.localSS.clear();
    this.router.navigateByUrl('/login');
  }

  fetchProfileImage(){
    return this.localSS.getItem('PROFILE_URL')
  }
}

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ENVIRONMENT } from '../../environments/environment';
import { Login } from '../../models/Login';
import { ImageService } from '../../services/image.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { SharedImageUserService } from '../../services/shared-image-user.service';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private formBuilder: FormBuilder, private usersService: UsersService,
    private router: Router, private localSS: LocalStorageService,
    private imageService: ImageService, private sharedImageUserService:SharedImageUserService) {

    const isLoggedIn = this.localSS.getItem('user');
    if (isLoggedIn)
      this.router.navigate(['/'])
  }


  loginForm!: FormGroup
  errorMessage!: string

  ngOnInit(): void {
    this.localSS.setItem('PROFILE_URL',ENVIRONMENT.DEF_PROFILE_PIC);
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    })
  }

  get username() {
    return this.loginForm.controls['username'];
  }
  get password() {
    return this.loginForm.controls['password'];
  }


  submit() {

    let loginData: Login = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    };

    this.errorMessage = ''

    this.usersService.loginUser(loginData)
      .subscribe(
        (res) => {
          this.localSS.setItem('user', res);
          this.sharedImageUserService.userSubject$.next(res);
          this.sharedImageUserService.profileImageSubject$.next(ENVIRONMENT.DEF_PROFILE_PIC);
          if (this.localSS.getItem('user')) {
            let imgId: number | undefined = res.profileImgId;
            if (imgId != undefined) {
              this.imageService.getImage(imgId)
                .subscribe(
                  (image) => {
                    this.sharedImageUserService.profileImageSubject$.next(image.img_url);
                    this.localSS.setItem('PROFILE_URL',image.img_url);
                  }
                )
            }
            this.router.navigate(['/'])
          }
          else
            this.router.navigate(['login'])
        },
        (err) => {
          this.errorMessage = err.error.errorMessage;
        }
      )
  }
}
